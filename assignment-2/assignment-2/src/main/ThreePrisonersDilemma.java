package main;

public class ThreePrisonersDilemma {

	/*
	 * This Java program models the two-player Prisoner's Dilemma game. We use the
	 * integer "0" to represent cooperation, and "1" to represent defection.
	 * 
	 * Recall that in the 2-players dilemma, U(DC) > U(CC) > U(DD) > U(CD), where we
	 * give the payoff for the first player in the list. We want the three-player
	 * game to resemble the 2-player game whenever one player's response is fixed,
	 * and we also want symmetry, so U(CCD) = U(CDC) etc. This gives the unique
	 * ordering
	 * 
	 * U(DCC) > U(CCC) > U(DDC) > U(CDC) > U(DDD) > U(CDD)
	 * 
	 * The payoffs for player 1 are given by the following matrix:
	 */

	static int[][][] payoff = { { { 6, 3 }, // payoffs when first and second players cooperate
			{ 3, 0 } }, // payoffs when first player coops, second defects
			{ { 8, 5 }, // payoffs when first player defects, second coops
					{ 5, 2 } } };// payoffs when first and second players defect

	/*
	 * So payoff[i][j][k] represents the payoff to player 1 when the first player's
	 * action is i, the second player's action is j, and the third player's action
	 * is k.
	 * 
	 * In this simulation, triples of players will play each other repeatedly in a
	 * 'match'. A match consists of about 100 rounds, and your score from that match
	 * is the average of the payoffs from each round of that match. For each round,
	 * your strategy is given a list of the previous plays (so you can remember what
	 * your opponent did) and must compute the next action.
	 */

	abstract class Player {
		// This procedure takes in the number of rounds elapsed so far (n), and
		// the previous plays in the match, and returns the appropriate action.
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			throw new RuntimeException("You need to override the selectAction method.");
		}

		// Used to extract the name of this player class.
		final String name() {
			String result = getClass().getName();
			return result.substring(result.indexOf('$') + 1);
		}
	}

	/* Here are four simple strategies: */

	class NicePlayer extends Player {
		// NicePlayer always cooperates
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			return 0;
		}
	}

	class NastyPlayer extends Player {
		// NastyPlayer always defects
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			return 1;
		}
	}

	class RandomPlayer extends Player {
		// RandomPlayer randomly picks his action each time
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (Math.random() < 0.5)
				return 0; // cooperates half the time
			else
				return 1; // defects half the time
		}
	}

	class TolerantPlayer extends Player {
		// TolerantPlayer looks at his opponents' histories, and only defects
		// if at least half of the other players' actions have been defects
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			int opponentCoop = 0;
			int opponentDefect = 0;
			for (int i = 0; i < n; i++) {
				if (oppHistory1[i] == 0)
					opponentCoop = opponentCoop + 1;
				else
					opponentDefect = opponentDefect + 1;
			}
			for (int i = 0; i < n; i++) {
				if (oppHistory2[i] == 0)
					opponentCoop = opponentCoop + 1;
				else
					opponentDefect = opponentDefect + 1;
			}
			if (opponentDefect > opponentCoop)
				return 1;
			else
				return 0;
		}
	}

	class FreakyPlayer extends Player {
		// FreakyPlayer determines, at the start of the match,
		// either to always be nice or always be nasty.
		// Note that this class has a non-trivial constructor.
		int action;

		FreakyPlayer() {
			if (Math.random() < 0.5)
				action = 0; // cooperates half the time
			else
				action = 1; // defects half the time
		}

		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			return action;
		}
	}

	class T4TPlayer extends Player {
		// Picks a random opponent at each play,
		// and uses the 'tit-for-tat' strategy against them
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (n == 0)
				return 0; // cooperate by default
			if (Math.random() < 0.5)
				return oppHistory1[n - 1];
			else
				return oppHistory2[n - 1];
		}
	}

	/**
	 * Assignment 2: New strategy
	 * 
	 * @author Kyle Huang Junyuan (U1721717G)
	 *
	 */
	class Huang_KyleJunyuan_Player extends Player {
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (n == 0)
				return 0; // cooperate by default

			/* 0. Count all scores */
			int[] scores = new int[3];
			for (int i = 0; i < n; i++) {
				scores[0] += payoff[myHistory[i]][oppHistory1[i]][oppHistory2[i]];
				scores[1] += payoff[oppHistory1[i]][oppHistory2[i]][myHistory[i]];
				scores[2] += payoff[oppHistory2[i]][myHistory[i]][oppHistory1[i]];
			}

			/* 1. Count number of times everyone cooperates */
			int iCoop = 0;
			int opp1Coop = 0, opp2Coop = 0;
			for (int i = 0; i < n; i++) {
				if (myHistory[i] == 0)
					iCoop++;
				if (oppHistory1[i] == 0)
					opp1Coop++;
				if (oppHistory2[i] == 0)
					opp2Coop++;
			}

			/* 2. Calculate percentage of cooperating */
			float perICoop = iCoop / n * 100;
			float perOpp1Coop = opp1Coop / n * 100;
			float perOpp2Coop = opp2Coop / n * 100;

			/* 3. Rule (based on popularity) */
			int choice;
			// If both are mostly cooperating, cooperate
			if (perOpp1Coop > 50 && perOpp2Coop > 50)
				choice = 0;
			// If both are mostly defecting, defect
			else if (perOpp1Coop < 50 && perOpp2Coop < 50)
				choice = 1;
			// Else defect
			else
				choice = 1;

			return choice;
		}
	}

	class WongJunYong_Harrison_Player extends Player {

		// 0 0 0 - 6 points
		// 0 0 1 - 3 points
		// 0 1 0 - 3 points
		// 0 1 1 - 0 points
		// 1 0 0 - 8 points
		// 1 0 1 - 5 points
		// 1 1 0 - 5 points
		// 1 1 1 - 2 points

		private boolean angryMode = false;

		int backstabPlayers(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			// detect players if he try to cooperate.
			if (n > 4) {
				// get previous
				if ((oppHistory1[oppHistory1.length - 1] == 0 && oppHistory1[oppHistory1.length - 2] == 0
						&& oppHistory1[oppHistory1.length - 3] == 0 && oppHistory1[oppHistory1.length - 4] == 0)
						&& (oppHistory2[oppHistory2.length - 1] == 0 && oppHistory2[oppHistory2.length - 2] == 0
								&& oppHistory2[oppHistory2.length - 3] == 0
								&& oppHistory2[oppHistory2.length - 4] == 0)) {
					return 1;
				}

//				if ((oppHistory1[oppHistory1.length - 1] == 0 &&
//						oppHistory2[oppHistory2.length - 1] == 1) || 
//						(oppHistory1[oppHistory1.length - 1] == 1 && oppHistory2[oppHistory2.length - 1] == 0)) {
//					return 1;
//				}

			}

			// dont backstab players
			return 0;
		}

		float[] calculateTotalPoints(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			float ScoreA = 0, ScoreB = 0, ScoreC = 0;
			float[] totalScore = new float[3];

			for (int i = 0; i < n; i++) {
				ScoreA = ScoreA + payoff[myHistory[i]][oppHistory1[i]][oppHistory2[i]];
				ScoreB = ScoreB + payoff[oppHistory1[i]][oppHistory2[i]][myHistory[i]];
				ScoreC = ScoreC + payoff[oppHistory2[i]][myHistory[i]][oppHistory1[i]];
			}

			totalScore[0] = ScoreA;
			totalScore[1] = ScoreB;
			totalScore[2] = ScoreC;
			return totalScore;
		}

		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			// first round
			if (n == 0) {
				return 1;
			}

			if (n > 1) {
				// caluclate points
				float[] totalScore = calculateTotalPoints(n, myHistory, oppHistory1, oppHistory2);
				if (totalScore[2] > totalScore[0] || totalScore[1] > totalScore[0]) {
					angryMode = true;
				} else {
					angryMode = false;
				}
			}

			if (angryMode == true) {
				return 1;
			} else {
				return backstabPlayers(n, myHistory, oppHistory1, oppHistory2);
			}

		}
	}

	/*
	 * In our tournament, each pair of strategies will play one match against each
	 * other. This procedure simulates a single match and returns the scores.
	 */
	float[] scoresOfMatch(Player A, Player B, Player C, int rounds) {
		int[] HistoryA = new int[0], HistoryB = new int[0], HistoryC = new int[0];
		float ScoreA = 0, ScoreB = 0, ScoreC = 0;

		for (int i = 0; i < rounds; i++) {
			int PlayA = A.selectAction(i, HistoryA, HistoryB, HistoryC);
			int PlayB = B.selectAction(i, HistoryB, HistoryC, HistoryA);
			int PlayC = C.selectAction(i, HistoryC, HistoryA, HistoryB);
			ScoreA = ScoreA + payoff[PlayA][PlayB][PlayC];
			ScoreB = ScoreB + payoff[PlayB][PlayC][PlayA];
			ScoreC = ScoreC + payoff[PlayC][PlayA][PlayB];
			HistoryA = extendIntArray(HistoryA, PlayA);
			HistoryB = extendIntArray(HistoryB, PlayB);
			HistoryC = extendIntArray(HistoryC, PlayC);
		}
		float[] result = { ScoreA / rounds, ScoreB / rounds, ScoreC / rounds };
		return result;
	}

//	This is a helper function needed by scoresOfMatch.
	int[] extendIntArray(int[] arr, int next) {
		int[] result = new int[arr.length + 1];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		result[result.length - 1] = next;
		return result;
	}

	/*
	 * The procedure makePlayer is used to reset each of the Players (strategies) in
	 * between matches. When you add your own strategy, you will need to add a new
	 * entry to makePlayer, and change numPlayers.
	 */

	int numPlayers = 8;

	Player makePlayer(int which) {
		switch (which) {
		case 0:
			return new NicePlayer();
		case 1:
			return new NastyPlayer();
		case 2:
			return new RandomPlayer();
		case 3:
			return new TolerantPlayer();
		case 4:
			return new FreakyPlayer();
		case 5:
			return new T4TPlayer();
		case 6:
			return new Huang_KyleJunyuan_Player();
		case 7:
			return new WongJunYong_Harrison_Player();
		}
		throw new RuntimeException("Bad argument passed to makePlayer");
	}

	/* Finally, the remaining code actually runs the tournament. */

	public static void main(String[] args) {
		int[] ranks = new int[8];
		int numTournaments = 200;

		for (int i = 1; i <= numTournaments; i++) {
			System.out.println("Tournament: " + i);
			ThreePrisonersDilemma instance = new ThreePrisonersDilemma();
			int rank = instance.runTournament("Huang_KyleJunyuan_Player");
			System.out.println();

			/* Update rankings */
			ranks[rank]++;
		}

		System.out.println("1st: " + ranks[0] / (float) numTournaments * 100 + "%");
		System.out.println("2nd: " + ranks[1] / (float) numTournaments * 100 + "%");
		System.out.println("3rd: " + ranks[2] / (float) numTournaments * 100 + "%");
	}

	boolean verbose = false; // set verbose = false if you get too much text output

	int runTournament(String playerName) {
		float[] totalScore = new float[numPlayers];

		// This loop plays each triple of players against each other.
		// Note that we include duplicates: two copies of your strategy will play once
		// against each other strategy, and three copies of your strategy will play
		// once.

		for (int i = 0; i < numPlayers; i++)
			for (int j = i; j < numPlayers; j++)
				for (int k = j; k < numPlayers; k++) {

					Player A = makePlayer(i); // Create a fresh copy of each player
					Player B = makePlayer(j);
					Player C = makePlayer(k);
					int rounds = 90 + (int) Math.rint(20 * Math.random()); // Between 90 and 110 rounds
					float[] matchResults = scoresOfMatch(A, B, C, rounds); // Run match
					totalScore[i] = totalScore[i] + matchResults[0];
					totalScore[j] = totalScore[j] + matchResults[1];
					totalScore[k] = totalScore[k] + matchResults[2];
					if (verbose)
						System.out.println(A.name() + " scored " + matchResults[0] + " points, " + B.name() + " scored "
								+ matchResults[1] + " points, and " + C.name() + " scored " + matchResults[2]
								+ " points.");
				}
		int[] sortedOrder = new int[numPlayers];
		// This loop sorts the players by their score.
		for (int i = 0; i < numPlayers; i++) {
			int j = i - 1;
			for (; j >= 0; j--) {
				if (totalScore[i] > totalScore[sortedOrder[j]])
					sortedOrder[j + 1] = sortedOrder[j];
				else
					break;
			}
			sortedOrder[j + 1] = i;
		}

		// Finally, print out the sorted results.
		if (verbose)
			System.out.println();
		System.out.println("Tournament Results");
		for (int i = 0; i < numPlayers; i++)
			System.out.println(makePlayer(sortedOrder[i]).name() + ": " + totalScore[sortedOrder[i]] + " points.");

		// Return rank of playerName
		int rank = 0;
		for (int i = 0; i < numPlayers; i++) {
			if (makePlayer(sortedOrder[i]).name().equals(playerName)) {
				rank = i;
				break;
			}
		}
		return rank;
	} // end of runTournament()

} // end of class PrisonersDilemma
