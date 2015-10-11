package com.mukeshmaurya91.defundooz;

import android.graphics.Canvas;

public class GameThread extends Thread {
	
	private GamePlay gamePlay;
	private boolean running=false;
	static final long FPS = 10;
	
	public GameThread(GamePlay gamePlay){
		this.gamePlay=gamePlay;
		
	}
	
	public void run(){
		 long ticksPS = 1000 / FPS;
         long startTime;
         long sleepTime;
		while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                   c = gamePlay.getHolder().lockCanvas();
                   
                          gamePlay.Draw(c);
                   
            } finally {
                   if (c != null) {
                          gamePlay.getHolder().unlockCanvasAndPost(c);
                   }
                   sleepTime = ticksPS-(System.currentTimeMillis() - startTime);

                   try {  if (sleepTime > 0)
                	   sleep(sleepTime);
                   else
                          sleep(10);

                   } catch (Exception e) {}
            }
        }	
	}

	public void setRunning(boolean b) {
		this.running=b;
	}

}
