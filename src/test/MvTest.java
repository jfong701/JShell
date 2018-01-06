package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Mv;
import driver.FileSystem;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNameException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * Test methods of Mv class
 * 
 * @author Kevin
 *
 */
public class MvTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fs;

  /**
   * Instantiate Mv
   */
  private Mv mvTest;

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

    // create Mv object
    mvTest = new Mv(fs);

    // create directories to test
    fs.makeDirectory("/moveme");
    fs.makeDirectory("/test");
    fs.makeDirectory("/test/test1");

    // create files to test
    fs.makeFile("/testFile");

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
   * test Mv execute command with a Folder
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   */
  @Test
  public void testMv()
      throws HasSameContentNameException, InvalidPathException,
      IncorrectContentTypeException, InvalidNumberOfArgument {

    // Set params
    param = new String[] {"/moveme", "/test/test1"};
    mvTest.setParam(param);

    // execute mv command
    mvTest.executeCommand();

    // Check original
    assertFalse(fs.pathExist("/moveme"));

    // Check new location
    assertEquals("moveme", fs.getPathContent("/test/test1/moveme").getName());
    assertEquals("/test/test1/moveme",
        fs.getPathContent("/test/test1/moveme").getPath());

  }

  /**
   * test Mv execute command with File
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   */
  @Test
  public void testMvWithFile()
      throws HasSameContentNameException, InvalidPathException,
      IncorrectContentTypeException, InvalidNumberOfArgument {

    // Set params
    param = new String[] {"/testFile", "/test/test1"};
    mvTest.setParam(param);

    // execute mv command
    mvTest.executeCommand();

    // Check original
    assertFalse(fs.pathExist("/testFile"));

    // Check new location
    assertEquals("testFile",
        fs.getPathContent("/test/test1/testFile").getName());
    assertEquals("/test/test1/testFile",
        fs.getPathContent("/test/test1/testFile").getPath());

  }

  /**
   * test Mv execute command with 3 arguments
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testMvWithInvalidNumParams()
      throws HasSameContentNameException, InvalidPathException,
      IncorrectContentTypeException, InvalidNumberOfArgument {

    // Set params
    param = new String[] {"/testFile", "/test/test1", "/test"};
    mvTest.setParam(param);

    // execute mv command
    mvTest.executeCommand();
  }

  /**
   * test Mv execute commands with no parameters
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testMvWithNoParams()
      throws HasSameContentNameException, InvalidPathException,
      IncorrectContentTypeException, InvalidNumberOfArgument {

    // Set params
    param = new String[] {};
    mvTest.setParam(param);

    // execute mv command
    mvTest.executeCommand();
  }

  /**
   * test Mv execute command to move into a File
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   */
  @Test(expected = IncorrectContentTypeException.class)
  public void testMvWithIncorrectContentType()
      throws HasSameContentNameException, InvalidPathException,
      IncorrectContentTypeException, InvalidNumberOfArgument {

    // Set params
    param = new String[] {"/moveme", "/testFile"};
    mvTest.setParam(param);

    // execute mv command
    mvTest.executeCommand();
  }

  /**
   * test Mv execute command with invalid path
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   */
  @Test(expected = InvalidPathException.class)
  public void testMvWithInvalidPath()
      throws HasSameContentNameException, InvalidPathException,
      IncorrectContentTypeException, InvalidNumberOfArgument {

    // Set params
    param = new String[] {"/test6", "/testFile"};
    mvTest.setParam(param);

    // execute mv command
    mvTest.executeCommand();
  }

  /**
   * test Mv execute command to move into a directory with another directory of
   * the same name
   * 
   * @throws InvalidNumberOfArgument
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   * @throws InvalidNameException
   * 
   */
  @Test(expected = HasSameContentNameException.class)
  public void testMvWithSameContentName() throws HasSameContentNameException,
      InvalidPathException, IncorrectContentTypeException,
      InvalidNumberOfArgument, InvalidNameException {

    // create a moveme directory
    fs.makeDirectory("/test/test1/moveme");

    // Set params
    param = new String[] {"/moveme", "/test/test1"};
    mvTest.setParam(param);

    // execute mv command
    mvTest.executeCommand();
  }


}
