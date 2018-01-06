package commands;

import driver.FileSystem;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * A command that changes the current working directory to a new one
 * 
 * @author Sin
 *
 */
public class Cd extends Command {

  /**
   * FileSystem instance
   */
  private FileSystem fileSys;

  /**
   * Default constructor
   * 
   * @param fs A single working FileSystem
   */
  public Cd(FileSystem fs) {
    fileSys = fs;
  }

  public void executeCommand()
      throws InvalidPathException, InvalidNumberOfArgument {
    // check for the number of parameters
    checkNumParam(1);
    // change the directory in file system
    fileSys.changeDirectory(parameter[0]);
  }

}
