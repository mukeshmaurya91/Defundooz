package com.mukeshmaurya91.defundooz;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
	private int[] ANIMATION_MAP={3,1,0,2};
	private Bitmap bmp;
	private GamePlay gp;
	private static final int ROWS=4;
	private static final int COLS=9;
	private int max=5;
	private static int life=3;
	private int speedX=5;
	private int speedY=5;
	private int currentFrame=0;
	private static int width;
	private static int height;
	public static int x=0;
	public static int y=0;
	
	public Sprite(GamePlay gp,Bitmap bmp){
		this.gp=gp;
		this.bmp=bmp;
		Sprite.width=bmp.getWidth()/COLS;
		Sprite.height=bmp.getHeight()/ROWS;
		Random rnd= new Random();
		x=rnd.nextInt((int) (40+width))+400;
		y=rnd.nextInt((int) (50+height))+200;
		speedX=rnd.nextInt(max*2)-max;
		speedY=rnd.nextInt(max*2)-max;
	}
private void update(){
	if (x > gp.getWidth()-width-speedX||x+speedX<0) {
        speedX = -speedX;
     }
	x+=speedX;
    if (y <gp.getHeight()-gp.getHeight()/2-height-speedY||y+speedY<0) {
        speedY = -speedY;
      }
 y+=speedY;

 if(x==gp.getHeight()-speedY&&life>0)
 {
	 life--;
 }
 currentFrame = ++currentFrame % COLS;
}
public void doDraw(Canvas c){
	update();
	int srcX = currentFrame * width;
   int srcY = getAnimationRow()* height;
   Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
    Rect dest = new Rect(x, y, x + width, y + height);
	c.drawBitmap(bmp, src, dest, null);
	
}
private int getAnimationRow() {

   double dirDouble = (Math.atan2(speedX, speedY) / (Math.PI / 2) + 2);
    int direction = (int) Math.round(dirDouble) % ROWS;

    return ANIMATION_MAP[direction];

}
public boolean isCollision(float x2, float y2) {

    return x2 > x && x2 < x + width && y2 > y && y2 < y + height;

}

public static int getSpriteWidth(){
	return width;
}
public static int getSpriteHeight(){
	return height;
}

public static int getLife(){
	return life;
}

}
