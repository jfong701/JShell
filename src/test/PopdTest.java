package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Popd;
import commands.Pushd;
import driver.DirectoryStack;
import driver.FileSystem;
import exception.EmptyDirectoryStackException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * Test methods of Popd class
 * 
 * @author Kevin Bato
 *
 */
public class PopdTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fs;

  /**
   * Instantiate Directory Stack
   */
  private DirectoryStack sDir;

  /**
   * Instantiate Popd
   */
  private Popd PopdTest;

  /**
   * Create param string array
   */
  private String[] param;

  /**
   * Instantiate Pushd
   */
  private Pushd pushd;

  /**
   * Before each test case
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {

    // create new FileSystem
    fs = FileSystem.createNewFileSystem();

    // create new Directory Stack
    sDir = new DirectoryStack();

    // create new Popd object
    PopdTest = new Popd(fs, sDir);

    // create new Pushd Object
    pushd = new Pushd(fs, sDir);

    // make directories
    fs.makeDirectory("/test");
    fs.makeDirectory("/test/test1");

    // Set param for pushd
    param = new String[] {"/test"};
    pushd.setParam(param);

    // execute Popd
    pushd.executeCommand();

    // Set param for pushd
    param = new String[] {"/test/test1"};
    pushd.setParam(param);

    // execute Popd
    pushd.executeCommand();


  }

  /**
   * After each test case
   */
  @After
  public void tearDown() {
    // Reset fileSystem
    fs.reset();
  }

  /**
   * test Popd execute command
   * 
   * @throws InvalidNumberOfArgument
   * @throws EmptyDirectoryStackException
   * @throws InvalidPathException
   */
  @Test
  public void testPopd() throws InvalidNumberOfArgument, InvalidPathException,
      EmptyDirectoryStackException {

    // Set param
    param = new String[] {};
    PopdTest.setParam(param);

    // execute Popd
    PopdTest.executeCommand();

    assertEquals("test", fs.getCurrFolder().getName());

    // Set param
    param = new String[] {};
    PopdTest.setParam(param);

    // execute Popd
    PopdTest.executeCommand();

    assertEquals("/", fs.getCurrFolder().getName());
  }

  /**
   * test Popd execute command with multiple parameters
   * 
   * @throws InvalidNumberOfArgument
   * @throws EmptyDirectoryStackException
   * @throws InvalidPathException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testPopdMultiParams() throws InvalidNumberOfArgument,
      InvalidPathException, EmptyDirectoryStackException {

    // Set param
    param = new String[] {"/test", "/test/test1"};
    PopdTest.setParam(param);

    // execute Popd
    PopdTest.executeCommand();

  }


}
