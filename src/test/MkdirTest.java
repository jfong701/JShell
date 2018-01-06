package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Mkdir;
import driver.FileSystem;
import exception.InvalidPathException;
import exception.MkdirMultiException;

/**
 * Test methods of Mkdir class
 * 
 * @author Kevin Bato
 *
 */
public class MkdirTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fs;

  /**
   * Instantiate Mkdir
   */
  private Mkdir mkdirTest;

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

    // create Mkdir object
    mkdirTest = new Mkdir(fs);

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
   * test Mkdir execute command with one param
   * 
   * @throws MkdirMultiException
   * @throws InvalidPathException
   */
  @Test
  public void testMkdirOneParam()
      throws MkdirMultiException, InvalidPathException {

    // Set param
    param = new String[] {"/test"};
    mkdirTest.setParam(param);

    // execute mkdir
    mkdirTest.executeCommand();

    // check if it made a directory
    assertEquals("test", fs.getPathContent("/test").getName());
    assertEquals("/test", fs.getPathContent("/test").getPath());

  }

  /**
   * test Mkdir execute command with multiple params
   * 
   * @throws MkdirMultiException
   * @throws InvalidPathException
   */
  @Test
  public void testMkdirMultiParam()
      throws MkdirMultiException, InvalidPathException {

    // Set param
    param = new String[] {"/test", "/test/test1"};
    mkdirTest.setParam(param);

    // execute mkdir
    mkdirTest.executeCommand();

    // check if it made a directory
    assertEquals("test", fs.getPathContent("/test").getName());
    assertEquals("/test", fs.getPathContent("/test").getPath());

    // check if it made a directory
    assertEquals("test1", fs.getPathContent("/test/test1").getName());
    assertEquals("/test/test1", fs.getPathContent("/test/test1").getPath());

  }

  /**
   * test Mkdir execute command with invalid path
   * 
   * @throws MkdirMultiException
   * @throws InvalidPathException
   */
  @Test(expected = MkdirMultiException.class)
  public void testMkdirWithInvalidPath()
      throws MkdirMultiException, InvalidPathException {

    // Set param
    param = new String[] {"/test/test1"};
    mkdirTest.setParam(param);

    // execute mkdir
    mkdirTest.executeCommand();
  }

}
