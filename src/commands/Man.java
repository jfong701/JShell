package commands;

import driver.PrintFormatter;
import exception.InvalidCommandHistorySizeException;
import exception.InvalidNumberOfArgument;
import driver.CommandHistory;

/**
 * Class to execute the man Command: prints out documentation for a command
 * specified by the user.
 * 
 * @author Jason Chow Fong
 */
public class Man extends Command {

  /**
   * Instance Variables for the PrintFormatter, and command name.
   */
  private PrintFormatter format;
  private CommandHistory history;

  /**
   * Constructor for Man class, which takes in a PrintFormatter. When this
   * class is created in the JShell, the JShell's PrintFormatter is used.
   * 
   * @param pf: PrintFormatter from JShell
   */
  public Man(CommandHistory ch, PrintFormatter pf) {
    format = pf;
    history = ch;
  }

  /**
   * Given the name of a command requested by a user, this method prints out
   * the documentation for that command.
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   * @throws NumberFormatException
   */
  public void executeCommand()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    checkNumParam(1);
    String command;
    if (parameter[0].startsWith("!") && parameter[0].length() > 1) {
      command = history
          .getCommandString(Integer.parseInt(parameter[0].substring(1)) - 1);
      command = command.substring(0, command.indexOf(" "));
    } else {
      command = parameter[0];
    }

    format.setDocumentation(command);
  }
}
