package com.example.wuht.vioceview;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.vioceView)
    VioceView vioceView;
    @InjectView(R.id.btn_add)
    Button btnAdd;
    @InjectView(R.id.btn_sub)
    Button btnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


    }

    @OnClick({R.id.btn_add, R.id.btn_sub})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                vioceView.addProcess();
                break;
            case R.id.btn_sub:
                vioceView.subProcess();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volume = am.getStreamVolume(AudioManager.STREAM_RING);//最大是15，最小是0
        vioceView.setPcocess(volume);
        Log.d("MainActivity", "volume:" + volume);
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                vioceView.addProcess();
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                vioceView.subProcess();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
