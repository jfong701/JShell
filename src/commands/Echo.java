package commands;

import driver.FileSystem;
import driver.ContentEditor;
import driver.PrintFormatter;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * Class for execution the echo command: print 'text' on the shell
 *
 */
public class Echo extends Command {
  FileSystem fileSys;
  ContentEditor editor;
  PrintFormatter format;

  public Echo(FileSystem fs, PrintFormatter format) {
    fileSys = fs;
    editor = new ContentEditor();
    this.format = format;
  }

  /**
   * Executes the echo command: print 'text' on the shell
   * 
   * @param text- string to be printed on the shell
   * @throws IncorrectContentTypeException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNumberOfArgument
   */
  public void executeCommand() throws InvalidNumberOfArgument {
    // check for the number of parameters
    checkNumParam(1);
    // set the output
    format.setOutput(parameter[0]);
  }
}
