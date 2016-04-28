package statepackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;
import basepackage.TotalManageView;

import com.example.mirrormirror.R;

import etcpackage.Button;
import etcpackage.EffectManager;
import etcpackage.DataManager;
import gamepackage.NumberManager;

public class RankingState extends BaseState {

	private Bitmap background_bit;
	private EffectManager emTop5;
	private EffectManager emRankingText;
	
	Bitmap main_back_bit;
	NumberManager[] nm;
	private EffectManager emTop;
	private Button homeBtn;
	private boolean isHome;
	private Bitmap bmTrack;
	private Bitmap bmBall;
	
	public RankingState(Context context, Handler handler) {
		super(context, handler);
		Bitmap bmTop = getBitmap(R.drawable.top,false);
		emTop = new EffectManager(bmTop, screenWidth/2, 
				-bmTop.getHeight()/2);		
		
		emTop.setMoveDelta(0, getScaledY(30), 0, emTop.getBitmapHeight());
		Bitmap bmRankText = getBitmap(R.drawable.ranking_text,false);
		
		emRankingText = new EffectManager(bmRankText, 
				getScaledX(237)+bmRankText.getWidth()/2,
				getScaledY(1500)+bmRankText.getHeight()/2);
		
		emRankingText.setAlpha(0);
		emRankingText.setAlphaDelta(15);
		
		isHome = false;
		setInterface();
		setRank();
	}
	private void setInterface() {
		background_bit = getBitmap(R.drawable.main_back1920,false);
		
		bmTrack = getBitmap(R.drawable.track1920,true);
		bmBall = getBitmap(R.drawable.ball1920,true);
		
		Bitmap bmTop4 = getBitmap(R.drawable.top4,false);
		
		emTop5 = new EffectManager(bmTop4,
				getScaledX(73)+bmTop4.getWidth()/2,
				getScaledY(316)+bmTop4.getHeight()/2);
		emTop5.setAlpha(0);
		emTop5.setAlphaDelta(10);
		
		
		main_back_bit = getBitmap(R.drawable.main_back1920,false);
		homeBtn = new Button((int) getScaledX(480), (int) getScaledY(1786),
				true, getBitmap(R.drawable.pause_home, false), getBitmap(
						R.drawable.pause_home, false)) {
			public void touchDownBehavior(MotionEvent event) {
				emTop.setMoveDelta(0, getScaledY(-30),0, -1*(emTop.getBitmapHeight() + getScaledY(100) ));
				isHome = true;
			}

		};
	}

	private void setRank() {
		// TODO Auto-generated method stub
		nm = new NumberManager[5];
		nm[0] = new NumberManager(getBitmap(R.drawable.rank_font,false), getScaledX(945+15), getScaledY(350+26));	
		nm[1] = new NumberManager(getBitmap(R.drawable.rank_font,false), getScaledX(945+15), getScaledY(520+26));	
		nm[2] = new NumberManager(getBitmap(R.drawable.rank_font,false), getScaledX(945+15), getScaledY(690+26));
		nm[3] = new NumberManager(getBitmap(R.drawable.rank_font,false), getScaledX(945+15), getScaledY(860+26));		
	}

	@Override
	public void draw(Canvas canvas) {
		Paint mPaint = new Paint();

		canvas.drawBitmap(background_bit, 0,0, mPaint);
		canvas.drawBitmap(bmTrack,(int)	getScaledX(540)-bmTrack.getWidth()/2
				, (int)getScaledY(928)-bmTrack.getHeight()/2,null);
		canvas.drawBitmap(bmBall,(int)	getScaledX(540)-bmBall.getWidth()/2
				, (int)getScaledY(928)-bmBall.getHeight()/2,null);
		emTop.effecting();
		emTop.draw(canvas);
		if(isHome && emTop.isMoveEnd())
		{
			endState(TotalManageView.ENDING_TO_MAIN_MESSAGE);
			return;
		}
		
		if(!emTop.isMoveEnd()) return;
		
		drawInterface(canvas,mPaint);
		drawRank(canvas,mPaint);
	}
		
	private void drawInterface(Canvas canvas, Paint mPaint) {
		// TODO Auto-generated method stub
		emTop5.effecting();
		emTop5.draw(canvas);
		
		if(!emTop5.isAlphaEnd()) return;
		
		emRankingText.effecting();
		emRankingText.draw(canvas);
		homeBtn.draw(canvas);
		
	}
	private void drawRank(Canvas canvas, Paint mPaint) {
		
		// TODO Auto-generated method stub
		long rank1 = dm.getRankingPreferences(DataManager.EASY_BEST);
		long rank2 = dm.getRankingPreferences(DataManager.NORMAL_BEST);
		long rank3 = dm.getRankingPreferences(DataManager.HARD_BEST);
		long rank4 = dm.getRankingPreferences(DataManager.HELL_BEST);
		nm[0].numberDraw(canvas,(int)rank1);
		nm[1].numberDraw(canvas,(int)rank2);
		nm[2].numberDraw(canvas,(int)rank3);
		nm[3].numberDraw(canvas,(int)rank4);
	}

	@Override
	public void update() {

	}

	@Override
	public boolean onTouch(MotionEvent e) {
		
		homeBtn.onTouch(e);
		return true;
	}
}
