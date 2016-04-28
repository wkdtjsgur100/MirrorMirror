package basepackage;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import statepackage.BaseState;
import statepackage.GameState;

public class PauseThread extends Thread{
	
	private boolean isRun;
	private BaseState currentState;
	private SurfaceHolder mHolder;
	private BaseState gameState;
	
	public PauseThread(SurfaceHolder holder, BaseState state, BaseState gs) {
		this.currentState = state;
		this.mHolder = holder;
		this.gameState = gs;
	}
		
	public void setRun(boolean isRun) {
		this.isRun = isRun;
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
					   gameState.draw(canvas);
			    		currentState.draw(canvas);
			    	}
			   }
			   finally{
			   if(canvas!=null)
				   mHolder.unlockCanvasAndPost(canvas);
			   }
		  }
		}
}
