package driver;

/**
 * Allows a way to format an output tied to a specified command and print it to
 * the user
 * 
 * @author Haosen Xu
 *
 */


import java.util.ArrayList;

/**
 * Prints the text content inside a file
 * 
 * @param dataFile: file object which content will be printed
 */

public class PrintFormatter {

  /**
   * The formatted output string to be printed to user
   */
  private String output;

  /**
   * Default Constructor for PrintFormatter class
   * 
   */
  public void printFormatter() {
    output = "";
  }


  /**
   * Returns output string
   * 
   * @return output - a string
   */
  public String getOutput() {
    return output;
  }


  /**
   * Sets output string
   * 
   * @param output - an unformatted string
   */
  public void setOutput(String output) {
    this.output = output;
  }

  /**
   * Appends text into output string
   * 
   * @param text - input string
   */
  public void appendOutput(String text) {
    if (this.output == null){
      this.output = "";
    }
    this.output = output.concat(text);
  }

  /**
   * prints current 'output' string
   * 
   */
  public void printOutput() {
    System.out.println(output);
  }


  /**
   * Sets output to the formatted output of the history command
   * 
   * @param commands - list of names of most recent commands in command history
   * @param size - The number of commands in recent history to be printed
   */
  public void setHistory(ArrayList<String> commands, int size) {

    // Initialize the string that stores the output
    String historyString = "";

    // Add each of the most recent commands in the history to the output string
    for (int i = 0; i < commands.size(); i++) {

      // Format each entry
      historyString =
          historyString + Integer.toString((size - commands.size()) + i + 1)
              + ". " + commands.get(i) + "\n";
    }

    // Remove the extra new line
    historyString = historyString.substring(0, historyString.length() - 1);

    setOutput(historyString);
  }


  /**
   * Sets output to list content of directories inside an array list of content
   * 
   * @param pathContent - array list with folders and files
   */
  public void setLsContents(ArrayList<Content> pathContent) {

    // Initialize the string that stores the output
    String contentString = "";

    // Loop thorough all given content
    for (Content directory : pathContent) {

      // If the content is a file then sets output to be its name
      if (directory instanceof File) {
        contentString += directory.getName();
      }

      // Otherwise if it is a directory sets output to be in the format:
      // directory1: content1 content2 content3
      else if (directory instanceof Folder) {
        contentString += directory.getName() + ":";

        ArrayList<Content> contents = ((Folder) directory).getAllContents();

        for (Content item : contents) {
          contentString += "  " + item.toString();
        }
      }
      contentString += "\n";
    }

    // Remove any extra new lines from the output
    while (contentString.endsWith("\n")) {
      contentString = contentString.substring(0, contentString.length() - 1);
    }
    if (contentString.equals("") == false) {
      setOutput(contentString);
    }
  }

  /**
   * Sets output to list content of directories inside an array list of content
   * for recursive ls
   * 
   * @param pathContent - array list with folders and files
   */
  public void setRecLsContents(ArrayList<Content> pathContent) {

    // Initialize the string that stores the output
    String contentString = "";

    // Loop thorough all given content
    for (Content directory : pathContent) {

      // If the content is a file then sets output to be its name
      if (directory instanceof File) {
        contentString += directory.getName();
      }

      // Otherwise if it is a directory sets output to be in the format:
      // directory1: content1 content2 content3
      else if (directory instanceof Folder) {
        contentString += directory.getName() + ":";

        ArrayList<Content> contents = ((Folder) directory).getAllContents();

        for (Content item : contents) {
          contentString += "  " + item.toString();
        }
      }
    }
    // Append into output
    appendOutput(contentString + "\n");
  }


  /**
   * Sets output to list content of the current working directory
   * 
   * @param currDir - the current working directory
   */
  public void setLsNoParams(Content currDir) {

    // Initialize the string that stores the output
    String contentString = "";

    // Store contents of the directory in an array list
    ArrayList<Content> contents = ((Folder) currDir).getAllContents();

    // If the are contents to print, print the names of each one in a new line
    if (contents.size() != 0) {
      for (Content item : contents) {
        contentString += item.toString() + ("\n");
      }

      // Remove the extra new line
      contentString = contentString.substring(0, contentString.length() - 1);

      setOutput(contentString);
      // printOutput();
    }
  }


  /**
   * Set output to the manual of a given command
   * 
   * @param command - name of a command
   */
  public void setDocumentation(String command) {
    String manualString;

    try {
      // Obtain text from manual page of a given command
      manualString = Documentation.getManPage(command);
    } catch (Exception e) {
      manualString = "Error: No manual exists for command: " + command;
    }

    // Print its manual
    setOutput(manualString);
  }


  /**
   * Given an array list of files, set output to each file's text content
   * concatenated
   * 
   * @param files - files with text content to be concatenated
   */
  public void setCatFiles(ArrayList<File> files) {

    // Initialize the string that stores the output
    String catString = "";

    // Add the text content of each file to the output with separation
    // three blank lines between each text content
    for (File fileData : files) {
      catString += fileData.getData() + "\n\n\n";
    }

    // Remove extra blank lines
    if(catString.length() >= 3)
    catString = catString.substring(0, catString.length() - 3);

    setOutput(catString);

  }

  /**
   * Set output to text content inside a file
   * 
   * @param dataFile: file object which content will be printed
   */
  public void setFileContent(File dataFile) {

    String text = dataFile.getData();
    setOutput(text);
  }

}
