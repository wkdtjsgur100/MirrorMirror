package etcpackage;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;
public class SoundManager {
	
	public static final int CORRECT_INDEX_CONST = 8031;
	public static final int COUNT_CONST = 8032;
	public static final int NEW_RECORD_CONST = 8033;
	public static final int GAME_OVER_VOICE_CONST = 8034;
	private static SoundManager s_instance;
	SoundPool mSoundPool;
	HashMap<Integer, Integer> mSoundPoolMap;
	AudioManager mAudioManager;
	Context mContext;
	public int soundInt;
	public float streamVolume;
	private MediaPlayer mpBgm;
	private int mBgmResourceId;

	public void Init(Context _context)
	{
		mSoundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
		mSoundPoolMap = new HashMap<Integer, Integer>();
		mAudioManager = (AudioManager)_context.getSystemService(Context.AUDIO_SERVICE);
		mContext = _context;		
		streamVolume = 0.5f;
	}
	public void addSoundPool(int _index, int _soundResourceID)
	{
		int id = mSoundPool.load(mContext, _soundResourceID, 1);
		mSoundPoolMap.put(_index, id);
	}
	
	public void playSoundPool(int _index)
	{
		if(DataManager.getInstance().getSoundPreferences())
		{
		mSoundPool.play( (Integer)mSoundPoolMap.get(_index),streamVolume,streamVolume,1,0,1f );
		}
	}
	public void playSoundPoolLooped(int _index)
	{
		if(DataManager.getInstance().getSoundPreferences())
		{
			 soundInt = mSoundPool.play((Integer)mSoundPoolMap.get(_index), streamVolume, streamVolume, 1, -1, 1f); 
		}
	}
	public void stopSoundPool(int _index)
	{
		mSoundPool.stop(_index);
	}
	
	public void setMedia(Context context,int resource)
	{
		mBgmResourceId = resource;
		if(mpBgm!=null)
		{
			stopMedia();
			mpBgm=null;
		}
		mpBgm = MediaPlayer.create(context,resource);
		mpBgm.setLooping(true);
		if(streamVolume<=0)streamVolume = 0.01f;
		mpBgm.setVolume(streamVolume, streamVolume);
	}
	public void playMedia()
	{
		if(mpBgm==null)
		{
			setMedia(mContext,mBgmResourceId);
		}
		if(!mpBgm.isPlaying())
		{
			mpBgm.start();
		}
	}
	public void stopMedia()
	{
		if(mpBgm==null)return;
		mpBgm.stop();	
		while(true)
		{
			if(!mpBgm.isPlaying())
			{
				mpBgm.release();
				mpBgm = null;
				break;
			}
		}
	}
	public void pauseMedia()
	{
		if(mpBgm!=null)
		if(mpBgm.isPlaying())
		{
			mpBgm.pause();
		}
	}
	
	public void releaseAll()
	{
		mSoundPool.release();
		if(mpBgm==null)return;
		mpBgm.stop();
		while(true)
		{
			if(!mpBgm.isPlaying())
			{
				mpBgm.release();
				break;
			}
		}
	}
	
	public static SoundManager getInstance()
	{
		if(s_instance==null)
		{
			s_instance = new SoundManager();
		}
		return s_instance;
	}

}
