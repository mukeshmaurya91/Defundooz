package com.mukeshmaurya91.defundooz;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener {

	ImageView score, sound, credit;
	ImageButton play;
	SoundPool sPool;
	int sid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		 getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		play = (ImageButton) findViewById(R.id.play);
		score = (ImageView) findViewById(R.id.score);
		sound = (ImageView) findViewById(R.id.sound);
		credit = (ImageView) findViewById(R.id.credit);
		play.setOnClickListener(this);
		score.setOnClickListener(this);
		sound.setOnClickListener(this);
		credit.setOnClickListener(this);
		sPool= new SoundPool(5,AudioManager.STREAM_MUSIC,0);
	      sid= sPool.load(this, R.raw.enter, 1);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play:
			sPool.play(sid, 1, 1, 1, 0, 1f);
			stopService(new Intent(getBaseContext(), BackgroundMusic.class));
			startActivity(new Intent(MenuActivity.this,StoryActivity.class));
			break;
		case R.id.score:
			sPool.play(sid, 1, 1, 1, 0, 1f);
			break;
		case R.id.sound:
			sPool.play(sid, 1, 1, 1, 0, 1f);
			if(isMyServiceRunning(BackgroundMusic.class))
			stopService(new Intent(getBaseContext(), BackgroundMusic.class));
			else
			startService(new Intent(this,BackgroundMusic.class));
			break;
		case R.id.credit:
			sPool.play(sid, 1, 1, 1, 0, 1f);
	     final  Dialog ad= new Dialog(MenuActivity.this);
	     ad.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        ad.setContentView(R.layout.custom_dialog);
	        TextView ok=(TextView)ad.findViewById(R.id.TextView4);
	        ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ad.dismiss();
					sPool.play(sid, 1, 1, 1, 0, 1f);
					
				}
			});
	        ad.show();
			break;
		}	
	}

	private boolean isMyServiceRunning(Class<?> serviceClass) {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceClass.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	@Override
	protected void onRestart() {
		if(!isMyServiceRunning(BackgroundMusic.class))
	    {
			startService(new Intent(this, BackgroundMusic.class));
	    }
		super.onRestart();
	}
	@Override
	protected void onStop() {
		if(isMyServiceRunning(BackgroundMusic.class))
	    {
			stopService(new Intent(this, BackgroundMusic.class));
	    }
		super.onStop();
	}
}
