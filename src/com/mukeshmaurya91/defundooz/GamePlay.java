package com.mukeshmaurya91.defundooz;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePlay extends SurfaceView implements SurfaceHolder.Callback {
     private Context context;
     private SurfaceHolder holder;
     private GameThread gameThread;
     private Bitmap bg,bmp,hit;
     private int screenWidth;
 	private int screenHeight;
     private static long score=0;
     private List<Sprite> sprites = new ArrayList<Sprite>();
     private SoundPool spool;
     private float x,y;
     private int sid,res,death;
     boolean h=false;
	private long lastClick;
	public GamePlay(Context context) {
		super(context);
       this.context=context;
       holder= getHolder();
       holder.addCallback(this);
       gameThread = new GameThread(this);
       addSprite();
      // sp=createSprite(R.drawable.img2);
       bg=BitmapFactory.decodeResource(getResources(), R.drawable.bg);
       hit=BitmapFactory.decodeResource(getResources(), R.drawable.hit);
       spool= new SoundPool(5,AudioManager.STREAM_MUSIC,0);
      sid= spool.load(context, R.raw.slap, 1);
      death= spool.load(context, R.raw.death, 1);
	}
	private void addSprite(){
		sprites.add(createSprite( R.drawable.action));
		sprites.add(createSprite( R.drawable.img1));
		sprites.add(createSprite( R.drawable.img2));
		sprites.add(createSprite( R.drawable.img3));
		sprites.add(createSprite( R.drawable.action));
		sprites.add(createSprite( R.drawable.img1));
		sprites.add(createSprite( R.drawable.img2));
		sprites.add(createSprite( R.drawable.img3));
	}
	private Sprite createSprite(int resource){
		bmp=BitmapFactory.decodeResource(getResources(),resource);
		return new Sprite(this,bmp);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		gameThread.setRunning(true);
		gameThread.start();
		
		
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		this.screenWidth=width;
		this.screenHeight=height;
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry=true;
		gameThread.setRunning(false);
		while(retry)
		{
			try {
				gameThread.join();
				retry=false;
			} catch (InterruptedException e) {		}
		}
		
	}
	 @Override
	public boolean onTouchEvent(MotionEvent event) {
				x=event.getX();
				y=event.getY();
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					if (System.currentTimeMillis() - lastClick > 500) {
	                    lastClick = System.currentTimeMillis();
	                    synchronized (getHolder()) {
	                        for (int i = sprites.size() - 1; i >= 0; i--) {
	                            Sprite sprite = sprites.get(i);
	                            if (sprite.isCollision(event.getX(), event.getY())) {
	                            	 spool.play(sid, PlayActivity.volume, PlayActivity.volume, 1, 0, 1f);
	                            	h=true;
	        					    score++;
	                                 sprites.remove(sprite);
	                                // spool.play(death, PlayActivity.volume, PlayActivity.volume, 1, 0, 1f);
	                                  break;
	                            }

	                        }

	                    }

	             }
					/*if (sp.isCollision(x,y)) {
					    bmp=null;
						getRes();
						sp=createSprite(res);}*/
					//Toast.makeText(context, this.getWidth()/2+"  "+this.getHeight()/2, Toast.LENGTH_SHORT).show();
					break;
				case MotionEvent.ACTION_UP:
					h=false;
					break;
				case MotionEvent.ACTION_MOVE:	
					break;
				
				}
		
		
		return true;
	}
	
	protected void Draw(Canvas canvas) {
		Bitmap b=Bitmap.createScaledBitmap(bg, screenWidth, screenHeight, true);
		try {
			canvas.drawBitmap(b, 0, 0, null);
			Paint p =new Paint();
			p.setColor(Color.RED);
			p.setAntiAlias(true);
			p.setStyle(Style.STROKE);
			p.setStrokeWidth(3);
			p.setTextSize(35);
			canvas.drawText("Score :"+score, 25, 50, p);
			canvas.drawText("Life :"+Sprite.getLife(), canvas.getWidth()-100, 50, p);
			if(h){
				Bitmap hi=Bitmap.createScaledBitmap(hit, Sprite.getSpriteWidth()+5, Sprite.getSpriteHeight()+5, true);
			     canvas.drawBitmap(hi, Sprite.x, Sprite.y, null);
			} for(Sprite sp:sprites){
				sp.doDraw(canvas);
			}
		} catch (Exception e) { }
		
	}

}
