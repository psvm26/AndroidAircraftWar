package com.example.aircraftwar2024;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OfflineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        TextView textView = (TextView) this.findViewById(R.id.textView);
        boolean hasMusic = getIntent().getBooleanExtra("hasMusic", false);
        if(hasMusic){
            textView.setText("有音乐");
        } else {
            textView.setText("无音乐");
        }
    }
}