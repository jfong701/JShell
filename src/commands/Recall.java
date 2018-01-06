package commands;

import driver.CommandExecution;
import driver.CommandHistory;
import driver.CommandInterpreter;
import driver.FileSystem;
import exception.InvalidCommandException;
import exception.InvalidCommandHistorySizeException;
import exception.InvalidNumberOfArgument;

/**
 * Class to execute the !number Command: Retrieve and recall(execute) the
 * numerical entry from the command history.
 * 
 * @author Kevin Bato
 * 
 */
public class Recall extends Command {

  /**
   * Instantiate CommandHistory
   */
  private CommandHistory comHist;

  /**
   * Instantiate CommandInterpreter
   */
  private CommandInterpreter ci;

  /**
   * Instantiate CommandExecution
   */
  private CommandExecution comExe;

  /**
   * Recall constructor that takes the current CommandExection, CommandHistory,
   * FileSystem
   * 
   * @param exec CommandExection object
   * @param ch Current CommandHistory
   * @param fs Current FileSystem
   */
  public Recall(CommandExecution exec, CommandHistory ch, FileSystem fs) {

    comHist = ch;
    comExe = exec;

    // Create CommandInterpreter Object using FileSystem
    ci = new CommandInterpreter(fs);
  }

  /**
   * Execute !number command using a parameter
   * 
   * @throws InvalidNumberOfArgument
   * @throws InvalidCommandHistorySizeException
   * @throws NumberFormatException
   * @throws InvalidCommandException
   * 
   */
  public void executeCommand()
      throws InvalidNumberOfArgument, NumberFormatException,
      InvalidCommandHistorySizeException, InvalidCommandException {
    checkNumParam(1);
    String comString = "";
    try {
      // get command string with parameter
      comString = comHist.getCommandString(Integer.parseInt(parameter[0]) - 1);
    } catch (NumberFormatException e) {
      throw new InvalidCommandException();
    }
    // format command string
    String[][] formatted_cmd = ci.interpretCommand(comString);
    // add recalled command into current history
    comHist.addCommand(comString);
    // if command is command is valid, execute
    if (!formatted_cmd[0][0].equals("invalid")) {
      comExe.execute(formatted_cmd);
    }
  }
}
