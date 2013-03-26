package nl.saxion.act.playground.highscore;

public class Score {

	private String name;
	private int time, place;

	public Score(int place, String value, int time) {
		this.name = value;
		this.time = time;
		this.place = place;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	public void setPlace(int place){
		this.place = place;
	}
	
	public int getPlace(){
		return place;
	}

}
