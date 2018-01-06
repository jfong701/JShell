package commands;

import driver.FileSystem;
import driver.PrintFormatter;
import exception.InvalidNumberOfArgument;

/**
 * A command that returns the current working directory in full path.
 * 
 * @author Sin
 *
 */
public class Pwd extends Command {

  /**
   * File system object instance
   */
  private FileSystem fileSys;
  /**
   * Print formatter object instance
   */
  private PrintFormatter format;

  /**
   * Default Constructor
   * 
   * @param fs FileSystem instance
   * @param pf PrintFormatter instance
   */
  public Pwd(FileSystem fs, PrintFormatter pf) {
    fileSys = fs;
    format = pf;
  }

  public void executeCommand() throws InvalidNumberOfArgument {
    // check the number of parameters
    checkNumParam(0);
    // get the current directory
    String currDir = fileSys.getCurrPath();
    // set it as the output
    format.setOutput(currDir);
  }

}
