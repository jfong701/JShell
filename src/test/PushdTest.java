package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Pushd;
import driver.DirectoryStack;
import driver.FileSystem;
import exception.InvalidNumberOfArgument;

/**
 * Test methods of Pushd class
 * 
 * @author Kevin Bato
 *
 */
public class PushdTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fs;
  
  /**
   * Instantiate Directory Stack
   */
  private DirectoryStack sDir;
  
  /**
   * Instantiate Pushd
   */
  private Pushd pushdTest;
  
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
    
    // create new Directory Stack
    sDir = new DirectoryStack();
    
    // create new Pushd object
    pushdTest = new Pushd(fs, sDir);
    
    
    // make directories
    fs.makeDirectory("/test");
    fs.makeDirectory("/test/test1");
  }

  /**
   * After each test case
   */
  @After
  public void tearDown(){
    // Reset fileSystem
    fs.reset();
  }
  
  /**
   * test Pushd execute command 
   * @throws InvalidNumberOfArgument 
   */
  @Test
  public void testPushd() throws InvalidNumberOfArgument {
    
    // Set param
    param = new String[]{"/test"};
    pushdTest.setParam(param);
    
    // execute Pushd
    pushdTest.executeCommand();
    
    assertEquals("test", fs.getCurrFolder().getName());

 // Set param
    param = new String[]{"/test/test1"};
    pushdTest.setParam(param);
    
    // execute Pushd
    pushdTest.executeCommand();
    
    assertEquals("test1", fs.getCurrFolder().getName());
  }

  /**
   * test Pushd execute command with no parameters
   * @throws InvalidNumberOfArgument 
   */
  @Test(expected=InvalidNumberOfArgument.class)
  public void testPushdNoParam() throws InvalidNumberOfArgument {
    
    // Set param
    param = new String[]{};
    pushdTest.setParam(param);
    
    // execute Pushd
    pushdTest.executeCommand();

  }
  
  /**
   * test Pushd execute command with multiple parameters
   * @throws InvalidNumberOfArgument 
   */
  @Test(expected=InvalidNumberOfArgument.class)
  public void testPushdMultiParams() throws InvalidNumberOfArgument {
    
    // Set param
    param = new String[]{"/test", "/test/test1"};
    pushdTest.setParam(param);
    
    // execute Pushd
    pushdTest.executeCommand();

  }


}
