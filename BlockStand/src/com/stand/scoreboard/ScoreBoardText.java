package com.stand.scoreboard;

import java.util.List;

import org.bukkit.scoreboard.Score;

public class ScoreBoardText {
	
	private String title;
	private List<Score> body;
	
	public ScoreBoardText(String title, List<Score> body) {
		this.title = title;
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Score> getBody() {
		return body;
	}

	public void setBody(List<Score> body) {
		this.body = body;
	}

}
