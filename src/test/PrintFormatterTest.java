package test;

/**
 * Tests the methods and behaviour of PrintFormatter Class
 * 
 * @author Kevin Bato
 */


import driver.PrintFormatter;
import exception.HasSameContentNameException;
import exception.InvalidCommandHistorySizeException;
import driver.CommandHistory;
import driver.Content;
import driver.Folder;
import driver.File;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PrintFormatterTest {

  // Declaration variables for CommandHistory, PrintFormatter and expected
  public CommandHistory ch;
  public PrintFormatter pfTest;
  public String expected;

  /**
   * Before each test case, create a new PrintFormatter, CommandHistory and
   * expected
   */
  @Before
  public void setUp() {

    ch = new CommandHistory();
    pfTest = new PrintFormatter();
    expected = "";

  }

  /**
   * Test PrintFormatter Constructor
   */
  @Test
  public void testPrintFormatterConstructor() {
    // Expecting null
    assertEquals(null, pfTest.getOutput());

  }

  /**
   * Test setHistory with given commands that prints all
   * 
   * @throws InvalidCommandHistorySizeException
   */
  @Test
  public void testSetHistoryAll() throws InvalidCommandHistorySizeException {
    // Add commands into history
    ch.addCommand("cd ..");
    ch.addCommand("hello");
    ch.addCommand("mkdir Dir1");

    // Expecting all commands printed
    expected = "1. cd ..\n2. hello\n3. mkdir Dir1";
    pfTest.setHistory(ch.getLastCommands(ch.getSize()), ch.getSize());
    assertEquals(expected, pfTest.getOutput());

  }

  /**
   * Test setHistory with given commands that prints the last 2 commands
   * 
   * @throws InvalidCommandHistorySizeException
   */
  @Test
  public void testSetHistoryLastTwo()
      throws InvalidCommandHistorySizeException {
    // Add commands into history
    ch.addCommand("cd ..");
    ch.addCommand("hello");
    ch.addCommand("mkdir Dir1");

    // Expecting last 2 commands in history
    expected = "2. hello\n3. mkdir Dir1";
    pfTest.setHistory(ch.getLastCommands(2), ch.getSize());
    assertEquals(expected, pfTest.getOutput());
  }

  /**
   * Test setContents that contains no Folders or Files
   * 
   * @throws HasSameContentNameException
   */
  @Test
  public void testSetContentsNoContent() throws HasSameContentNameException {

    // Create ROOT Folder
    Folder testROOTFolder = new Folder("/", "testROOT");

    // Create an array list of type content to store all contents
    ArrayList<Content> testArray = new ArrayList<Content>();

    // Gets all contents from testROOTFolder
    testArray = testROOTFolder.getAllContents();

    // Expecting an empty string since there are no contents
    expected = null;
    pfTest.setLsContents(testArray);
    assertEquals(expected, pfTest.getOutput());
  }

  /**
   * Test setContents that contains a Folder and File
   * 
   * @throws HasSameContentNameException
   */
  @Test
  public void testSetContents() throws HasSameContentNameException {

    // Create a ROOT Folder that contains a File and Folder
    Folder testROOTFolder = new Folder("/", "testROOT");

    // Create an array list of type content to store all contents
    ArrayList<Content> testArray = new ArrayList<Content>();

    // Create test File and Folder
    Folder testFolder = new Folder("/", "testFolder");
    File testFile = new File("/", "testFile", "Testing!");

    // Add the test File and Folder into the ROOT Folder
    testROOTFolder.addContent(testFolder);
    testROOTFolder.addContent(testFile);

    // Gets all contents from testROOTFolder
    testArray = testROOTFolder.getAllContents();

    // Expect the testFile and testFolder in ROOT
    expected = "testFile\ntestFolder:";
    pfTest.setLsContents(testArray);
    assertEquals(expected, pfTest.getOutput());
  }

  /**
   * Test setContents that contains a Folder and File
   * 
   * @throws HasSameContentNameException
   */
  @Test
  public void testSetLsNoParams() throws HasSameContentNameException {

    // Create a ROOT Folder that contains a File and Folder
    Folder testROOTFolder = new Folder("/", "testROOT");

    // Create test File and Folder
    Folder testFolder = new Folder("/", "testFolder");
    File testFile = new File("/", "testFile", "Testing!");

    // Add the test File and Folder into the ROOT Folder
    testROOTFolder.addContent(testFolder);
    testROOTFolder.addContent(testFile);

    // Expect the testFile and testFolder in ROOT
    expected = "testFile\ntestFolder";
    pfTest.setLsNoParams(testROOTFolder);
    assertEquals(expected, pfTest.getOutput());
  }



  /**
   * Test printDocumentation for exit command
   */
  @Test
  public void testDocumentationExit() {

    // Expect the exit message from documentation
    expected = "Usage: exit\n\n" + "Takes no arguments.\n"
        + "Quits the JShell Program.";
    pfTest.setDocumentation("exit");
    assertEquals(expected, pfTest.getOutput());
  }

  /**
   * Test setCatFiles for one file
   */
  @Test
  public void testSetCatFilesOneFile() {

    // Create a test File
    File testFile1 = new File("/", "testFile1", "Testing!");

    // Create an Array List to hold Files
    ArrayList<File> testArrayFiles = new ArrayList<File>();

    // Add testFile1
    testArrayFiles.add(testFile1);

    // Expecting the content of the testFile to be Testing!
    expected = "Testing!";
    pfTest.setCatFiles(testArrayFiles);
    assertEquals(expected, pfTest.getOutput());
  }


  /**
   * Test setCatFiles for two files
   */
  @Test
  public void testSetCatFilesTwoFiles() {

    // Create two test Files
    File testFile1 = new File("/", "testFile1", "Testing!");
    File testFile2 = new File("/", "testFile2", "Still Testing!");

    // Create an Array List of Files to store both test files
    ArrayList<File> testArrayFiles = new ArrayList<File>();

    // Add both test files
    testArrayFiles.add(testFile1);
    testArrayFiles.add(testFile2);

    // Expecting the concatenation of testFile1 and testFile2
    expected = "Testing!\n\n\nStill Testing!";
    pfTest.setCatFiles(testArrayFiles);
    assertEquals(expected, pfTest.getOutput());
  }

  /**
   * Test setFileContent with a file with contents
   */
  @Test
  public void testSetPrintFileContent() {

    // Create new File with content inside
    File testFile1 = new File("/", "testFile1", "This a test file");

    // Expecting the content string
    expected = testFile1.getData();
    pfTest.setFileContent(testFile1);
    assertEquals(expected, pfTest.getOutput());
  }


}
