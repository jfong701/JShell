package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Grep;
import driver.FileSystem;
import driver.PrintFormatter;
import exception.IncorrectContentTypeException;
import exception.InvalidPathException;
import exception.InvalidRegexException;

/**
 * Test methods of Grep class
 * 
 * @author Kevin Bato
 *
 */
public class GrepTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fs;

  /**
   * Instantiate PrintFormatter
   */
  private PrintFormatter pf;

  /**
   * Instantiate Grep
   */
  private Grep grepTest;

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
    // create new PrintFormatter object
    pf = new PrintFormatter();

    // create Grep object
    grepTest = new Grep(fs, pf);

    // create test file
    fs.makeFile("/testFile");
    fs.getFile("/testFile").overwriteData("hello");

    // create directory
    fs.makeDirectory("/test");

    // create test file
    fs.makeFile("/test/testFile1");
    fs.getFile("/test/testFile1").overwriteData("hi");


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
   * test Grep execute command
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws InvalidRegexException
   */
  @Test
  public void testGrep() throws InvalidRegexException, InvalidPathException,
      IncorrectContentTypeException {

    // Set param
    param = new String[] {"hello", "/testFile"};
    grepTest.setParam(param);

    // execute grep
    grepTest.executeCommand();
    assertEquals("hello", pf.getOutput());

    // Set param
    param = new String[] {"hi", "/test/testFile1"};
    grepTest.setParam(param);

    // execute grep
    grepTest.executeCommand();
    assertEquals("hi", pf.getOutput());

  }

  /**
   * test Grep execute command with recursion
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws InvalidRegexException
   */
  @Test
  public void testGrepWithRec() throws InvalidRegexException,
      InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"-R", "hello", "/"};
    grepTest.setParam(param);

    // execute grep
    grepTest.executeCommand();
    assertEquals("/testFile: hello", pf.getOutput());
  }

  /**
   * test Grep execute command with recursion using an invalid path
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws InvalidRegexException
   */
  @Test(expected = InvalidPathException.class)
  public void testGrepWithInvalidPath() throws InvalidRegexException,
      InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"-R", "", "/test3"};
    grepTest.setParam(param);

    // execute grep
    grepTest.executeCommand();

  }

  /**
   * test Grep execute command with incorrect content type
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws InvalidRegexException
   */
  @Test(expected = IncorrectContentTypeException.class)
  public void testGrepWithIncorrectContentType() throws InvalidRegexException,
      InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"(12)", "/test"};
    grepTest.setParam(param);

    // execute grep
    grepTest.executeCommand();

  }
}
