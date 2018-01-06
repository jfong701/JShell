/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import commands.Curl;
import driver.FileSystem;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNameException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;
import exception.InvalidURLException;

/**
 * @author Jason Chow Fong
 *
 */
public class CurlTest {
  private FileSystem fileSys;
  private Curl curlCommand;

  @Before
  public void setUp() {
    fileSys = FileSystem.createNewFileSystem();
    curlCommand = new Curl(fileSys);
  }

  /**
   * Test curl with a text file
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectContentTypeException
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidURLException
   */
  @Test
  public void testCurlTextFile() throws InvalidURLException,
      InvalidPathException, HasSameContentNameException, InvalidNameException,
      IncorrectContentTypeException, InvalidNumberOfArgument {
    String[] parameters = {"http://www.cs.cmu.edu/~spok/grimmtmp/188.txt"};
    final String expected =
        "Three women were transformed into flowers which grew in\n"
            + "the field, but one of them was allowed to be in her own home "
            + "at\n"
            + "night.  Then once when day was drawing near, and she was "
            + "forced to\n"
            + "go back to her companions in the field and become a flower "
            + "again,\n"
            + "she said to her husband, if you will come this afternoon and\n"
            + "gather me, I shall be set free and henceforth stay with you.  "
            + "And\n"
            + "he did so.  Now the question is, how did her husband know her, "
            + "for\n"
            + "the flowers were exactly alike, and without any difference.\n"
            + "Answer - as she was at her home during the night and not in "
            + "the\n"
            + "field, no dew fell on her as it did on the others, and by "
            + "this\n" + "her husband knew her.";
    curlCommand.setParam(parameters);
    curlCommand.executeCommand();
    assertEquals(expected, fileSys.getFile("/188.txt").getData());
  }

  /**
   * Test curl with an HTML file
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectContentTypeException
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidURLException
   */
  @Test
  public void testCurlHTMLFile() throws InvalidURLException,
      InvalidPathException, HasSameContentNameException, InvalidNameException,
      IncorrectContentTypeException, InvalidNumberOfArgument {
    String[] parameters =
        {"http://www.utsc.utoronto.ca/~nick/cscB36/index.html"};
    final String expected = "Boo!";
    curlCommand.setParam(parameters);
    curlCommand.executeCommand();
    assertEquals(expected, fileSys.getFile("/index.html").getData());
  }

  /**
   * Test curl on a non existent website (invalid URL)
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectContentTypeException
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidURLException
   */
  @Test(expected = InvalidURLException.class)
  public void testURLDoesNotExist() throws InvalidURLException,
      InvalidPathException, HasSameContentNameException, InvalidNameException,
      IncorrectContentTypeException, InvalidNumberOfArgument {
    String[] parameters = {"fdtgfhgfdzsxdcv.com"};
    curlCommand.setParam(parameters);
    curlCommand.executeCommand();
  }
}
