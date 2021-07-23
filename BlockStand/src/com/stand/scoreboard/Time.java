package com.stand.scoreboard;

public enum Time {

	SECONDS, MINUTES, HOURS;
	
	public int milliseconds(Time time) {
		switch (time) {
		case SECONDS:
			return 1000;
		case MINUTES:
			return 60000;
		case HOURS:
			return 360000;
		default:
			return 1000;
		}
	}
	
}
