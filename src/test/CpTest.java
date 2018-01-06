package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Cp;
import driver.FileSystem;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * Test methods of Cp class
 * 
 * @author Kevin Bato
 *
 */
public class CpTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fs;

  /**
   * Instantiate Cp
   */
  private Cp cpTest;

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

    // create Cp object
    cpTest = new Cp(fs);

    // create directories to test
    fs.makeDirectory("/copyme");
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
   * test Cp execute command
   * 
   * @throws InvalidNumberOfArgument
   * @throws HasSameContentNameException
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testCpWithTwoValidParams()
      throws InvalidPathException, IncorrectContentTypeException,
      HasSameContentNameException, InvalidNumberOfArgument {

    // Set param
    param = new String[] {"/copyme", "/test/test1"};
    cpTest.setParam(param);

    // Execute cp command
    cpTest.executeCommand();

    // Check original
    assertEquals("copyme", fs.getPathContent("/copyme").getName());
    assertEquals("/copyme", fs.getPathContent("/copyme").getPath());

    // Check copy
    assertEquals("copyme", fs.getPathContent("/test/test1/copyme").getName());
    assertEquals("/test/test1/copyme",
        fs.getPathContent("/test/test1/copyme").getPath());

  }

  /**
   * test Cp execute command with invalid number of arguments
   * 
   * @throws InvalidNumberOfArgument
   * @throws HasSameContentNameException
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testCpWithInvalidParam()
      throws InvalidPathException, IncorrectContentTypeException,
      HasSameContentNameException, InvalidNumberOfArgument {

    // Set param
    param = new String[] {"/test/test1"};
    cpTest.setParam(param);

    // Execute cp command
    cpTest.executeCommand();
  }

  /**
   * test Cp execute command with invalid number of arguments
   * 
   * @throws InvalidNumberOfArgument
   * @throws HasSameContentNameException
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = HasSameContentNameException.class)
  public void testCpWithSameName()
      throws InvalidPathException, IncorrectContentTypeException,
      HasSameContentNameException, InvalidNumberOfArgument {

    // Set param
    param = new String[] {"/copyme", "/test/test1"};
    cpTest.setParam(param);

    // Execute cp command
    cpTest.executeCommand();

    // Execute cp again
    cpTest.executeCommand();
  }

  /**
   * test Cp execute command with invalid number of arguments
   * 
   * @throws InvalidNumberOfArgument
   * @throws HasSameContentNameException
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = InvalidPathException.class)
  public void testCpWithInvalidPath()
      throws InvalidPathException, IncorrectContentTypeException,
      HasSameContentNameException, InvalidNumberOfArgument {

    // Set param
    param = new String[] {"/test/copyme", "/test/test1"};
    cpTest.setParam(param);

    // Execute cp command
    cpTest.executeCommand();
  }

  /**
   * test Cp execute command with invalid number of arguments
   * 
   * @throws InvalidNumberOfArgument
   * @throws HasSameContentNameException
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = IncorrectContentTypeException.class)
  public void testCpWithIncorrectContentType()
      throws InvalidPathException, IncorrectContentTypeException,
      HasSameContentNameException, InvalidNumberOfArgument {

    // Set param
    param = new String[] {"/copyme", "/testFile"};
    cpTest.setParam(param);

    // Execute cp command
    cpTest.executeCommand();
  }


}
