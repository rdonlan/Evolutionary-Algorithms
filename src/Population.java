
public class Population {
	private int populationSize;
	private Individual[] population;

	// Initial population maker for Genetic Algorithm
	public Population(int popSize, int numVars) { // initialize first population
		this.populationSize = popSize;
		population = new Individual[populationSize];

		// fills the population with random individuals
		for (int i = 0; i < populationSize; i++) {
			population[i] = new Individual(numVars);
		}
	}

	// Population maker for PBIL
	public Population(int popSize, int numVars, double[] probVector) {
		this.populationSize = popSize;
		population = new Individual[populationSize];

		// fills the population with individuals based on percentages of probVector
		for (int i = 0; i < populationSize; i++) {
			population[i] = new Individual(numVars, probVector);
		}

	}

	// Getter for array filled with Individuals that was the Population
	public Individual[] getIndividuals() {
		return population;
	}

	// Setter that takes in an array of Individuals and sets the Population to this
	// new array
	public void setIndividuals(Individual[] newPop) {
		population = newPop;
	}

	// Getter for populationSize
	public int getSize() {
		return populationSize;
	}

	// Method that printed the bit strings of all the Individuals in the Population
	public void print_population() {
		for (int i = 0; i < populationSize; i++) {
			String ind = population[i].getVars();
			System.out.println("Ind " + (i + 1) + " is: " + ind);
		}
	}
}
