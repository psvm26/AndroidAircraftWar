package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aircraftwar2024.R;

public class MainActivity extends AppCompatActivity {

    boolean hasMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.addActivity(this);

        Button startButton = (Button) this.findViewById(R.id.start_button);

        RadioGroup musicControl = (RadioGroup) this.findViewById(R.id.music_control);
        RadioButton hasMusicButton = (RadioButton) this.findViewById(R.id.music_on);
        RadioButton hasNotMusicButton = (RadioButton) this.findViewById(R.id.music_off);

        hasNotMusicButton.setChecked(true);
        hasMusicButton.setOnCheckedChangeListener((buttonView, isChecked) -> this.hasMusic = isChecked);

        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
            intent.putExtra("hasMusic", MainActivity.this.hasMusic);
            Log.d("RadioGroupSelection", "Selected RadioButton: " + MainActivity.this.hasMusic);
            startActivity(intent);
    });
    }

}