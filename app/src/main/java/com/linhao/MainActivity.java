package com.linhao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linhao.view.CustomSoundView;

public class MainActivity extends AppCompatActivity {


    private CustomSoundView sound_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sound_view = (CustomSoundView) findViewById(R.id.sound_view);
        sound_view.setStraightLineDistance(20);
    }
}
