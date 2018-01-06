package commands;

import driver.DirectoryStack;
import driver.FileSystem;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * Class to execute the pushd Command: pushes the current directory onto the
 * Directory Stack, and changes to the directory specified in the command
 * argument.
 * 
 * @author Jason Chow Fong
 */
public class Pushd extends Command {

  /**
   * Instance variables for the FileSystem, DirectoryStack, and path of
   * directory we want to change to.
   */
  private FileSystem fileSys;
  private DirectoryStack stackDir;

  /**
   * Constructor for pushd, using the FileSystem and DirectoryStack from the
   * JShell Class (this object is created from JShell class)
   * 
   * @param fs: FileSystem Object from JShell
   * @param ds: DirectoryStack Object from JShell
   */
  public Pushd(FileSystem fs, DirectoryStack ds) {
    fileSys = fs;
    stackDir = ds;
  }

  /**
   * Executes the pushd command: pushes the working directory into the JShell's
   * DirectoryStack, then switches the new current directory to param path.
   * 
   * @param path: directory path we want to change to.
   * @throws InvalidNumberOfArgument
   */
  public void executeCommand() throws InvalidNumberOfArgument {
    checkNumParam(1);
    // Push current working directory to DirectoryStack
    stackDir.pushd(fileSys.getCurrFolder());

    // Change directory to given path.
    try {
      fileSys.changeDirectory(parameter[0]);
    } catch (InvalidPathException e) {
      System.out.println(e.getMessage());
    }
  }
}
