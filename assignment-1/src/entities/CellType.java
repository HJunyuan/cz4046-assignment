package entities;

public enum CellType {
	WHITE, GREEN, BROWN, WALL;

	public float getReward() {
		switch (this) {
		case WHITE:
			return Constants.REWARD_WHITE;
		case GREEN:
			return Constants.REWARD_GREEN;
		case BROWN:
			return Constants.REWARD_BROWN;
		case WALL:
			return Constants.REWARD_WALL;
		default:
			return 0f;
		}
	}

	public String getSymbol() {
		switch (this) {
		case WHITE:
			return "W";
		case GREEN:
			return "G";
		case BROWN:
			return "B";
		case WALL:
			return "X";
		default:
			return "";
		}
	}
}
