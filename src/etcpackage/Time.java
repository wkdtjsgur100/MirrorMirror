package etcpackage;

public class Time
	{
		private long mTime; //millisecond
		private long mRemainTime;
		private long startTime;
		
		public Time(long time)
		{
			mTime = time*10;
			mRemainTime = time*10;
			
			startTime = 0;
		}
		public void Start()
		{
			startTime = System.currentTimeMillis();
		}
		public void timeInit()
		{
			mRemainTime = mTime;
		}
		public long getRemainTime()
		{
			return mRemainTime;
		}
		public void remaintimeDeltaChange(int delta)
		{
			mRemainTime += delta;
		}
		public void timeDeltaChange(int delta)
		{
			mTime += delta;
		}
		public void clock()
		{
			if(startTime != 0)
			{
				long delta = System.currentTimeMillis() - startTime;
				if(delta >= 100)	
				{
					mRemainTime--;
					startTime = System.currentTimeMillis();
				}
			}
		}
	}
