/**
 * This is the main method. 
 * It handles the input line parameters and reading the data into strings.
 * The DependencyEvaluator does most of the work after the data is read in.
 * 
 * I use the global LOGGER throughout to log message at various levels.
 * 
 * This main program sets the default input file name,
 * Parses the command line arguments to see if there is an input file override
 * Checks to make sure the input file exists
 * Reads the input file into an ArrayList<String>
 * Uses the DependencyEvaluator to evaluate the dependencies & generate the output string.
 * 
 * @author reed
 */
package rbb.mdexample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	private final static int STATUS_ERROR = -1;
	private final static int STATUS_NORMAL = 0;
	private final static int STATUS_USAGE = 1;
	private final static int STATUS_NOFILE = 2;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {

		LOGGER.setLevel(Level.SEVERE);
		String inputFileName = "data/input.dat";

		List<String> inputData = new ArrayList<>();

		// If there are command line args, parse them and look for the input file name.
		if (args.length > 0) {
			// if have command line args, there must be 2 
			if (args.length != 2) {
				printUsage();
			}
		
			// Make sure the first arg is a -f
			if ("-f".equals(args[0])) {
				inputFileName = args[1];
			} else {
				printUsage();
			}
		}

		// Check to make sure the input file exists - if not, print and error and exit
		File f = new File(inputFileName);
		if (!f.exists()) {
			System.err.println("Can't find file: " + inputFileName);
			System.exit(STATUS_NOFILE);
		}
		
		// Read the input file into a list - printing out information and exiting if there is an error.
		LOGGER.info("Loading data from " + inputFileName);
		try (Stream<String> stream = Files.lines(Paths.get(inputFileName))) {
			inputData = stream.collect(Collectors.toList());
		} catch (IOException e) {
			System.err.println("Error reading input data");
			e.printStackTrace();
			System.exit(STATUS_ERROR);
		}
		
		// Evaluate the dependencies in the input file
		DependencyEvaluator de = new DependencyEvaluator(inputData);
		String result = de.generateOutput();
		System.out.println(result);
		
		// Exit with a normal status.
		System.exit(STATUS_NORMAL);
	}

	/**
	 * Print out a usage message and exit with status of STATUS_USAGE;
	 */
	private static void printUsage() {
		System.err.println("Usage: java -jar DependencyEvaluator.jar ");
		System.err.println("       [-f <fileName>] //Optional argument to specify an input file - default = input.dat");
		System.exit(STATUS_USAGE);
	}
}
