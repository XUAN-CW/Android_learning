package com.example.lab10_ggmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

        private final int REQUEST_EXTERNAL_STORAGE = 1;
        private static String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE ,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

    private ContentResolver mContentResolver;
    private ListView mPlaylist;
    private MediaCursorAdapter mCursorAdapter;

    private final String SELECTION =
            MediaStore.Audio.Media.IS_MUSIC + " = ? " + " AND " +
                    MediaStore.Audio.Media.MIME_TYPE + " LIKE ? ";
    private final String[] SELECTION_ARGS = {
            Integer.toString(1),
            "audio/mpeg"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentResolver = getContentResolver();
        mCursorAdapter = new MediaCursorAdapter(MainActivity.this);
        mPlaylist=findViewById(R.id.lv_playlist);
        mPlaylist.setAdapter(mCursorAdapter);

        if (ContextCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this ,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PERMISSIONS_STORAGE ,
                            REQUEST_EXTERNAL_STORAGE);
                }
            }
        } else {
            initPlaylist();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode ,
                                           String[] permissions , int[] grantResults) {
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

}

