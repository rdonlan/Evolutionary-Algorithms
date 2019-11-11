import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Execute {

	// Main method that helps to run the program
	public static void main(String[] args) throws IOException {

		System.out.println("Type out next command line instruction after full output has been printed");
		System.out.println("Type out first instruction to get program started!!!");
		Scanner sc = new Scanner(System.in);
		String experiment = sc.nextLine();

		// Allows for constant reading of user inputs
		while (!experiment.matches("STOP")) {
			// Run the program with experiment as the user given command line
			runExecute(experiment);
			System.out.println("\n");
			experiment = sc.nextLine();
		}
		System.out.println("Program ended");
		sc.close();
	}

	// This method will create a GA or PBIL based on the user given command line
	public static void runExecute(String experiment) throws IOException {
		String[] userInput = new String[8];
		userInput = experiment.split(" ");
		String filename = userInput[0];
		System.out.println("filename: " + filename); // #1 of output
		int[] info = read_file_for_info(filename);
		int[][] allClauses = read_file_for_clauses(filename, info[1]);

		// Info needed for both PBIL and GA
		int numVar = info[0];
		System.out.println("number of variables: " + numVar); // #2a of output
		int numClauses = info[1];
		System.out.println("number of clauses: " + numClauses); // #2b of output
		int populationSize = Integer.parseInt(userInput[1]);
		int numGenerations = Integer.parseInt(userInput[6]);
		String algorithmType = userInput[7];

		// Determines if PBIL must be created and so the correct information will be
		// parsed and passed into PBIL constructor
		if (algorithmType.equals("p")) {
			float posLearningRate = Float.parseFloat(userInput[2]);
			float negLearningRate = Float.parseFloat(userInput[3]);
			float mutationProbPBIL = Float.parseFloat(userInput[4]);
			float mutationAmount = Float.parseFloat(userInput[5]);

			// Instantiates and new instance of PBIL
			PBIL test = new PBIL(numVar, numClauses, populationSize, numGenerations, posLearningRate, negLearningRate,
					mutationProbPBIL, mutationAmount, allClauses);
			
			// Starts the evolution process
			test.runPBIL();
		}

		// Determines if GA must be created and so the correct information will be
		// parsed and passed into GA constructor
		if (algorithmType.equals("g")) {
			String selectionType = userInput[2];
			String crossoverMethod = userInput[3];
			float crossoverProb = Float.parseFloat(userInput[4]);
			float mutationProb = Float.parseFloat(userInput[5]);

			// Instantiates and new instance of PBIL
			GeneticAlgorithm test = new GeneticAlgorithm(numVar, numClauses, populationSize, numGenerations,
					selectionType, crossoverMethod, crossoverProb, mutationProb, allClauses);
			
			// Starts the evolution process
			test.runGA();
		}
	}

	// This method creates a 2D array that stores all of the clauses from the file
	public static int[][] read_file_for_clauses(String filename, int numClauses) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String str;
		ArrayList<String> lineList = new ArrayList<String>();

		// Turns file into an array
		while ((str = in.readLine()) != null) {
			lineList.add(str);
		}

		// Change arraylist to array so we can index into it
		String[] stringArr = lineList.toArray(new String[0]);

		int[][] allClauses = new int[numClauses][];
		int clauseCount = 0;
		for (int i = 0; i < stringArr.length; i++) {
			String line = stringArr[i];
			if (!line.subSequence(0, 1).equals("p") && !line.subSequence(0, 1).equals("c")) {
				String fixedLine = (String) stringArr[i].subSequence(0, stringArr[i].length() - 2);
				String[] fixedLineArray = fixedLine.split(" ");
				int[] fixedLineArrayInts = new int[fixedLineArray.length];
				for (int j = 0; j < fixedLineArray.length; j++) {
					fixedLineArrayInts[j] = Integer.parseInt(fixedLineArray[j]);
				}
				allClauses[clauseCount] = fixedLineArrayInts;
				clauseCount += 1;
			}
		}
		in.close();
		return allClauses;
	}

	// This method reads the file to find the number of variables and number of
	// clauses
	public static int[] read_file_for_info(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String str;
		ArrayList<String> lineList = new ArrayList<String>();

		// Turns file into an array
		while ((str = in.readLine()) != null) {
			lineList.add(str);
		}

		// Change arraylist to array so we can index into it
		String[] stringArr = lineList.toArray(new String[0]);

		int[] info = new int[2];
		for (int i = 0; i < stringArr.length; i++) {
			String line = stringArr[i];
			if (line.subSequence(0, 1).equals("p")) { // find first line
				String[] firstLine = line.split(" ");
				if (firstLine.length == 5) {
					int numVar = Integer.parseInt(firstLine[2]);
					int numClauses = Integer.parseInt(firstLine[4]);
					info[0] = numVar;
					info[1] = numClauses;
				} else {
					int numVar = Integer.parseInt(firstLine[2]);
					int numClauses = Integer.parseInt(firstLine[3]);
					info[0] = numVar;
					info[1] = numClauses;
				}
			}
		}
		in.close();
		return info;
	}

}
