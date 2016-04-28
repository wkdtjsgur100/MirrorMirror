package basepackage;

import statepackage.BaseState;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	private boolean isRun;
	public BaseState currentState;
	private SurfaceHolder mHolder;
	public PauseThread tempThread;
	private boolean isPause;
	
	public GameThread(SurfaceHolder holder, BaseState state) {
		this.currentState = state;
		this.mHolder = holder;
		isPause = false;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}
	public void setState(BaseState state) {
		this.currentState = state;		
	}
	@Override
	public void run() {
	  Canvas canvas=null;
	  while(isRun){
		  currentState.update();
		   canvas=mHolder.lockCanvas();  
		   if(canvas!=null)
		   try{
			   if(!isRun)break;
			   synchronized (mHolder) {
		    		currentState.draw(canvas);
		    	}
		   }
		   finally{
		   if(canvas!=null)
			   mHolder.unlockCanvasAndPost(canvas);
		   if(isPause){
				try {
					tempThread.join();
					tempThread.setRun(true);
					tempThread.run();
					isPause = false;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }
		   }
		  }
	  }
	public void onPause(PauseThread t)
	{
		tempThread = t;
		isPause = true;
	}
	
	public void onResume() {
		// TODO Auto-generated method stub
		currentState.setStart(true);
		tempThread.setRun(false);
	}

	

}
