package com.mukeshmaurya91.defundooz;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

public class PlayActivity extends Activity{
     MediaPlayer mp;
    public static float volume;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GamePlay(this));
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
	      float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	      float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	      volume = actualVolume / maxVolume;
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mp= MediaPlayer.create(this, R.raw.jungle);
		mp.setLooping(true);
		mp.start();
	}
	
	@Override
	protected void onStop() {
		mp.stop();
		mp.reset();
		mp.release();
		mp=null;
		super.onStop();
	}
	@Override
	protected void onPause() {
		mp.pause();
		super.onPause();
	}
	@Override
	protected void onResume() {
		mp.start();
		super.onResume();
	}
}
