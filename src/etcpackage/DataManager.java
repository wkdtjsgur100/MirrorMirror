package etcpackage;

import statepackage.GameState;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class DataManager {
	public static final String EASY_BEST= "easy_best";
	public static final String NORMAL_BEST = "normal_best";
	public static final String HARD_BEST = "hard_best";
	public static final String HELL_BEST = "hell_best";
	public static final String CURRENT_SCORE = "current_score";
	public static final String COMBO = "combo";
	public static final String LEVEL = "level";
	public static final String BEST = "best";
	public static final String SOUND = "sound";
	public static final String FIRST = "first";
	
	
	private Context mContext;
	private static DataManager mInstance;

	private DataManager()
	{
	}
	public void setContext(Context context)
	{
		this.mContext = context;
	}
	public static DataManager getInstance()
	{
		if(mInstance==null)mInstance = new DataManager();
		return mInstance;
	}
	public void setSoundPreferences(boolean tf) {
		SharedPreferences pref = null;
		pref = mContext.getSharedPreferences("SOUND", 0);
		SharedPreferences.Editor prefEditor = pref.edit();
		prefEditor.putBoolean(SOUND,tf);
		prefEditor.commit();
	}
	public void setRankingPreferences(String rank, 
			int score) {
		SharedPreferences pref = null;
		pref = mContext.getSharedPreferences("Ranking", 0);
		SharedPreferences.Editor prefEditor = pref.edit();
		prefEditor.putInt(rank, score);
		prefEditor.commit();
	}
	public void setRankingBestPreferences(int gameType,int score) {
		String rank =NORMAL_BEST;
		switch(gameType)
		{
		case GameState.EASY : rank = EASY_BEST; break;
		case GameState.NORMAL : rank = NORMAL_BEST; break;
		case GameState.HARD : rank = HARD_BEST; break;
		case GameState.HELL : rank = HELL_BEST; break;
		}
		SharedPreferences pref = null;
		pref = mContext.getSharedPreferences("Ranking", 0);
		SharedPreferences.Editor prefEditor = pref.edit();
		prefEditor.putInt(rank, score);
		prefEditor.commit();
		
	}
	public boolean getSoundPreferences() {
		boolean returnValue = true;
		SharedPreferences pref = null;
		pref = mContext.getSharedPreferences("SOUND", 0);
		returnValue = pref.getBoolean(SOUND, true);
		return returnValue;
	}
	public void setFirstPreferences(boolean tf) {
		SharedPreferences pref = null;
		pref = mContext.getSharedPreferences("FIRST", 0);
		SharedPreferences.Editor prefEditor = pref.edit();
		prefEditor.putBoolean(FIRST,tf);
		prefEditor.commit();
	}
	public int getRankingPreferences(String rank) {
		int returnValue = 0;
		SharedPreferences pref = null;
		pref = mContext.getSharedPreferences("Ranking", 0);
		returnValue = pref.getInt(rank, 0);
		return returnValue;
	}
	
	public int getRankingBestPreferences(int gameType) {
		int returnValue = 0;
		SharedPreferences pref = null;
		String rank =NORMAL_BEST;
		switch(gameType)
		{
		case GameState.EASY : rank = EASY_BEST; break;
		case GameState.NORMAL : rank = NORMAL_BEST; break;
		case GameState.HARD : rank = HARD_BEST; break;
		case GameState.HELL : rank = HELL_BEST; break;
		}
		pref = mContext.getSharedPreferences("Ranking", 0);
		returnValue = pref.getInt(rank, 0);
		return returnValue;
	}
	public boolean getFristPreferences() {
		boolean returnValue = true;
		SharedPreferences pref = null;
		pref = mContext.getSharedPreferences("FIRST", 0);
		returnValue = pref.getBoolean(FIRST, true);
		return returnValue;
	}
	
	

	
	
	
}
