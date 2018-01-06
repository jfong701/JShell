package test;

/**
 * Tests the methods and behaviour of CommandInterpreter Class
 * 
 * @author Haosen Xu
 */

import driver.CommandInterpreter;
import driver.FileSystem;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CommandInterpreterTest {

  // Declaration variables for CommandInterpreter, actual and expected values
  private FileSystem fileSys;
  public CommandInterpreter ciTest;
  public String[][] actual;
  public String[][] expected;

  /**
   * Before each test case, create new CommandInterpreter and empty the actual
   * and expected
   */
  @Before
  public void setUp() {
    fileSys = FileSystem.createNewFileSystem();
    ciTest = new CommandInterpreter(fileSys);
    actual = new String[][] {};
    expected = new String[][] {};

  }


  /**
   * Test interpretCommand with mkdir command with one parameter
   */
  @Test
  public void testInterpretCommandWithMkdirWithPartialPath() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("mkdir test");

    // Expecting two element array containing "mkdir" and its argument
    expected = new String[][] {{"mkdir", "/test"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with mkdir command given full paths
   */
  public void testInterpretCommandWithMkdirWithFullPath() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("mkdir /test1/test");

    // Expecting two element array containing "mkdir" and its argument
    expected = new String[][] {{"mkdir", "/test1/test"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with mkdir command with one parameter
   */
  @Test
  public void testInterpretCommandWithMkdirWithMultiArgs() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("mkdir test1 test2 test3");

    // Expecting four element array containing "mkdir" and its arguments
    expected =
        new String[][] {{"mkdir", "/test1", "/test2", "/test3"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with mkdir with redirection
   */
  public void testInterpretCommandWithMkdirWithRedirection() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("mkdir test test0 >> test1");

    // Expecting two element array containing "mkdir" and its argument
    expected = new String[][] {{"mkdir", "/test", "/test0"}, {">>", "/test1"}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("mkdir test test0 > test1");

    // Expecting two element array containing "mkdir" and its argument
    expected = new String[][] {{"mkdir", "/test", "/test0"}, {">", "/test1"}};
    len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with valid input that contain multiple whitespace
   * characters
   */
  public void testInterpretCommandWithMultiWhitespace() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("  mkdir   test2   test3   >>   test4 ");

    // Expecting four element array containing "mkdir" and its arguments
    expected = new String[][] {{"mkdir", "/test1", "/test2", "/test3"},
        {">>", "/test4"}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }


  /**
   * Test interpretCommand with cd command with "."
   */
  @Test
  public void testInterpretCommandWithChangeDirectoryWithOnePeriod() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("cd .");

    // Expect two element array containing "cd" and its argument
    expected = new String[][] {{"cd", "/"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with cd command with ".."
   */
  @Test
  public void testInterpretCommandWithChangeDirectoryWithTwoPeriods() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("cd ..");

    // Expect two element array containing "cd" and its argument
    expected = new String[][] {{"cd", "/"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with cd command in test
   */
  @Test
  public void testInterpretCommandWithChangeDirectoryInCurrentDir() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("cd test");

    // Expect two element array containing "cd" and its argument
    expected = new String[][] {{"cd", "/test"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with cd command with a path
   */
  @Test
  public void testInterpretCommandWithChangeDirectoryWithAPath() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("cd /test1/test2/test3");

    // Expect two element array containing "cd" and its argument
    expected = new String[][] {{"cd", "/test1/test2/test3"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with ls command with no arguments
   */
  @Test
  public void testInterpretCommandLsWithNoArgs() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("ls");

    // Expect single element array containing "ls"
    expected = new String[][] {{"ls"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with ls command with a file
   */
  @Test
  public void testInterpretCommandLsWithAFile() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("ls /testFile");

    // Expect two element array containing "ls" and its argument
    expected = new String[][] {{"ls", "/testFile"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with ls command with a directory following other
   * directory
   */
  @Test
  public void testInterpretCommandLsWithDirectories() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("ls /test1/test2/test3");

    // Expect two element array containing "ls" and its argument
    expected = new String[][] {{"ls", "/test1/test2/test3"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with ls command with recursion
   */
  @Test
  public void testInterpretCommandLsWithRecursion() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("ls -r /test1/test2/test3 test4 >> hi");

    expected = new String[][] {{"ls", "-r", "/test1/test2/test3", "/test4"},
        {">>", "/hi"}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with pwd
   */
  @Test
  public void testInterpretCommandPwd() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("pwd");

    // Expect single element array containing "pwd"
    expected = new String[][] {{"pwd"}, {"", ""}};
    int len = actual[0].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }

  }

  /**
   * Test interpretCommand with pushd command
   */
  @Test
  public void testInterpretCommandPushd() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("pushd testDir");

    // Expect two element array containing "pushd" and its argument
    expected = new String[][] {{"pushd", "/testDir"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with popd command
   */
  @Test
  public void testInterpretCommandPopd() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("popd");
    expected = new String[][] {{"popd"}, {"", ""}};

    // Expect single element array containing "popd"
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with history command with no arguments
   */
  @Test
  public void testInterpretCommandHistoryWithNoArgs() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("history");

    // Expect single element array containing "history"
    expected = new String[][] {{"history"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }

  }

  /**
   * Test interpretCommand with history command with an argument
   */
  @Test
  public void testInterpretCommandHistoryWithOneArg() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("history 4");

    // Expect two element array containing "history" and its argument
    expected = new String[][] {{"history", "4"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with cat command with one argument
   */
  @Test
  public void testInterpretCommandCatWithOneArg() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("cat file1");

    // Expect two element array containing "cat" and its argument
    expected = new String[][] {{"cat", "/file1"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with cat command with multiple arguments
   */
  @Test
  public void testInterpretCommandCatWithMultipleArgs() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("cat file1 file2 file3 file4");

    // Expect five element array containing "cat" and its arguments
    expected = new String[][] {{"cat", "/file1", "/file2", "/file3", "/file4"},
        {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with echo command with a string and without an
   * outfile
   */
  @Test
  public void testInterpretCommandEchoWithStringWithoutAnOutfile() {

    // Create an echo STRING command
    String message = "echo " + '"' + "hello, I am testing" + '"';

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand(message);

    // Expect two element array containing "echo" and its argument
    expected = new String[][] {{"echo", "hello, I am testing"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with echo, trying to echo a string without double
   * quotations surrounding it
   */
  @Test
  public void testInterpretCommandEchoWithoutDoubleQuotation() {

    // Create an echo STRING command
    String message =
        "echo  " + "\'" + "hello I am testing" + "\'" + "   >>  file1";

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand(message);

    // Expect four element array containing "echo" and its arguments
    expected = new String[][] {{"invalid"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with echo with only one double quotation symbol
   */
  @Test
  public void testInterpretCommandEchoWithOneDoubleQuotation() {

    // Create an echo STRING command
    String message = "echo  " + "\"" + "hello I am testing" + "   >>  file1";

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand(message);

    // Expect four element array containing "echo" and its arguments
    expected = new String[][] {{"invalid"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }


  /**
   * Test interpretCommand with echo with an empty string
   */
  @Test
  public void testInterpretCommandEchoWithEmptyString() {

    // Create an echo STRING command
    String message = "echo  " + "\"\" " + " >>  file1";

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand(message);

    // Expect four element array containing "echo" and its arguments
    expected = new String[][] {{"echo", ""}, {">>", "/file1"}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }


  /**
   * Test interpretCommand with an invalid command
   */
  @Test
  public void testInterpretCommandWithInvalidCommand() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("Make me a sandwich");

    // Expect four element array containing "invalid" and its arguments
    expected = new String[][] {{"invalid", "me", "a", "sandwich"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with mv command with an under-numbered amount of
   * arguments
   */
  @Test
  public void testInterpretCommandMvLessThanMinArgs() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("mv file");

    expected = new String[][] {{"invalid"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }


  /**
   * Test interpretCommand with mv command
   */
  @Test
  public void testInterpretCommandMv() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("mv file1 file2 >> dir");

    expected = new String[][] {{"mv", "/file1", "/file2"}, {">>", "/dir"}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }


  /**
   * Test interpretCommand with cp command with an under-numbered amount of
   * arguments
   */
  @Test
  public void testInterpretCommandCpLessThanMinArgs() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("cp file");

    expected = new String[][] {{"invalid"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }


  /**
   * Test interpretCommand with mv command
   */
  @Test
  public void testInterpretCommandCp() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("cp file1 file2 >> dir");

    expected = new String[][] {{"cp", "/file1", "/file2"}, {">>", "/dir"}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with curl command
   */
  @Test
  public void testInterpretCommandCurl() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("curl www.svn.ca >> dir");

    expected = new String[][] {{"curl", "www.svn.ca"}, {">>", "/dir"}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with recall command with !*, where * is a numerical
   * string
   */
  @Test
  public void testInterpretCommandRecall() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("!8987 >> dir");

    expected = new String[][] {{"!8987"}, {">>", "/dir"}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with recall command with !*, where * is a not a
   * numerical string
   */
  @Test
  public void testInterpretCommandRecallInvalidRegex() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("!one >> dir");

    expected = new String[][] {{"invalid"}, {"", ""}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }


  /**
   * Test interpretCommand with grep command
   */
  @Test
  public void testInterpretCommandGrep() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("grep regex path1 path2 >> dir");

    expected =
        new String[][] {{"grep", "regex", "/path1", "/path2"}, {">>", "/dir"}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }

  /**
   * Test interpretCommand with grep command with recursion
   */
  @Test
  public void testInterpretCommandGrepWithRecursion() {

    // Get String array from Command interpreter
    actual = ciTest.interpretCommand("grep -R regex path1 path2 >> dir");

    expected = new String[][] {{"grep", "-R", "regex", "/path1", "/path2"},
        {">>", "/dir"}};
    int len = actual[0].length;

    for (int i = 0; i < len; i++) {
      assertEquals(expected[0][i], actual[0][i]);
    }
    len = actual[1].length;
    for (int i = 0; i < len; i++) {
      assertEquals(expected[1][i], actual[1][i]);
    }
  }



}

