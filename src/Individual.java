import java.util.Random;

public class Individual {
	private int numVariables;
	private String theIndividual;

	// create new Individual that is totally random for Genetic Algorithm
	public Individual(int numVar) {
		numVariables = numVar;
		theIndividual = "";
		Random rand = new Random();

		// Create the bit string for the individual
		for (int i = 0; i < numVariables; i++) {
			int num = rand.nextInt(2);
			if (num == 0) {
				theIndividual += "0";
			}
			if (num == 1) {
				theIndividual += "1";
			}
		}
	}

	// Makes Individual with respect to probability vector for PBIL
	public Individual(int numVar, double[] probVector) {
		numVariables = numVar;
		theIndividual = "";

		// Create the bit string for the individual
		for (int i = 0; i < numVariables; i++) {
			double probVectorInteger = probVector[i] * 1000;
			Random rn = new Random();
			int randomProb = rn.nextInt(1000);
			if (randomProb < probVectorInteger) {
				theIndividual += "1";
			} else {
				theIndividual += "0";
			}
		}
	}

	// Method that determines the fitness for an Individual
	public int getFitness(int[][] allClauses, int numClauses) {
		int indFitness = 0;
		for (int i = 0; i < numClauses; i++) {
			for (int j = 0; j < allClauses[i].length; j++) {
				if (allClauses[i][j] < 0) {
					if (theIndividual.charAt(Math.abs(allClauses[i][j]) - 1) == '0') {
						indFitness++;
						break;
					}
				}
				if (allClauses[i][j] > 0) {
					if (theIndividual.charAt(allClauses[i][j] - 1) == '1') {
						indFitness++;
						break;
					}
				}
			}
		}
		return indFitness;
	}

	// Getter that returns the bit string of the Individual
	public String getVars() {
		return theIndividual;
	}

	// Getter that returns the length of the bit string of the Individual
	public int getNumVar() {
		return numVariables;
	}

	// Setter that changes the bit string of the individual to the new string passed
	// in
	public void setVars(String newIndividual) {
		theIndividual = newIndividual;
	}

}
