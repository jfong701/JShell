package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Cat;
import driver.FileSystem;
import driver.PrintFormatter;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNameException;
import exception.InvalidPathException;

/**
 * Test methods of Cat class
 * 
 * @author Kevin
 *
 */
public class CatTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fs;

  /**
   * Instantiate PrintFormattter
   */
  private PrintFormatter pf;

  /**
   * Instantiate Cat
   */
  private Cat catTest;

  /**
   * Create param string array
   */
  private String[] param;

  /**
   * Before each test case
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {

    // create new FileSystem
    fs = FileSystem.createNewFileSystem();
    // create printformatter object
    pf = new PrintFormatter();

    // create new Cat object
    catTest = new Cat(fs, pf);

    // create test file
    fs.makeFile("/testFile");
    fs.getFile("/testFile").overwriteData("Hello");

    // create second test file
    fs.makeFile("/testFile1");
    fs.getFile("/testFile1").overwriteData("Hi");

  }

  /**
   * After each test case
   */
  @After
  public void tearDown() {
    // Reset FileSystem
    fs.reset();
  }

  /**
   * test Cat execute command with a File
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testCatFile()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/testFile"};
    catTest.setParam(param);

    // execute Cat command
    catTest.executeCommand();

    assertEquals("Hello", pf.getOutput());

  }

  /**
   * test Cat execute command with two Files
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testCatTwoFiles()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/testFile", "/testFile1"};
    catTest.setParam(param);

    // execute Cat command
    catTest.executeCommand();

    assertEquals("Hello\n\n\nHi", pf.getOutput());

  }

  /**
   * test Cat execute command with incorrect content type
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   * @throws IncorrectContentTypeException
   */
  @Test(expected = IncorrectContentTypeException.class)
  public void testCatFolder()
      throws InvalidNameException, InvalidPathException,
      HasSameContentNameException, IncorrectContentTypeException {

    // create directory
    fs.makeDirectory("/test");

    // Set param
    param = new String[] {"/test"};
    catTest.setParam(param);

    // execute Cat command
    catTest.executeCommand();
  }

  /**
   * test Cat execute command
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = InvalidPathException.class)
  public void testCatInvalidPath()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/testFile3"};
    catTest.setParam(param);

    // execute Cat command
    catTest.executeCommand();

  }

}
