package test;

/**
 * Tests the methods and behaviour of File Class
 * 
 * @author Kevin Bato
 */

import driver.File;
import driver.Folder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FileTest {

  // Declaration Variable

  public File testFile;

  /**
   * Before each test case, create new File
   */
  @Before
  public void setUp() {

    // Create new File
    testFile = new File("/", "TestFile", "");

  }

  /**
   * Test File Constructor
   */
  @Test
  public void testFileConstructor() {

    // Checks for testFile name
    assertEquals("TestFile", testFile.getName());

    // Check for testFile path
    assertEquals("/", testFile.getPath());

    // Check for testFile data
    assertEquals("", testFile.getData());

  }

  /**
   * Test overwriteData
   */
  @Test
  public void testOverwriteData() {
    // Overwrite current data in testFile
    testFile.overwriteData("Testing!");
    // Check for new data in testFile
    assertEquals("Testing!", testFile.getData());

  }
  
  /**
   * Test clone
   */
  @Test
  public void testClone(){
    
    // Invoke clone command on the test Folder
    File actualFile = testFile.clone();
   
    // Tests for the properties of a Folder (name and path)
    assertEquals(testFile.getName(), actualFile.getName());
    assertEquals(testFile.getPath(), actualFile.getPath());
    
  }
  

}
