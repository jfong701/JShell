package test;

import driver.ContentEditor;
import driver.File;
import driver.Folder;
import exception.InvalidPathException;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods and behaviour of ContentEditor Class
 * @author Jason Chow Fong
 */
public class ContentEditorTest {

  /**
   * Declare needed variables for testing
   */
  private final String SLASH = "/";
  private final Folder ROOT = new Folder(SLASH, SLASH);
  private final int NUM_FOLDERS = 6;
  private final int NUM_FILES = 4;

  private Folder[] testFolders;
  private File[] testFiles;
  private ContentEditor cEditor;

  /**
   * Initialize variables for testing
   * For each test case, create new Folder and File Objects, and a
   * new ContentEditor Instance
   */
  @Before
  public void setUp()
  {
    // create a ContentEditor and the Folder and File Objects for every test
    cEditor = new ContentEditor();
    testFolders = new Folder[NUM_FOLDERS];
    testFiles = new File[NUM_FILES];

    testFolders[0] = new Folder(SLASH, "folder1a");
    testFolders[1] = new Folder(SLASH, "folder1b");
    testFolders[2] = new Folder("/folder1a", "folder2a");
    testFolders[3] = new Folder("/folder1a", "folder2b");
    testFolders[4] = new Folder("/folder2a", "folder3a");
    testFolders[5] = new Folder(SLASH, "folder1c");

    testFiles[0] = new File(SLASH, "file1a", "");
    testFiles[1] = new File(SLASH, "file1b", "This is line 1.");
    testFiles[2] = new File("/folder1a", "file2a", "");
    testFiles[3] = new File("/folder1a/folder2a", "file3a", "This is text.");

    // tree view of the setUp(), with their Array Indices written next to them
    // odd testFiles indices are the ones with writing in them.
    /*
     * ROOT
     *   |
     *   --------folder1a [0]
     *   |           |
     *   |           --------------folder2a [2]
     *   |           |                  |
     *   |           |                  ----------folder 3a [4]
     *   |           |                  |
     *   |           |                  ----------file3a[3]
     *   |           |
     *   |           --------------folder2b [3]
     *   |           |
     *   |           --------------file2a[2]
     *   |
     *   --------folder1b [1]
     *   |
     *   --------folder1c [5]
     *   |
     *   --------file1a[0]
     *   |
     *   --------file1b[1]
     */ 

  }

  /**
   * Test if Deleting all contents of a Folder works properly.
   * @throws InvalidPathException 
   */
  @Test
  public void testDeleteAllFolderContents() throws InvalidPathException
  { 
    //delete everything from a folder should equal contents of an
    // empty folder
    cEditor.deleteAllContents(testFolders[2]);
    assertEquals(testFolders[1].getAllContents(),
        testFolders[2].getAllContents());

    // delete everything from an empty folder should equal another empty
    // folder
    cEditor.deleteAllContents(testFolders[1]);
    assertEquals(testFolders[5].getAllContents(),
        testFolders[1].getAllContents());

    // should be able to call delete multiple times without issues or change
    cEditor.deleteAllContents(testFolders[1]);
    cEditor.deleteAllContents(testFolders[1]);
    cEditor.deleteAllContents(testFolders[1]);
    assertEquals(testFolders[5].getAllContents(),
        testFolders[1].getAllContents());

    // deleting a folder with multiple Content Objects should be equal to the
    //contents of an empty folder.
    cEditor.deleteAllContents(testFolders[0]);
    assertEquals(testFolders[5].getAllContents(),
        testFolders[0].getAllContents());
  }

  /**
   * Test if deleting all contents of a file works properly
   */
  @Test
  public void testdeleteAllFileContents()
  {
    // deleting all of the Contents from an empty file should be the same
    // as an already empty file.
    cEditor.deleteAllContents(testFiles[2]);
    assertEquals(testFiles[0].getData(), testFiles[2].getData());

    // deleting all of the Contents from a file that had data should be
    // equal to an already empty file.
    cEditor.deleteAllContents(testFiles[1]);
    assertEquals(testFiles[0].getData(), testFiles[1].getData());

    // deleting Content from two files that both had content should be equal
    // to each other's content, and to an empty file
    // NOTE: testFiles[1]'s Contents have already been deleted ^
    cEditor.deleteAllContents(testFiles[3]);
    assertEquals(testFiles[1].getData(), testFiles[3].getData());
    assertEquals(testFiles[0].getData(), testFiles[3].getData());

    //compare a deleted file's data to an empty string
    assertEquals("", testFiles[3].getData());
  }

  /**
   * Test if appending new content to a file works properly.
   */
  @Test
  public void testAppendContents()
  {
    // append nothing to a blank file, should be equal to another blank file
    // and equal to empty String.
    cEditor.appendContents(testFiles[2], "");
    assertEquals(testFiles[0].getData(), testFiles[2].getData());
    assertEquals("", testFiles[2].getData());

    // append the same thing to two blank files, should be equal to each other
    cEditor.appendContents(testFiles[0], "This is line 1.");
    cEditor.appendContents(testFiles[2], "This is line 1.");
    assertEquals(testFiles[2].getData(), testFiles[0].getData());
    assertEquals(testFiles[0].getData(), testFiles[2].getData());
    //should also be same as the data testFiles[1] was made with
    assertEquals(testFiles[2].getData(), testFiles[1].getData());
    assertEquals(testFiles[0].getData(), testFiles[1].getData());

    // append the same thing to two filled files, should still be equal to
    // each other, should have a newline character to separate the appended
    // lines
    cEditor.appendContents(testFiles[0], "This is line 2.");
    cEditor.appendContents(testFiles[2], "This is line 2.");
    assertEquals(testFiles[2].getData(), testFiles[0].getData());
    assertEquals(testFiles[0].getData(), testFiles[2].getData());

    // append the same thing to testFiles[1], should also be the same as [0]
    // and [2]
    cEditor.appendContents(testFiles[1], "This is line 2.");
    assertEquals(testFiles[2].getData(), testFiles[1].getData());
    assertEquals(testFiles[0].getData(), testFiles[1].getData());

    // all of them should be equal to the exact string written below.
    assertEquals(testFiles[0].getData(), "This is line 1.\nThis is line 2.");
    assertEquals(testFiles[1].getData(), "This is line 1.\nThis is line 2.");
    assertEquals(testFiles[2].getData(), "This is line 1.\nThis is line 2.");
  }

  /**
   * Test if setting a path for a Folder works properly.
   */
  @Test
  public void testSetPath()
  {
    // try to change Folder path from ROOT to the ROOT.
    // (Path should remain ROOT - same as before)
    String originalPath = testFolders[1].getPath();
    cEditor.setPath(testFolders[1], SLASH);
    assertEquals(originalPath, testFolders[1].getPath());
    assertEquals(SLASH, testFolders[1].getPath());

    // try to change path of Folder folder3a[4] up one level
    // (to the folder 2X level)
    // should also have same paths as all other folders / files at same level
    cEditor.setPath(testFolders[4], testFolders[3].getPath());
    assertEquals(testFolders[2].getPath(), testFolders[4].getPath());
    assertEquals(testFolders[3].getPath(), testFolders[4].getPath());
    assertEquals(testFiles[2].getPath(), testFolders[4].getPath());

    // manually change path from Folder folder3a[4] to the Root, compare with
    // other items with ROOT path.
    cEditor.setPath(testFolders[4], SLASH);
    assertEquals(SLASH, testFolders[4].getPath());
    assertEquals(testFolders[0].getPath(), testFolders[4].getPath());
    assertEquals(testFolders[1].getPath(), testFolders[4].getPath());
    assertEquals(testFolders[5].getPath(), testFolders[4].getPath());
    assertEquals(testFiles[0].getPath(), testFolders[4].getPath());
    assertEquals(testFiles[1].getPath(), testFolders[4].getPath());
  }
}