/**
 * Assignment 2: Kyle's Strategy
 * 
 * @author Kyle Huang Junyuan (U1721717G)
 *
 */
class Huang_KyleJunyuan_Player extends Player {
	// Helper function to calculate percentage of cooperation
	float calCoopPercentage(int[] history) {
		int cooperates = 0;
		int length = history.length;

		for (int i = 0; i < length; i++)
			if (history[i] == 0)
				cooperates++;

		return (float) cooperates / length * 100;
	}

	int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
		if (n == 0)
			return 0; // First round: Cooperate

		/* 1. Calculate percentage of cooperation */
		float perOpp1Coop = calCoopPercentage(oppHistory1);
		float perOpp2Coop = calCoopPercentage(oppHistory2);

		/* 2. If both players are mostly cooperating */
		if (perOpp1Coop > 90 && perOpp2Coop > 90) {
			int range = (10 - 5) + 1; // Max: 10, Min: 5
			int random = (int) (Math.random() * range) + 5;
			
			if (n > (90 + random))  // Selfish: Last min defect
				return 1;
			else
				return 0;	// First ~90 rounds: Cooperate
		}

		/* 3. Defect by default */
		return 1;
	}
}
