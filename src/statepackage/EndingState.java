package statepackage;

import com.example.mirrormirror.R;

import basepackage.TotalManageView;


import etcpackage.Button;
import etcpackage.EffectManager;
import etcpackage.DataManager;
import etcpackage.SoundManager;
import etcpackage.Time;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import gamepackage.*;

public class EndingState extends BaseState {
	private Bitmap bmTrack;
	private Bitmap bmBall;
	private Bitmap bmOrder;
	
	private EffectManager mTop;
	private EffectManager mGameOver;
	private EffectManager mBestScore;
	private Bitmap bmScore;
	private Bitmap bmCombo;
	private Bitmap bmLevel;
	private long startTime;
	private EffectManager emNewRecord;
	
	private boolean start_flag;
	private boolean isBestScore;
	private NumberManager nmBestScore;
	private NumberManager nmScore;
	private NumberManager nmCombo;
	private NumberManager nmLevel;
	Bitmap main_back_bit;
	
	Button homeBtn;
	Button playAgainBtn;
	
	private boolean isPlayAgain;
	private boolean isHome;
	private int mGameType;
	private boolean isVoiceOnce;
	private boolean isNewRecordOnce;
	
	public EndingState(Context context, Handler handler, int gameType) {
		super(context, handler);
		isVoiceOnce = true;
		isNewRecordOnce = true;
		mGameType = gameType;
		dm = DataManager.getInstance();
		bmTrack = getBitmap(R.drawable.track1920,true);
		bmBall = getBitmap(R.drawable.ball1920,true);
		Bitmap bmTop = this.getBitmap(R.drawable.top,false);
		main_back_bit = getBitmap(R.drawable.main_back1920, false);
		
		homeBtn = new Button((int) getScaledX(480), (int) getScaledY(1786),
				true, getBitmap(R.drawable.pause_home, false), getBitmap(
						R.drawable.pause_home, false)) {
			public void touchDownBehavior(MotionEvent event) {
				mTop.setMoveDelta(0, getScaledY(-30),0, -1*(mTop.getBitmapHeight() + getScaledY(100) ));
				isHome = true;
			}

		};
		
		playAgainBtn= new Button((int)getScaledX(36), (int)getScaledY(1786) , true,
				getBitmap(R.drawable.pause_resume,false),
						getBitmap(R.drawable.pause_resume,false)   ){
			public void touchDownBehavior(MotionEvent event){
				mTop.setMoveDelta(0, getScaledY(-30),0, -1*(mTop.getBitmapHeight() + getScaledY(100) ));
				isPlayAgain = true;
			}
		};
		
		mTop = new EffectManager(bmTop, screenWidth/2, 
				getScaledY(180)-bmTop.getHeight()/2);
		
		mTop.setMoveDelta(0, getScaledY(30), 0, mTop.getBitmapHeight()-getScaledY(190));
		
		Bitmap bmGameOver = getBitmap(R.drawable.over_gameover_text,false);
		
		mGameOver = new EffectManager(bmGameOver,
				getScaledX(51)+bmGameOver.getWidth()/2,
				getScaledY(108)+bmGameOver.getHeight()/2);
		
		mGameOver.setAlpha(0);
		mGameOver.setAlphaDelta(10);
		
		Bitmap bmBestScore = getBitmap(R.drawable.over_best,false);
		
		mBestScore = new EffectManager(bmBestScore,
				getScaledX(99)+bmBestScore.getWidth()/2,
				getScaledY(402)+bmBestScore.getHeight()/2);
		
		mBestScore.setAlpha(0);
		mBestScore.setAlphaDelta(10);
		
		bmScore = getBitmap(R.drawable.over_score,false);
		bmCombo = getBitmap(R.drawable.over_combo,false);
		bmLevel = getBitmap(R.drawable.over_level,false);
		
		start_flag = false;
		
		nmBestScore = new NumberManager(getBitmap(R.drawable.best_score_font,false),
					getScaledX(765+25),
					getScaledY(471+43)
				);
		nmBestScore.getEffectManager().setAlpha(0);
		nmBestScore.getEffectManager().setAlphaDelta(10);
		
		Bitmap bit = getBitmap(R.drawable.sco_com_lv_font,false);
		nmScore = new NumberManager(bit,
				getScaledX(925+17),
				getScaledY(715+31)
				);
		nmCombo = new NumberManager(bit,
				getScaledX(925+17),
				getScaledY(865+31)
				);
		nmLevel = new NumberManager(bit,
				getScaledX(925+17),
				getScaledY(1015+31)
				);
		emNewRecord = new EffectManager(getBitmap(R.drawable.over_newrecord_text,false),
				screenWidth/2,getScaledY(1600));
		emNewRecord.setAlpha(0);
		emNewRecord.setAlphaDelta(8);
		
		bmOrder = getBitmap(R.drawable.order_background,false);
		isPlayAgain = false;
		isBestScore = false;
		isHome = false;
		renewRanking();
	}

	private void renewRanking() {
		int score = dm.getRankingPreferences(dm.CURRENT_SCORE);
		if(dm.getRankingBestPreferences(mGameType)<score)
		{
			dm.setRankingBestPreferences(mGameType,score);
			isBestScore = true;
		}
		
	}
	@Override
	public void draw(Canvas canvas) 
	{
		canvas.drawBitmap(main_back_bit, 0, 0, null);
		canvas.drawBitmap(bmTrack,(int)	getScaledX(540)-bmTrack.getWidth()/2
				, (int)getScaledY(928)-bmTrack.getHeight()/2,null);
		canvas.drawBitmap(bmBall,(int)	getScaledX(540)-bmBall.getWidth()/2
				, (int)getScaledY(928)-bmBall.getHeight()/2,null);
		mTop.effecting();
		mTop.draw(canvas);
		if(isPlayAgain&&mTop.isMoveEnd())
		{
			endState(TotalManageView.ENDING_TO_GAME_MESSAGE);
			return;
		}
		if(isHome && mTop.isMoveEnd())
		{
			endState(TotalManageView.ENDING_TO_MAIN_MESSAGE);
			return;
		}
		
		if(!mTop.isMoveEnd()) return;
		
		if(isVoiceOnce)
		{
			SoundManager.getInstance().playSoundPool(SoundManager.GAME_OVER_VOICE_CONST);
			SoundManager.getInstance().stopMedia();
			isVoiceOnce = false;
		}
		
		canvas.drawBitmap(bmOrder,(int)getScaledX(92),(int)getScaledY(1395),null);
		mGameOver.effecting();
		mGameOver.draw(canvas);
		
		playAgainBtn.draw(canvas);
		homeBtn.draw(canvas);
		
		if(!mGameOver.isAlphaEnd()) return;
		
		mBestScore.effecting();
		mBestScore.draw(canvas);
		
		int best_score = dm.getRankingBestPreferences(mGameType);
		
		nmBestScore.getEffectManager().effecting();
		nmBestScore.numberDraw(canvas, best_score);
		
		if(!mBestScore.isAlphaEnd()) return;
		else 
		{
			if(!start_flag)
			{
				startTime = System.currentTimeMillis();
				start_flag = true;
			}
		}
		
		int score = dm.getRankingPreferences(DataManager.CURRENT_SCORE);
		
		if(System.currentTimeMillis() - startTime > 100)
		{
			canvas.drawBitmap(bmScore, (int)getScaledX(79),(int)getScaledY(685),null);
			nmScore.numberDraw(canvas,score);
		}
		if(System.currentTimeMillis() - startTime > 300)
		{
			canvas.drawBitmap(bmCombo, (int)getScaledX(79),(int)getScaledY(835), null);
			nmCombo.numberDraw(canvas, 
					dm.getRankingPreferences(DataManager.COMBO));
			
		}
		if(System.currentTimeMillis() - startTime > 500)
		{
			canvas.drawBitmap(bmLevel, (int)getScaledX(79),(int)getScaledY(985), null);
			nmLevel.numberDraw(canvas, 
					dm.getRankingPreferences(DataManager.LEVEL));
		
		}
		if(System.currentTimeMillis() - startTime > 700)
		{
			if(isBestScore)
			{
				emNewRecord.effecting();
				emNewRecord.draw(canvas);
				if(isNewRecordOnce){
					SoundManager.getInstance().playSoundPool(SoundManager.NEW_RECORD_CONST);
					isNewRecordOnce = false;
				}
			}
		}
	}
	@Override
	public void update()
	{
		
	}

	@Override
	public boolean onTouch(MotionEvent e) {
		
		playAgainBtn.onTouch(e);
		homeBtn.onTouch(e);
		return true;
	}

}