package statepackage;

import com.example.mirrormirror.R;

import etcpackage.Button;
import etcpackage.DataManager;
import etcpackage.EffectManager;
import etcpackage.SoundManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import basepackage.TotalManageView;

public class PauseState extends BaseState {

	private Button backBtn;
	private Button homeBtn;
	private Button soundBtn;
	private Bitmap bmCurrentSound;
	private EffectManager emBox;
	
	public PauseState(Context context, Handler handler) {
		super(context, handler);
		backBtn = new Button((int)getScaledX(36), (int)getScaledY(1786) , true,
				getBitmap(R.drawable.pause_resume,false),
						getBitmap(R.drawable.pause_resume,false)   ){
			public void touchDownBehavior(MotionEvent event){
					mHandler.sendEmptyMessage(TotalManageView.PAUSE_TO_GAME_MESSAGE);
			}
		};
		homeBtn = new Button((int) getScaledX(480), (int) getScaledY(1786),
				true, getBitmap(R.drawable.pause_home, false), getBitmap(
						R.drawable.pause_home, false)) {
			public void touchDownBehavior(MotionEvent event) {
				mHandler.sendEmptyMessage(TotalManageView.PAUSE_TO_MAIN_MESSAGE);
			}
		};
	
		if(dm.getSoundPreferences())
		{
			bmCurrentSound = getBitmap(R.drawable.pause_sound_on,false);
		}
		else
		{
			bmCurrentSound = getBitmap(R.drawable.pause_sound_off,false);
		}
		soundBtn = new Button((int) getScaledX(915), (int) getScaledY(1786),
				true,bmCurrentSound, bmCurrentSound) {
			public void touchDownBehavior(MotionEvent event) {
				if(dm.getSoundPreferences() == true){
					soundBtn.setClickedImage(getBitmap(R.drawable.pause_sound_off,false));
					soundBtn.setUnClickedImage(getBitmap(R.drawable.pause_sound_off,false));
				}else{
					soundBtn.setClickedImage(getBitmap(R.drawable.pause_sound_on,false));
					soundBtn.setUnClickedImage(getBitmap(R.drawable.pause_sound_on,false));
				}
				dm.setSoundPreferences(!dm.getSoundPreferences());
			}
		};
		
		emBox = new EffectManager(getBitmap(R.drawable.pause_box,false),screenWidth / 2, getScaledY(347));
		
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(getBitmap(R.drawable.pausescreen,false),0,0,null);
		emBox.draw(canvas);
		backBtn.draw(canvas);
		homeBtn.draw(canvas);
		soundBtn.draw(canvas);
	}

	@Override
	public void update() {
	}

	@Override
	public boolean onTouch(MotionEvent e) {
		backBtn.onTouch(e);
		homeBtn.onTouch(e);
		soundBtn.onTouch(e);
		return false;
	}
}
