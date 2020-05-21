package com.example.lab13_ggmusic4;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final String DATA_URI =
            "com.glriverside.xgqin.ggmusic.DATA_URI";

    private ContentResolver mContentResolver;
    private ListView mPlaylist;
    private MediaCursorAdapter mCursorAdapter;
    private MediaPlayer mMediaPlayer = null;

    private final String SELECTION =
            MediaStore.Audio.Media.IS_MUSIC + " = ? " + " AND " +
                    MediaStore.Audio.Media.MIME_TYPE + " LIKE ? ";
    private final String[] SELECTION_ARGS = {
            Integer.toString(1),
            "audio/mpeg"
    };


    private BottomNavigationView navigation;
    private TextView tvBottomTitle;
    private TextView tvBottomArtist;
    private ImageView ivAlbumThumbnail;

    private MusicService mService;
    private boolean mBound = false;

    private Boolean mPlayStatus = true;
    private ImageView ivPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentResolver = getContentResolver();
        mCursorAdapter = new MediaCursorAdapter(MainActivity.this);
        mPlaylist = findViewById(R.id.lv_playlist);

        if (ivPlay != null) {
            ivPlay.setOnClickListener(MainActivity.this);
        }


        mPlaylist.setOnItemClickListener( new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                System.out.println("itemClickListener");
                Cursor cursor = mCursorAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(i)) {

                    int titleIndex = cursor.getColumnIndex(
                            MediaStore.Audio.Media.TITLE);
                    int artistIndex = cursor.getColumnIndex(
                            MediaStore.Audio.Media.ARTIST);
                    int albumIdIndex = cursor.getColumnIndex(
                            MediaStore.Audio.Media.ALBUM_ID);
                    int dataIndex = cursor.getColumnIndex(
                            MediaStore.Audio.Media.DATA);

                    String title = cursor.getString(titleIndex);
                    String artist = cursor.getString(artistIndex);
                    Long albumId = cursor.getLong(albumIdIndex);
                    String data = cursor.getString(dataIndex);
                    Uri dataUri = Uri.parse(data);

                    Intent serviceIntent = new Intent(MainActivity.this,
                            MusicService.class);
                    serviceIntent.putExtra(MainActivity.DATA_URI, data);
                    startService(serviceIntent);


                    if (mMediaPlayer != null) {
                        try {
                            mMediaPlayer.reset();
                            mMediaPlayer.setDataSource(
                                    MainActivity.this, dataUri);
                            mMediaPlayer.prepare();
                            mMediaPlayer.start();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    navigation.setVisibility(View.VISIBLE);

                    if (tvBottomTitle != null) {
                        tvBottomTitle.setText(title);
                    }
                    if (tvBottomArtist != null) {
                        tvBottomArtist.setText(artist);
                    }

                    Uri albumUri = ContentUris.withAppendedId(
                            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                            albumId);

                    Cursor albumCursor = mContentResolver.query(
                            albumUri,
                            null,
                            null,
                            null,
                            null);

                    if (albumCursor != null && albumCursor.getCount() > 0) {
                        albumCursor.moveToFirst();
                        int albumArtIndex = albumCursor.getColumnIndex(
                                MediaStore.Audio.Albums.ALBUM_ART);
                        String albumArt = albumCursor.getString(
                                albumArtIndex);
                        Glide.with(MainActivity.this)
                                .load(albumArt)
                                .into(ivAlbumThumbnail);
                        albumCursor.close();
                    }
                }
            }
        });
        mPlaylist.setAdapter(mCursorAdapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE);
                }
            }
        } else {
            initPlaylist();
        }

        navigation = findViewById(R.id.navigation);
        LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.bottom_media_toolbar,
                        navigation,
                        true);

        ivPlay = navigation.findViewById(R.id.iv_play);
        tvBottomTitle = navigation.findViewById(R.id.tv_bottom_title);
        tvBottomArtist = navigation.findViewById(R.id.tv_bottom_artist);
        ivAlbumThumbnail = navigation.findViewById(R.id.iv_thumbnail);

        if (ivPlay != null) {
            ivPlay.setOnClickListener(MainActivity.this);
        }

        navigation.setVisibility(View.GONE);

        musicReceiver = new MusicReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_MUSIC_START);
        intentFilter.addAction(ACTION_MUSIC_STOP);
        registerReceiver(musicReceiver , intentFilter);
//        pbProgress=new ProgressBar(MainActivity.this);
        pbProgress=findViewById(R.id.progress);
        System.out.println("++++++++++++++++="+findViewById(R.id.progress).toString());
        System.out.println("--------:"+pbProgress);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initPlaylist();
                }
                break;
            default:
                break;
        }
    }

    private void initPlaylist() {
        //这里会去查找手机里的音乐文件
        //需要注意的是，我用虚拟机运行，出来的是一片空白，这是正常的，因为虚拟机里没有放 MP3文件
        //试了半天，还以为是哪里出问题了 -_-
        //最后我用手机进行测试，因为手机里存在 MP3 文件，所以出现了列表
        Cursor mCursor = mContentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                SELECTION,
                SELECTION_ARGS,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        );
//        System.out.println("----------initPlaylist："+mCursor);
        mCursorAdapter.swapCursor(mCursor);
        mCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_play) {
            mPlayStatus = !mPlayStatus;
            if (mPlayStatus == true) {
                mService.play();
                ivPlay.setImageResource(
                        R.drawable.ic_pause_circle_outline_black_24dp);
            } else {
                mService.pause();
                ivPlay.setImageResource(
                        R.drawable.ic_play_circle_outline_black_24dp);
            }
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (mMediaPlayer == null) {
//            mMediaPlayer = new MediaPlayer();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        if (mMediaPlayer != null) {
//            mMediaPlayer.stop();
//            mMediaPlayer.release();
//            mMediaPlayer = null;
////            Log.d(MainActivity.TAG, "onStop invoked!");
//            System.out.println("onStop invoked!");
//        }
//        super.onStop();
//    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(MainActivity.this ,
                MusicService.class);
        bindService(intent , mConn , Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        unbindService(mConn);
        mBound = false;

        super.onStop();
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(
                ComponentName componentName , IBinder iBinder) {
            MusicService.MusicServiceBinder binder =
                    (MusicService.MusicServiceBinder) iBinder;

            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(
                ComponentName componentName) {
            mService = null;
            mBound = false;
        }
    };


    public static final int UPDATE_PROGRESS = 1;
    private ProgressBar pbProgress;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    int position = msg.arg1;
                    System.err.println(new Date().toString()+":"+position);
                    pbProgress.setProgress(position);
                    break;
                default:
                    break;
            }
        }
    };


    private class MusicProgressRunnable implements Runnable {
        public MusicProgressRunnable() {
        }

        @Override
        public void run() {
            boolean mThreadWorking = true;
            while (mThreadWorking) {
                try {
                    if (mService != null) {
                        int position =
                                mService.getCurrentPosition();
                        Message message = new Message();
                        message.what = UPDATE_PROGRESS;
                        message.arg1 = position;
                        mHandler.sendMessage(message);
                    }
                    mThreadWorking = mService.isPlaying();
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }



    public static final String ACTION_MUSIC_START =
            "com.glriverside.xgqin.ggmusic.ACTION_MUSIC_START";
    public static final String ACTION_MUSIC_STOP =
            "com.glriverside.xgqin.ggmusic.ACTION_MUSIC_STOP";
    private MusicReceiver musicReceiver;


    public class MusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context , Intent intent) {

            if (mService != null) {
                System.out.println("-----------------pbProgress:"+pbProgress);
                pbProgress.setMax(mService.getDuration());
                new Thread(new MusicProgressRunnable()).start();
            }
        }
    }

    @Override
    protected void onDestroy() {

        unregisterReceiver(musicReceiver);
        super.onDestroy();
    }


}
