package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Ls;
import driver.File;
import driver.FileSystem;
import driver.Folder;
import driver.PrintFormatter;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNameException;
import exception.InvalidPathException;

/**
 * 
 * Test the methods of Ls class
 * 
 * @author Kevin Bato
 *
 */
public class LsTest {

  // Declaration Variables for FileSystem, ROOT folder,
  // Folders and Files
  private final String SLASH = "/";
  private final Folder ROOT = new Folder(SLASH, SLASH);
  private final int numTest = 5;
  public FileSystem fs;
  public Folder[] Folders;
  public File[] Files;

  /**
   * Instantiate Ls
   */
  private Ls lsTest;
  /**
   * Instantiate PrintFormatter
   */
  private PrintFormatter pf;

  /**
   * Create a string array param 
   */
  private String[] param;

  /**
   * Create expected and actual output string
   */
  private String actualOutput;
  private String expectOutput;


  /**
   * Before each test case
   * 
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   * @throws InvalidNameException
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws InvalidPathException, InvalidNameException,
      HasSameContentNameException {

    // Create New PrintFormatter Object
    pf = new PrintFormatter();

    // Set expected and actual output
    actualOutput = "";
    expectOutput = "";

    // Create new FileSystem
    fs = FileSystem.createNewFileSystem();

    // Start from ROOT
    fs.changeDirectory(SLASH);

    // Create Files and Folders array
    Files = new File[numTest - 1];
    Folders = new Folder[numTest];

    // Create Folder Objects into Folder array
    Folders[0] = new Folder(SLASH, "testFolder1");
    Folders[1] = new Folder("/testFolder1", "testFolder2");
    Folders[2] = new Folder("/testFolder1/testFolder2", "testFolder3");
    Folders[3] = new Folder("/testFolder1", "testFolder4");
    Folders[4] = new Folder(SLASH, "testFolder5");

    // Create File Objects into File array
    Files[0] = new File(SLASH, "testFile1", "test file 1");
    Files[1] = new File("/testFolder1", "testFile2", "test file 2");
    Files[2] = new File("/testFolder1/testFolder2", "testFile3", "test file 3");
    Files[3] = new File("/testFolder1/testFolder2/testFolder3", "testFile4",
        "test file 4");

    // Checks if there is a FileSystem
    if (fs.getCurrFolder().getAllContents().isEmpty()) {

      // Create Directories that match the tree below
      fs.makeDirectory(Folders[0].getPath().concat(Folders[0].getName()));
      fs.makeDirectory(Folders[1].getPath().concat("/" + Folders[1].getName()));
      fs.makeDirectory(Folders[2].getPath().concat("/" + Folders[2].getName()));
      fs.makeDirectory(Folders[3].getPath().concat("/" + Folders[3].getName()));
      fs.makeDirectory(Folders[4].getPath().concat(Folders[4].getName()));

      // Create Files that match the tree below
      fs.makeFile("/testFile1");
      fs.makeFile("/testFolder1/testFile2");
      fs.makeFile("/testFolder1/testFolder2/testFile3");
      fs.makeFile("/testFolder1/testFolder2/testFolder3/testFile4");
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


    // Create Ls Object
    lsTest = new Ls(fs, pf);

  }

  /**
   * After each test case
   */
  @After
  public void tearDown() {
    // Zero out the FileSystem
    fs.reset();
  }

  /**
   * test execute Ls command with no parameters
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testLsExecuteCommandNoParam()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {};
    lsTest.setParam(param);

    // Start at ROOT

    // Set expected and actual output
    expectOutput = "testFile1\ntestFolder1\ntestFolder5";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

    // Change directory to testFolder1
    fs.changeDirectory("/testFolder1");

    // Set param
    param = new String[] {};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput = "testFile2\ntestFolder2\ntestFolder4";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

    // Change directory to testFolder3
    fs.changeDirectory("/testFolder1/testFolder2/testFolder3");

    // Set param
    param = new String[] {};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput = "testFile4";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);
  }

  /**
   * test execute Ls command with a valid Folder parameter
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testLsExecuteCommandOneValidFolderParameter()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/"};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput = "/:  testFile1  testFolder1  testFolder5";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

    // Set param
    param = new String[] {"/testFolder1"};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput = "testFolder1:  testFile2  testFolder2  testFolder4";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

  }

  /**
   * test execute Ls command with a valid File parameter
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testLsExecuteCommandOneValidFileParameter()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/testFile1"};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput = "testFile1";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

    // Set param
    param = new String[] {"/testFolder1/testFolder2/testFolder3/testFile4"};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput = "testFile4";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

  }

  /**
   * test execute Ls command with multiple valid Folder parameters
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testLsExecuteCommandMultiValidFolderParameters()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/", "/testFolder1", "/testFolder1/testFolder2"};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput =
        "/:  testFile1  testFolder1  testFolder5\ntestFolder1:  testFile2"
        + "  testFolder2  testFolder4\ntestFolder2:  testFile3  testFolder3";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

  }

  /**
   * test execute Ls command with multiple valid File parameters
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testLsExecuteCommandMultiValidFileParameters()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/testFile1", "/testFolder1/testFile2",
        "/testFolder1/testFolder2/testFile3"};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput = "testFile1\ntestFile2\ntestFile3";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

  }

  /**
   * test execute Ls command with multiple valid Folder and File parameters
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testLsExecuteCommandMultiValidFolderAndFileParameters()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/", "/testFile1", "/testFolder1",
        "/testFolder1/testFile2"};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput =
        "/:  testFile1  testFolder1  testFolder5\ntestFile1\ntestFolder1: "
        + " testFile2  testFolder2  testFolder4\ntestFile2";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

  }

  /**
   * test execute Ls command using recursion with no parameters
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testLsExecuteCommandWithRecNoParam()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"-R"};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput =
        "/:  testFile1  testFolder1  testFolder5\ntestFolder1:  testFile2 "
        + " testFolder2  testFolder4\ntestFolder2:  testFile3  testFolder3\n"
        + "testFolder3:  testFile4\ntestFolder4:\ntestFolder5:";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

  }

  /**
   * test execute Ls command using recursion with one valid Folder parameter
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testLsExecuteCommandWithRecOneFolderParam()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"-R", "/testFolder1"};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput =
        "testFolder1:  testFile2  testFolder2  testFolder4\ntestFolder2:"
        + "  testFile3  testFolder3\ntestFolder3:  testFile4\ntestFolder4:";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

  }


  /**
   * test execute Ls command using recursion with one valid Folder parameter
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testLsExecuteCommandWithRecMultiFolderParams()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"-R", "/", "/testFolder1",
        "/testFolder1/testFolder2/testFolder3"};
    lsTest.setParam(param);

    // Set expected and actual output
    expectOutput =
        "/:  testFile1  testFolder1  testFolder5\ntestFolder1:  testFile2 "
        + " testFolder2  testFolder4\ntestFolder2:  testFile3 "
        + " testFolder3\ntestFolder3:  testFile4\ntestFolder4:\n"
        + "testFolder5:\ntestFolder1:  testFile2  testFolder2  testFolder4\n"
        + "testFolder2:  testFile3  testFolder3\ntestFolder3: "
        + " testFile4\ntestFolder4:\ntestFolder3:  testFile4";
    lsTest.executeCommand();
    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

  }

  /**
   * test execute Ls command with one an invalid Folder path parameter
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = InvalidPathException.class)
  public void testLsExecuteCommandWithOneInvalidFolderPathParam()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/testFolder6"};
    lsTest.setParam(param);

    // Execute Ls expecting an invalid Path error
    lsTest.executeCommand();

  }

  /**
   * test execute Ls command with one an invalid File path parameter
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = InvalidPathException.class)
  public void testLsExecuteCommandWithOneInvalidFilePathParam()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/testFile5"};
    lsTest.setParam(param);

    // Execute Ls expecting an invalid Path error
    lsTest.executeCommand();

  }

  /**
   * test execute Ls command with multiple invalid path parameters
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = InvalidPathException.class)
  public void testLsExecuteCommandWithMultiInvalidPathParams()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"/testFile2", "/testFolder12"};
    lsTest.setParam(param);

    // Execute Ls expecting an invalid Path error
    lsTest.executeCommand();

  }

  /**
   * test execute Ls command with recursion of one invalid Folder path 
   * parameter
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = InvalidPathException.class)
  public void testLsExecuteCommandWithRecOneInvalidPathParam()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"-R", "/testFolder2"};
    lsTest.setParam(param);

    // Execute Ls expecting an invalid Path error
    lsTest.executeCommand();

  }

  /**
   * test execute Ls command with recursion of one File path parameter
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = IncorrectContentTypeException.class)
  public void testLsExecuteCommandWithRecOneValidFilePathParam()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"-R", "/testFile1"};
    lsTest.setParam(param);

    // Execute Ls expecting an incorrect content type error
    lsTest.executeCommand();

  }

  /**
   * test execute Ls command with recursion of one File path parameter
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = IncorrectContentTypeException.class)
  public void testLsExecuteCommandWithRecMultiFilePathParams()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"-R", "/testFile1", "/testFolder1/testFile2"};
    lsTest.setParam(param);

    // Execute Ls expecting an incorrect content type error
    lsTest.executeCommand();

  }

  /**
   * test execute Ls command with recursion of one File path parameter
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test(expected = InvalidPathException.class)
  public void testLsExecuteCommandWithRecMultiInvalidFolderPathParams()
      throws InvalidPathException, IncorrectContentTypeException {

    // Set param
    param = new String[] {"-R", "/testFolder8", "/testFolder3"};
    lsTest.setParam(param);

    // Execute Ls expecting an invalid path error
    lsTest.executeCommand();

  }

}
