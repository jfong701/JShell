package commands;

import driver.CommandHistory;
import driver.PrintFormatter;
import java.util.regex.Pattern;

import exception.IncorrectCommandHistoryArgException;
import exception.InvalidCommandHistorySizeException;
import exception.InvalidNumberOfArgument;

/**
 * Class to execute the history Command: Display and list all or recent
 * commands
 * 
 * @author Kevin Bato
 *
 */
public class History extends Command {

  /**
   * Instantiate Command History
   */
  private CommandHistory comHistory;
  /**
   * Instantiate PrintFormatter
   */
  private PrintFormatter format;

  /**
   * History constructor that takes the current CommandHistory and
   * PrintFormatter
   * 
   * @param ch Current CommandHistory
   * @param pf PrintFormatter Object
   */
  public History(CommandHistory ch, PrintFormatter pf) {
    comHistory = ch;
    format = pf;
  }

  /**
   * Executes history command using with or without parameters
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectCommandHistoryArgException
   * 
   * @throws InvalidCommandHistoryParamTypeException
   * 
   */
  public void executeCommand() throws InvalidCommandHistorySizeException,
      InvalidNumberOfArgument, IncorrectCommandHistoryArgException {
    this.checkNumParam(1);
    // checks if parameter is empty
    if (parameter.length == 0) {
      // print all recent commands
      format.setHistory(comHistory.getLastCommands(comHistory.getSize()),
          comHistory.getSize());

      // otherwise, get recent commands
    } else if (parameter.length == 1) {
      if (Pattern.matches("([0-9]*)", parameter[0])) {
        format.setHistory(
            comHistory.getLastCommands(Integer.parseInt(parameter[0])),
            comHistory.getSize());
      } else {
        throw new IncorrectCommandHistoryArgException();
      }
    } else {
      throw new InvalidNumberOfArgument();
    }
  }


  public void checkNumParam(int num) throws InvalidNumberOfArgument {
    if (parameter.length > num) {
      throw new InvalidNumberOfArgument(
          "Error: Invalid number of parameter entered, expected " + num
              + " parameter(s)");
    }
  }


}
