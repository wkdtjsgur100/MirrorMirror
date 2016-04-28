package basepackage;

import com.example.mirrormirror.R;

import etcpackage.DataManager;
import etcpackage.SoundManager;
import statepackage.BaseState;
import statepackage.DeveloperState;
import statepackage.EndingState;
import statepackage.TutorialSelectState;
import statepackage.GameState;
import statepackage.IntroState;
import statepackage.LevelChoosingState;
import statepackage.MainState;
import statepackage.PauseState;
import statepackage.RankingState;

import statepackage.TutorialState;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class TotalManageView extends SurfaceView implements SurfaceHolder.Callback{
	public static final int INTRO_TO_MAIN_MESSAGE = 3001;
	public static final int MAIN_TO_RANK_MESSAGE = 3011;
	public static final int MAIN_TO_GAME_MESSAGE = 3012;
	public static final int RANK_TO_MAIN_MESSAGE = 3021;
	public static final int TUTORIAL_TO_MAIN_MESSAGE = 2352;
	public static final int ENDING_TO_MAIN_MESSAGE = 3041;
	public static final int ENDING_TO_GAME_MESSAGE = 3051;
	public static final int GAME_TO_END_MESSAGE = 5051;
	public static final int GAME_TO_MAIN_MESSAGE = 2351;
	public static final int GAME_PAUSE_MESSAGE = 5052;
	public static final int PAUSE_TO_GAME_MESSAGE = 6061;
	public static final int PAUSE_TO_MAIN_MESSAGE = 6062;
	public static final int MAIN_TO_LEVEL_CHOOSING_MESSAGE = 1163;
	public static final int LEVEL_CHOOSING_TO_GAME_MESSAGE = 1233;	
	public static final int EXIT = 0101;
	public static final int HOME_BUTTON_CLICKED_MESSAGE = 0102;
	public static final int MAIN_TO_TUTORIAL_MESSAGE = 0103;
	public static final int MAIN_TO_CREDIT_MESSAGE = 0104;
	

	BaseState currentState;
	private BaseState tempState;
	public GameThread mThread;
	public Handler mHandler;
	private SurfaceHolder mHolder;
	private Context mContext;
	private int mGameType;
	private SoundManager sm;

	public TotalManageView(Context context) {
		super(context);
		mGameType = GameState.NORMAL;
		mHolder = getHolder();
		mHolder.addCallback(this);
		mContext = context;
		soundInit();
		mHandler = new Handler()
		{
			private int mGameType;

			@Override
			public void handleMessage(Message msg)
			{
				switch (msg.what) {
				case INTRO_TO_MAIN_MESSAGE:
					if(DataManager.getInstance().getFristPreferences())
					{
						changeState(new TutorialSelectState(mContext, this));
						DataManager.getInstance().setFirstPreferences(false);
					}
					else
						changeState(new MainState(mContext,this));
					break;
				case MAIN_TO_LEVEL_CHOOSING_MESSAGE:
					changeState(new LevelChoosingState(mContext,this));
					break;				
				case LEVEL_CHOOSING_TO_GAME_MESSAGE:
					mGameType = msg.arg1;
					changeState(new GameState(mContext,this,mGameType));
					sm.stopMedia();
					break;
				case TUTORIAL_TO_MAIN_MESSAGE:
					changeState(new MainState(mContext, this));
					break;
				case GAME_TO_END_MESSAGE:
					changeState(new EndingState(mContext,this,mGameType));
					break;
				case MAIN_TO_RANK_MESSAGE:
					changeState(new RankingState(mContext, this));
					break;
				case MAIN_TO_CREDIT_MESSAGE:
					changeState(new DeveloperState(mContext,this));
					break;
				case RANK_TO_MAIN_MESSAGE : 
					changeState(new MainState(mContext,this));
					break;
				case ENDING_TO_MAIN_MESSAGE:
					changeState(new MainState(mContext,this));
					break;
				case ENDING_TO_GAME_MESSAGE:
					changeState(new GameState(mContext,this,mGameType));
					break;
				case GAME_PAUSE_MESSAGE:
					tempState = currentState;
					currentState = new PauseState(mContext,this);
					PauseThread mt = new PauseThread(mHolder,currentState,tempState);
					mThread.onPause(mt);
					sm.pauseMedia();
					
					break;
				case PAUSE_TO_GAME_MESSAGE:
					currentState = tempState;
					currentState.setTime(true);
					mThread.onResume();
					if(DataManager.getInstance().getSoundPreferences())
						sm.playMedia();
					break;
				case PAUSE_TO_MAIN_MESSAGE:
					mThread.onResume();
					changeState(new MainState(mContext,this));
					if(DataManager.getInstance().getSoundPreferences())
						sm.playMedia(); 
					break;
				case HOME_BUTTON_CLICKED_MESSAGE:
					if(currentState.getClass()==GameState.class)
					{
						currentState.setTime(false);
					}
					break;
				case MAIN_TO_TUTORIAL_MESSAGE:
					changeState(new TutorialState(mContext,this));
					break;
				default:
				}
			}
		};
		currentState = new IntroState(mContext,mHandler);
		mThread = new GameThread(getHolder(), currentState);
	}
	private void soundInit() {
		// TODO Auto-generated method stub
		sm = SoundManager.getInstance();
		sm.addSoundPool(sm.CORRECT_INDEX_CONST, R.raw.correct);
		sm.addSoundPool(sm.COUNT_CONST,R.raw.count);
		sm.addSoundPool(sm.GAME_OVER_VOICE_CONST, R.raw.game_over_voice);
		sm.addSoundPool(sm.NEW_RECORD_CONST, R.raw.new_record);
		sm.setMedia(mContext, R.raw.ground_bgm_main);
	}
	public void changeState(BaseState state)
	{
		this.currentState = state;
		mThread.setState(state);
	}
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if(mThread.getState() == Thread.State.TERMINATED)
		{
			if(currentState.getClass() == PauseState.class)
			{
				mThread = new GameThread(getHolder(),tempState);
				mThread.onPause(new PauseThread(mHolder,new PauseState(mContext,mHandler), tempState  ));
			}
			else{
				if(DataManager.getInstance().getSoundPreferences())
					sm.playMedia(); // 배경음 재생
				mThread = new GameThread(getHolder(), currentState);	
			}
		}
			mThread.setRun(true);
			mThread.start();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {

		currentState.setStart(false);
		sm.stopMedia();
		if(currentState.getClass() == PauseState.class)
		{
			boolean isRetry = true;
			mThread.tempThread.setRun(false);
			while(isRetry)
			{
				try{
					mThread.tempThread.join();
					isRetry = false;
				}
				catch(InterruptedException e)
				{
				}
			}
		}
		boolean isRetry = true;
		mThread.setRun(false);
		while(isRetry)
		{
			try{
				mThread.join();
				isRetry = false;
			}
			catch(InterruptedException e)
			{
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		return currentState.onTouch(e);
		
	}
	
}
