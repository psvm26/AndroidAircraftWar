package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aircraftwar2024.R;

public class OfflineActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean hasMusic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        ActivityManager.addActivity(this);
        Button easyButton = (Button) this.findViewById(R.id.easy_button);
        Button mediumButton = (Button) this.findViewById(R.id.medium_button);
        Button hardButton = (Button) this.findViewById(R.id.hard_button);

        if(getIntent() != null){
            hasMusic = getIntent().getBooleanExtra("hasMusic",false);
        }

        easyButton.setOnClickListener(this);
        mediumButton.setOnClickListener(this);
        hardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int gameType = -1;
        Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
        if(view.getId() == R.id.easy_button) {
            gameType = 1;
        } else if (view.getId() == R.id.medium_button) {
            gameType = 2;
        } else {
            gameType = 3;
        }
        intent.putExtra("gameType",gameType);
        intent.putExtra("hasMusic",hasMusic);
        intent.putExtra("isOnline", false);
        Log.d("gameTypeSelection", "Selected GameType: " + gameType);
        startActivity(intent);
    }
}