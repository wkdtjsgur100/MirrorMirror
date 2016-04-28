package gamepackage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import etcpackage.EffectManager;

public class TimeManager
{
	private long mTime; //millisecond
	private long mRemainTime;
	private long startTime;
	EffectManager emGuage;
	private int mPartialRemainingTime;
	boolean isPartialLinePassed;
	
	public TimeManager(float posX,float posY,Bitmap gaugeBit,long time, int partialRemainingTimeSeconds)
	{
		emGuage = new EffectManager(gaugeBit,posX,posY);
		mTime = time/(long)100;
		mRemainTime = time/(long)100;
		mPartialRemainingTime = partialRemainingTimeSeconds * 10;
		
		if( mPartialRemainingTime >= mRemainTime){
			isPartialLinePassed = true;
		}else{
			isPartialLinePassed = false;
		}
	}
	public void setPartialRemainingTimeSecond(int partialRemainingTimeSeconds){
		mPartialRemainingTime = partialRemainingTimeSeconds*10;
	}
	public boolean isPartialLinePassed(){
		if(isPartialLinePassed==true){
			return true;
		}
		return false;
	}
	
	public void Start()
	{
		startTime = System.currentTimeMillis();
	}
	public void timeInit()
	{ 
		
		mRemainTime = mTime;
		isPartialLinePassed = false;
	}
	public long getRemainTime()
	{
		
		return mRemainTime;
	}
	public void remaintimeDeltaChange(int delta)
	{
		mRemainTime += delta;
		if( mPartialRemainingTime >= mRemainTime){
			isPartialLinePassed = true;
		}else{
			isPartialLinePassed = false;
		}
	}
	public void timeDeltaChange(int delta)
	{
		
		mTime += delta;
	}
	public void clock()
	{
		if(startTime != 0)
		{
			long delta = System.currentTimeMillis() - startTime;
			if(delta >= 100)	
			{
				mRemainTime--;
				
				startTime = System.currentTimeMillis();
			}
			if( mPartialRemainingTime >= mRemainTime){
				isPartialLinePassed = true;
			}else{
				isPartialLinePassed = false;
			}
		}
	}
	public void draw(Canvas canvas)
	{
		Rect r = new Rect();
		int width = emGuage.getBitmapWidth();
		r.left = 0; r.top = 0;
		
		if(mTime != 0)
		{
			if(mRemainTime == 0)
				r.right = 0;
			else
				r.right = (int)(width - (float)width/(mTime)*(mTime-mRemainTime));
		}
		r.bottom = emGuage.getBitmapHeight();
		
		if( isPartialLinePassed()){
			//
		}
		
		
		
		
		
		
		
		
		emGuage.setPartPrint(r);
		float guagePosX = emGuage.getStartPosX()-emGuage.getBitmapWidth()/2;
		
		emGuage.setPos(guagePosX+(r.left+r.right)/2, emGuage.getPosY());
		
		emGuage.draw(canvas);
	}
	public EffectManager getGuageEffectManager() {
		// TODO Auto-generated method stub
		return emGuage;
	}
	public void setTime(int questTime) {
		
		//questTime millisec
		mTime = questTime/100;	//0.1sec unit
		mRemainTime = mTime;	//0.1sec unit
		startTime = System.currentTimeMillis();
	}
}