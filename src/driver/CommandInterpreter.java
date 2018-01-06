package driver;

/**
 * Translates user input strings into readable parameters for the
 * CommandExecutor to execute commands
 * 
 * @author Haosen Xu
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import exception.InvalidPathException;

public class CommandInterpreter {
  /**
   * String array where each element is a keyword in a user input
   */
  String[] parameters;

  /**
   * Instance of FileSystem
   */
  private FileSystem operator;

  /**
   * List of names of valid commands
   */
  List<String> validCommands =
      Arrays.asList("mkdir", "cd", "ls", "pwd", "curl", "(!.[0-9]*)", "mv",
          "cp", "cat", "pushd", "grep", "popd", "history", "echo", "man");

  /**
   * List of minimum length of parameters for each valid command
   */
  List<Integer> minParamLength =
      Arrays.asList(2, 2, 1, 1, 2, 1, 3, 3, 2, 2, 3, 1, 1, 2, 2);

  /**
   * List of valid commands that includes a path string as an argument
   */
  List<String> commandWithPaths =
      Arrays.asList("mkdir", "cd", "ls", "mv", "cp", "cat", "pushd", "grep");

  /**
   * List of valid commands that has additional recursive functionality
   */
  List<String> commandWithRecursion = Arrays.asList("ls", "grep");



  /**
   * Default Constructor
   * 
   * @param fileSys An instance of FileSystem
   */
  public CommandInterpreter(FileSystem fileSys) {
    // Set an instance of FileSystem
    operator = fileSys;
  }


  /**
   * Interpret and format an input into commands to be executed
   * 
   * @param input The string representing user's input into the JShell
   * @return A 2D array that contains both the formatted arguments for a valid
   *         command and redirection arguments
   */
  public String[][] interpretCommand(String input) {

    // Store each keyword in the input to a string array
    parameters = formatParameters(input);

    // Format any strings in parameters that denote paths into a full path
    parameters = convertToPath(parameters);

    // Check if the given command is valid and meets minimum parameter length
    boolean isValid = isCommandValid(parameters);

    // If the command is not one of the valid commands, turn it invalid
    if (!isValid) {
      parameters = new String[] {"invalid"};
    }

    // Create a new array to store strings dealing with redirection in input
    String[] redirection = new String[2];

    // Split the arguments into two separate arrays one stores formatted
    // arguments for a valid command the other for redirection
    if ((parameters.length > 2)
        && (parameters[parameters.length - 2].equals(">>")
            || parameters[parameters.length - 2].equals(">"))) {

      redirection[0] = parameters[parameters.length - 2];
      redirection[1] = parameters[parameters.length - 1];
      parameters = Arrays.copyOfRange(parameters, 0, parameters.length - 2);
    } else {
      redirection[0] = "";
      redirection[1] = redirection[0];
    }
    String[][] formattedInput = {parameters, redirection};

    return formattedInput;
  }



  /**
   * Formats a raw input string into a list of string containing keywords
   * 
   * @param input The string representing user's input into the JShell
   * @return A list of strings with each element denoting to a possible keyword
   *         found in a command
   */
  private String[] formatParameters(String input) {

    // String array used to store each keyword in an input
    String[] parameters;

    // Remove white spaces at beginning and end of the input
    input = input.trim();

    // If the input to be formatted includes the command echo, then call a
    // specific method that formats input with echo commands
    if (input.startsWith("echo ")) {
      parameters = formatEchoParams(input);
    } else {
      // Simplify excess of white space characters into 1 white space character
      while (input.contains("  ")) {
        input = input.replaceAll("  ", " ");
      }
      // Split the input into an array using white space character as delimiter
      parameters = input.split(" ");
    }
    return parameters;
  }


  /**
   * Formats a raw input string into a list of string containing keywords
   * 
   * @param parameters String array containing command keywords
   * @return A boolean that denotes whether the command is valid
   */
  private boolean isCommandValid(String[] parameters) {
    boolean isValid = false;
    boolean isRightSize = true;

    // Check if a given command matches one in the list of valid commands
    for (String command : validCommands)
      if (Pattern.matches(command, parameters[0])) {
        isValid = true;
        // Check if argument length meets the minimum length for the command
        int commandIndex = validCommands.indexOf(command);
        if (!(minParamLength.get(commandIndex) <= parameters.length)) {
          isRightSize = false;
        }
      }
    // print appropriate errors if either command not found or is
    // missing operands
    if (!isValid) {
      System.out.println("Error: Command Not Valid");
    } else if (!isRightSize) {
      System.out
          .println("Error: Missing operands for command " + parameters[0]);
    }
    // If both conditions above are satisfied then the command is valid
    return isValid && isRightSize;
  }


  /**
   * Formats a raw input string that starts with "echo " into a list of string
   * containing keywords
   * 
   * @param input The string representing user's input into the JShell
   * @return A list of strings with each element denoting to a possible keyword
   *         found in an echo command
   */
  private String[] formatEchoParams(String input) {

    ArrayList<String> paramList = new ArrayList<String>();

    // Identify the text keyword in the path and store it in a string
    int stringBegin = input.indexOf('"');
    int stringEnd = input.lastIndexOf('"');

    // Check if there's at least two double quote characters in input
    if (stringBegin == stringEnd) {
      return new String[] {"invalid"};
    }
    String stringText = input.substring(stringBegin + 1, stringEnd);

    // Formats left and right side of the text keyword into string arrays
    String[] leftOfText = formatParameters(input.substring(0, stringBegin));
    String[] rightOfText =
        formatParameters(input.substring(stringEnd + 1, input.length()));

    // Merge keywords on left side, string text and right side together
    for (String keyword : leftOfText) {
      if (!keyword.equals("")) {
        paramList.add(keyword);
      }
    }
    paramList.add(stringText);
    for (String keyword : rightOfText) {
      if (!keyword.equals("")) {
        paramList.add(keyword);
      }
    }
    // Convert the array list that stores command keywords into a string array
    String[] parameters = new String[paramList.size()];
    return paramList.toArray(parameters);
  }


  /**
   * Formats each string in a command's arguments that denotes to a path to a
   * full path
   * 
   * @param parameters String array containing a command's argument with each
   *        element being a keyword in a command call
   * @return String array with formatted parameter with full paths
   */
  private String[] convertToPath(String[] parameters) {
    // Index where the first/last path is found in a command call
    int startPath = 1;
    int endPath;
    endPath = parameters.length;
    if (parameters[0].equals("grep")) {
      startPath += 1;
    }
    // Format path for redirection part of the command call
    try {
      if (hasRedirect(parameters[parameters.length - 2])) {
        endPath = parameters.length - 2;
        parameters[parameters.length - 1] =
            formatPath(parameters[parameters.length - 1]);
      }
    } catch (IndexOutOfBoundsException e) {
    }
    if (commandWithPaths.contains(parameters[0])) {
      try {
        // Change index of first path if command is recursive and has '-R'
        if ((parameters[1].equals("-r") || parameters[1].equals("-R"))
            && commandWithRecursion.contains(parameters[0])) {
          startPath += 1;
        }
      } catch (IndexOutOfBoundsException e) {
      }
      // Format the path keywords into a full path
      for (int i = startPath; i < endPath; i++) {
        parameters[i] = formatPath(parameters[i]);
      }
    }
    return parameters;
  }


  /**
   * Return if the parameter is any of redirection symbol
   * 
   * @param parm The parameter being checked
   * @return True if parm is a redirection symbol
   */
  private boolean hasRedirect(String parm) {
    return parm.equals(">") || parm.equals(">>");
  }


  /**
   * Formats a string input into a string representing an absolute path and
   * formats '.' and '..' in a path
   * 
   * @param path The string representing an unformatted path
   * @return A string formatted absolute path
   */
  private String formatPath(String path) {
    // Check if the path is a full path if not turn it into a full path
    path = toFullPath(path);

    // Remove all instances of the '.' character, since in unix it is a special
    // character denoting to current directory. Thus /dir1/./. == /dir is true
    path = replaceSingleDots(path);

    // Replace all instances of the '..' character, since in unix it is a
    // special character denoting to parent directory.
    // Thus /../dir1/../.. == / is true
    path = replaceDoubleDots(path);

    // Remove the last slash character from the path (if it is a directory)
    try {
      if (!(path.equals("/")) && path.endsWith("/") && operator.getPathContent(
          path.substring(0, path.length() - 1)) instanceof Folder) {
        path = path.substring(0, path.length() - 1);
      }
    } catch (InvalidPathException e) {
    }

    // If the path is empty, add '/' which denotes the root
    if (path.equals("")) {
      path = "/";
    }

    return path;
  }

  /**
   * Converts a given path to a full path without taking to account '.' and '..'
   * symbols in a path
   * 
   * @param path The string representing an unformatted path
   * @return A string formatted absolute path
   */
  private String toFullPath(String path) {
    // Get the path of the current working directory
    String currDir = operator.getCurrPath();

    // Check if the path is a full path if not turn it into a full path
    if (path.startsWith("/") == false) {
      if (currDir.equals("/")) {
        path = currDir + path;
      } else {
        path = currDir + "/" + path;
      }
    }
    return path;
  }


  /**
   * Formats instances of '.' string inside with an equivalent path
   * 
   * @param path The string representing an unformatted path
   * @return A string formatted path
   */
  private String replaceSingleDots(String path) {
    // Remove all instances of the '.' character, since in unix it is a special
    // character denoting to current directory. Thus /dir1/./. == /dir is true
    while (path.contains("/./")) {
      path = path.replace("/./", "/");
    }
    // Remove the '.' character found at the end of a path
    if (path.endsWith("/.")) {
      path = path.substring(0, path.length() - 2);
    }
    return path;
  }


  /**
   * Formats instances of '..' string inside with an equivalent path
   * 
   * @param path The string representing an unformatted path
   * @return A string formatted path
   */
  private String replaceDoubleDots(String path) {
    // A subpath in path
    String subpath;
    // A boolean representing whether a path exists in the file system
    boolean pathValid = true;
    // startIndex and endIndex denotes the indexes of directory before a '..'
    int endIndex;
    int startIndex;

    // Format the '..' special character with the parent of current subpath
    while (path.contains("/..") == true && pathValid) {

      // If the path begins with "/.." simplify it to ""
      if (path.startsWith("/..")) {
        path = path.replaceFirst("/..", "");
      } else {
        // Let the subpath be the path up to the first instance of '/..'
        endIndex = path.indexOf("/..");
        subpath = path.substring(0, endIndex);
        startIndex = subpath.lastIndexOf("/");
        pathValid = operator.pathExist(subpath);
        // If the subpath is a valid one remove the directory before the '..'
        if (pathValid) {
          path = path.substring(0, startIndex) + path.substring(endIndex + 3);
        }
      }
      // If the subpath is not a valid one, make the path invalid also
      if (pathValid == false) {
        path = "Invalid path";
      }
    }
    return path;
  }

}
