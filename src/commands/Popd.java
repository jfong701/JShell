package commands;

import driver.DirectoryStack;
import driver.FileSystem;
import driver.Folder;
import exception.EmptyDirectoryStackException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * Class to execute the popd Command: Removes the top entry from the Directory
 * Stack, and changes into that directory.
 * 
 * @author Jason Chow Fong
 */
public class Popd extends Command {

  /**
   * Instance variables for the FileSystem and DirectoryStack
   */
  private FileSystem fileSys;
  private DirectoryStack stackDir;

  /**
   * Constructor for popd, using the FileSystem and DirectoryStack from the
   * JShell Class (this object is created from JShell class)
   * 
   * @param fs: FileSystem Object from JShell
   * @param ds: DirectoryStack Object from JShell
   */
  public Popd(FileSystem fs, DirectoryStack ds) {
    fileSys = fs;
    stackDir = ds;
  }

  /**
   * Executes the popd command: removes the top entry of the DirectoryStack and
   * changes into that entry (a directory)
   * 
   * @throws InvalidNumberOfArgument
   * @throws InvalidPathException
   * @throws EmptyDirectoryStackException
   */
  public void executeCommand() throws InvalidNumberOfArgument,
      InvalidPathException, EmptyDirectoryStackException {
    checkNumParam(0);
    // have a Folder reference to keep the entry that will be popped.
    Folder dir;
    // Pop directory and get its path
    dir = stackDir.popd();
    String path = dir.getPath();

    // change to the path of the popped directory
    fileSys.changeDirectory(path);
  }
}
