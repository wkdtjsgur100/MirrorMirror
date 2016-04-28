package etcpackage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;
import android.view.MotionEvent;

public class Button {

	private Vertex buttonCenterPosition;

	private int buttonWidth;
	private int buttonHeight;
	private EffectManager clickedEffectManager;
	private EffectManager unClickedEffectManager;
	boolean isClicked = false;
	
	public Button(int centerX, int centerY, boolean isTheXYLeftTop, Bitmap unClickedImg, Bitmap clickedImg) {
		
		buttonWidth = clickedImg.getWidth();
		buttonHeight =unClickedImg.getHeight(); 
		
		if(isTheXYLeftTop==true){
			buttonCenterPosition = new Vertex(centerX + buttonWidth/2, centerY + buttonHeight/2);
			unClickedEffectManager = new EffectManager(unClickedImg, centerX+ buttonWidth/2, centerY+ buttonHeight/2);
			clickedEffectManager = new EffectManager(clickedImg, centerX+ buttonWidth/2, centerY+ buttonHeight/2);
		}
		else{
			buttonCenterPosition = new Vertex(centerX, centerY);
			unClickedEffectManager = new EffectManager(unClickedImg, centerX, centerY);
			clickedEffectManager = new EffectManager(clickedImg, centerX, centerY);
		}
		;
	}

	public void setUnClickedImage(Bitmap newBitmapImg) {
		
		unClickedEffectManager = new EffectManager(newBitmapImg,
				buttonCenterPosition.getX(),
				buttonCenterPosition.getY());
		buttonWidth = unClickedEffectManager.getBitmapWidth();
		buttonHeight = unClickedEffectManager.getBitmapHeight();
	}
	public void setClickedImage(Bitmap newClickedBitmapImg){
		clickedEffectManager = new EffectManager(newClickedBitmapImg, buttonCenterPosition.getX(),
				buttonCenterPosition.getY());
		
	}

	public void draw(Canvas canvas) {

		if(isClicked == true){
			clickedEffectManager.draw(canvas);
		}
		else
			unClickedEffectManager.draw(canvas);
		
	}


	public boolean isPointInsideOfRect(float eventPositionX, float eventPositionY) {

		if (buttonCenterPosition.getX() - buttonWidth < eventPositionX
				&& eventPositionX < buttonCenterPosition.getX() + buttonWidth
				&& buttonCenterPosition.getY() - buttonHeight < eventPositionY
				&& eventPositionY < buttonCenterPosition.getY() + buttonHeight) {

			return true;
		}
		return false;
	}

	public void onTouch(MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if(isPointInsideOfRect(event.getX(),event.getY())==true){
				if(isClicked == false)
					isClicked = true;
				touchDownBehavior(event);
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
		
			if(isClicked == true){
				if(isPointInsideOfRect(event.getX(),event.getY())==false){
					isClicked = false;
				}
				
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if(isClicked == true){
				if(isPointInsideOfRect(event.getX(),event.getY())==true){
					validClickedBehavior(event);
				}
			}
			isClicked = false;
		} 
		
	}

	public void touchDownBehavior(MotionEvent event) {

	}

	

	public void validClickedBehavior(MotionEvent event) {

	}

}
