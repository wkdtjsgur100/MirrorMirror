package statepackage;


import etcpackage.DataManager;
import etcpackage.Vertex;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;


public abstract class BaseState {
	
	protected Context mContext;
	protected Handler mHandler;
	private static float BASE_SCREEN_WIDTH= 1080f;
	private static float BASE_SCREEN_HEIGHT= 1920f;
	
	int screenWidth;
	int screenHeight;
	Vertex screenCenter;
	boolean isFirstCollision;
	boolean isStart;
	boolean isTime;
	DataManager dm;
	
	public BaseState(Context context, Handler handler)
	{
		mContext = context;
		mHandler = handler;
		screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
		screenHeight = mContext.getResources().getDisplayMetrics().heightPixels
				-(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, mContext.getResources().getDisplayMetrics());
		screenCenter = new Vertex(screenWidth/2, screenHeight/2);
		dm = DataManager.getInstance();
		dm.setContext(context);
		isStart = true;
		isFirstCollision = true;
	}
	public float getRatioX()
	{
		return screenWidth/BASE_SCREEN_WIDTH;
	}
	public float getRatioY()
	{
		return screenHeight/BASE_SCREEN_HEIGHT;
	}
	public float getScaledX(float x)
	{
		return x*getRatioX();
	}
	public float getScaledY(float y)
	{
		return y*getRatioY();
	}
	
	public Bitmap getBitmap(int id, boolean isSquare)
	{
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inSampleSize = 1;
		option.inScaled = false;
		option.inPurgeable = true;       
		option.inDither = true;
		
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),id,option);
		float pw = getRatioX();
		float ph = getRatioY();;
		int bw = bitmap.getWidth();
		int bh = bitmap.getHeight();
		
		if(isSquare == true){
			if(pw < ph){
				bitmap = Bitmap.createScaledBitmap(bitmap,(int)( bw*pw), (int)(bh*pw),  true);
			}
			else{
				bitmap = Bitmap.createScaledBitmap(bitmap,(int)( bw*ph), (int)(bh*ph),  true);
			}
		}
		else{
			bitmap = Bitmap.createScaledBitmap(bitmap,(int)( bw*pw), (int)(bh*ph),  true);
		}
		return bitmap;
	}

	public abstract void draw(Canvas canvas);
	
	public abstract void update();
	
	public void endState(int currentStateEndMessage)
	{
		if(isFirstCollision)
		{
			mHandler.sendEmptyMessage(currentStateEndMessage);
			isFirstCollision=false;
		}
	}
	public void endState(int currentStateEndMessage, int data) {
		if(isFirstCollision)
		{
			Message msg = new Message();
			msg.what = currentStateEndMessage;
			msg.arg1 = data;
			mHandler.sendMessage(msg);
			isFirstCollision=false;
		}
	}
	public abstract boolean onTouch(MotionEvent e);
	public void setStart(boolean f) {
		isStart = f;
	}
	public void setTime(boolean t) {
		isTime = t;
		
	}
	

}
