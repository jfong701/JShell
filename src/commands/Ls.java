package commands;

import driver.Content;
import driver.Folder;
import driver.FileSystem;
import driver.PrintFormatter;
import exception.IncorrectContentTypeException;
import exception.InvalidPathException;

import java.util.ArrayList;

/**
 * Class to execute the ls Command: Retrieves content from directories using
 * current directory by default or from a given path or recursively display
 * subdirectories from a given path or a directory found in the current
 * directory. Also, displays the name of a file if given as an argument.
 * 
 * @author Kevin Bato
 *
 */
public class Ls extends Command {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSys;

  /**
   * Instantiate PrintFormatter
   */
  private PrintFormatter format;

  /**
   * Ls constructor that takes current FileSystem and PrintFormatter
   * 
   * @param fs Current FileSystem
   * @param pf PrintFormatter Object
   */
  public Ls(FileSystem fs, PrintFormatter pf) {
    fileSys = fs;
    format = pf;
  }

  /**
   * Executes ls command using parameters
   * 
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   * 
   */
  public void executeCommand()
      throws InvalidPathException, IncorrectContentTypeException {
    // create a new list of Contents for printing
    ArrayList<Content> listContents = new ArrayList<Content>();
    // ls without parameters will print contents of the current directory
    if (parameter.length == 0) {
      // prints contents of the current directory
      format.setLsNoParams(fileSys.getCurrFolder());
    }
    // checks for the recursive parameter
    else if (parameter[0].equals("-R") || parameter[0].equals("-r")) {
      // if there is no path given, use the current directory
      if (parameter.length == 1) {
        parameter = new String[2];
        parameter[1] = fileSys.getCurrPath();
      }
      // for one or more parameters passed in
      for (int i = 1; i < parameter.length; i++) {
        // recursively goes through a content's subdirectories
        recLs(parameter[i]);
      }
      // remove the new line
      format.setOutput(
          format.getOutput().substring(0, format.getOutput().length() - 1));
    }
    // checks content of multiple parameters passed in
    else {
      listAllParam(listContents);
      // set the output contents
      format.setLsContents(listContents);
    }
  }

  /**
   * Goes through all the parameters and add all the Content to printFormatter
   * 
   * @param listContents The array list of content
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  private void listAllParam(ArrayList<Content> listContents)
      throws InvalidPathException, IncorrectContentTypeException {
    // goes through all parameters
    for (String path : parameter) {
      // checks if it is a Folder instance
      if (Folder.class.isInstance(fileSys.getPathContent(path))) {
        // adds a Folder object
        listContents.add(fileSys.getFolder(path));
      }
      // checks if it is a File instance
      else {
        // adds a File object
        listContents.add(fileSys.getFile(path));
      }
    }
  }


  /**
   * Recursively goes through all subdirectories of the directory of a given
   * path.
   * 
   * @param path Path of a directory
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  private void recLs(String path)
      throws InvalidPathException, IncorrectContentTypeException {
    // create a new list of Contents for printing
    ArrayList<Content> content = new ArrayList<Content>();
    // create a new list of Contents
    ArrayList<Content> listContents = new ArrayList<Content>();

    // get all contents in a Folder
    listContents = fileSys.getFolder(path).getAllContents();
    // adds the directory path given as an argument
    content.add(fileSys.getFolder(path));

    format.setRecLsContents(content);

    // Goes through all Contents from the given directory
    for (Content c : listContents) {
      // Base case: If File or Content is empty then do nothing

      // Recursive: If Folder then get path and perform recursive call
      if (Folder.class.isInstance(c)) {
        recLs(c.getPath());
      }
    }
  }
}
