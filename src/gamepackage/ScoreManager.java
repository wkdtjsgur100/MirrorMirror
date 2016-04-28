package gamepackage;

public class ScoreManager {
	int currentScore;
	
	public ScoreManager(){
		currentScore = 0;
	}
	public int getScore(){
		return currentScore;
	}
	public void plusScore(int scoreToBeAdded){
		if( currentScore + scoreToBeAdded < 0){
			currentScore = 0;
		}
		else
			currentScore += scoreToBeAdded;
	}
	void setScore(int score){
		if(score >= 0){
			currentScore = score;
		}
		else
			currentScore = 0;
	}
}
