package etcpackage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;


public class EffectManager
{
	private Bitmap mEfctedImg;
	private Paint mPnt;
	private float mPosX,mPosY;

	private float mHDelta,mWDelta;
	private float mMaxWDelta,mMaxHDelta;
	private float mMoveXDelta,mMoveYDelta;
	private float mMoveXMax,mMoveYMax;
	private int moveXIndex,moveYIndex;
	
	private int wIndex,hIndex;
	private int mAlphaDelta;
	private Rect printPart;
	
	final static float DEFAULT_HSCALE = (float)3.0;
	final static float DEFAULT_WSCALE = (float)3.0;
	public final static int EFFECT_PERIOD = 10;
	
	private boolean moveEnd;
	private boolean scaleEnd;
	private boolean alphaEnd;
	
	private float mStartPosX,mStartPosY;
	private long startTime;
	
	Rect sRect;
	
	public void setAlpha(int alpha)
	{
		mPnt.setAlpha(alpha);
	}
	public void setColor(int color){
		mPnt.setColor(color);
	}
	public float getPosX()
	{
		return mPosX;
	}
	public float getPosY()
	{
		return mPosY;
	}
	public int getBitmapWidth()
	{
		return mEfctedImg.getWidth();
	}
	public int getBitmapHeight()
	{
		return mEfctedImg.getHeight();
	}
	public boolean isMoveEnd() 
	{
		return moveEnd;
	}
	public boolean isScaleEnd()
	{
		return scaleEnd;
	}
	public boolean isAlphaEnd()
	{
		return alphaEnd;
	}
	public void setPos(float posX,float posY)
	{
		mPosX = posX;
		mPosY = posY;
		
		int printWidth = printPart.right - printPart.left;
		int printHeight = printPart.bottom - printPart.top;
		
		sRect = new Rect((int)(mPosX-(printWidth/2+mWDelta*wIndex)),
				(int)(mPosY-(printHeight/2+mHDelta*hIndex)),
				(int)(mPosX+(printWidth/2+mWDelta*wIndex)),
				(int)(mPosY+(printHeight/2+mHDelta*hIndex)));
	}
	public float getStartPosX()
	{
		return mStartPosX;
	}
	public float getStartPosY()
	{
		return mStartPosY;
	}
	public EffectManager(Bitmap efctedImg,float posX,float posY)
	{
		mStartPosX = posX;
		mStartPosY = posY;
		moveEnd = scaleEnd = alphaEnd = true;
		mEfctedImg = efctedImg;
		mPosX = posX;
		mPosY = posY;
		mHDelta = mWDelta = 0;
		mMaxWDelta = mMaxHDelta = 0;
		moveXIndex = moveYIndex = 1;
		wIndex = hIndex = 1;
		mAlphaDelta = 0;
		mPnt = new Paint();
		mPnt.setAlpha(255);
		mMoveXDelta = mMoveYDelta = 0;
		mMoveXMax = mMoveYMax = 0;
		
		printPart = new Rect(0,0,efctedImg.getWidth(),efctedImg.getHeight());
		
		Rect r = printPart;
		
		int printWidth = r.right - r.left;
		int printHeight = r.bottom - r.top;
		
		sRect = new Rect((int)(mPosX-(printWidth/2)),
				(int)(mPosY-(printHeight/2)),
				(int)(mPosX+(printWidth/2)),
				(int)(mPosY+(printHeight/2)));
		
		startTime = System.currentTimeMillis();
	}
	public void SetScale(float hDelta,float wDelta,
			float maxWDelta,float maxHDelta)
	//순서대로 커질 h의 변화율,w의 변화율, 최대의 변화율. -1을 넘기면 default로 할당
	{
		scaleEnd = false;
		wIndex = hIndex = 0;
		if(hDelta == -1) hDelta = DEFAULT_HSCALE;
		if(wDelta == -1) wDelta = DEFAULT_WSCALE;
		
		mHDelta = hDelta;
		mWDelta = wDelta;
		mMaxWDelta = maxWDelta;
		mMaxHDelta = maxHDelta;
	}
	public void setPartPrint(Rect lrtb)
	{
		printPart = lrtb;
		Rect r = printPart;
		
		int printWidth = r.right - r.left;
		int printHeight = r.bottom - r.top;
		
		sRect = new Rect((int)(mPosX-(printWidth/2)),
				(int)(mPosY-(printHeight/2)),
				(int)(mPosX+(printWidth/2)),
				(int)(mPosY+(printHeight/2)));
	}
	public void setAlphaDelta(int alphaDelta)
	{
		alphaEnd = false;
		mAlphaDelta = alphaDelta;
	}
	public void setMoveDelta(float xDelta,float yDelta,float maxXDelta,float maxYDelta)
	{	
		moveEnd = false;
		mMoveXDelta = xDelta;
		mMoveYDelta = yDelta;
		
		mMoveXMax = maxXDelta;
		mMoveYMax = maxYDelta;
		
		moveXIndex = moveYIndex = 1;
	}
	public void moveToStartPos()
	{
		mPosX -= mMoveXMax;
		mPosY -= mMoveYMax;
	}
	public void effecting()
	{
		if(System.currentTimeMillis() - startTime < EFFECT_PERIOD) return;
		else startTime = System.currentTimeMillis();

		Rect r = printPart;
		
		int printWidth = r.right - r.left;
		int printHeight = r.bottom - r.top;
		
		float wDel = mWDelta*wIndex;
		float hDel = mHDelta*hIndex;
		
		float movXDelta = mMoveXDelta*moveXIndex;
		float movYDelta = mMoveYDelta*moveYIndex;
		
		if(Math.abs(movXDelta) <= Math.abs(mMoveXMax))
		{
			mPosX += mMoveXDelta;
			moveXIndex++;
		}
		
		if(Math.abs(movYDelta) <= Math.abs(mMoveYMax))
		{
			mPosY += mMoveYDelta;
			moveYIndex++;
		}
		
		if(Math.abs(movXDelta) >= Math.abs(mMoveXMax) && 
				Math.abs(movYDelta) >= Math.abs(mMoveYMax))
		{
			if(!moveEnd)
			{
				mPosX += Math.abs(mMoveXMax-(movXDelta-mMoveXDelta));
				mPosY += Math.abs(mMoveYMax-(movYDelta-mMoveYDelta));
			}
			moveEnd = true;
		}
		
		if(Math.abs(wDel) < Math.abs(mMaxWDelta))
			wIndex++;
		if(Math.abs(hDel) < Math.abs(mMaxHDelta))
			hIndex++;
		
		if(Math.abs(wDel) >= Math.abs(mMaxWDelta) 
				&& Math.abs(hDel) >= Math.abs(mMaxHDelta))
			scaleEnd = true;
		
		int alpha = mPnt.getAlpha();
		
		if(mPnt.getAlpha()+mAlphaDelta < 255 
				&& mPnt.getAlpha()+mAlphaDelta > 0)
			mPnt.setAlpha(mAlphaDelta+mPnt.getAlpha());
		
		if(mPnt.getAlpha()+mAlphaDelta >= 255)
		{
			mPnt.setAlpha(255);
			alphaEnd = true;
		}
		
		
		if(mPnt.getAlpha()+mAlphaDelta <= 0)
		{
			mPnt.setAlpha(0);
			alphaEnd = true;
		}
		sRect = new Rect((int)(mPosX-(printWidth/2+mWDelta*wIndex)),
				(int)(mPosY-(printHeight/2+mHDelta*hIndex)),
				(int)(mPosX+(printWidth/2+mWDelta*wIndex)),
				(int)(mPosY+(printHeight/2+mHDelta*hIndex)));
		
	}
	public void draw(Canvas canvas)
	{
		if(mPnt.getAlpha() != 0)
		canvas.drawBitmap(mEfctedImg,printPart,sRect,mPnt); 
	}
	public void setEfctedImg(Bitmap bitmap) {
		// TODO Auto-generated method stub
		this.mEfctedImg = bitmap;
		printPart = new Rect(0,0,mEfctedImg.getWidth(),mEfctedImg.getHeight());
	}
	public boolean isVisible() {
		// TODO Auto-generated method stub
		if(mPnt.getAlpha()!=0)return true;
		else return false;
	}
	
}