package commands;

import driver.Content;
import driver.FileSystem;
import driver.Folder;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

public class Mv extends Command {

  /**
   * FileSystem instance
   */
  private FileSystem fileSys;
  /**
   * Cp command for copying process
   */
  private Cp copy;

  /**
   * Default Constructor
   * 
   * @param fs
   */
  public Mv(FileSystem fs) {
    fileSys = fs;
    copy = new Cp(fileSys);
  }

  /**
   * Move the content from path a to path b. If path b does not exist, change
   * name of path a to path b
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   * @throws InvalidNumberOfArgument
   * 
   */
  public void executeCommand()
      throws HasSameContentNameException, InvalidPathException,
      IncorrectContentTypeException, InvalidNumberOfArgument {
    // check for the number of parameters
    checkNumParam(2);
    Content target_content = null;
    Folder moving_location = null;
    // check if the first path is a parent of second path
    int path_index = parameter[1].indexOf(parameter[0]);
    // check if its moving into a sub directory
    if (path_index == 0 && parameter[1].charAt(parameter[0].length()) == '/')
      throw new InvalidPathException(
          "Error: Cannot move directory into it sub directory");

    target_content = fileSys.getPathContent(parameter[0]);
    // get the Folder at the second path
    try {
      moving_location = fileSys.getFolder(parameter[1]);
    } catch (InvalidPathException e) {
    }

    if (moving_location != null) {
      // Perform the copy
      copy.copyContent(target_content, moving_location);
      // remove the old content from the old folder
      fileSys.getParentFolder(target_content)
          .removeContent(target_content.getName());
    } else {
      // rename the file
      target_content.changePath(parameter[1]);
      target_content.changeName(
          parameter[1].substring(parameter[1].lastIndexOf("/") + 1));
    }
  }
}
