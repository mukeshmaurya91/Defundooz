package com.mukeshmaurya91.defundooz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ImageView img = (ImageView)findViewById(R.id.my_img);
        startService(new Intent(this,BackgroundMusic.class));
        Animation ani = AnimationUtils.loadAnimation(this, R.anim.image_animation);
        img.startAnimation(ani);
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent in = new Intent(MainActivity.this,MenuActivity.class);
				startActivity(in);
				finish();
			}
		}, 3000);
       
    }

}
