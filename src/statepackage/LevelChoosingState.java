package statepackage;

import basepackage.TotalManageView;


import com.example.mirrormirror.R;
import etcpackage.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class LevelChoosingState extends BaseState {

	
	
	private Context context;
	private Canvas mCanvas;
	private TracknBall tnb;
	private Bitmap main_back_img;
	private Button sound;
	private boolean isSoundOn;

	private long preparingTime;
	public LevelChoosingState(Context context, Handler handler) {
		super(context, handler);
		this.context = context; 
		tnb = new TracknBall(getBitmap(R.drawable.ball1920, true), getBitmap(R.drawable.track1920, true),
				 (int)getScaledX(540), (int)getScaledY(928), screenWidth, screenHeight, context);		//화면 사이즈 필요...!
		
		tnb.setImageOnTheNode(getBitmap(R.drawable.easy,true), TracknBall.Direction.UP);
		tnb.setImageOnTheNode(getBitmap(R.drawable.normal,true), TracknBall.Direction.DOWN);
		tnb.setImageOnTheNode(getBitmap(R.drawable.hard,true), TracknBall.Direction.RIGHT);
		tnb.setImageOnTheNode(getBitmap(R.drawable.hell,true), TracknBall.Direction.LEFT);
		preparingTime=0; 
		isSoundOn = dm.getSoundPreferences();;
		
		
		
		main_back_img = getBitmap(R.drawable.main_back1920, false);
	}
	@Override
	public void draw(Canvas canvas) {
		mCanvas = canvas;
		canvas.drawBitmap(main_back_img, 0, 0,null);
		tnb.Draw(canvas);
		
		
	}
	@Override
	public void update()
	{
		try {
			tnb.update();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.UP){
			endState(TotalManageView.LEVEL_CHOOSING_TO_GAME_MESSAGE,GameState.EASY);
		}
		else if(tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.DOWN){
			endState(TotalManageView.LEVEL_CHOOSING_TO_GAME_MESSAGE,GameState.NORMAL);
		}
		else if(tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.LEFT){
			endState(TotalManageView.LEVEL_CHOOSING_TO_GAME_MESSAGE,GameState.HELL);
		}
		else if(tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.RIGHT){
			endState(TotalManageView.LEVEL_CHOOSING_TO_GAME_MESSAGE,GameState.HARD);
		}
		else{
		}
	}
	
	@Override
	public boolean onTouch(MotionEvent e) {
		try {
			tnb.onTouch(e, screenWidth,screenHeight);
			
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	
		return true;
		
	}

}
