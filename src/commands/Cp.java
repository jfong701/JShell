package commands;

import driver.Content;
import driver.FileSystem;
import driver.Folder;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * Create a copy of a content and move the copy version of the content to
 * another directory.
 * 
 * @author Sin
 *
 */
public class Cp extends Command {
  private FileSystem fileSys;
  private String SLASH = "/";

  public Cp(FileSystem fs) {
    fileSys = fs;
  }

  public void executeCommand()
      throws InvalidPathException, IncorrectContentTypeException,
      HasSameContentNameException, InvalidNumberOfArgument {
    // check for number of parameters
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
    moving_location = fileSys.getFolder(parameter[1]);

    this.copyContent(target_content, moving_location);
  }

  /**
   * Create a copy of the content and move the copy to the new location
   * 
   * @param target
   * @param new_location
   * @throws HasSameContentNameException
   */
  protected void copyContent(Content target, Folder new_location)
      throws HasSameContentNameException {
    if (target != null && new_location != null) {
      // make a copy of the content
      Content copy = fileSys.createCopy(target);
      // Set the path for the copy
      copy.changePath(new_location.getPath() + SLASH + copy.getName());
      // Add the copy to new location
      new_location.addContent(copy);
    }
  }
}
