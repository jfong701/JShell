package test;

import driver.CommandExecution;
import driver.CommandHistory;
import driver.Content;
import driver.FileSystem;
import driver.PrintFormatter;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNameException;
import exception.InvalidPathException;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the methods and behaviour of the CommandExecutionClass
 * 
 * @author Jason Chow Fong
 *
 */
public class CommandExecutionTest {

  /**
   * Declare needed instances and variables for tests
   */
  private static FileSystem fileSys;
  private static CommandHistory history;
  private static PrintFormatter format;
  private CommandExecution execution;
  private String[][] executeArgs;
  private String[] commandArgs;
  private String[] redirectArgs;
  private final String SLASH = "/";

  /**
   * Setup to run once
   */
  @BeforeClass
  public static void setUpClass() {
    fileSys = FileSystem.createNewFileSystem();
  }

  /**
   * Setup to run prior to each test
   */
  @Before
  public void setUp() {
    fileSys.reset();
    history = new CommandHistory();
    format = new PrintFormatter();
    execution = new CommandExecution(fileSys, history, format);

    executeArgs = new String[2][];

    redirectArgs = new String[2];
    redirectArgs[0] = "";
    redirectArgs[1] = "";

    executeArgs[1] = redirectArgs;
  }

  /**
   * Test if executing the mkdir command works properly.
   * 
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  @Test
  public void testExecuteMkDir()
      throws IncorrectContentTypeException, InvalidPathException {
    commandArgs = new String[2];
    commandArgs[0] = "mkdir";
    commandArgs[1] = "/folder1";

    // have index 0 of executeArgs point to the Array commandArgs.
    executeArgs[0] = commandArgs;

    execution.execute(executeArgs);
    assertEquals(commandArgs[1].substring(1),
        fileSys.getFolder("/folder1").getName());

    // test with multiple folders at different levels;
    commandArgs = new String[4];
    commandArgs[0] = "mkdir";
    commandArgs[1] = "/folder2";
    commandArgs[2] = "/folder1/something";
    commandArgs[3] = "/folder1/somethingelse";

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("folder2", fileSys.getFolder("/folder2").getName());
    assertEquals("something",
        fileSys.getFolder("/folder1/something").getName());
    assertEquals("somethingelse",
        fileSys.getFolder("/folder1/somethingelse").getName());

  }

  /**
   * Get no new folder when we try to make a directory with a name from invalid
   * characters.
   */
  @Test
  public void testExecuteMkDirInvalidName() {
    commandArgs = new String[2];
    commandArgs[0] = "mkdir";
    commandArgs[1] = "/~~@ @!";

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);

    ArrayList<Content> emptyArray = new ArrayList<Content>();
    assertEquals(emptyArray, fileSys.getCurrFolder().getAllContents());
  }

  /**
   * Test if we can enter / go to parent folders / given relative or absolute
   * paths.
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test
  public void testExecuteCd() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {
    commandArgs = new String[2];
    commandArgs[0] = "cd";
    // make some folders to enter and exit from, using fileSys directly,
    // instead of the commandExecutor.
    fileSys.makeDirectory("/folder1a");
    fileSys.makeDirectory("/folder1b");
    fileSys.makeDirectory("/folder1a/folder2a");
    fileSys.makeDirectory("/folder1a/folder2b");
    fileSys.makeDirectory("/folder1a/folder2a/folder3a");

    // NOTE, CommandInterpreter would already format everything into complete
    // paths.
    commandArgs[1] = "/folder1a";

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("/folder1a", fileSys.getCurrPath());



    commandArgs[1] = "/folder1a/folder2a";
    execution.execute(executeArgs);
    assertEquals("/folder1a/folder2a", fileSys.getCurrPath());



    // given a completely invalid path, the fileSys should not change
    // directories.

    // THIS WILL PRINT TO STDERR: "Invalid Path entered"
    commandArgs[1] = "edsfghnb sadfgyt";
    execution.execute(executeArgs);
    assertEquals("/folder1a/folder2a", fileSys.getCurrPath());

    commandArgs[1] = SLASH;
    execution.execute(executeArgs);
    assertEquals(SLASH, fileSys.getCurrPath());
  }

  /**
   * Test if regular ls without any arguments works properly.
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test
  public void testExecuteLsNoArgs() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {
    commandArgs = new String[1];
    commandArgs[0] = "ls";
    fileSys.makeDirectory("/folder1");
    fileSys.makeDirectory("/folder2");
    fileSys.makeDirectory("/folder3");

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("folder1\nfolder2\nfolder3", format.getOutput());
  }

  /**
   * Test if recursive ls without any arguments works properly
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test
  public void testExecuteLsNoArgsRecursive() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {
    commandArgs = new String[2];
    commandArgs[0] = "ls";
    commandArgs[1] = "-R";
    fileSys.makeDirectory("/folder1");
    fileSys.makeDirectory("/folder1/folder2");
    fileSys.makeDirectory("/folder1/folder3");

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    // prints the current items in a row first, then
    // containing items afterwards
    assertEquals("/:  folder1\nfolder1:  folder2  folder3\nfolder2:\nfolder3:",
        format.getOutput());
  }

  /**
   * Test if ls with arguments works properly.
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   * @throws IncorrectContentTypeException
   */
  @Test
  public void testExecuteLsWithArgs()
      throws InvalidNameException, InvalidPathException,
      HasSameContentNameException, IncorrectContentTypeException {
    commandArgs = new String[2];
    commandArgs[0] = "ls";

    // make some other contentItems to see that wont be called by argument
    // and see that they aren't read by ls
    fileSys.makeDirectory("/folderA");

    // test with an explicit path to a file.
    fileSys.makeFile("/file1");
    fileSys.getFile("/file1").overwriteData("some writing");
    fileSys.makeFile("/file2");
    fileSys.getFile("/file1").overwriteData("more writing");

    commandArgs[1] = "/file1";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("file1", format.getOutput());


    // multipleFiles should be alphabetic
    commandArgs = new String[3];
    commandArgs[0] = "ls";
    commandArgs[1] = "/file1";
    commandArgs[2] = "/file2";

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("file1\nfile2", format.getOutput());

    // test with an explicit path to a folder, put stuff in the folder.
    fileSys.makeDirectory("/folder1");
    fileSys.makeDirectory("/folder1/folder2");
    fileSys.makeDirectory("/folder1/folder3");
    fileSys.makeFile("/folder1/file1");
    commandArgs = new String[2];
    commandArgs[0] = "ls";
    commandArgs[1] = "/folder1";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("folder1:  file1  folder2  folder3", format.getOutput());
  }

  /**
   * Test if pwd prints the correct working directory.
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test
  public void testExecutePwd() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {
    // should start off in root directory.
    commandArgs = new String[1];
    commandArgs[0] = "pwd";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals(SLASH, fileSys.getCurrPath());
    assertEquals(SLASH, format.getOutput());

    // if entering a folder, should print path of that folder
    fileSys.makeDirectory("/folder1");
    fileSys.setCurrPath("/folder1");
    execution.execute(executeArgs);
    assertEquals("/folder1", fileSys.getCurrPath());
    assertEquals("/folder1", format.getOutput());

    // if entering a nested folder, should print path of that folder
    fileSys.makeDirectory("/folder1/folder2");
    fileSys.setCurrPath("/folder1/folder2");
    execution.execute(executeArgs);
    assertEquals("/folder1/folder2", fileSys.getCurrPath());
    assertEquals("/folder1/folder2", format.getOutput());
  }

  /**
   * Test if pushd properly pushes onto the DirectoryStack, and switches to a
   * new current directory.
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test
  public void testExecutePushd() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {
    commandArgs = new String[2];
    commandArgs[0] = "pushd";
    // test pushing the root directory with two entries. and switching to
    // one of the entries.
    // make some folders to enter and exit from, using fileSys directly,
    // instead of the commandExecutor.
    fileSys.makeDirectory("/folder1a");
    fileSys.makeDirectory("/folder1b");
    fileSys.makeDirectory("/folder1a/folder2a");
    fileSys.makeDirectory("/folder1a/folder2b");

    commandArgs[1] = "/folder1a";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    // current dir should now be /folder1a;
    assertEquals("/folder1a", fileSys.getCurrPath());

    // current dir should now be /folder2b;
    commandArgs[1] = "/folder1a/folder2b";
    execution.execute(executeArgs);
    assertEquals("/folder1a/folder2b", fileSys.getCurrPath());
  }

  /**
   * Test if pushd behaves correctly when an invalid path is pushed.
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test
  public void testExecutePushdException() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {
    commandArgs = new String[2];
    commandArgs[0] = "pushd";
    // THIS WILL PRINT TO STDERR: "Invalid Path entered" (x2)
    fileSys.makeDirectory("/folder1a");

    commandArgs[1] = "/DOESNOTEXIST";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);

    commandArgs[1] = "Inv@lid";
    execution.execute(executeArgs);

    // the current directory should still be the root after trying to push
    // two invalid paths.
    assertEquals(SLASH, fileSys.getCurrPath());
  }

  /**
   * Test if popd properly pops the top element from the DirectoryStack, and
   * switches to that directory.
   * 
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test
  public void testExecutePopd() throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {
    // need to push something in, to test if popping works
    fileSys.makeDirectory("/folder1a");
    fileSys.makeDirectory("/folder1b");
    fileSys.makeDirectory("/folder1a/folder2a");

    commandArgs = new String[2];
    commandArgs[0] = "pushd";
    commandArgs[1] = "/folder1a";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);

    commandArgs = new String[1];
    commandArgs[0] = "popd";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals(SLASH, fileSys.getCurrPath());

    // NOTE: the current directory is / - the root directory.

    // cd into /folder1a/folder2a, Dstack has /
    // pushing
    commandArgs = new String[2];
    commandArgs[0] = "pushd";
    commandArgs[1] = "/folder1a/folder2a";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);

    // cd into /folder1b, Dstack has /folder1a/folder2a -- /
    // pushing again
    commandArgs[1] = "/folder1b";
    execution.execute(executeArgs);

    // check paths, and pop.
    // check that we cd' d properly.
    assertEquals("/folder1b", fileSys.getCurrPath());

    // popping
    commandArgs = new String[1];
    commandArgs[0] = "popd";
    executeArgs[0] = commandArgs;

    execution.execute(executeArgs);
    assertEquals("/folder1a/folder2a", fileSys.getCurrPath());
    execution.execute(executeArgs);
    assertEquals("/", fileSys.getCurrPath());
  }

  /**
   * Test if popd properly returns an exception when trying to pop an empty
   * DirectoryStack.
   */
  @Test
  public void testExecutePopdEmptyDS() {
    // THIS WILL PRINT: "The DirectoryStack is empty, cannot pop a Folder
    // from it." (Default EmptyDirectoryStackException)
    commandArgs = new String[1];
    commandArgs[0] = "popd";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
  }

  /**
   * Test if history returns the entire list of past entries.
   */
  @Test
  public void testExecuteHistoryNoArgs() {
    commandArgs = new String[1];
    commandArgs[0] = "history";
    executeArgs[0] = commandArgs;

    history.addCommand("nonsense");
    execution.execute(executeArgs);
    assertEquals("1. nonsense", format.getOutput());

    history.addCommand("echo \"hello world\" > file1");
    execution.execute(executeArgs);
    assertEquals("1. nonsense\n2. echo \"hello world\" > file1",
        format.getOutput());
  }

  /**
   * Test if history returns the correct number of most recent past entries
   * when given a number
   */
  @Test
  public void testExecuteHistoryNumArg() {
    commandArgs = new String[1];
    commandArgs[0] = "history";
    executeArgs[0] = commandArgs;
    history.addCommand("nonsense");
    execution.execute(executeArgs);

    history.addCommand("nonsense2");
    history.addCommand("man cd");

    commandArgs = new String[2];
    commandArgs[0] = "history";
    commandArgs[1] = "2";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("2. nonsense2\n3. man cd", format.getOutput());
  }

  /**
   * Test if cat prints the correct contents of the specified file
   * 
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  @Test
  public void testExecuteCat()
      throws InvalidPathException, HasSameContentNameException,
      InvalidNameException, IncorrectContentTypeException {
    // test with only one file
    commandArgs = new String[2];
    commandArgs[0] = "cat";
    commandArgs[1] = "/file1";
    fileSys.makeFile(commandArgs[1]);
    fileSys.getFile(commandArgs[1]).overwriteData("blabla\nline 2.");

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("blabla\nline 2.", format.getOutput());


    // test with a file inside a folder
    fileSys.makeDirectory("/folder1");
    // overwrite the commandArg from above test
    commandArgs[1] = "/folder1/file2";
    fileSys.makeFile(commandArgs[1]);
    fileSys.getFile("/folder1/file2").overwriteData("THIS IS A FILE.");

    execution.execute(executeArgs);
    assertEquals("THIS IS A FILE.", format.getOutput());

    // test with multiple files, one in folder, one at root.
    commandArgs = new String[3];
    commandArgs[0] = "cat";
    commandArgs[1] = "/file1";
    commandArgs[2] = "/folder1/file2";

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("blabla\nline 2." + "\n\n\n" + "THIS IS A FILE.",
        format.getOutput());

    // test with multiple files, again, but one file doesn't exist
    commandArgs[2] = "/wrongFile";
    execution.execute(executeArgs);

    // note that the Error message should not actually be set in the
    // PrintFormatter's output here, but is instead printed from
    // CommandExecution. The other file prints as normal.
    //assertEquals("blabla\nline 2.", format.getOutput());
  }

  /**
   * Test if echo properly prints out the argument
   */
  @Test
  public void testExecuteEcho() {
    // printing command
    commandArgs = new String[2];
    commandArgs[0] = "echo";
    commandArgs[1] = "this is a test message";

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("this is a test message", format.getOutput());

    // test with some escape chars
    commandArgs[1] = "\"df";
    execution.execute(executeArgs);
    assertEquals("\"df", format.getOutput());

    // test with unusual chars
    commandArgs[1] = "!@#$%^&*~`";
    execution.execute(executeArgs);
    assertEquals("!@#$%^&*~`", format.getOutput());
  }

  /**
   * Test if file writing works correctly using the single '>'
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   * @throws InvalidNameException
   */
  @Test
  public void testSingleAngleBracketRedirect()
      throws InvalidPathException, IncorrectContentTypeException,
      InvalidNameException, HasSameContentNameException {
    commandArgs = new String[2];
    commandArgs[0] = "echo";
    commandArgs[1] = "this is text";

    // set the redirect args
    redirectArgs[0] = ">";
    redirectArgs[1] = "/file1";

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("this is text", fileSys.getFile("/file1").getData());

    // echo something different to an existing file (overwrite)
    commandArgs[1] = "NEW TEXT";
    execution.execute(executeArgs);
    assertEquals("NEW TEXT", fileSys.getFile("/file1").getData());

    // try to redirect the contents of ls into a new file
    fileSys.makeDirectory("/folder2");
    fileSys.makeDirectory("/folder1");

    commandArgs = new String[1];
    commandArgs[0] = "ls";
    redirectArgs[1] = "/file2";

    // set executeArgs to point to the new commandArgs of length 1.
    // NOTE: we are writing to the same file
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("file1\nfolder1\nfolder2",
        fileSys.getFile("/file2").getData());

    // try to redirect the contents of ls into a file that exists (overwrite)
    // we write into /file2 again

    // set executeArgs to point to the new commandArgs of length 1.
    // NOTE: we are writing to the same file - the assertion changes
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("file1\nfile2\nfolder1\nfolder2",
        fileSys.getFile("/file2").getData());
  }

  /**
   * Test if echo properly appends to a file, or creates a new file if needed.
   * 
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   */
  @Test
  public void testExecuteEchoFileAppending()
      throws InvalidPathException, IncorrectContentTypeException,
      InvalidNameException, HasSameContentNameException {

    // try to append to a file that does not exist, should create the file.
    commandArgs = new String[2];
    commandArgs[0] = "echo";
    commandArgs[1] = "this is line 1";

    redirectArgs[0] = ">>";
    redirectArgs[1] = "/file1";

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("this is line 1", fileSys.getFile("/file1").getData());

    // append a line to the file, should have a newline to separate the old
    // line from the new one.
    commandArgs[1] = "THIS IS APPENDED.";
    execution.execute(executeArgs);
    assertEquals("this is line 1\nTHIS IS APPENDED.",
        fileSys.getFile("/file1").getData());

    // try to append output that has been redirected from ls to a file that
    // does not exist
    commandArgs[0] = "ls";
    commandArgs[1] = "-R";

    // write to new file
    redirectArgs[1] = "/file2";

    execution.execute(executeArgs);
    assertEquals("/:  file1", fileSys.getFile("/file2").getData());

    // try to append output that has been redirected from ls to an existing
    // file
    fileSys.makeDirectory("/folder1");

    execution.execute(executeArgs);
    assertEquals("/:  file1\n/:  file1  file2  folder1\nfolder1:",
        fileSys.getFile("/file2").getData());
  }

  /**
   * Test if man prints the correct documentation
   */
  @Test
  public void testExecuteMan() {
    // check for 'man man'
    commandArgs = new String[2];
    commandArgs[0] = commandArgs[1] = "man";
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    // should have the two strings concatenated together:
    final String MAN_MAN = "Usage: man CMD\n\n"
        + "Prints out the documentation for a command CMD.\n\n"
        + "To get documentation for any command,\n"
        + "type man CMD, where CMD is the name of a command.";
    final String COM_LIST = "List of commands:\n" + "!\n" + "cat\n" + "cd\n"
        + "cp\n" + "curl\n" + "echo\n" + "exit\n" + "grep\n" + "history\n"
        + "ls\n" + "man\n" + "mkdir\n" + "mv\n" + "popd\n" + "pushd\n" + "pwd";
    assertEquals(MAN_MAN + "\n----\n" + COM_LIST, format.getOutput());

    // check if recall works with man
    history.addCommand("pwd");
    history.addCommand("mkdir someDirectory");
    commandArgs[1] = "!2";

    execution.execute(executeArgs);
    final String MKDIR_MAN = "Usage: mkdir DIR [DIR2 DIR3 DIR4" + "...]\n\n"
        + "Takes in one required argument DIR, a directory name, and an\n"
        + "unlimited number of optional arguments of other directory\n"
        + "names.\n\n"
        + "Arguments are directory names if in the current path, or full\n"
        + "paths, with the directory name we want to add at the end.\n\n"
        + "Creates directories, new directories can be relative to current\n"
        + "directory or be a full path.\n\n"
        + "NOTE: Directory names cannot have SPACES, or special characters\n"
        + "such as: !@$&*()?:[]\"<>'`|={}\\/,;";
    assertEquals(MKDIR_MAN, format.getOutput());
  }

  /**
   * Test if curl retrieves a URL correctly
   * 
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  @Test
  public void testCurl()
      throws InvalidPathException, IncorrectContentTypeException {
    // test an invalid URL, should not create a file
    commandArgs = new String[2];
    commandArgs[0] = "curl";
    commandArgs[1] = "ewrdfghbjnmwdofbcjlmsdxc.com";

    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);

    // the fileSystem should remain empty
    ArrayList<Content> emptyArray = new ArrayList<Content>();
    assertEquals(emptyArray, fileSys.getCurrFolder().getAllContents());

    // test with a text file
    commandArgs[1] = "http://www.cs.cmu.edu/~spok/grimmtmp/188.txt";
    execution.execute(executeArgs);
    final String aRiddlingTale =
        "Three women were transformed into flowers which grew in\n"
            + "the field, but one of them was allowed to be in her own home "
            + "at\n"
            + "night.  Then once when day was drawing near, and she was "
            + "forced to\n"
            + "go back to her companions in the field and become a flower "
            + "again,\n"
            + "she said to her husband, if you will come this afternoon and\n"
            + "gather me, I shall be set free and henceforth stay with you.  "
            + "And\n"
            + "he did so.  Now the question is, how did her husband know her, "
            + "for\n"
            + "the flowers were exactly alike, and without any difference.\n"
            + "Answer - as she was at her home during the night and not in "
            + "the\n"
            + "field, no dew fell on her as it did on the others, and by "
            + "this\n" + "her husband knew her.";
    assertEquals(aRiddlingTale, fileSys.getFile("/188.txt").getData());

    // test with an HTML page
    commandArgs[1] = "http://www.utsc.utoronto.ca/~nick/cscB36/index.html";
    execution.execute(executeArgs);
    assertEquals("Boo!", fileSys.getFile("/index.html").getData());
  }

  /**
   * Test if moving / renaming works
   * 
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   */
  @Test
  public void testMv() throws InvalidPathException,
      HasSameContentNameException, InvalidNameException {

    // move a file from a path that doesn't exist (should do nothing)
    commandArgs = new String[3];
    commandArgs[0] = "mv";
    commandArgs[1] = "/non-existent-path";
    commandArgs[2] = "/non-existent-path2";

    // the fileSystem should remain empty
    ArrayList<Content> emptyArray = new ArrayList<Content>();
    assertEquals(emptyArray, fileSys.getCurrFolder().getAllContents());

    // move an existing file into an existing folder
    commandArgs[1] = "/file1";
    commandArgs[2] = "/abc";

    fileSys.makeFile("/file1");
    fileSys.makeDirectory("/abc");
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("file1", fileSys.getPathContent("/abc/file1").getName());

    // rename a file (move an existing file to a non-existent path)
    commandArgs[1] = "/abc/file1";
    commandArgs[2] = "/newFileName";
    execution.execute(executeArgs);
    assertEquals("newFileName",
        fileSys.getPathContent("/abc/newFileName").getName());
  }

  /**
   * Test if copying works properly
   * 
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  @Test
  public void testCp()
      throws InvalidPathException, HasSameContentNameException,
      InvalidNameException, IncorrectContentTypeException {

    // copy a file from a path that doesn't exist (should do nothing)
    commandArgs = new String[3];
    commandArgs[0] = "cp";
    commandArgs[1] = "/non-existent-path";
    commandArgs[2] = "/non-existent-path2";

    // the fileSystem should remain empty
    ArrayList<Content> emptyArray = new ArrayList<Content>();
    assertEquals(emptyArray, fileSys.getCurrFolder().getAllContents());

    // copy an existing file into an existing folder
    commandArgs[1] = "/file1";
    commandArgs[2] = "/abc";

    fileSys.makeFile("/file1");
    fileSys.makeDirectory("/abc");
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("file1", fileSys.getPathContent("/abc/file1").getName());
    assertEquals("file1", fileSys.getPathContent("/file1").getName());

    // copy an existing folder with contents inside it to another folder
    fileSys.makeFile("/abc/file2");
    fileSys.makeDirectory("/destinationDir");
    commandArgs[1] = "/abc";
    commandArgs[2] = "/destinationDir";

    execution.execute(executeArgs);
    assertEquals("file1",
        fileSys.getPathContent("/destinationDir/abc/file1").getName());
    assertEquals("file2",
        fileSys.getPathContent("/destinationDir/abc/file2").getName());

    // make sure a DEEP COPY of the folder was made
    assertNotSame(fileSys.getFolder("/abc"),
        fileSys.getFolder("/destinationDir/abc"));
  }

  /**
   * Test if recall works properly
   * 
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  @Test
  public void testRecall()
      throws IncorrectContentTypeException, InvalidPathException {
    commandArgs = new String[1];
    commandArgs[0] = "!1";
    executeArgs[0] = commandArgs;

    // add some entries to history to simulate past commands
    history.addCommand("nonsense");
    history.addCommand("mkdir abc");
    history.addCommand("cd abc");

    // first execution should not do anything to fileSystem, fileSystem should
    // remain empty
    execution.execute(executeArgs);
    ArrayList<Content> emptyArray = new ArrayList<Content>();
    assertEquals(emptyArray, fileSys.getCurrFolder().getAllContents());

    // try !2 - the mkdir entry, should make a directory called 'abc'
    commandArgs[0] = "!2";
    execution.execute(executeArgs);
    assertTrue(fileSys.pathExist("/abc"));
    assertEquals(fileSys.getFolder("/abc"), fileSys.getPathContent("/abc"));

    // try !3 - the cd abc, the current directory should now be abc
    commandArgs[0] = "!3";
    execution.execute(executeArgs);
    assertEquals("/abc", fileSys.getCurrPath());
  }

  /**
   * Test grep command
   * 
   * @throws InvalidNameException
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  public void testGrep()
      throws InvalidPathException, HasSameContentNameException,
      InvalidNameException, IncorrectContentTypeException {
    // try to match lines in a file
    commandArgs = new String[3];
    // match lines that start with a
    commandArgs[0] = "grep";
    commandArgs[1] = "a.*";
    commandArgs[2] = "/file1";

    // make the file
    fileSys.makeFile("/file1");
    fileSys.getFile("/file1").overwriteData("abcde\nffewrdfg\na");
    executeArgs[0] = commandArgs;
    execution.execute(executeArgs);
    assertEquals("abcde\na", format.getOutput());

    // try to match lines from a folder, with recursion
    fileSys.makeDirectory("/folder1");
    fileSys.makeFile("/folder1/file2");
    fileSys.makeFile("/folder1/file3");
    fileSys.makeFile("/folder1/file4");

    fileSys.getFile("/folder1/file2").overwriteData("abc\ndef");
    fileSys.getFile("/folder1/file3").overwriteData("NO MATCH");
    fileSys.getFile("/folder1/file4").overwriteData("line1\nmatch");

    commandArgs = new String[4];
    // match lines containing an a
    commandArgs[0] = "grep";
    commandArgs[1] = "-R";
    commandArgs[1] = ".*a.*";
    commandArgs[2] = "/folder1";

    assertEquals("/folder1/file2: abc\n/folder1/file4: match",
        format.getOutput());
  }
}
