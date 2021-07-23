package com.stand.scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreMaker {
	
	private Scoreboard scoreboard;
	private List<Score> currentScore = new ArrayList<Score>();
	
	public ScoreMaker(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}

	public static List<Score> maker(Score... score){
		
		List<Score> scores = new ArrayList<Score>();
		
		for(Score i : score.clone()) {
			scores.add(i);
		}
		
		return scores;
	}

	public void setScore() {
		
	}
	
	public ScoreMaker build(String parent, int value) {
		
		Set<Score> data = this.scoreboard.getScores(parent);
		
		for(Score i : data) {
			i.setScore(value);
			this.currentScore.add(i);
		}
		
		ScoreMaker maker = new ScoreMaker(this.scoreboard);
		maker.setCurrentScore(this.currentScore);
		return maker;
	}
	
	public ScoreMaker build(String parent) {
		
		Set<Score> data = this.scoreboard.getScores(parent);
		
		for(Score i : data) {
			this.currentScore.add(i);
		}
		
		ScoreMaker maker = new ScoreMaker(this.scoreboard);
		maker.setCurrentScore(this.currentScore);
		return maker;
	}

	public List<Score> getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(List<Score> currentScore) {
		this.currentScore = currentScore;
	}
	
}
