package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer {
    private MediaPlayer bgm;
    private final Boolean isLoop;
    private final boolean hasMusic;

    public MyMediaPlayer(Context context, int resid, boolean isLoop, boolean hasMusic) {
        this.hasMusic = hasMusic;
        if(hasMusic) {
            this.bgm = MediaPlayer.create(context, resid);
        }else {
            this.bgm = null;
        }
        this.isLoop = isLoop;

    }

    public void start() {
        if(hasMusic && bgm != null) {
            bgm.start();
            bgm.setLooping(isLoop);
        }
    }

    public void pause() {
        if(hasMusic && bgm != null) {
            bgm.pause();
        }
    }

    public void restart() {
        if(hasMusic && bgm != null) {
            int position = bgm.getCurrentPosition();
            bgm.seekTo(position);
            bgm.start();
        }
    }

    public void stop() {
        if(hasMusic && bgm != null) {
            bgm.stop();
            bgm.release();
            bgm = null;
        }
    }

    public boolean isPlaying() {
        if(hasMusic && bgm != null) {
            return bgm.isPlaying();
        }else {
            return false;
        }
    }
}
