package gamepackage;

import etcpackage.EffectManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;


public class NumberManager
{
	private EffectManager mNumberEM;
	float mPosX;
	private float mPosY;
	private float mstartPosX;
	
	final static int NUMBER_COUNT = 10;
	
	public NumberManager(Bitmap numberImg,float startPosX,float startPosY)
	{
		mPosX = startPosX;
		mPosY = startPosY;
		mstartPosX = startPosX;
	
		mNumberEM = new EffectManager(numberImg,startPosX,startPosY);
	}
	public float getStartPosX()
	{
		return mstartPosX;
	}
	public float getStartPosY()
	{
		return mPosY;
	}
	public void movePos(float deltaX,float deltaY)
	{
		mPosX += deltaX;
		mPosY += deltaY;
		mNumberEM.setPos(mNumberEM.getPosX()+deltaX, 
				mNumberEM.getPosY()+deltaY);
	}
	public void setPos(float posX,float posY)
	{
		mPosX = posX;
		mPosY = posY;
		mNumberEM.setPos(posX, posY);
	}
	public EffectManager getEffectManager()
	{
		return mNumberEM;
	}
	public void numberDraw(Canvas canvas,int showNum)
	{
		float partWidth = (float)mNumberEM.getBitmapWidth()/NUMBER_COUNT;
		
		float tempPosX = mNumberEM.getPosX();
		do
		{
			mNumberEM.setPos(mPosX, mNumberEM.getPosY());
			Rect r = new Rect();
			r.left = (int)((showNum%NUMBER_COUNT)*partWidth);
			r.right = (int)(r.left + partWidth);
			r.top = 0;
			r.bottom = mNumberEM.getBitmapHeight();
			mNumberEM.setPartPrint(r);
			mNumberEM.draw(canvas);
			mPosX -= partWidth;
			showNum /= NUMBER_COUNT;
		}while(showNum != 0);
		
		mPosX = tempPosX;
		mNumberEM.setPos(tempPosX, mNumberEM.getPosY()); 
	}
}