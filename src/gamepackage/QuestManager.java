package gamepackage;

import android.util.Log;

class Quest {

	public int[] arrowVectorArray;
	public int[] arrowTypeArray;
	public int questTime;
	public int incorrectTimeMinusProprotionality;
	public int comboRemainingTimeSecond;
}

class QuestMaker {
	protected final static int VECTOR_NUM = 4;
	protected final static int TYPE_NUM = 5;
	public static final int NORMAL = 0;
	public static final int REVERSE = 1;
	public static final int CLOCKWISE = 3;
	public static final int COUNTER_CLOCKWISE = 2;
	public static final int SKIP = 4;
	public static final int MAX_LEVEL = 25;
	
	private static final int EASY_BASE_TIME_CONST = 8;
	private static final int NORMAL_BASE_TIME_CONST = 6;
	private static final int HARD_BASE_TIME_CONST = 4;
	private static final int HELL_BASE_TIME_CONST = 3;

	public Quest generateQuest(int currentStage, int gameType) {

		Quest re = new Quest();
		if(gameType != 5)
			re.arrowVectorArray = makeArrowVectorArray();
		else
			re.arrowVectorArray = makeTutorialArrowVectorArray();
		int level = updateLevel(currentStage);
		int levelTime=0;
		switch (gameType) {
		case 1: // EASY
			if(level<=2)
			{
				re.arrowTypeArray = makeArrowTypeArray(0,0,0,0);
				levelTime = 0;
			}
			else if(level<=5)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,0,0,0);
				levelTime = 2;
			}
			else if(level<=8)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,80,0,0);
				levelTime = 2;
			}
			else if(level<13)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,80,30,0);
				levelTime = 3;
			}
			else if(level<18)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,80,50,0);
				levelTime =3;
			}
			else if(level<25)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,80,50,10);
				levelTime =4;
			}
			else if(level>=25)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,80,80,50);
				levelTime = 4;
			}
			re.questTime = makeQuestTime(EASY_BASE_TIME_CONST-levelTime);
			re.comboRemainingTimeSecond =re.questTime/1000 - 4;
			if(re.comboRemainingTimeSecond<=1)re.comboRemainingTimeSecond = 1;
			re.incorrectTimeMinusProprotionality = 8; // 10이 1초
			break;
		case 2:					//NORMAL
			if(level<=2)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,50,0,0);
				levelTime = 0;
			}
			else if(level<=5)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,80,10,0);
				levelTime = 1;
			}
			else if(level<=8)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,80,30,0);
				levelTime = 1;
			}
			else if(level<13)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,80,30,10);
				levelTime = 1;
			}
			else if(level<18)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,80,50,30);
				levelTime = 2;
			}
			else if(level<25)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,80,80,50);
				levelTime = 2;
			}
			else if(level>=25)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,80,80,80);
				levelTime = 3;
			}
			re.questTime = makeQuestTime(NORMAL_BASE_TIME_CONST - levelTime);
			re.comboRemainingTimeSecond = re.questTime/1000 - 3;
			if(re.comboRemainingTimeSecond<=1)re.comboRemainingTimeSecond = 1;
			
			re.incorrectTimeMinusProprotionality = 8; // 10이 1초
			break;
		case 3:				//HARD
			if(level<=2)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,60,50,0);
				levelTime = 0;
			}
			else if(level<=5)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,80,60,0);
				levelTime = 0;
			}
			else if(level<=8)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,80,50,30);
				levelTime = 0;
			}
			else if(level<13)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,80,50,50);
				levelTime = 0;
			}
			else if(level<18)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,80,60,60);
				levelTime = 1;
			}
			else if(level<25)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,80,80,60);
				levelTime = 1;
			}
			else if(level>=25)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,80,80,80);
				levelTime = 1;
			}
			re.questTime = makeQuestTime(HARD_BASE_TIME_CONST - levelTime);
			re.comboRemainingTimeSecond = re.questTime/1000 - 2;
			if(re.comboRemainingTimeSecond<=1)re.comboRemainingTimeSecond = 1;
			re.incorrectTimeMinusProprotionality = 8; // 10이 1초
			break;
		case 4:
			if(level<=2)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,60,30,10);
				levelTime = 0;
			}
			else if(level<=5)
			{
				re.arrowTypeArray = makeArrowTypeArray(80,50,30,30);
				levelTime = 0;
			}
			else if(level<=8)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,50,50,30);
				levelTime =0;
			}
			else if(level<13)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,50,50,50);
				levelTime = 0;
			}
			else if(level<18)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,80,80,50);
				levelTime = 0;
			}
			else if(level<25)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,80,80,80);
				levelTime = 1;
			}
			else if(level>=25)
			{
				re.arrowTypeArray = makeArrowTypeArray(100,100,100,100);
				levelTime = 1;
			}
			re.questTime = makeQuestTime(HELL_BASE_TIME_CONST - levelTime);
			re.comboRemainingTimeSecond = re.questTime/1000 - 2;
			if(re.comboRemainingTimeSecond<=1)re.comboRemainingTimeSecond = 1;
			re.incorrectTimeMinusProprotionality = 8;
			break;
		
		case 5:	//Tutorial Mode
			switch(currentStage) {
			case 1:
				re.arrowTypeArray = makeTutorialArrowTypeArray(0);
				re.questTime = makeQuestTime(500);
				re.comboRemainingTimeSecond = 500;
				re.incorrectTimeMinusProprotionality = 0;
				break;
			case 2:
				re.arrowTypeArray = makeTutorialArrowTypeArray(1);
				re.questTime = makeQuestTime(500);
				re.comboRemainingTimeSecond = 500;
				re.incorrectTimeMinusProprotionality = 0;
				break;
			case 3:
				re.arrowTypeArray = makeTutorialArrowTypeArray(2);
				re.questTime = makeQuestTime(500);
				re.comboRemainingTimeSecond = 500;
				re.incorrectTimeMinusProprotionality = 0;
				break;
			case 4:
				re.arrowTypeArray = makeTutorialArrowTypeArray(3);
				re.questTime = makeQuestTime(500);
				re.comboRemainingTimeSecond = 500;
				re.incorrectTimeMinusProprotionality = 0;
				break;
			case 5:
				re.arrowTypeArray = makeTutorialArrowTypeArray(4);
				re.questTime = makeQuestTime(500);
				re.comboRemainingTimeSecond = 500;
				re.incorrectTimeMinusProprotionality = 0;
			default :
				re.arrowTypeArray = makeTutorialArrowTypeArray(4);
				re.questTime = makeQuestTime(500);
				re.comboRemainingTimeSecond = 500;
				re.incorrectTimeMinusProprotionality = 0;
			}
			break;
		
		default :
			break;
		}

		return re;
	}

	int updateLevel(int currentStage) {
		int level=1;
		for(int i=MAX_LEVEL-1; i>=0; i--)
			if(currentStage>3*i)
			{
				level = i+1;
				return level;
			}
		level=MAX_LEVEL;
		return level;
	}
	public int[] makeArrowVectorArray() {
		int[] vector = new int[VECTOR_NUM];
		for (int i = 0; i < VECTOR_NUM; i++)
			vector[i] = (int) (Math.random() * VECTOR_NUM);

		return vector;
	}
	public int[] makeTutorialArrowVectorArray() {
		int[] vector = new int[VECTOR_NUM];
		for(int i=0; i<VECTOR_NUM; i++)
			vector[i]=i;

		return vector;
	}
	public int[] makeTutorialArrowTypeArray( int arrowType){
		int[] paint = new int[VECTOR_NUM];
		if(arrowType !=4)
			for(int i=0; i<VECTOR_NUM; i++){
				paint[i]=arrowType;
			}
		else{
			paint[0]=4;
			for(int i=1; i<VECTOR_NUM; i++){
				paint[i]=0;
			}
			
		}
		return paint;
	}
	
	public int[] makeArrowTypeArray( int reversePercentage,
			int skipPercentage, int clockPercentage, int counterClockPercentage) {

		int[] paint = new int[VECTOR_NUM];
		for (int i = 0; i < VECTOR_NUM; i++)
			paint[i] = 0; // Normal ¸¸ ³ÖÀ½.

		if ((int) (Math.random() * 100) < skipPercentage) { // 50%ÀÇ È®·ü·Î
			paint[(int) (Math.random() * (VECTOR_NUM - 1))] = SKIP; // SKIP À» ¾Õ¿¡
																	// ¼¼Ä­ Áß ÇÏ³ª¿¡
																	// ³ÖÀ½.
		}
		if ((int) (Math.random() * 100) < reversePercentage) { // 50%ÀÇ È®·ü·Î
			paint[(int) (Math.random() * VECTOR_NUM)] = REVERSE; // SKIP À» ¾Õ¿¡ ¼¼Ä­
																	// Áß ÇÏ³ª¿¡ ³ÖÀ½.
		}
		if ((int) (Math.random() * 100) < clockPercentage) { // 50%ÀÇ È®·ü·Î
			paint[(int) (Math.random() * VECTOR_NUM)] = CLOCKWISE; // SKIP À» ¾Õ¿¡
																	// ¼¼Ä­ Áß ÇÏ³ª¿¡
																	// ³ÖÀ½.
		}
		if ((int) (Math.random() * 100) < counterClockPercentage) { // 50%ÀÇ È®·ü·Î
			paint[(int) (Math.random() * VECTOR_NUM)] = COUNTER_CLOCKWISE; // SKIP
																			// À»
																			// ¾Õ¿¡
																			// ¼¼Ä­
																			// Áß
																			// ÇÏ³ª¿¡
																			// ³ÖÀ½.
		}

		return paint;
	}

	public int makeQuestTime(float questTimeSeconds) {
		return (int)(questTimeSeconds * 1000);
	}
}



















public class QuestManager {
	public static final int NODE_CLEAR = 5111;
	public static final int STAGE_CLEAR = 5112;
	public static final int STAGE_FALE = 5113;

	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	public static final int NORMAL = 0;
	public static final int REVERSE = 1;
	public static final int CLOCKWISE = 3;
	public static final int COUNTER_CLOCKWISE = 2;
	public static final int SKIP = 4;

	private QuestMaker mQM;
	private Quest mCurrentQuest;
	int clearNum; // clearÇÑ °¹¼ö
	int mLevel;
	private int mGameType;
	private long questStartTime;
	private int mStage;

	public QuestManager(int gameType) {
		mLevel = 1;
		mStage = 1;
		mGameType = gameType;
		mQM = new QuestMaker();
		QuestRefresh();
	}

	public void LevelUp() {
		mLevel = mQM.updateLevel(getClearStage());
		mQM.generateQuest(getClearStage(), mGameType);
	}
	public void clearStage()
	{
		++mStage;
	}

	public int getComboScoreRemainingTimeSecond() {
		return mCurrentQuest.comboRemainingTimeSecond;
	}

	public void QuestRefresh() {
		
		mLevel = mQM.updateLevel(getClearStage());
		mCurrentQuest = mQM.generateQuest(getClearStage(), mGameType);
		clearNum = 0;
		questStartTime = System.currentTimeMillis();
	}
	public int getIncorrectTimeMinusProprotionality(){
		return mCurrentQuest.incorrectTimeMinusProprotionality;
	}
	public int getQuestTime() {
		return mCurrentQuest.questTime;
	}

	public int getClearNodeNum() {
		return clearNum;
	}

	public int getLevel() {
		mLevel = mQM.updateLevel(getClearStage());
		return mLevel;
	}
	
	public int getClearStage()
	{
		return mStage;
	}

	public int getArrowType(int i) {
		// TODO Auto-generated method stub
		return mCurrentQuest.arrowTypeArray[i];
	}

	public int getArrowVector(int i) {
		// TODO Auto-generated method stub
		return mCurrentQuest.arrowVectorArray[i];
	}

	public int getAnswerForArrow(int arrowDirection, int arrowType){
		
		if(arrowType == 4)
			return -1;
		else if(arrowType == 0){
			
			return arrowDirection;
			
		}
		else if(arrowType == 1){
			switch(arrowDirection){
			
			
			case 0:
				return 2;
				
			case 2:
				return 0;
				
			case 1:
				return 3;
				
			case 3:
				return 1;
			default:
				return -1;
			}
		}
		else if(arrowType == 3){
			switch(arrowDirection){
			
			case 0:
				return 3;
				
			case 1:
				return 0;
				
			case 2:
				return 1;
				
			case 3:
				return 2;
			default:
				return -1;
			}
		}
		else if(arrowType == 2){
			switch(arrowDirection){
			
			case 0:
				return 1;
				
			case 1:
				return 2;
				
			case 2:
				return 3;
				
			case 3:
				return 0;
			default:
				return -1;
			}
		}
		else{
			return -1;
		}
	}
	public int isCorrect(int moveIndex) {
		if (mCurrentQuest.arrowTypeArray[clearNum] == SKIP) {
			clearNum++;
		}
		if (mCurrentQuest.arrowTypeArray[clearNum] == NORMAL) {
			if (mCurrentQuest.arrowVectorArray[clearNum] == moveIndex) {
				if (clearNum < 3) {
					clearNum++;
					return NODE_CLEAR;
				} else {
					clearStage();
					QuestRefresh();
					return STAGE_CLEAR;
				}
			} else
				return STAGE_FALE;
		} else if (mCurrentQuest.arrowTypeArray[clearNum] == REVERSE) {

			int directionAnswer = mCurrentQuest.arrowVectorArray[clearNum];
			/*
			 * UP = 0; RIGHT = 1; DOWN = 2; LEFT = 3;
			 */

			if ((moveIndex == 0 && directionAnswer == 2)
					|| (moveIndex == 2 && directionAnswer == 0)
					|| (moveIndex == 1 && directionAnswer == 3)
					|| (moveIndex == 3 && directionAnswer == 1)) {

				if (clearNum < 3) {
					clearNum++;
					return NODE_CLEAR;
				} else {
					clearStage();	
					QuestRefresh();
					return STAGE_CLEAR;
				}
			} else
				// Æ²·ÈÀ» ¶§
				return STAGE_FALE;
		} else if (mCurrentQuest.arrowTypeArray[clearNum] == CLOCKWISE) {

			int directionAnswer = mCurrentQuest.arrowVectorArray[clearNum];
			/*
			 * UP = 0; RIGHT = 1; DOWN = 2; LEFT = 3;
			 */

			if ((moveIndex == 0 && directionAnswer == 1)
					|| (moveIndex == 2 && directionAnswer == 3)
					|| (moveIndex == 1 && directionAnswer == 2)
					|| (moveIndex == 3 && directionAnswer == 0)) {

				if (clearNum < 3) {
					clearNum++;
					return NODE_CLEAR;
				} else {
					clearStage();
					QuestRefresh();
					return STAGE_CLEAR;
				}
			} else
				// Æ²·ÈÀ» ¶§
				return STAGE_FALE;
		} else if (mCurrentQuest.arrowTypeArray[clearNum] == COUNTER_CLOCKWISE) {

			int directionAnswer = mCurrentQuest.arrowVectorArray[clearNum];
			/*
			 * UP = 0; RIGHT = 1; DOWN = 2; LEFT = 3;
			 */

			if ((moveIndex == 0 && directionAnswer == 3)
					|| (moveIndex == 2 && directionAnswer == 1)
					|| (moveIndex == 1 && directionAnswer == 0)
					|| (moveIndex == 3 && directionAnswer == 2)) {

				if (clearNum < 3) {
					clearNum++;
					return NODE_CLEAR;
				} else {
					clearStage();
					QuestRefresh();
					return STAGE_CLEAR;
				}
			} else
				// Æ²·ÈÀ» ¶§
				return STAGE_FALE;
		} else { // PUSH ÀÏ ¶§.
			if (mCurrentQuest.arrowVectorArray[clearNum] == moveIndex) {
				if (clearNum < 3) {
					clearNum++;
					return NODE_CLEAR;
				} else {
					clearStage();
					QuestRefresh();
					return STAGE_CLEAR;
				}
			} else
				return STAGE_FALE;
		}

	}

	public boolean isFalseThisQuest() {
		// ½Ã°£³»¿¡ Äù½ºÆ®¸¦ ²£´À³Ä ¸ø²£´À³Ä.
		if (System.currentTimeMillis() - questStartTime < mCurrentQuest.questTime)
			return false;
		else
			return true;
	}

}