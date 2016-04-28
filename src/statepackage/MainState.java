package statepackage;

import basepackage.TotalManageView;

import etcpackage.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.example.mirrormirror.R;

public class MainState extends BaseState {
	private Context context;
	private Canvas mCanvas;
	private TracknBall tnb;
	private Bitmap main_back_img;
	private Button sound;

	private Button tutorial;
	private long preparingTime;
	private Bitmap bmCurrentSound;
	private Button ranking;
	
	public MainState(Context context, Handler handler) {
		super(context, handler);
		this.context = context; 
		tnb = new TracknBall(getBitmap(R.drawable.ball1920, true), getBitmap(R.drawable.track1920, true),
				 (int)getScaledX(540), (int)getScaledY(928), screenWidth, screenHeight, context);		//화면 사이즈 필요...!
		
		tnb.setImageOnTheNode(getBitmap(R.drawable.main_start,true), TracknBall.Direction.UP);
		tnb.setImageOnTheNode(getBitmap(R.drawable.main_exit,true), TracknBall.Direction.DOWN);
		tnb.setImageOnTheNode(getBitmap(R.drawable.main_rank,true), TracknBall.Direction.RIGHT);
		tnb.setImageOnTheNode(getBitmap(R.drawable.main_devs,true), TracknBall.Direction.LEFT);
		
		if (dm.getSoundPreferences()) {
			bmCurrentSound = getBitmap(R.drawable.pause_sound_on, false);
			SoundManager.getInstance().playMedia(); // 배경음 재생
		} else {
			bmCurrentSound = getBitmap(R.drawable.pause_sound_off, false);
		}

		sound = new Button((int) getScaledX(915), (int) getScaledY(1786), true,
				bmCurrentSound, bmCurrentSound) {
			public void touchDownBehavior(MotionEvent event) {
				if (dm.getSoundPreferences() == true) {
					sound.setClickedImage(getBitmap(R.drawable.pause_sound_off,
							false));
					sound.setUnClickedImage(getBitmap(
							R.drawable.pause_sound_off, false));
					SoundManager.getInstance().pauseMedia();
				} else {
					sound.setClickedImage(getBitmap(R.drawable.pause_sound_on,
							false));
					sound.setUnClickedImage(getBitmap(
							R.drawable.pause_sound_on, false));
					SoundManager.getInstance().playMedia();
				}
				dm.setSoundPreferences(!dm.getSoundPreferences());
			}
		};
		preparingTime=0; 
		
		tutorial = new Button(  (int)getScaledX(327),  (int)getScaledY(1620), true,
				getBitmap(R.drawable.tutorial,false), getBitmap(R.drawable.tutorial_push,false) ){
				
			public void validClickedBehavior(MotionEvent event){
				
				endState(TotalManageView.MAIN_TO_TUTORIAL_MESSAGE);
					
			}
		};
		
		
		main_back_img = getBitmap(R.drawable.main_back1920, false);
	}
	@Override
	public void draw(Canvas canvas) {
		mCanvas = canvas;
		canvas.drawBitmap(main_back_img, 0, 0,null);
		canvas.drawBitmap(getBitmap(R.drawable.title,false),getScaledX(135),getScaledY(162),null);
		tnb.Draw(canvas);
		sound.draw(canvas);
		tutorial.draw(canvas);
		
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
			endState(TotalManageView.MAIN_TO_LEVEL_CHOOSING_MESSAGE);
			
		}
		//else if(tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.RIGHT){
			//endState(TotalManageView.MAIN_TO_RANK_MESSAGE);
		//}
		else if(tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.DOWN){
			endState(TotalManageView.EXIT);
			android.os.Process.killProcess(android.os.Process.myPid());
		}
		else if(tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.LEFT){
			endState(TotalManageView.MAIN_TO_CREDIT_MESSAGE);
			
		}
		else if(tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.RIGHT){
			endState(TotalManageView.MAIN_TO_RANK_MESSAGE);
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
		
		
		sound.onTouch(e);
		tutorial.onTouch(e);
		return true;
		
	}


}
