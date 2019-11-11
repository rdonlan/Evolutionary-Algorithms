import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GeneticAlgorithm {

	private int numVars;
	private int numClauses;
	private int popSize;
	private int numGenerations;
	private String selectionType;
	private String crossoverMethod;
	private float crossoverProb;
	private float mutationProb;
	private Population pop;
	private int[][] clauses;

	// Constructor for Genetic Algorithm
	public GeneticAlgorithm(int numVars, int numClauses, int popSize, int numGenerations, String selectionType,
			String crossoverMethod, float crossoverProb, float mutationProb, int[][] clauses) {
		this.numVars = numVars;
		this.numClauses = numClauses;
		this.popSize = popSize;
		this.numGenerations = numGenerations;
		this.selectionType = selectionType;
		this.crossoverMethod = crossoverMethod;
		this.crossoverProb = crossoverProb;
		this.mutationProb = mutationProb;
		this.clauses = clauses;

		// Creates the initial population
		pop = new Population(popSize, numVars);
	}

	// This method runs the iterations of Genetic Algorithm
	public void runGA() {
		int numIterations = 0;

		// Store the global best individual and its fitness and the iteration it was
		// found on
		Individual globalBestIndividual = pop.getIndividuals()[0];
		int globalBestFitness = globalBestIndividual.getFitness(clauses, numClauses);
		int bestFoundIteration = 0;

		// This while loop runs the process of (1) selection, (2) recombination, and (3)
		// mutation until it runs the
		// desired amount of iterations or the optimal solution is found
		while (numIterations != numGenerations && globalBestIndividual.getFitness(clauses, numClauses) != numClauses) {

			// selection
			if (selectionType.equals("ts")) {
				this.tournamentSelect();
			} else if (selectionType.equals("rs")) {
				this.rankSelect();
			} else if (selectionType.equals("gs")) {
				this.groupSelect();
			}

			// recombination
			if (crossoverMethod.equals("1c")) {
				this.onePointCrossover();
			} else if (crossoverMethod.equals("uc")) {
				this.uniformCrossover();
			}

			// mutation
			this.mutation(mutationProb);

			// edit best individuals
			for (int k = 0; k < popSize; k++) {
				int currentIndFitness = pop.getIndividuals()[k].getFitness(clauses, numClauses);

				// Found a new best Individual
				if (currentIndFitness > globalBestFitness) {
					globalBestIndividual = pop.getIndividuals()[k];
					globalBestFitness = globalBestIndividual.getFitness(clauses, numClauses);
					bestFoundIteration = numIterations + 1;
				}
			}
			numIterations++;
		}

		// Print Statements for the output
		System.out.println("Total Clauses correct: " + globalBestFitness); // print out 3a
		System.out.println(
				"Total percentage of clauses correct: " + (((double) globalBestFitness / numClauses) * 100) + "%"); // print
																													// out
																													// 3b
		System.out.println("Best Individual: " + globalBestIndividual.getVars()); // print out 4
		System.out.println("Found on iteration number: " + bestFoundIteration); // prints out 5

	}

	// This method executes one point crossover
	public void onePointCrossover() {
		for (int i = 0; i < popSize / 2; i += 1) {
			int crossoverInteger = (int) (crossoverProb * 1000);
			Random rn = new Random();
			int swapOrNot = rn.nextInt(1000);
			int swapPoint = rn.nextInt(numVars);

			// Execute a crossover
			if (swapOrNot <= crossoverInteger) {
				String temp1 = pop.getIndividuals()[i].getVars().substring(0, swapPoint)
						+ pop.getIndividuals()[popSize - 1 - i].getVars().substring(swapPoint);
				String temp2 = pop.getIndividuals()[popSize - 1 - i].getVars().substring(0, swapPoint)
						+ pop.getIndividuals()[i].getVars().substring(swapPoint);

				// Reset the bit strings of the Individuals to their new bit strings
				pop.getIndividuals()[i].setVars(temp1);
				pop.getIndividuals()[popSize - 1 - i].setVars(temp2);
			}
		}
	}

	// This method executes uniform crossover
	public void uniformCrossover() {
		for (int i = 0; i < popSize; i += 2) {
			String newString = "";
			int crossOverInteger = (int) (crossoverProb * 1000);
			Random rn = new Random();
			int swapOrNot = rn.nextInt(1000);

			// Execute a crossover
			if (swapOrNot <= crossOverInteger) {
				// For each bit randomly decide which parent it will take a bit from
				for (int j = 0; j < numVars; j++) {
					int choosingNum = rn.nextInt(2);
					if (choosingNum == 0) { // gives a 0 or a 1
						newString = newString + pop.getIndividuals()[i].getVars().charAt(j);
					}
					if (choosingNum == 1) { // gives a 0 or a 1
						newString = newString + pop.getIndividuals()[i + 1].getVars().charAt(j);
					}
				}
				// Reset the bit strings of the Individuals to their new bit strings
				pop.getIndividuals()[i].setVars(newString);
				pop.getIndividuals()[i + 1].setVars(newString);
			}
		}
	}

	// Method that printed the bit strings and fitness of all the Individuals in the
	// Population
	public void find_Fitness_of_all_inds_to_print() {
		for (int i = 0; i < popSize; i++) {
			System.out.println(pop.getIndividuals()[i].getVars());
			System.out.println(
					"ind " + (i + 1) + "'s fitness: " + pop.getIndividuals()[i].getFitness(clauses, numClauses));
		}
	}

	// This method executes mutation
	public void mutation(float mutationProb) {
		for (int i = 0; i < popSize; i++) {
			for (int j = 0; j < numVars; j++) {
				float mutationInteger = mutationProb * 1000;
				Random rn = new Random();
				int mutate = rn.nextInt(1000);

				// Execute a mutation
				if (mutate < mutationInteger) {
					char wantToChange = (pop.getIndividuals()[i]).getVars().charAt(j);
					if (wantToChange == '0') {
						String subString1 = pop.getIndividuals()[i].getVars().substring(0, j);
						String subString2 = "1";
						String subString3 = pop.getIndividuals()[i].getVars().substring(j + 1, numVars);
						String newIndString = subString1 + subString2 + subString3;

						// Reset the bit strings of the Individuals to their new bit strings
						pop.getIndividuals()[i].setVars(newIndString);
					}
					if (wantToChange == '1') {
						String subString1 = pop.getIndividuals()[i].getVars().substring(0, j);
						String subString2 = "0";
						String subString3 = pop.getIndividuals()[i].getVars().substring(j + 1, numVars);
						String newIndString = subString1 + subString2 + subString3;

						// Reset the bit strings of the Individuals to their new bit strings
						pop.getIndividuals()[i].setVars(newIndString);
					}
				}
			}
		}
	}

	// This method executes tournament selection
	public void tournamentSelect() {
		for (int i = 0; i < popSize; i += 2) {
			if (pop.getIndividuals()[i].getFitness(clauses, numClauses) > pop.getIndividuals()[i + 1]
					.getFitness(clauses, numClauses)) {
				pop.getIndividuals()[i + 1] = pop.getIndividuals()[i];
			} else {
				pop.getIndividuals()[i] = pop.getIndividuals()[i + 1];
			}
		}
	}

	// This method executes group selection by splitting the group into 2 halves and
	// adding up the fitness of each half. Then
	// the half with the greater fitness was duplicated and a new population was
	// created
	public void groupSelect() {
		int groupOneFitness = 0;
		int groupTwoFitness = 0;
		// First half of the population
		for (int i = 0; i < popSize / 2; i++) {
			groupOneFitness += pop.getIndividuals()[i].getFitness(clauses, numClauses);
		}
		// Second half of the population
		for (int j = popSize / 2; j < popSize; j++) {
			groupTwoFitness += pop.getIndividuals()[j].getFitness(clauses, numClauses);
		}

		// Determine which half to keep and adjust the population accordingly
		if (groupOneFitness > groupTwoFitness) {
			int replacement = 0;
			for (int n = popSize / 2; n < popSize; n++) {
				pop.getIndividuals()[n] = pop.getIndividuals()[replacement];
				replacement++;
			}
		}
		if (groupTwoFitness > groupOneFitness) {
			int replacement = popSize / 2;
			for (int m = 0; m < popSize / 2; m++) {
				pop.getIndividuals()[m] = pop.getIndividuals()[replacement];
				replacement++;
			}
		}
	}

	// This method implements rank select
	public void rankSelect() {
		Individual[] individuals = pop.getIndividuals();
		TreeMap<Integer, List<Individual>> sortedRank = new TreeMap<Integer, List<Individual>>();
		int thisRank = 0;

		// Gets fitness of each individual and puts it into sorted map
		for (int i = 0; i < popSize; i++) {
			thisRank = individuals[i].getFitness(clauses, numClauses);
			if (sortedRank.containsKey(thisRank)) {
				List<Individual> myList = sortedRank.get(thisRank);
				myList.add(individuals[i]);
				sortedRank.put(thisRank, myList);
			} else {
				List<Individual> myList = new ArrayList<Individual>();
				myList.add(individuals[i]);
				sortedRank.put(thisRank, myList);
			}

		}

		// Create an array of individuals in rank order rankedIndividual[i]
		Individual[] rankedIndividuals = new Individual[popSize + 1];
		rankedIndividuals[0] = null; // no individual ranked 0

		// Iterate over sorted map to put into array
		int counter = 1;
		List<Integer> probArray = new ArrayList<Integer>();
		for (Integer key : sortedRank.keySet()) {
			List<Individual> thisList = sortedRank.get(key);
			for (int i = 0; i < thisList.size(); i++) {
				rankedIndividuals[counter] = thisList.get(i);
				for (int j = 0; j < counter; j++) {
					probArray.add(counter);
				}
				counter++;
			}
		}

		// Generate N random individuals to replace the current population
		Random rand = new Random();
		for (int i = 0; i < popSize; i++) {
			int probability = rand.nextInt(probArray.size());
			int chosenRank = Integer.valueOf(probArray.get(probability));
			individuals[i] = rankedIndividuals[chosenRank];
		}
		pop.setIndividuals(individuals);

	}

}
