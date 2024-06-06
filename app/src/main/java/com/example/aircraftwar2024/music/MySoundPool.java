package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.aircraftwar2024.R;

import java.util.HashMap;
import java.util.Objects;

public class MySoundPool {
    private SoundPool mysp = null;
    private HashMap<Integer, Integer> soundPoolMap = null;

    public MySoundPool(Context context,boolean hasMusic) {
        if(hasMusic) {
            AudioAttributes audioAttributes = null;
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            mysp = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(audioAttributes)
                    .build();
            soundPoolMap = new HashMap<>();
            soundPoolMap.put(1, mysp.load(context, R.raw.bullet_hit,1));
            soundPoolMap.put(2, mysp.load(context, R.raw.get_supply,1));
            soundPoolMap.put(3, mysp.load(context, R.raw.bomb_explosion,1));
            soundPoolMap.put(4, mysp.load(context, R.raw.game_over,1));
        }
    }

    public void play(String name) {
        int id = (Objects.equals(name, "hit")) ? 1:
                 (Objects.equals(name, "supply")) ? 2:
                 (Objects.equals(name, "bomb")) ? 3:
                 (Objects.equals(name, "over")) ? 4:0;
        if(id != 0 && mysp != null && soundPoolMap != null) {
            mysp.play(soundPoolMap.get(id),1,1,0,0,1);
        }
    }
}
