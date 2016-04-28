package statepackage;

import com.example.mirrormirror.R;

import basepackage.TotalManageView;


import etcpackage.Button;
import etcpackage.EffectManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;

public class DeveloperState extends BaseState {

	private Bitmap background_bit;
	private Bitmap bmTrack;
	private Bitmap bmBall;
	private Bitmap bmDev;
	private Bitmap bmDevText;
	private EffectManager emTop;
	private boolean isHome;
	private Bitmap main_back_bit;
	private Button homeBtn;

	public DeveloperState(Context context, Handler handler) {
		super(context, handler);
		// TODO Auto-generated constructor stub
		background_bit = getBitmap(R.drawable.main_back1920,false);
		bmTrack = getBitmap(R.drawable.track1920,true);
		bmBall = getBitmap(R.drawable.ball1920,true);
		bmDev = getBitmap(R.drawable.devscreen,false);
		bmDevText = getBitmap(R.drawable.developer,false);
		
		Bitmap bmTop = getBitmap(R.drawable.top,false);
		emTop = new EffectManager(bmTop, screenWidth/2, 
				-bmTop.getHeight()/2);		
		
		emTop.setMoveDelta(0, getScaledY(30), 0, emTop.getBitmapHeight());
		isHome = false;
		
		main_back_bit = getBitmap(R.drawable.main_back1920,false);
		homeBtn = new Button((int) getScaledX(480), (int) getScaledY(1786),
				true, getBitmap(R.drawable.pause_home, false), getBitmap(
						R.drawable.pause_home, false)) {
			public void touchDownBehavior(MotionEvent event) {
				emTop.setMoveDelta(0, getScaledY(-30),0, -1*(emTop.getBitmapHeight() + getScaledY(100) ));
				isHome = true;
			}

		};
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(background_bit, 0,0,null);
		canvas.drawBitmap(bmTrack,(int)	getScaledX(540)-bmTrack.getWidth()/2
				, (int)getScaledY(928)-bmTrack.getHeight()/2,null);
		canvas.drawBitmap(bmBall,(int)	getScaledX(540)-bmBall.getWidth()/2
				, (int)getScaledY(928)-bmBall.getHeight()/2,null);
		
		emTop.draw(canvas);
		emTop.effecting();
		
		if(emTop.isMoveEnd())
		{
			if(isHome){
			endState(TotalManageView.ENDING_TO_MAIN_MESSAGE);
			return;
			}
		}
		else return;
		canvas.drawBitmap(bmDev,getScaledX(88),getScaledY(100), null);
		canvas.drawBitmap(bmDevText,getScaledX(124),getScaledY(1463), null);
		homeBtn.draw(canvas);
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouch(MotionEvent e) {
		// TODO Auto-generated method stub
		homeBtn.onTouch(e);
		return false;
	}

}
