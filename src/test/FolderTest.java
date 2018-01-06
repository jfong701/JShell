package test;

/**
 * Tests the methods and behaviour of Folder Class
 * 
 * @author Kevin Bato
 */


import java.util.ArrayList;

import driver.Content;
import driver.Folder;
import exception.HasSameContentNameException;
import exception.InvalidPathException;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FolderTest {

  // Declaration Variables

  private final String SLASH = "/";
  private final Folder ROOT = new Folder(SLASH, SLASH);

  public Folder testFolder;
  public Folder testFolder1;
  public Folder testFolder2;

  public ArrayList<Content> testRootFolder;


  /**
   * Before each test case, create new Folders
   */
  @Before
  public void setUp() {

    // Create test Folder objects
    testFolder = new Folder("/home", "home");
    testFolder1 = new Folder("/user", "user");
    // Use for exception catching
    testFolder2 = new Folder("/user", "user");

    // Create Array List
    testRootFolder = new ArrayList<Content>();
  }

  /**
   * Test Folder Constructor
   */
  @Test
  public void testFolderConstructor() {

    // Checks for the name of the Folder
    assertEquals("/", ROOT.getName());
    assertEquals("home", testFolder.getName());

    // Checks for the path of the Folder
    assertEquals("/", ROOT.getPath());
    assertEquals("home", testFolder.getName());

  }

  /**
   * Test addContent Method
   * 
   * @throws HasSameContentNameException
   */
  @Test
  public void testAddContent() throws HasSameContentNameException {

    // Adds test Folders into ROOT
    ROOT.addContent(testFolder);
    assertEquals(testFolder, ROOT.getContent("home"));
    ROOT.addContent(testFolder1);
    assertEquals(testFolder1, ROOT.getContent("user"));

  }

  /**
   * Test addContent with an existing Folder with the same name
   * 
   * @throws HasSameContentNameException
   */
  @Test(expected = HasSameContentNameException.class)
  public void testAddContentWithSameName() throws HasSameContentNameException {

    ROOT.addContent(testFolder1);
    // Throws an Exception
    ROOT.addContent(testFolder2);

  }


  /**
   * Test removeContent
   * 
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   */
  @Test
  public void testRemoveContent()
      throws InvalidPathException, HasSameContentNameException {

    // Adds testFolder into ROOT
    ROOT.addContent(testFolder);
    assertEquals(testFolder, ROOT.getContent("home"));

    // Removes specific folder
    ROOT.removeContent("home");
    assertEquals(null, ROOT.getContent("home"));

    // Adds all testFolders into ROOT
    ROOT.addContent(testFolder);
    ROOT.addContent(testFolder1);

    // Removes all Folders into ROOT
    ROOT.removeContent("home");
    ROOT.removeContent("user");

    assertEquals(testRootFolder, ROOT.getAllContents());

  }

  /**
   * Test getAllContents
   * 
   * @throws HasSameContentNameException
   */
  @Test
  public void testGetAllContents() throws HasSameContentNameException {

    // Adds testFolders into ROOT
    ROOT.addContent(testFolder);
    ROOT.addContent(testFolder1);

    // Create a testRootFolder and add all testFolders as expected
    testRootFolder.add(testFolder);
    testRootFolder.add(testFolder1);

    assertEquals(testRootFolder, ROOT.getAllContents());

  }

  /**
   * Test overwriteAllContents
   */
  @Test
  public void testOverwriteAllContents(){

    // Create a testRootFolder and add all testFolders as expected
    testRootFolder.add(testFolder);
    testRootFolder.add(testFolder1);
    
    // Overwrite the current ROOT Folder 
    ROOT.overwriteAllContents(testRootFolder);

    assertEquals(testRootFolder, ROOT.getAllContents());
  }
  
  /**
   * Test clone
   */
  @Test
  public void testClone(){
    
    // Invoke clone command on the test Folder
    Folder actualFolder = testFolder1.clone();
   
    // Tests for the properties of a Folder (name and path)
    assertEquals(testFolder1.getName(), actualFolder.getName());
    assertEquals(testFolder1.getPath(), actualFolder.getPath());
    
  }
  
  
}
