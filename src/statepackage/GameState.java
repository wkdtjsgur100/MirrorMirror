package statepackage;

import com.example.mirrormirror.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import basepackage.TotalManageView;

import etcpackage.Button;
import etcpackage.DataManager;
import etcpackage.EffectManager;
import etcpackage.SoundManager;
import etcpackage.TracknBall;
import gamepackage.ComboManager;
import gamepackage.NumberManager;
import gamepackage.QuestManager;
import gamepackage.ScoreManager;
import gamepackage.TimeManager;

public class GameState extends BaseState {

	private static final int CORRECT_EFC_NUM = 3;
	private static final int MAX_LEVEL_NUM = 2;
	private static final int NORM = 0;
	private static final int REV = 1;
	private static final int CLOCKWISE = 2;
	private static final int COUNTER_CLOCKWISE = 3;
	private static final int SKIP = 4;
	private static final int PUSH = 5;
	
	private static final int GET_EFFECT_MAX = 4;
	private static final long PAUSE_CONST = 1800;
	public static final int EASY = 1;
	public static final int NORMAL = 2;
	public static final int HARD = 3;
	public static final int HELL = 4;
	private Boolean isCollision;
	private Bitmap back_bit;

	private TracknBall tnb;
	private QuestManager qm;
	private ScoreManager sm;
	private NumberManager nmScore;
	private ComboManager cm;
	int currentLevel;

	private EffectManager topEfcted;
	private EffectManager emGuageBox;
	private EffectManager emComboLine;

	private TimeManager tm;

	private EffectManager[] emCorrectEfct;
	private Bitmap[] bmCorrect;
	private int randomEffectPick;
	private Bitmap[] bmLevelUp;
	private EffectManager emLevelUp;
	private EffectManager emTimeOver;

	private Bitmap[] currentArrow_bit;
	private Bitmap[][] arrow_bit;
	private EffectManager[] emCurrentArrow;
	private boolean isTimeStart;
	private Vibrator vibrator;
	private Button pauseBtn;
	private Button soundBtn;
	private NumberManager[] nmGetScore;
	private long startTime;
	private Bitmap currentCount;
	private EffectManager emOrderBox;
	private EffectManager emOrder;

	private EffectManager emMiss;
	
	private EffectManager emPraiseClear;
	private EffectManager emPraiseScreen;
	
	private Bitmap bmGood;
	private Bitmap bmPerfect;
	private Bitmap bmExcellent;
	private long wholeCountStartingTime;
	private long eachCountStartingTime;
	private int nthSound;
	private int mGameType;
	private Bitmap bmCurrentSound;
	private boolean isSoundPlayOnce;
	private boolean isFirstMomentOfCountSound;
	private EffectManager emPauseBox;
	private Bitmap[] bmPraise;
	private int praiseCnt;

	public GameState(Context context, Handler handler, int gameType) {
		super(context, handler);
		setStart(true);
		setTime(true);
		isSoundPlayOnce = false;
		isFirstMomentOfCountSound = true;
		nthSound=1;
		praiseCnt = 0;
		mGameType = gameType;
		tnb = new TracknBall(getBitmap(R.drawable.ball1920, true), getBitmap(
				R.drawable.track1920, true), (int) getScaledX(540),
				(int) getScaledY(928), screenWidth, screenHeight, context);
		sm = new ScoreManager();
		qm = new QuestManager(mGameType);
		
		
		currentLevel = 1;
		cm = new ComboManager(getBitmap(R.drawable.combo_num, false),
				getBitmap(R.drawable.combo, false));
		vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		setBitmap();
		isTimeStart = false;
		emComboLine = new EffectManager(getBitmap(R.drawable.comboline,false),screenWidth / 2, getScaledY(347));
		float timeRatioOfMaintainingCombo = 
				(float)qm.getComboScoreRemainingTimeSecond()/((float)qm.getQuestTime()/1000.0f);
		emComboLine.setPos(emGuageBox.getPosX()-emGuageBox.getBitmapWidth()/2+ (timeRatioOfMaintainingCombo)* emGuageBox.getBitmapWidth()  ,
				emGuageBox.getPosY());

		pauseBtn = new Button((int) getScaledX(36), (int) getScaledY(1786),
				true, getBitmap(R.drawable.pause, false), getBitmap(
						R.drawable.pause, false)) {

			public void touchDownBehavior(MotionEvent event) {
				setTime(false);
				mHandler.sendEmptyMessage(TotalManageView.GAME_PAUSE_MESSAGE);
			}
		};
		if(dm.getSoundPreferences())
		{
			bmCurrentSound = getBitmap(R.drawable.pause_sound_on,false);
		}
		else
		{
			bmCurrentSound = getBitmap(R.drawable.pause_sound_off,false);
		}
		soundBtn = new Button((int) getScaledX(915), (int) getScaledY(1786),
				true,bmCurrentSound, bmCurrentSound) {

			public void touchDownBehavior(MotionEvent event) {
				if(dm.getSoundPreferences() == true){
					soundBtn.setClickedImage(getBitmap(R.drawable.pause_sound_off,false));
					soundBtn.setUnClickedImage(getBitmap(R.drawable.pause_sound_off,false));
					SoundManager.getInstance().pauseMedia();
				}else{
					soundBtn.setClickedImage(getBitmap(R.drawable.pause_sound_on,false));
					soundBtn.setUnClickedImage(getBitmap(R.drawable.pause_sound_on,false));
					SoundManager.getInstance().playMedia();
				}
				dm.setSoundPreferences(!dm.getSoundPreferences());
			}
		};
		
		
	}

	public void StageClearEffect() {
		int ran = (int) (Math.random() * MAX_LEVEL_NUM);
		emLevelUp.setEfctedImg(bmLevelUp[ran]);
		emLevelUp.setAlpha(255);
		emLevelUp.setAlphaDelta(-20);
		emLevelUp.SetScale(getScaledX(8), getScaledY(8), getScaledX(80),
				getScaledY(80));
	}

	public void corOuccr(int corIndex) {
		randomEffectPick = (int) (Math.random() * CORRECT_EFC_NUM);
		emCorrectEfct[corIndex].setEfctedImg(bmCorrect[randomEffectPick]);
		emCorrectEfct[corIndex].setAlpha(255);
		emCorrectEfct[corIndex].setAlphaDelta(-15);
		emCorrectEfct[corIndex].SetScale(getScaledX(5), getScaledY(5),
				getScaledX(40), getScaledY(40));
		SoundManager.getInstance().playSoundPool(SoundManager.CORRECT_INDEX_CONST);
	}

	@Override
	public void draw(Canvas canvas) {
		Paint mPaint = new Paint();
		
		if(isStart)emPauseBox.setAlpha(255);
		
		canvas.drawBitmap(back_bit, 0, 0, mPaint);
		emGuageBox.draw(canvas);
		emOrderBox.draw(canvas);
		emOrder.draw(canvas);
		tm.draw(canvas);
		if(!emPauseBox.isVisible())
		for (int i = 0; i < 4; i++)
			emCurrentArrow[i].draw(canvas);
		emPauseBox.draw(canvas);

		for (int i = 0; i < GET_EFFECT_MAX; i++) {
			nmGetScore[i].getEffectManager().effecting();
			nmGetScore[i].numberDraw(canvas, (int) (cm.getCurrentCombo()*(praiseCnt+1)
					+ qm.getClearStage()
					));
		}
		emLevelUp.effecting();
		emLevelUp.draw(canvas);
		
		emPraiseScreen.effecting();
		emPraiseScreen.draw(canvas);
		
		tnb.Draw(canvas);

		topEfcted.effecting();
		topEfcted.draw(canvas);

		for (int i = 0; i < 4; i++) {
			emCorrectEfct[i].effecting();
			emCorrectEfct[i].draw(canvas);
		}

		if (!topEfcted.isMoveEnd())
			return;

		nmScore.getEffectManager().effecting();
		nmScore.numberDraw(canvas, sm.getScore());
		if (!nmScore.getEffectManager().isAlphaEnd())
			return;

		for (int i = 0; i < 4; i++)
			emCurrentArrow[i].effecting();
		if (isTime) {
			tm.clock();
			tm.getGuageEffectManager().effecting();
		}
		emGuageBox.effecting();
		emOrder.effecting();
		emOrderBox.effecting();

		if (!tm.getGuageEffectManager().isAlphaEnd() || !emOrder.isAlphaEnd()
				|| !emOrderBox.isAlphaEnd())
			return;
		
		emPraiseClear.effecting();
		emPraiseClear.draw(canvas);
		cm.drawCombo(canvas);

		if (tm.getRemainTime() <= 0) {
			emTimeOver.effecting();
			emTimeOver.draw(canvas);
		}


		pauseBtn.draw(canvas);
		soundBtn.draw(canvas);
		if (isStart) {
			if (!isTime) {
				pauseThisState(PAUSE_CONST, canvas, mPaint);
			} else {
				if(dm.getSoundPreferences())
				{
					if(DataManager.getInstance().getSoundPreferences())
						SoundManager.getInstance().playMedia();
					bmCurrentSound = getBitmap(R.drawable.pause_sound_on,false);
				}
				else
				{
					bmCurrentSound = getBitmap(R.drawable.pause_sound_off,false);
				}
				soundBtn.setClickedImage(bmCurrentSound);
				soundBtn.setUnClickedImage(bmCurrentSound);
				setTime(false);
				isSoundPlayOnce = false;
				isFirstMomentOfCountSound=true;
				startTime = System.currentTimeMillis();
			}
		}else
		{
			emPauseBox.setAlpha(0);
			emComboLine.draw(canvas);
			emMiss.effecting();
			emMiss.draw(canvas);
		}
	}


	private void pauseThisState(long t, Canvas canvas, Paint mPaint) {
		long ct = System.currentTimeMillis();
		if (ct - startTime >= t) {
			setTime(true);
			setStart(false);
			if (tm.getGuageEffectManager().isAlphaEnd())
				if (!isTimeStart) {
					tm.Start();
					isTimeStart = true;
				}
		} else {
			tnb.Draw(canvas);
			if (ct - startTime <= t / 4) {
				currentCount = getBitmap(R.drawable.resume_count_3, false);
				canvas.drawBitmap(currentCount, getScaledX(507),
						getScaledY(890), mPaint);
			} else if (ct - startTime <= t / 4 * 2) {
				currentCount = getBitmap(R.drawable.resume_count_2, false);
				canvas.drawBitmap(currentCount, getScaledX(507),
						getScaledY(890), mPaint);
			} else if (ct - startTime <= t / 4 * 3) {
				currentCount = getBitmap(R.drawable.resume_count_1, false);
				canvas.drawBitmap(currentCount, getScaledX(507),
						getScaledY(890), mPaint);
			} else {
				canvas.drawBitmap(getBitmap(R.drawable.resume_start, false),
						getScaledX(441), getScaledY(890), mPaint);
			}
			if(!isSoundPlayOnce)
			{
				if(isFirstMomentOfCountSound==true){
					wholeCountStartingTime= System.currentTimeMillis();
					eachCountStartingTime = wholeCountStartingTime;
					isFirstMomentOfCountSound = false;
					SoundManager.getInstance().playSoundPool(SoundManager.COUNT_CONST);
				}
				
				
				if(wholeCountStartingTime + t > System.currentTimeMillis() ){
					playCountSound(t);
				}
				else{
					isSoundPlayOnce = true;
				}
			}
		}
	}

	private void playCountSound(long t) {
		if(eachCountStartingTime + t/4 <=  System.currentTimeMillis()){
			SoundManager.getInstance().playSoundPool(SoundManager.COUNT_CONST);
			eachCountStartingTime = System.currentTimeMillis();
		}
		
	}
	@Override
	public void update() {
		try {
			tnb.update();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (tm.getRemainTime() <= 0) {
			if (emTimeOver.isAlphaEnd())
				endGame();
		} else {
			if (tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.UP) {
				if (isCollision)
					confirmCorrectness(QuestManager.UP);
			} else if (tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.RIGHT) {
				if (isCollision)
					confirmCorrectness(QuestManager.RIGHT);
			} else if (tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.DOWN) {
				if (isCollision)
					confirmCorrectness(QuestManager.DOWN);
			} else if (tnb.leftTopPosition.OverWhichLine() == TracknBall.Direction.LEFT) {
				if (isCollision)
					confirmCorrectness(QuestManager.LEFT);
			} else
				isCollision = true;
		}
		if(tm.getRemainTime()/10 < qm.getComboScoreRemainingTimeSecond())
			cm.InitCombo();
		
	}

	private void confirmCorrectness(int directionIndex) {
		isCollision = false;
		int correctness = qm.isCorrect(directionIndex);
		if (correctness == QuestManager.NODE_CLEAR) {
			nodeClearF(directionIndex);
			currentArrowEffect(qm.getClearNodeNum());
		} else if (correctness == QuestManager.STAGE_CLEAR) {
			stageClearF(directionIndex);
			for (int i = 0; i < 4; i++) {
				emCurrentArrow[i].setAlpha(0);
				emCurrentArrow[i].setAlphaDelta(50);
				emCurrentArrow[i].SetScale(0, 0, 0, 0);
			}
		} else if (correctness == QuestManager.STAGE_FALE) // FAIL? 오타인듯
		{
			vibrator.vibrate(300);
			miss();
			cm.InitCombo();
			tm.remaintimeDeltaChange(-qm.getIncorrectTimeMinusProprotionality());
		}
	}
	private void currentArrowEffect(int i) {
		emCurrentArrow[i - 1].setAlphaDelta(-15);
		emCurrentArrow[i - 1].SetScale(getScaledX(5), getScaledY(5),
				getScaledX(40), getScaledY(40));
	}
	private void endGame() {
		dm.setRankingPreferences(
				dm.CURRENT_SCORE, sm.getScore());
		dm.setRankingPreferences(
				dm.LEVEL, qm.getLevel());
		endState(TotalManageView.GAME_TO_END_MESSAGE);
	}

	private void miss() {
		emMiss.setAlpha(255);
		emMiss.setAlphaDelta(-15);
	}
	private void praiseClear(){
		emPraiseScreen.setAlpha(255);
		emPraiseScreen.setAlphaDelta(-7);
		emPraiseClear.setAlpha(255);
		emPraiseClear.setAlphaDelta(-10);
	}
	private void nodeClearF(int i) {
		cm.ComboOccur(getScaledX(890), getScaledY(640));
		corOuccr(i);
		sm.plusScore(
				(int) (cm.getCurrentCombo()*(praiseCnt+1)
						+ qm.getClearStage()
						)
				);
		getScore(qm.getClearNodeNum() - 1);
	}
	private void stageClearF(int i) {
		cm.ComboOccur(getScaledX(890), getScaledY(640));
		if(tm.getRemainTime()/10 >= qm.getComboScoreRemainingTimeSecond())
		{
			praiseClear();
			emPraiseClear.setEfctedImg(bmPraise[praiseCnt]);
			praiseCnt++;
			if(praiseCnt>2)praiseCnt = 2;
		}
		else
		{
			praiseCnt = 0;
		}
		getScore(3);
		sm.plusScore(
				(int) (cm.getCurrentCombo()*(praiseCnt+1)
						+ qm.getClearStage()
						)
				);
		if (qm.getLevel() != currentLevel) {
			currentLevel = qm.getLevel();
			qm.LevelUp();
			tm.setPartialRemainingTimeSecond(qm
					.getComboScoreRemainingTimeSecond());
		}
		float timeRatioOfMaintainingCombo = 
				(float)qm.getComboScoreRemainingTimeSecond()/((float)qm.getQuestTime()/1000.0f);
		emComboLine.setPos(emGuageBox.getPosX()-emGuageBox.getBitmapWidth()/2+ (timeRatioOfMaintainingCombo)* emGuageBox.getBitmapWidth()  ,
				emGuageBox.getPosY());
		corOuccr(i);
		StageClearEffect();
		tm.setTime(qm.getQuestTime());
		for (int index = 0; index < 4; index++) {
			currentArrow_bit[index] = getArrowBitmap(index);
			emCurrentArrow[index].setEfctedImg(getArrowBitmap(index));
		}
	}
	private void getScore(int index) {
		nmGetScore[index].getEffectManager().moveToStartPos();
		nmGetScore[index].getEffectManager().setAlpha(255);
		nmGetScore[index].getEffectManager().setAlphaDelta(-10);
		nmGetScore[index].getEffectManager().setMoveDelta(0, getScaledY(-5), 0,
				getScaledY(-50));
	}
	private void setBitmap() {
		// TODO Auto-generated method stub
		back_bit = getBitmap(R.drawable.main_back1920, false);
		emPauseBox = new EffectManager(getBitmap(R.drawable.pause_box,false),screenWidth / 2, getScaledY(347));
		arrow_bit = new Bitmap[6][4];
		arrow_bit[NORM][qm.UP] = getBitmap(R.drawable.up, false);
		arrow_bit[NORM][qm.RIGHT] = getBitmap(R.drawable.right, false);
		arrow_bit[NORM][qm.DOWN] = getBitmap(R.drawable.down, false);
		arrow_bit[NORM][qm.LEFT] = getBitmap(R.drawable.left, false);

		arrow_bit[REV][qm.UP] = getBitmap(R.drawable.reverse_up, false);
		arrow_bit[REV][qm.RIGHT] = getBitmap(R.drawable.reverse_right, false);
		arrow_bit[REV][qm.DOWN] = getBitmap(R.drawable.reverse_down, false);
		arrow_bit[REV][qm.LEFT] = getBitmap(R.drawable.reverse_left, false);

		arrow_bit[SKIP][qm.UP] = getBitmap(R.drawable.skip_up, false);
		arrow_bit[SKIP][qm.RIGHT] = getBitmap(R.drawable.skip_right, false);
		arrow_bit[SKIP][qm.DOWN] = getBitmap(R.drawable.skip_down, false);
		arrow_bit[SKIP][qm.LEFT] = getBitmap(R.drawable.skip_left, false);

		arrow_bit[COUNTER_CLOCKWISE][qm.UP] = getBitmap(R.drawable.cclock_up,
				false);
		arrow_bit[COUNTER_CLOCKWISE][qm.RIGHT] = getBitmap(
				R.drawable.cclock_right, false);
		arrow_bit[COUNTER_CLOCKWISE][qm.DOWN] = getBitmap(
				R.drawable.cclock_down, false);
		arrow_bit[COUNTER_CLOCKWISE][qm.LEFT] = getBitmap(
				R.drawable.cclock_left, false);

		arrow_bit[CLOCKWISE][qm.UP] = getBitmap(R.drawable.clock_up, false);
		arrow_bit[CLOCKWISE][qm.RIGHT] = getBitmap(R.drawable.clock_right,
				false);
		arrow_bit[CLOCKWISE][qm.DOWN] = getBitmap(R.drawable.clock_down, false);
		arrow_bit[CLOCKWISE][qm.LEFT] = getBitmap(R.drawable.clock_left, false);

		arrow_bit[PUSH][qm.UP] = getBitmap(R.drawable.push_up, false);
		arrow_bit[PUSH][qm.RIGHT] = getBitmap(R.drawable.push_right, false);
		arrow_bit[PUSH][qm.DOWN] = getBitmap(R.drawable.push_down, false);
		arrow_bit[PUSH][qm.LEFT] = getBitmap(R.drawable.push_left, false);

		currentArrow_bit = new Bitmap[4];

		for (int i = 0; i < 4; i++)
			currentArrow_bit[i] = getArrowBitmap(i);

		emCurrentArrow = new EffectManager[4];

		for (int i = 0; i < 4; i++) {
			Bitmap arrowBit = getArrowBitmap(i);
			float w = arrowBit.getWidth() / 2;
			float h = arrowBit.getHeight() / 2;
			if (w > h) {
				w = h;
				h = arrowBit.getWidth() / 2;
			}
			emCurrentArrow[i] = new EffectManager(arrowBit,
					getScaledX(193 + 200 * i) + w, getScaledY(340));
			emCurrentArrow[i].setAlpha(0);
			emCurrentArrow[i].setAlphaDelta(15);
		}
		Bitmap bmTop = getBitmap(R.drawable.top, false);
		topEfcted = new EffectManager(bmTop, screenWidth / 2,
				-bmTop.getHeight() / 2);
		topEfcted.setMoveDelta(0, getScaledY(3), 0, getScaledY(180));
		Bitmap bmGuage = getBitmap(R.drawable.time_guage, false);

		tm = new TimeManager(screenWidth / 2, getScaledY(347), bmGuage,
				qm.getQuestTime(), 4);

		tm.getGuageEffectManager().setAlpha(0);
		tm.getGuageEffectManager().setAlphaDelta(15);

		Bitmap bmGuageBox = getBitmap(R.drawable.time_gage_box, false);
		emGuageBox = new EffectManager(bmGuageBox, tm.getGuageEffectManager()
				.getStartPosX(), tm.getGuageEffectManager().getStartPosY());

		emGuageBox.setAlpha(0);
		emGuageBox.setAlphaDelta(15);

		Bitmap orderback_bit = getBitmap(R.drawable.order_background, false);
		emOrderBox = new EffectManager(orderback_bit, getScaledX(92 + 448.5f),
				getScaledY(1407 + 164.5f));
		emOrderBox.setAlpha(0);
		emOrderBox.setAlphaDelta(15);

		Bitmap order_bit = getBitmap(R.drawable.order, false);
		emOrder = new EffectManager(order_bit, getScaledX(92 + 448.5f),
				getScaledY(1407 + 164.5f));
		emOrder.setAlpha(0);
		emOrder.setAlphaDelta(15);

		nmScore = new NumberManager(getBitmap(R.drawable.game_lv_scorefont,
				false), getScaledX(1000), getScaledY(30 + 30));

		nmScore.getEffectManager().setAlpha(0);
		nmScore.getEffectManager().setAlphaDelta(10);

		bmCorrect = new Bitmap[CORRECT_EFC_NUM];

		bmCorrect[0] = this.getBitmap(R.drawable.check_1, true);
		bmCorrect[1] = this.getBitmap(R.drawable.check_2, true);
		bmCorrect[2] = this.getBitmap(R.drawable.check_3, true);

		emCorrectEfct = new EffectManager[4];

		for (int i = 0; i < 4; i++) {
			randomEffectPick = (int) (Math.random() * CORRECT_EFC_NUM);
			emCorrectEfct[i] = new EffectManager(bmCorrect[randomEffectPick],
					tnb.getCollisionVertex(i).getX(), tnb.getCollisionVertex(i)
							.getY());
			emCorrectEfct[i].setAlpha(0);
		}
		emTimeOver = new EffectManager(getBitmap(R.drawable.timeover,
				false), emGuageBox.getStartPosX(), emGuageBox.getStartPosY());

		emTimeOver.setAlpha(0);
		emTimeOver.setAlphaDelta(10);

		bmLevelUp = new Bitmap[MAX_LEVEL_NUM];

		bmLevelUp[0] = getBitmap(R.drawable.levelup_1, false);
		bmLevelUp[1] = getBitmap(R.drawable.levelup_2, false);
		int ran = (int) (Math.random() * MAX_LEVEL_NUM);
		emLevelUp = new EffectManager(bmLevelUp[ran],
				tnb.getTNBCenterPosition().getX(), tnb.getTNBCenterPosition()
						.getY());

		emLevelUp.setAlpha(0);
		nmGetScore = new NumberManager[GET_EFFECT_MAX];

		Bitmap bmGetScore = getBitmap(R.drawable.get_score_font, false);

		for (int i = 0; i < GET_EFFECT_MAX; i++) {
			nmGetScore[i] = new NumberManager(bmGetScore,
					getScaledX(210 + 200 * i), getScaledY(300)
							+ getScaledY(bmGetScore.getHeight() / 2));

			nmGetScore[i].getEffectManager().setAlpha(0);
		}
		Bitmap bmMiss = getBitmap(R.drawable.miss_screen, false);

		emMiss = new EffectManager(bmMiss, screenWidth / 2, screenHeight / 2);

		emMiss.setAlpha(0);
		
		Bitmap bmComboScreen = getBitmap(R.drawable.goodscreen_1,false);
		emPraiseScreen = new EffectManager(bmComboScreen,screenWidth/2,screenHeight/2);
		emPraiseScreen.setAlpha(0);
		
		bmGood = getBitmap(R.drawable.goodcombo,false);
		bmExcellent = getBitmap(R.drawable.excellent, false);
		bmPerfect = getBitmap(R.drawable.perfect,false);
		bmPraise = new Bitmap[3];
		bmPraise[0] = bmGood;
		bmPraise[1] = bmExcellent;
		bmPraise[2] = bmPerfect;
		emPraiseClear = new EffectManager(bmGood,screenWidth/2,screenHeight/2-getScaledY(300));
		emPraiseClear.setAlpha(0);
		
		isCollision = true;

	}

	private Bitmap getArrowBitmap(int i) {
		return arrow_bit[qm.getArrowType(i)][qm.getArrowVector(i)];
		
	}

	@Override
	public boolean onTouch(MotionEvent e) {
		if (!isStart) {
			try {
				tnb.onTouch(e, screenWidth, screenHeight);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pauseBtn.onTouch(e);
			soundBtn.onTouch(e);
		}
		return true;
	}

}
