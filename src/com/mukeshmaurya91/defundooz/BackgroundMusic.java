package com.mukeshmaurya91.defundooz;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusic extends Service {
	MediaPlayer mp=null;

	@Override
	public void onCreate() {
		mp= MediaPlayer.create(getApplicationContext(), R.raw.bk);
		mp.setLooping(true);
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mp.start();
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
		mp.stop();
		mp.release();
		super.onDestroy();
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
