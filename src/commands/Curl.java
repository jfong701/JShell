package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import driver.File;
import driver.FileSystem;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNameException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;
import exception.InvalidURLException;

/**
 * Class to execute the curl Command: retrieves a file from a URL and adds it
 * to the current working directory
 * 
 * @author Jason Chow Fong
 */
public class Curl extends Command {

  /**
   * a String constant for the "/" character
   */
  private final String SLASH = "/";

  /**
   * the FileSystem from the JShell
   */
  private FileSystem fileSys;

  /**
   * a URL Object for accessing a file from the Internet
   */
  private URL urlObj;

  /**
   * a String to hold the contents of a file read from the Internet
   */
  private String fileContents;

  /**
   * a String to hold the name of a file read from the Internet
   */
  private String fileName;

  /**
   * BufferedReader for reading characters from an online file efficiently.
   */
  private BufferedReader input;


  /**
   * Constructor for Curl class, which takes in a FileSystem to write files to
   * 
   * @param fs: FileSystem from JShell
   */
  public Curl(FileSystem fs) {
    fileSys = fs;
  }

  /**
   * Given a valid URL, this method retrieves the file from the URL and adds it
   * to the current working directory.
   * 
   * @throws InvalidURLException
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   * @throws InvalidNumberOfArgument
   */
  public void executeCommand() throws InvalidURLException,
      InvalidPathException, HasSameContentNameException, InvalidNameException,
      IncorrectContentTypeException, InvalidNumberOfArgument {
    checkNumParam(1);
    fileContents = "";
    fileName = "";
    // split in two methods. Retrieving URL, and local fileSystem work.

    getURLFile();

    // If getURLFile() completed without throwing any Exceptions, then
    // it should be safe to write the Internet data to a File.
    writeFile();
  }

  /**
   * Goes to the URL and opens an inputStream of the file to read from, then
   * fills up a String with the file contents. Returns True if the file was
   * successfully retrieved. False otherwise.
   * 
   * @throws InvalidURLException
   */
  private void getURLFile() throws InvalidURLException {
    try {
      urlObj = new URL(parameter[0]);
      /*
       * openStream() opens the actual connection to the Internet, and returns
       * an inputStream from that URL.
       * 
       * InputStreamReader reads a byte stream and decodes them to a char
       * stream (prevent issues with differences in encoding between online
       * source and local system)
       * 
       * bufferedReader reads characters from an input stream, and buffers
       * stream for efficiency
       */
      input = new BufferedReader(new InputStreamReader(urlObj.openStream()));

      // Get the name of the file (keep only characters to the right of the
      // right-most slash (not including the slash))
      fileName = urlObj.getFile();
      int lastSlashIndex = fileName.lastIndexOf(SLASH);
      fileName = fileName.substring(lastSlashIndex + 1);

      // FILL A LOCAL STRING WITH THE CONTENTS OF THE FILE.

      // var to hold the current line.
      String curLine;

      // keep reading lines and appending to local String until End Of File
      while ((curLine = input.readLine()) != null) {
        // add a newline character at the end of every line read.
        fileContents += curLine + "\n";
      }
      // no longer need to read the stream
      input.close();

      // strip the extra newline character added at the end of the file
      if (fileContents.length() > 0) {
        fileContents = fileContents.substring(0, fileContents.length() - 1);
      }
    } catch (MalformedURLException e) {
      throw new InvalidURLException();
    } catch (IOException e) {
      throw new InvalidURLException();
    }
  }

  /**
   * Writes a new File to the FileSystem, using data retrieved from the URL
   * 
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  private void writeFile()
      throws InvalidPathException, HasSameContentNameException,
      InvalidNameException, IncorrectContentTypeException {
    // generate the filePath, based on where we are in the fileSystem, and the
    // fileName from the Internet.
    String newFilePath;
    boolean fileExists;
    File curFile;

    // if the fileSys is NOT in the root, we need to add a slash
    if (fileSys.getCurrPath() != SLASH) {
      newFilePath = fileSys.getCurrPath() + SLASH + fileName;
    } else {
      newFilePath = fileSys.getCurrPath() + fileName;
    }

    // check if there is an existing file with the same Path.
    fileExists = fileSys.pathExist(newFilePath);

    // if the file does not exist, create it.
    if (!fileExists) {
      fileSys.makeFile(newFilePath);
    }

    // write the read Internet data to the file, overwrites data if the
    // file already exists
    curFile = fileSys.getFile(newFilePath);
    curFile.overwriteData(fileContents);
  }
}
