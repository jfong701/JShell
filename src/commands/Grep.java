package commands;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import driver.Content;
import driver.File;
import driver.FileSystem;
import driver.Folder;
import driver.PrintFormatter;
import exception.IncorrectContentTypeException;
import exception.InvalidPathException;
import exception.InvalidRegexException;

/**
 * Grep prints lines of a file that match a string. Grep can also search
 * through a folder recursively, and check all lines in the files of the folder
 * for a string.
 * 
 * @author Jason Chow Fong
 */
public class Grep extends Command {

  /**
   * the FileSystem from JShell
   */
  private FileSystem fileSys;

  /**
   * the PrintFormatter from JShell
   */
  private PrintFormatter format;

  /**
   * A String to hold everything planned to print, to be sent to PrintFormatter
   * at the end of processing
   */
  private String printBuffer;

  /**
   * A boolean for checking if we need to print method headers or not (are
   * multiple files being checked by grep)
   */
  private boolean headerRequired;

  /**
   * Constructor for Grep, taking in FileSystem to search from, and a
   * PrintFormatter to print to
   * 
   * @param fs FileSystem from JShell
   * @param pf PrintFormatter from JShell
   */
  public Grep(FileSystem fs, PrintFormatter pf) {
    fileSys = fs;
    format = pf;
  }

  /**
   * When path is a file, grep works the same with or without the flag -R.
   * 
   * Given a regular expression REGEX, and a PATH for a file, grep searches the
   * file at path according to the regular expression REGEX.
   * 
   * Given the flag -R, a regular expression REGEX, and a path for a directory,
   * grep RECURSIVELY traverses the directory and searches each file inside
   * according to the regular expression REGEX.
   * 
   * @throws InvalidRegexException
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  public void executeCommand() throws InvalidRegexException,
      InvalidPathException, IncorrectContentTypeException {
    printBuffer = "";
    // if there are multiple files, or not immediately clear which file
    // we will find the regex in, we include a header with the fileName.
    headerRequired = false;

    // if the first parameter is not -R only do a file Search
    if (parameter[0].toUpperCase().equals("-R")) {
      // 1 is the index of the regex
      parameter[1] = removeOuterQuotes(parameter[1]);

      // check if regex is valid before proceeding
      checkRegex(parameter[1]);

      // if not stopped by an exception, reaches this point, check all paths

      for (int i = 2; i < parameter.length; i++) {
        searchFolder(parameter[1], parameter[i]);
      }
    } else {
      // 0 is the index of the regex
      parameter[0] = removeOuterQuotes(parameter[0]);

      // check if regex is valid before proceeding
      checkRegex(parameter[0]);

      // check all paths
      if (parameter.length > 2) {
        headerRequired = true;
      }
      for (int i = 1; i < parameter.length; i++) {
        searchFile(parameter[0], parameter[i]);
      }
    }

    // only print if something is found
    if (printBuffer.length() > 0) {
      // strip trailing newline from printBuffer if present
      printBuffer = printBuffer.substring(0, printBuffer.length() - 1);
      format.setOutput(printBuffer);
    }
  }


  /**
   * Helper method that searches an individual file, for a regex, given the
   * regex and a File path.
   * 
   * @param regex A regular expression to search for within the file
   * @param filePath The path in FileSystem of the file we want to search
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  private void searchFile(String regex, String filePath)
      throws InvalidPathException, IncorrectContentTypeException {
    // current file we are working with
    File curFile = fileSys.getFile(filePath);

    // Will hold each line of the given file
    String[] fileContents;

    // header of a fileName and colon to add to every line, if used in
    // recursive folder search
    String header = "";
    if (headerRequired) {
      header = curFile.getPath() + ": ";
    }

    fileContents = curFile.getData().split("\n");
    for (String currentLine : fileContents) {
      // if the regex matches the currentLine, it is printed
      // NOTE: header is blank if not in recursive search
      if (Pattern.matches(regex, currentLine)) {
        printBuffer += header + currentLine + "\n";
      }
    }
  }

  /**
   * Helper method that searches a Folder's files for a regex, given the regex
   * and a File path.
   * 
   * @param regex A regular expression to search for within files
   * @param contentPath The path in FileSystem of a Content Object we want to
   *        search (determining if Folder or File)
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  private void searchFolder(String regex, String contentPath)
      throws IncorrectContentTypeException, InvalidPathException {
    // get the root folder to compare as an instance of a Folder object
    Folder curFolder = fileSys.getFolder("/");

    // check if contentPath specifies a folder or a file, if searching for
    // a folder fails, it must be a file
    if (fileSys.getPathContent(contentPath).getClass().isInstance(curFolder)) {

      // in a folder search each item
      curFolder = fileSys.getFolder(contentPath);
      // if its a folder that has been specified, then we need headers to
      // know which files matched lines have come from.
      headerRequired = true;

      // when getting each item, we need the paths of all the child items
      ArrayList<String> childPaths = new ArrayList<String>();
      for (Content eachItem : curFolder.getAllContents()) {
        childPaths.add(eachItem.getPath());
      }

      // with each childPath, search through its Folder (recursion part)
      for (String newPath : childPaths) {
        searchFolder(regex, newPath);
      }

    } else {
      // if the current item is not an instance of folder, it must be a file
      searchFile(regex, contentPath);
    }
  }

  /**
   * Checks if a regex is valid. If it is not, throws an InvalidRegexException
   * 
   * @param regex A regular expression
   * @throws InvalidRegexException
   */
  private void checkRegex(String regex) throws InvalidRegexException {
    try {
      Pattern.compile(regex);
    } catch (PatternSyntaxException e) {
      throw new InvalidRegexException();
    }
  }

  /**
   * Remove outer double quotes from a Regex String
   * 
   * @param regex A regex to remove outer double quotes from
   * @return regex A regex with the outer double quotes removed
   */
  private String removeOuterQuotes(String regex) {
    if (regex.startsWith("\"") && regex.endsWith("\"")) {
      regex = regex.substring(1, regex.length() - 1);
    }
    return regex;
  }

}
