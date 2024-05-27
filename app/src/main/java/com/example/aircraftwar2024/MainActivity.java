package com.example.aircraftwar2024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    boolean hasMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button) this.findViewById(R.id.start_button);

        RadioGroup musicControl = (RadioGroup) this.findViewById(R.id.music_control);
        RadioButton hasMusicButton = (RadioButton) this.findViewById(R.id.music_on);
        RadioButton hasNotMusicButton = (RadioButton) this.findViewById(R.id.music_off);

        hasNotMusicButton.setChecked(true);
        hasMusicButton.setOnCheckedChangeListener((buttonView, isChecked) -> this.hasMusic = isChecked);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.start_button){
                    Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                    intent.putExtra("hasMusic", MainActivity.this.hasMusic);
                    Log.d("RadioGroupSelection", "Selected RadioButton: " + MainActivity.this.hasMusic);
                    startActivity(intent);
            }
        }
        });
    }

}