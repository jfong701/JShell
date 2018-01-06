package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Cd;
import driver.FileSystem;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * Test methods of Cd class
 * 
 * @author Kevin
 *
 */
public class CdTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fs;

  /**
   * Instantiate Cd
   */
  private Cd cdTest;

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

    // create Cd object
    cdTest = new Cd(fs);

    // create directories
    fs.makeDirectory("/test");
    fs.makeDirectory("/test/test1");

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
   * test Cd execute command
   * 
   * @throws InvalidNumberOfArgument
   * @throws InvalidPathException
   */
  @Test
  public void testCd() throws InvalidPathException, InvalidNumberOfArgument {

    // Set param
    param = new String[] {"/test"};
    cdTest.setParam(param);

    // execute Cd
    cdTest.executeCommand();

    // check location in FileSystem
    assertEquals("test", fs.getCurrFolder().getName());

    // Set param
    param = new String[] {"/test/test1"};
    cdTest.setParam(param);

    // execute Cd
    cdTest.executeCommand();

    // check location in FileSystem
    assertEquals("test1", fs.getCurrFolder().getName());

  }

  /**
   * test Cd execute command with multiple parameters
   * 
   * @throws InvalidNumberOfArgument
   * @throws InvalidPathException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testCdWithMultiParams()
      throws InvalidPathException, InvalidNumberOfArgument {

    // Set param
    param = new String[] {"/test", "/test/test1"};

    cdTest.setParam(param);

    // execute Cd
    cdTest.executeCommand();

  }

  /**
   * test Cd execute command with no parameters
   * 
   * @throws InvalidNumberOfArgument
   * @throws InvalidPathException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testCdWithNoParams()
      throws InvalidPathException, InvalidNumberOfArgument {

    // Set param
    param = new String[] {};

    cdTest.setParam(param);

    // execute Cd
    cdTest.executeCommand();

  }

  /**
   * test Cd execute command with multiple parameters
   * 
   * @throws InvalidNumberOfArgument
   * @throws InvalidPathException
   */
  @Test(expected = InvalidPathException.class)
  public void testCdWithInvalidPath()
      throws InvalidPathException, InvalidNumberOfArgument {

    // Set param
    param = new String[] {"/test/test3"};

    cdTest.setParam(param);

    // execute Cd
    cdTest.executeCommand();

  }
}
