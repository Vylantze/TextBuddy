

import java.io.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.Scanner;

public class TextBuddy { 
	// strings used in the application
	private static final String DEFAULT_FILENAME = "youForgotYourFilename.txt";
	private static final String MESSAGE_COMMAND = "command: ";
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy, dood. %1$s is ready for use, dood.";
	private static final String MESSAGE_ADD = "Added \"%1$s\" to %2$s";
	private static final String MESSAGE_ADD_ERROR = "Failed to add \"%1$s\" to %2$s";
	private static final String MESSAGE_EMPTY = "%1$s is most unfortunately empty";
	private static final String MESSAGE_CLEAR = "All content has been deleted from %1$s, dood";
	private static final String MESSAGE_DELETE = "\"%1$s\" has been deleted from %2$s";
	private static final String MESSAGE_DELETE_ERROR = "%1$s is an invalid number dood!";
	private static final String MESSAGE_INVALID_ERROR = "\"%1$s\" is not a valid command dood!";
	private static final String MESSAGE_EXIT = "Kthxbye";
	
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_DISPLAY = "display";
	private static final String COMMAND_CLEAR = "clear";
	private static final String COMMAND_EXIT = "exit";
	
	private static ArrayList<String> _array = new ArrayList<String>();
	private static String _fileName = "";
	private static Boolean _isRunning = true;
	
	/*
	 * This variable is declared for the whole class (instead of declaring it
	 * inside the readUserCommand() method to facilitate automated testing using
	 * the I/O redirection technique. If not, only the first line of the input
	 * text file will be processed.
	 */
	private static Scanner scanner = new Scanner(System.in);
	
	// These are the possible commands
	enum COMMAND_TYPE {
		ADD, // appends a string to the end of the current contents
		DISPLAY, // display the entire current contents
		DELETE, // takes one parameter (a number) and deletes that line number
		CLEAR, // deletes all the contents of the array
		EXIT, // an exit command to terminate the program
		INVALID // if the command given is not a valid command
	};


	public static void main(String[] args){
		readFile(args);	
		welcomeUser();
		while (_isRunning) { // keep looping until exit command given
			String userCommand = promptCommand();
			String feedback = executeCommand(userCommand);
			showToUser(feedback);
			writeToFile(); // assuming that array size < 1000
			// and that the user expects the txt file to be updated live
		}
		
		System.exit(0); // when the loop ends terminate the application 
	}
	
	/**
	 * Indicates to the user that commands can now be input
	 */
	private static String promptCommand() {
		System.out.print(MESSAGE_COMMAND);
		return scanner.nextLine();
	}
	
	/**
	 * Things to do before letting the user input commands
	 */
	private static void welcomeUser() {
		showToUser(String.format(MESSAGE_WELCOME, _fileName));
	}
	
	private static void showToUser(String text) {
		System.out.println(text);
	}
	
	/**
	 * Writes the contents of _array into a file named _fileName
	 */
	private static void writeToFile() {
		try {
			File file = new File(_fileName);
			file.createNewFile();
			FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0;i<_array.size();i++) {
				bw.write(_array.get(i) + "\n");
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	/**
	 * This operation determines which of the supported command types the user
	 * wants to perform
	 * 
	 * @param commandTypeString
	 *            is the first word of the user command
	 */
	private static COMMAND_TYPE determineCommandType(String commandTypeString) {
		if (commandTypeString == null) { // additional error check
			return COMMAND_TYPE.INVALID; // even if error will return this
		} else if (commandTypeString.equalsIgnoreCase(COMMAND_DISPLAY)) {
			return COMMAND_TYPE.DISPLAY;
		} else if (commandTypeString.equalsIgnoreCase(COMMAND_ADD)) {
			return COMMAND_TYPE.ADD;
		} else if (commandTypeString.equalsIgnoreCase(COMMAND_DELETE)) {
			return COMMAND_TYPE.DELETE;
		} else if (commandTypeString.equalsIgnoreCase(COMMAND_CLEAR)) {
			return COMMAND_TYPE.CLEAR;
		} else if (commandTypeString.equalsIgnoreCase(COMMAND_EXIT)) {
		 	return COMMAND_TYPE.EXIT;
		} else {
			return COMMAND_TYPE.INVALID; 
		}
	}
	
	private static String executeCommand(String userCommand) {
		COMMAND_TYPE commandType = determineCommandType(getFirstWord(userCommand));
		
		switch (commandType) { // checks the commands by cross referencing the valid commands
		case ADD:
			return add(removeFirstWord(userCommand));
		case DISPLAY:
			return display();
		case DELETE:
			return remove(removeFirstWord(userCommand));
		case CLEAR:
			return clear();
		case EXIT:
			return exit();
		default:
			return invalid(userCommand); 
		}
	}
	
	/**
	 * What happens when an invalid input occurs
	 */
	public static String invalid(String command) {
		return String.format(MESSAGE_INVALID_ERROR, command);
	}
	
	/**
	 * Ends the application
	 */
	public static String exit() {
		_isRunning = false;
		return MESSAGE_EXIT;
	}
	
	/**
	 * Appends the string provided to the back of the array
	 */
	public static String add(String toBeAdded) {
		toBeAdded = toBeAdded.trim();
		if (toBeAdded.length()!=0) {
			_array.add(toBeAdded);
			return String.format(MESSAGE_ADD, toBeAdded, _fileName);
		}
		else {
			return String.format(MESSAGE_ADD_ERROR, toBeAdded, _fileName);
		}
	}
	
	/**
	 * Displays the items in the array, numbering them in order
	 */
	public static String display() {
		String toBeReturned = "";
		if (_array.size()==0) {
			toBeReturned = String.format(MESSAGE_EMPTY, _fileName);
		}
		else {
			for (int i=0; i<_array.size();i++) {
				toBeReturned += i+1 + ". " + _array.get(i);
				if (i<_array.size()-1) { // to prevent an additional addline in the main program
					toBeReturned += "\n"; 
				}
			}
		}
		
		return toBeReturned;
	}
	
	/**
	 * Deletes a specific item in the array
	 */
	public static String remove(String command) {
		int index = convertToInt(command);
		if (index>0&&index<=_array.size()) {
			return String.format(MESSAGE_DELETE, 
					_array.remove(index-1), _fileName);
		}
		else {
			return String.format(MESSAGE_DELETE_ERROR, command);
		}
	}
	
	/**
	 * Deletes all items in the array
	 */
	public static String clear() {
		_array.clear();
		return String.format(MESSAGE_CLEAR, _fileName);
	}
	
	/**
	 * Opens the file and extracts the data into an array
	 * @param file
	 */
	public static void readFile(String[] fileName){
		if (fileName.length>0) {
			_fileName = fileName[0];
		}
		else {
			_fileName = DEFAULT_FILENAME;
		}
		try{
			File file = new File(_fileName);
			FileReader f = new FileReader(file);
			BufferedReader b = new BufferedReader(f);
			String line = "";
			while(b.ready()) {
				line = b.readLine();
				line = line.trim(); 
				if (line!="") {
					_array.add(line);
				}
			}
			b.close();
			f.close();
		}
		catch(IOException e)
		{
			//System.out.println("Error reading file: "+ e);
			// ignore the exception as 
			// a new file will be recreated later
		}
	}
	
	private static String getFirstWord(String userCommand) {
		return userCommand.trim().split("\\s+")[0];
	}
	
	private static String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}
	
	private static int convertToInt(String s) {
		try {
			int i = Integer.parseInt(s.trim());
			//return true if i is greater than 0
			return i;
		} catch (NumberFormatException nfe) {
			return -1; // -1 will not be used for this program
		}
	}

}

