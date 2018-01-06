package test;

import commands.Man;
import driver.PrintFormatter;
import exception.InvalidCommandHistorySizeException;
import exception.InvalidNumberOfArgument;
import driver.CommandHistory;
import driver.Documentation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the methods of Man
 * 
 * @author Sin Chi Chiu
 *
 */
public class ManTest {
  private PrintFormatter format;
  private CommandHistory history;
  private Man man;
  private String[] command = new String[1];
  private String expected;

  /**
   * Setup before each test cases
   */
  @Before
  public void setUp() {
    format = new PrintFormatter();
    history = new CommandHistory();
    man = new Man(history, format);
  }

  /**
   * Test the exit command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testExit()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "exit";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the mkdir command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testMkdir()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "mkdir";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the cd command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testCd()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "cd";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the ls command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testLs()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "ls";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the pwd command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testPwd()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "pwd";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the mv command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testMv()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "mv";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the cp command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testCp()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "cp";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the pushd command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testPushd()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "pushd";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the popd command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testPopd()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "popd";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the history command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testhistory()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "history";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the cat command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testCat()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "cat";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the curl command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testCurl()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "curl";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the echo command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testEcho()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "echo";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the ! command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testRecall()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "!";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the grep command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testGrep()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "grep";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the man command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testMan()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "man";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the invalid command documentation
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testOther()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd = "Some invalid command";
    command[0] = cmd;
    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage(cmd);

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test when there are more than one parameter passed in
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testMultiParam()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String[] command = {"ls", "man"};
    man.setParam(command);
    man.executeCommand();
  }

  /**
   * Test the man using "!#" to call the history command at #
   * 
   * @throws InvalidCommandHistorySizeExceptio
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testRecallCommand()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd1 = "mkdir test";
    String cmd2 = "ls";
    history.addCommand(cmd1);
    history.addCommand(cmd2);
    command[0] = "!1";

    man.setParam(command);
    man.executeCommand();
    expected = Documentation.getManPage("mkdir");

    assertEquals(expected, format.getOutput());
  }

  /**
   * Test the man using "!#" to call the history that does not exist
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test(expected = InvalidCommandHistorySizeException.class)
  public void testNoSuchHistoryEntry()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument {
    String cmd1 = "mkdir test";
    String cmd2 = "ls";
    history.addCommand(cmd1);
    history.addCommand(cmd2);
    command[0] = "!5";

    man.setParam(command);
    man.executeCommand();
  }

}

