package gamepackage;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import etcpackage.EffectManager;
import etcpackage.DataManager;
import gamepackage.NumberManager;


class Combo
{
	private NumberManager comboNumberImg;
	private EffectManager comboImg;
	private int mCombo;
	
	public Combo(Bitmap numBit,Bitmap comboBit,int combo,float posX,float posY)
	{
		int width = comboBit.getWidth()/2 + (numBit.getWidth()/2/10);
		comboImg = new EffectManager(comboBit,posX,posY);
		comboNumberImg = new NumberManager(numBit,posX-width,posY);	
		
		mCombo = combo;
		
		comboImg.setAlphaDelta(-5);
		comboNumberImg.getEffectManager().setAlphaDelta(-5);
		comboImg.setMoveDelta(0, -1, 0, -90);
		comboNumberImg.getEffectManager().setMoveDelta(0,-1, 0, -90);	
	}
	public void Draw(Canvas canvas)
	{
		comboNumberImg.getEffectManager().effecting();
		comboNumberImg.numberDraw(canvas, mCombo);
		
		comboImg.effecting();
		comboImg.draw(canvas);
	}
}
public class ComboManager
{
	private final static int COMBO_SHOW_THRE = 2;
	private LinkedList<Combo> mCombos = new LinkedList<Combo>();
	private int currentCombo;
	private int bestCombo;
	Bitmap numBit;
	Bitmap comboBit;
	
	public ComboManager(Bitmap numBit,Bitmap comboBit)
	{
		this.numBit = numBit;
		this.comboBit = comboBit;
		currentCombo = 0;
		bestCombo = 0;
		DataManager.getInstance().setRankingPreferences(DataManager.COMBO, 0);
	}
	public void InitCombo()
	{
		currentCombo = 0;
	}
	public int getCurrentCombo()
	{
		return currentCombo;
	}
	public void ComboOccur(float posX,float posY)
	{
		DataManager rm = DataManager.getInstance();
		currentCombo++;
		if(currentCombo > bestCombo)
		{
			rm.setRankingPreferences(DataManager.COMBO, currentCombo);
			bestCombo = currentCombo;
		}
		
		if(currentCombo >= COMBO_SHOW_THRE)
			mCombos.add(new Combo(numBit,comboBit,currentCombo,posX,posY));
		if(mCombos.size() > 5)
			mCombos.poll();
	}
	public void drawCombo(Canvas canvas)
	{
		try{
			LinkedList<Combo> temp = (LinkedList<Combo>) mCombos.clone();
			Iterator<Combo> it = temp.iterator();
			
			while(it.hasNext())
				it.next().Draw(canvas);
		}
		catch(ConcurrentModificationException e)
		{
			
		}
	}
}







