package basepackage;

import com.example.mirrormirror.R;
import com.google.ads.*;

import statepackage.GameState;
import statepackage.PauseState;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import etcpackage.DataManager;
import etcpackage.SoundManager;
public class MainActivity extends Activity {

	TotalManageView myView;
	GameThread mThread;
	private long startTime;
	private boolean backFirst;
	private Toast mToast;
	private AdView adView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DataManager.getInstance().setContext(this);
		SoundManager.getInstance().Init(this);
		backFirst = true;
		myView = new TotalManageView(this);
		adView = new AdView(this, AdSize.SMART_BANNER, "a15235dcee34f10");
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adView.setLayoutParams(lp);

        RelativeLayout layout = new RelativeLayout(this);
        
        layout.addView(myView);
        layout.addView(adView);
        
        adView.loadAd(new AdRequest());
        setContentView(layout);
	}
	@Override
	public void onPause()
	{
		SoundManager.getInstance().pauseMedia();
		myView.mHandler.sendEmptyMessage(TotalManageView.HOME_BUTTON_CLICKED_MESSAGE);
		if(myView.currentState.getClass()==GameState.class)
		{
			myView.mHandler.sendEmptyMessage(myView.GAME_PAUSE_MESSAGE);
		}
		super.onPause();
		
		if(this.isFinishing())
		{
			if(mToast!=null)
			{
				mToast.cancel();
			}
		}
	}
	@Override
	public void onResume()
	{
		super.onResume();
		if(DataManager.getInstance().getSoundPreferences() && myView.currentState.getClass() != PauseState.class)
			SoundManager.getInstance().playMedia();
	}
	@Override
	public void onBackPressed() 
	{
		if(backFirst)
		{
			startTime = System.currentTimeMillis();
			mToast = Toast.makeText(this, "종료하시려면 2초 내로 back키를 한 번 더 눌러주세요.", 2000);
			mToast.show();
			backFirst = !backFirst;
		}
		else
		{
			long currentTime = System.currentTimeMillis();
			if(currentTime-startTime<2000)
			{
				super.onBackPressed();
			}
			else
			{
				backFirst = !backFirst;
			}
		}
		
	}

}

