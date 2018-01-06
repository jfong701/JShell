package commands;

import java.util.ArrayList;

import driver.File;
import driver.FileSystem;
import driver.PrintFormatter;
import exception.IncorrectContentTypeException;
import exception.InvalidPathException;

public class Cat extends Command {

  private FileSystem fileSys;
  private PrintFormatter format;

  public Cat(FileSystem fs, PrintFormatter pf) {
    fileSys = fs;
    format = pf;
  }

  /**
   * Executes the cat command: display content of given files concatenated
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * 
   */
  public void executeCommand()
      throws InvalidPathException, IncorrectContentTypeException {
    ArrayList<File> data = new ArrayList<File>();
    // For each filename get its file and store it in a string array list
    for (int i = 0; i < parameter.length; i++) {
      data.add(fileSys.getFile(parameter[i]));
    }
    format.setCatFiles(data);
  }
}
