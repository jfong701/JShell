package commands;

import exception.EmptyDirectoryStackException;
import exception.HasSameContentNameException;
import exception.IncorrectCommandHistoryArgException;
import exception.IncorrectContentTypeException;
import exception.InvalidCommandException;
import exception.InvalidCommandHistorySizeException;
import exception.InvalidNameException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;
import exception.InvalidRegexException;
import exception.InvalidURLException;
import exception.MkdirMultiException;

/**
 * Represents a command that user can be executed.
 * 
 * @author Sin
 *
 */
public class Command {
  /**
   * The string array of the parameters/arguments
   */
  protected String[] parameter;


  /**
   * Default constructor
   */
  public Command() {}

  /**
   * Execute the command
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   * @throws InvalidURLException
   * @throws InvalidNameException
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidRegexException
   * @throws MkdirMultiException
   * @throws InvalidNumberOfArgument
   * @throws IncorrectCommandHistoryArgException
   * @throws EmptyDirectoryStackException
   * @throws InvalidCommandException
   * @throws NumberFormatException
   * 
   */
  public void executeCommand() throws HasSameContentNameException,
      InvalidPathException, IncorrectContentTypeException, InvalidURLException,
      InvalidNameException, InvalidCommandHistorySizeException,
      InvalidRegexException, MkdirMultiException, InvalidNumberOfArgument,
      IncorrectCommandHistoryArgException, EmptyDirectoryStackException,
      NumberFormatException, InvalidCommandException {}

  /**
   * Set the parameter for the command to be executed
   * 
   * @param param An array of parameters/arguments
   */
  public void setParam(String[] param) {
    parameter = param;

  }

  /**
   * Check for the number of parameter stored in the command object, if it does
   * not match the required number. an error will raise.
   * 
   * @param num The expected number of parameters
   * @throws InvalidNumberOfArgument
   */
  public void checkNumParam(int num) throws InvalidNumberOfArgument {
    if (parameter.length != num) {
      throw new InvalidNumberOfArgument(
          "Error: Invalid number of parameter entered, expected " + num
              + " parameter(s)");
    }
  }

}
