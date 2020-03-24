package entities;

public class Constants {
	/* Grid World */
	public final static int NUM_COL = 6;
	public final static int NUM_ROW = 6;
	
	/* Reward */
	public final static float REWARD_WHITE = -0.04f;
	public final static float REWARD_GREEN = 1f;
	public final static float REWARD_BROWN = -1f;
	public final static float REWARD_WALL = 0f;
	
	/* Probabilities */
	public final static float PROBABILITY_UP = 0.8f;
	public final static float PROBABILITY_LEFT = 0.1f;
	public final static float PROBABILITY_RIGHT = 0.1f;
	
	/* Discount Factor */
	public final static float DISCOUNT_FACTOR = 0.99f;
	
	/* Epsilon */
	public final static int EPSILON = 62;
}
