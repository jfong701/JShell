package commands;

import driver.FileSystem;
import exception.HasSameContentNameException;
import exception.InvalidNameException;
import exception.InvalidPathException;
import exception.MkdirMultiException;

/**
 * A command that creates a new directory
 * 
 * @author Sin
 *
 */
public class Mkdir extends Command {

  /**
   * File system object instance
   */
  private FileSystem fileSys;

  /**
   * Default constructor
   * 
   * @param fs FileSystem instance
   */
  public Mkdir(FileSystem fs) {
    fileSys = fs;
  }

  public void executeCommand() throws MkdirMultiException {
    boolean cought_exception = false;
    String error_message = "";
    for (int i = 0; i < parameter.length; i++) {
      // keep making new directory via file system
      try {
        fileSys.makeDirectory(parameter[i]);
      } catch (HasSameContentNameException e) {
        cought_exception = true;
        error_message += "Error: Cannot create directroy \"" + parameter[i]
            + "\", Content exists\n";
      } catch (InvalidNameException e) {
        cought_exception = true;
        error_message += "Error: Cannot create directroy \"" + parameter[i]
            + "\", invalid name\n";
      } catch (InvalidPathException e) {
        cought_exception = true;
        error_message += "Error: Cannot create directroy \"" + parameter[i]
            + "\", invalid path \n";
      }
    }
    if (cought_exception) {
      throw new MkdirMultiException(
          error_message.substring(0, error_message.length() - 2));
    }
  }
}
