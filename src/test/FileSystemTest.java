package test;

import driver.Content;

/**
 * Tests the methods and behaviour of FileSystem Class
 * 
 * @author Kevin Bato
 */

import driver.File;
import driver.FileSystem;
import driver.Folder;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNameException;
import exception.InvalidPathException;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileSystemTest {

  // Declaration Variables for FileSystem, ROOT folder,
  // testFolders and testFiles
  private final String SLASH = "/";
  private final Folder ROOT = new Folder(SLASH, SLASH);
  private final int numTest = 5;

  public FileSystem testFS;
  public Folder[] testFolders;
  public File[] testFiles;

  /**
   * For each test case, create new FileSystem, Folder and File Objects
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   */
  @Before
  public void setUp()
      throws InvalidNameException, IncorrectContentTypeException,
      InvalidPathException, HasSameContentNameException {

    testFS = FileSystem.createNewFileSystem();

    testFS.changeDirectory(SLASH);

    testFiles = new File[numTest - 1];
    testFolders = new Folder[numTest];

    testFolders[0] = new Folder(SLASH, "testFolder1");
    testFolders[1] = new Folder("/testFolder1", "testFolder2");
    testFolders[2] = new Folder("/testFolder1/testFolder2", "testFolder3");
    testFolders[3] = new Folder("/testFolder1", "testFolder4");
    testFolders[4] = new Folder(SLASH, "testFolder5");

    testFiles[0] = new File(SLASH, "testFile1", "test file 1");
    testFiles[1] = new File("/testFolder1", "testFile2", "test file 2");
    testFiles[2] =
        new File("/testFolder1/testFolder2", "testFile3", "test file 3");
    testFiles[3] = new File("/testFolder1/testFolder2/testFolder3", "testFile4",
        "test file 4");

    if (testFS.getCurrFolder().getAllContents().isEmpty()) {

      // Create Directories that match the tree below
      testFS.makeDirectory(
          testFolders[0].getPath().concat(testFolders[0].getName()));
      testFS.makeDirectory(
          testFolders[1].getPath().concat("/" + testFolders[1].getName()));
      testFS.makeDirectory(
          testFolders[2].getPath().concat("/" + testFolders[2].getName()));
      testFS.makeDirectory(
          testFolders[3].getPath().concat("/" + testFolders[3].getName()));
      testFS.makeDirectory(
          testFolders[4].getPath().concat(testFolders[4].getName()));

      // Create Files that match the tree below
      testFS.makeFile("/testFile1");
      testFS.makeFile("/testFolder1/testFile2");
      testFS.makeFile("/testFolder1/testFolder2/testFile3");
      testFS.makeFile("/testFolder1/testFolder2/testFolder3/testFile4");
    }

    // tree view of the setUp(), with their Array Indices written next to them
       /*
        * Root
        *   |
        *   --------- testFolder1 [0]
        *   |           |
        *   |           ---------------- testFolder2 [1]
        *   |           |                  |
        *   |           |                  ---------- testFolder3 [2]
        *   |           |                  |           |
        *   |           |                  |           -----------testFile4 [3]
        *   |           |                  |
        *   |           |                  ---------- testFile3 [2]
        *   |           |
        *   |           ---------------- testFolder4 [3]
        *   |           |
        *   |           ---------------- testFile2 [1]
        *   |
        *   --------- testFolder5 [4]
        *   |
        *   --------- testFile1 [0]
        */ 

  }

  /**
   * Test FileSystem Constructor
   */
  @Test
  public void testFileSystemConstructor() {
    // Checks for name and path
    assertEquals(ROOT.getName(), testFS.getCurrFolder().getName());
    assertEquals(SLASH, testFS.getCurrPath());

  }

  /**
   * Test makeDirectory
   * 
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   */
  @Test
  public void testMakeDirectory() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {

    // Create testROOT folder and test folders in testROOT
    Folder testROOT = new Folder(SLASH, SLASH);
    Folder test1 = new Folder(SLASH, "a");
    Folder test2 = new Folder(SLASH, "b");
    Folder test3 = new Folder(SLASH, "c");

    // Make directory in ROOT
    testFS.makeDirectory("/a");
    testFS.makeDirectory("/b");
    testFS.makeDirectory("/c");

    // Check if the Contents are the same
    assertEquals(testROOT.getAllContents(), ROOT.getAllContents());

  }

  /**
   * Test makeDirectory with a symbolic name
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test(expected = InvalidNameException.class)
  public void testMakeDirectoryWithSymbolicName() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {

    // Create symbolic directory
    testFS.makeDirectory("/*&@^@");

  }

  /**
   * Test makeDirectory with duplicates
   * 
   * @throws InvalidPathException
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   */
  @Test(expected = HasSameContentNameException.class)
  public void testMakeDirectoryWithDuplicates()
      throws HasSameContentNameException, InvalidNameException,
      InvalidPathException {

    // Create duplicates
    testFS.makeDirectory("/user");
    testFS.makeDirectory("/user");

  }

  /**
   * Test makeFile according to the tree above
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   * @throws IncorrectContentTypeException
   * 
   */
  @Test
  public void testMakeFile() throws InvalidNameException, InvalidPathException,
      HasSameContentNameException, IncorrectContentTypeException {

    // Find testFile1 in ROOT
    assertEquals(testFiles[0].getName(),
        testFS.getFile("/testFile1").getName());

    // Find testFile2 in testFolder1
    assertEquals(testFiles[1].getName(),
        testFS.getFile("/testFolder1/testFile2").getName());

    // Find testFile3 in testFolder2
    assertEquals(testFiles[2].getName(),
        testFS.getFile("/testFolder1/testFolder2/testFile3").getName());

    // Find testFile4 and change directory to testFolder4
    assertEquals(testFiles[3].getName(), testFS
        .getFile("/testFolder1/testFolder2/testFolder3/testFile4").getName());

  }

  /**
   * Test changeDirectory according to the tree above
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   * 
   */
  @Test
  public void testChangeDirectory() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {

    // Starting from ROOT Directory to farthest directory(testFolder3)
    testFS.changeDirectory("/testFolder1/testFolder2/testFolder3");
    assertEquals(testFolders[2].getName(), testFS.getCurrFolder().getName());
    assertEquals("/testFolder1/testFolder2/testFolder3", testFS.getCurrPath());

    // From current(testFolder3) to testFolder4
    testFS.changeDirectory("/testFolder1/testFolder4");
    assertEquals(testFolders[3].getName(), testFS.getCurrFolder().getName());
    assertEquals("/testFolder1/testFolder4", testFS.getCurrPath());

    // From current(testFolder4) to ROOT
    testFS.changeDirectory("/");
    assertEquals(ROOT.getName(), testFS.getCurrFolder().getName());
    assertEquals(SLASH, testFS.getCurrPath());

    // From ROOT to testFolder5
    testFS.changeDirectory("/testFolder5");
    assertEquals(testFolders[4].getName(), testFS.getCurrFolder().getName());
    assertEquals("/testFolder5", testFS.getCurrPath());

    // From current(testFolder5) to testFolder5
    testFS.changeDirectory("/testFolder5");
    assertEquals(testFolders[4].getName(), testFS.getCurrFolder().getName());
    assertEquals("/testFolder5", testFS.getCurrPath());

  }

  /**
   * Test changeDirectory to a non-existent folder
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test(expected = InvalidPathException.class)
  public void testChangeDirectoryToNonExistentFolder()
      throws InvalidNameException, InvalidPathException,
      HasSameContentNameException {

    // From current(testFolder5) to testFolder6(non-existent directory)
    testFS.changeDirectory("/testFolder5/testFolder6");
  }

  /**
   * Test changeDirectory to a testFile
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test(expected = InvalidPathException.class)
  public void testChangeDirectoryInFile() throws InvalidPathException,
      HasSameContentNameException, InvalidNameException {

    // From ROOT to testFile1
    testFS.changeDirectory("/testFile1");
  }

  /**
   * Test makeDirectory and changeDirectory according to the tree above
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   * 
   */
  @Test
  public void testMakeDirectoryAndChangeDirectory() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {

    // Check testFolder1 and change directory to testFolder1
    testFS.changeDirectory("/testFolder1");
    assertEquals(testFolders[0].getName(), testFS.getCurrFolder().getName());

    // Check testFolder2 and change directory to testFolder2
    testFS.changeDirectory("/testFolder1/testFolder2");
    assertEquals(testFolders[1].getName(), testFS.getCurrFolder().getName());

    // Check testFolder3 and change directory to testFolder3
    testFS.changeDirectory("/testFolder1/testFolder2/testFolder3");
    assertEquals(testFolders[2].getName(), testFS.getCurrFolder().getName());

    // Check testFolder4 and change directory to testFolder4
    testFS.changeDirectory("/testFolder1/testFolder4");
    assertEquals(testFolders[3].getName(), testFS.getCurrFolder().getName());

    // Check testFolder5 and change directory to testFolder5
    testFS.changeDirectory("/testFolder5");
    assertEquals(testFolders[4].getName(), testFS.getCurrFolder().getName());

  }

  /**
   * Test getSubFolder according to the tree above
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   * @throws IncorrectContentTypeException
   * 
   */
  @Test
  public void testGetFile() throws InvalidNameException, InvalidPathException,
      HasSameContentNameException, IncorrectContentTypeException {

    // Check for each files created
    assertEquals("testFile1", testFS.getFile("/testFile1").getName());
    assertEquals("testFile2",
        testFS.getFile("/testFolder1/testFile2").getName());
    assertEquals("testFile3",
        testFS.getFile("/testFolder1/testFolder2/testFile3").getName());
    assertEquals("testFile4", testFS
        .getFile("/testFolder1/testFolder2/testFolder3/testFile4").getName());

  }

  /**
   * Test pathExist according to the tree above
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   * 
   */
  @Test
  public void testPathExist() throws InvalidNameException, InvalidPathException,
      HasSameContentNameException {

    // Checks the existing paths within the file system
    assertTrue(testFS.pathExist("/"));
    assertTrue(testFS.pathExist("/testFolder1"));
    assertTrue(testFS.pathExist("/testFolder1/testFolder2"));
    assertTrue(testFS.pathExist("/testFolder1/testFolder2/testFolder3"));
    assertTrue(testFS.pathExist("/testFolder1/testFolder4"));
    assertTrue(testFS.pathExist("/testFolder5"));

    // Checks the existing paths of files within the file system
    assertTrue(testFS.pathExist("/testFile1"));
    assertTrue(testFS.pathExist("/testFolder1/testFile2"));
    assertTrue(testFS.pathExist("/testFolder1/testFolder2/testFile3"));
    assertTrue(
        testFS.pathExist("/testFolder1/testFolder2/testFolder3/testFile4"));

    // Checks non-existent paths
    assertFalse(testFS.pathExist("/fwelfiug"));
    assertFalse(testFS.pathExist("/testFolder1/testFile3"));

  }

  /**
   * Test getPathContent according to the tree above
   * 
   * @throws InvalidPathException
   */
  @Test
  public void testGetPathContent() throws InvalidPathException {

    // Check the Folders
    assertEquals(testFolders[0].getName(),
        testFS.getPathContent("/testFolder1").getName());
    assertEquals(testFolders[1].getName(),
        testFS.getPathContent("/testFolder1/testFolder2").getName());
    assertEquals(testFolders[2].getName(), testFS
        .getPathContent("/testFolder1/testFolder2/testFolder3").getName());
    assertEquals(testFolders[3].getName(),
        testFS.getPathContent("/testFolder1/testFolder4").getName());
    assertEquals(testFolders[4].getName(),
        testFS.getPathContent("/testFolder5").getName());

    // Check the Files
    assertEquals(testFiles[0].getName(),
        testFS.getPathContent("/testFile1").getName());
    assertEquals(testFiles[1].getName(),
        testFS.getPathContent("/testFolder1/testFile2").getName());
    assertEquals(testFiles[2].getName(),
        testFS.getPathContent("/testFolder1/testFolder2/testFile3").getName());
    assertEquals(testFiles[3].getName(),
        testFS.getPathContent("/testFolder1/testFolder2/testFolder3/testFile4")
            .getName());
  }

  /**
   * Test getPathContent with non-existent content
   * 
   * @throws InvalidPathException
   */
  @Test(expected = InvalidPathException.class)
  public void testGetPathContentWithNonExistentContent()
      throws InvalidPathException {

    // get non-existing content
    testFS.getPathContent("/testfile100");
  }

  /**
   * Test getFolder with valid path
   * 
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  @Test
  public void testGetFolder()
      throws IncorrectContentTypeException, InvalidPathException {

    // checks Directory from tree above
    assertEquals(testFolders[0].getName(),
        testFS.getFolder("/testFolder1").getName());
    assertEquals(testFolders[1].getName(),
        testFS.getFolder("/testFolder1/testFolder2").getName());
    assertEquals(testFolders[2].getName(),
        testFS.getFolder("/testFolder1/testFolder2/testFolder3").getName());
    assertEquals(testFolders[3].getName(),
        testFS.getFolder("/testFolder1/testFolder4").getName());
    assertEquals(testFolders[4].getName(),
        testFS.getFolder("/testFolder5").getName());
  }

  /**
   * Test getFolder with invalid path
   * 
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  @Test(expected = InvalidPathException.class)
  public void getFolderWithInvalidPath()
      throws IncorrectContentTypeException, InvalidPathException {

    // get folder from incorrect path
    testFS.getFolder("/testFolder1/testFolder5");

  }

  /**
   * Test getFolder with incorrect Content Type
   * 
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  @Test(expected = IncorrectContentTypeException.class)
  public void getFolderWithIncorrectType()
      throws IncorrectContentTypeException, InvalidPathException {

    // get file
    testFS.getFolder("/testFile1");

  }

  /**
   * Test createCopy by cloning a File and Folder
   */
  @Test
  public void testCreateCopy() {

    // Create Copies of File and Folder
    Content copyFolder = testFS.createCopy(testFolders[0]);
    Content copyFile = testFS.createCopy(testFiles[0]);

    // Checks copy folder
    assertEquals(testFolders[0].getName(), copyFolder.getName());
    assertEquals(testFolders[0].getPath(), copyFolder.getPath());

    // Checks copy file
    assertEquals(testFiles[0].getName(), copyFile.getName());
    assertEquals(testFiles[0].getPath(), copyFile.getPath());

  }

}
