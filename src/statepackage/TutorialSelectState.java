package statepackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;
import basepackage.TotalManageView;

import com.example.mirrormirror.R;

import etcpackage.Button;

public class TutorialSelectState extends BaseState {

	Bitmap bmBack;
	Button btnO;
	Button btnX;
	public TutorialSelectState(Context context, final Handler handler) {
		super(context, handler);
		bmBack = getBitmap(R.drawable.tutorial_select,false);
		btnO = new Button((int)getScaledX(318),(int)getScaledY(820),true,getBitmap(R.drawable.tuto_o,false),getBitmap(R.drawable.tuto_o_push,false))
		{
			@Override
			public void validClickedBehavior(MotionEvent event) {
				handler.sendEmptyMessage(TotalManageView.MAIN_TO_TUTORIAL_MESSAGE);
			}
		};
		btnX = new Button((int)getScaledX(600),(int)getScaledY(820),true,getBitmap(R.drawable.tuto_x,false),getBitmap(R.drawable.tuto_x_push,false))	
		{
			@Override
			public void validClickedBehavior(MotionEvent event) {
				handler.sendEmptyMessage(TotalManageView.ENDING_TO_MAIN_MESSAGE);
			}
		};
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stu
		canvas.drawBitmap(bmBack,0,0,null);
		btnO.draw(canvas);
		btnX.draw(canvas);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouch(MotionEvent e) {
		// TODO Auto-generated method stub
		btnO.onTouch(e);
		btnX.onTouch(e);
		return true;
	}

}
