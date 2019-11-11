import java.util.Random;

public class PBIL {

	private int numVars;
	private int numClauses;
	private int popSize;
	private int numGenerations;
	private float posLearningRate;
	private float negLearningRate;
	private float mutationProb;
	private float mutationAmount;
	private int[][] clauses;
	private double[] probVector;

	// Constructor of PBIL
	public PBIL(int numVars, int numClauses, int popSize, int numGenerations, float posLearningRate,
			float negLearningRate, float mutationProb, float mutationAmount, int[][] clauses) {
		this.numVars = numVars;
		this.numClauses = numClauses;
		this.popSize = popSize;
		this.numGenerations = numGenerations;
		this.posLearningRate = posLearningRate;
		this.negLearningRate = negLearningRate;
		this.mutationProb = mutationProb;
		this.mutationAmount = mutationAmount;
		this.clauses = clauses;
		this.probVector = new double[numVars];

		// fill probArray with 0.5 in each spot
		for (int i = 0; i < numVars; i++) {
			probVector[i] = 0.500;
		}
	}

	// This method runs the iterations of PBIL
	public void runPBIL() {
		int numIterations = 0;
		Population pop = null;

		// Store the global best individual and its fitness and the iteration it was
		// found on
		Individual globalBestIndividual = new Individual(numVars);
		int bestFoundIteration = 0;

		// This while loop will run the process of PBIL until the number of iterations
		// has been met or the optimal solution has been found
		while (numIterations != numGenerations && globalBestIndividual.getFitness(clauses, numClauses) != numClauses) {

			// make a new population of individuals based on the probability vector
			pop = new Population(popSize, numVars, probVector);

			// find the best and worst individuals
			Individual best_individual = pop.getIndividuals()[0];
			Individual worst_individual = pop.getIndividuals()[0];
			int currentFitness;

			// edit best individuals
			for (int k = 1; k < popSize; k++) {
				currentFitness = pop.getIndividuals()[k].getFitness(clauses, numClauses);
				// Found new best Individual for this iteration
				if (currentFitness > best_individual.getFitness(clauses, numClauses)) {
					best_individual = pop.getIndividuals()[k];
					// Found a new best global Individual
					if (currentFitness > globalBestIndividual.getFitness(clauses, numClauses)) {
						globalBestIndividual = best_individual;
						bestFoundIteration = numIterations + 1;
					}
				}
				// Found a new worst Individual for this iteration
				if (currentFitness < worst_individual.getFitness(clauses, numClauses)) {
					worst_individual = pop.getIndividuals()[k];
				}
			}

			// Update probability vector toward best Individual
			for (int j = 0; j < probVector.length; j++) {
				probVector[j] = probVector[j] * (1.0 - posLearningRate)
						+ Character.getNumericValue(best_individual.getVars().charAt(j)) * (posLearningRate);
			}

			// Update probability vector away from worst Individual
			for (int m = 1; m < probVector.length; m++) {
				if (best_individual.getVars().charAt(m) != worst_individual.getVars().charAt(m)) {
					probVector[m] = probVector[m] * (1.0 - negLearningRate)
							+ Character.getNumericValue(best_individual.getVars().charAt(m)) * (negLearningRate);
				}
			}

			// mutate probability vector
			for (int n = 1; n < probVector.length; n++) {
				float PBILmutationInteger = mutationProb * 1000;
				Random rn = new Random();
				int PBILmutate = rn.nextInt(1000);
				int mutate_direction = 0;
				if (PBILmutate <= PBILmutationInteger) {
					if (rn.nextInt(2) == 1) {
						mutate_direction = 1;
					} else {
						mutate_direction = 0;
					}

					probVector[n] = probVector[n] * (1.0 - mutationAmount) + mutate_direction * (mutationAmount);

				}
			}
			numIterations++;
		}

		// Round probability vector
		for (int i = 0; i < probVector.length; i++) {
			if (probVector[i] > 0.5) {
				probVector[i] = 1;
			} else {
				probVector[i] = 0;
			}
		}

		// Create an Individual based off of the rounded probability vector
		Individual finalInd = new Individual(numVars, probVector);
		int ProbVectorFitness = finalInd.getFitness(clauses, numClauses);

		// Find the fitness for the global best Individual found
		int globalBestIndFitness = globalBestIndividual.getFitness(clauses, numClauses);

		// Output if probability vector Individual's fitness is highest
		if (ProbVectorFitness > globalBestIndFitness) {
			System.out.println("Total Clauses correct: " + ProbVectorFitness); // print out 3a
			System.out.println(
					"Total percentage of clauses correct: " + (((double) ProbVectorFitness / numClauses) * 100) + "%"); // print
																														// out
																														// 3b
			System.out.println("Best Individual: " + finalInd.getVars()); // print out 4
			System.out.println("Best was probability vector so number of iterations is: " + numGenerations);
		}

		// Output if the global best Individual's fitness is highest or the two are
		// equal
		if (ProbVectorFitness <= globalBestIndFitness) {
			System.out.println("Total Clauses correct: " + globalBestIndFitness); // print out 3a
			System.out.println("Total percentage of clauses correct: "
					+ (((double) globalBestIndFitness / numClauses) * 100) + "%"); // print out 3b
			System.out.println("Best Individual: " + globalBestIndividual.getVars()); // print out 4
			System.out.println("Found on iteration number: " + bestFoundIteration);
		}
	}

	// This method prints out the value of the probability vector
	public void print_vector() {
		for (int i = 0; i < probVector.length; i++) {
			System.out.println("prob of char " + i + " being 1 = " + probVector[i]);
		}
	}

}
