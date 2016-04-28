package statepackage;

import com.example.mirrormirror.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import basepackage.TotalManageView;

public class IntroState extends BaseState {

	private final long INTRO_END_TIME_CONST = 1500;
	
	Bitmap logoBitmap;
	long startTime;

	public IntroState(Context context, Handler handler) {
		super(context, handler);		
		startTime = System.currentTimeMillis();
		logoBitmap = getBitmap(R.drawable.intro, false);
		isFirstCollision = true;
	}
	
	@Override
	public void draw(Canvas canvas) {
		Paint mPaint = new Paint();
		canvas.drawBitmap(logoBitmap,0,0,mPaint );
	}
	@Override
	public void update()
	{
		long currentTime = System.currentTimeMillis();
			if(currentTime-startTime>INTRO_END_TIME_CONST)
			{
				endState(TotalManageView.INTRO_TO_MAIN_MESSAGE);
			
			}
	}

	@Override
	public boolean onTouch(MotionEvent e) {
		return true;
	}

}
