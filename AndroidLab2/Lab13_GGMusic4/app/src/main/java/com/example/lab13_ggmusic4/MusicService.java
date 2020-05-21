package com.example.lab13_ggmusic4;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {

    MediaPlayer mMediaPlayer;
    private final IBinder mBinder = new MusicServiceBinder();

    public MusicService() {
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags, int startId) {
        String data = intent.getStringExtra(
                MainActivity.DATA_URI);
        Uri dataUri = Uri.parse(data);

        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(
                        getApplicationContext(),
                        dataUri);
                mMediaPlayer.prepare();
                mMediaPlayer.start();


                Intent musicStartIntent =
                        new Intent(MainActivity.ACTION_MUSIC_START);
                sendBroadcast(musicStartIntent);


            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }



    public class MusicServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    /** method for clients */
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    public void play() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    public int getDuration() {
        int duration = 0;

        if (mMediaPlayer != null) {
            duration = mMediaPlayer.getDuration();
        }

        return duration;
    }
    public int getCurrentPosition() {
        int position = 0;

        if (mMediaPlayer != null) {
            position = mMediaPlayer.getCurrentPosition();
        }

        return position;
    }

    public boolean isPlaying() {

        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }
}


