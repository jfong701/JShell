package driver;

/**
 * Stores all user input and retrieve previous commands
 * 
 * @author Kevin Bato
 */

import java.util.ArrayList;

import exception.InvalidCommandHistorySizeException;

public class CommandHistory {

  // Declare variables
  private ArrayList<String> commandList;
  private int size = 0;

  /**
   * Constructor: Creates an empty Command List
   */
  public CommandHistory() {

    commandList = new ArrayList<String>();

  }

  /**
   * Appends new command into command history
   * 
   * @param new_command User input command
   */
  public void addCommand(String new_command) {

    // Adds command into history
    commandList.add(this.getSize(), new_command);
    // increment size
    size++;

  }

  /**
   * 
   * An integer parameter will display the last n commands in command list.
   * 
   * @param n is an integer from n to the last command
   * @throws Exception
   * @return output, output is the last-n-commands. From the least to most
   *         recent commands.
   * 
   */
  public ArrayList<String> getLastCommands(int n)
      throws InvalidCommandHistorySizeException {

    // Create output for return
    ArrayList<String> output = new ArrayList<String>();

    // Catch size exceptions
    if (n > this.getSize() || n < 0) {

      throw new InvalidCommandHistorySizeException();

    } else {

      // Loop through to add last-n-commands
      for (int i = 0; i < n; i++) {
        output.add(i, this.commandList.get((this.getSize() - (n - i))));
      }
    }
    return output;
  }

  // Get command string from commandList
  public String getCommandString(int index)
      throws InvalidCommandHistorySizeException {
    if (index < this.getSize() && index >= 0) {
      return this.commandList.get(index);
    } else {
      throw new InvalidCommandHistorySizeException();
    }

  }

  // Get size
  public int getSize() {
    return this.size;
  }

}
