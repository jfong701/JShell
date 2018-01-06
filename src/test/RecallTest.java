package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Recall;
import driver.CommandExecution;
import driver.CommandHistory;
import driver.CommandInterpreter;
import driver.Content;
import driver.FileSystem;
import driver.Folder;
import driver.PrintFormatter;
import exception.InvalidCommandException;
import exception.InvalidCommandHistorySizeException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * Test the methods of Recall class
 * 
 * @author Kevin Bato
 *
 */
public class RecallTest {

  /**
   * Instantiate Recall
   */
  private Recall reExe;

  /**
   * Instantiate CommandHistory
   */
  private CommandHistory ch;

  /**
   * Instantiate CommandExecution
   */
  private CommandExecution cExe;

  /**
   * Instantiate CommandInterpreter
   */
  private CommandInterpreter ci;

  /**
   * Instantiate PrintFormatter
   */
  private PrintFormatter pf;

  /**
   * Instantiate FileSystem
   */
  private FileSystem fs;

  /**
   * Instantiate param string array
   */
  private String[] param;

  /**
   * Before each test case
   * 
   * @throws Exception
   */
  @Before
  public void setUp() {

    // Create new Command History
    ch = new CommandHistory();

    // Add commands into command history
    ch.addCommand("mkdir test test1 test2");
    ch.addCommand("cd test");
    ch.addCommand("cd ..");
    ch.addCommand("history");

    // Create all Objects
    pf = new PrintFormatter();
    fs = FileSystem.createNewFileSystem();
    ci = new CommandInterpreter(fs);
    cExe = new CommandExecution(fs, ch, pf);
    reExe = new Recall(cExe, ch, fs);

  }

  /**
   * After each test case
   */
  @After
  public void tearDown() {
    // Reset FileSystem
    fs.reset();
  }

  /**
   * test Recall execute command with mkdir
   * 
   * @throws InvalidPathException
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   * @throws NumberFormatException
   * @throws InvalidCommandException
   */
  @Test
  public void testRecallMkdir() throws InvalidPathException,
      NumberFormatException, InvalidNumberOfArgument,
      InvalidCommandHistorySizeException, InvalidCommandException {

    // Set param
    param = new String[] {"1"};
    reExe.setParam(param);

    // Execute recall with param
    reExe.executeCommand();

    // Loop through to check all of the recently made directories
    for (Content c : fs.getCurrFolder().getAllContents()) {
      assertTrue(Pattern.matches("test|test1|test2", c.getName()));
    }
  }

  /**
   * test Recall execute command with cd
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   * @throws NumberFormatException
   * @throws InvalidCommandException
   */
  @Test
  public void testRecallCd()
      throws NumberFormatException, InvalidNumberOfArgument,
      InvalidCommandHistorySizeException, InvalidCommandException {

    // recall mkdir
    param = new String[] {"1"};
    reExe.setParam(param);
    reExe.executeCommand();

    // recall cd to test
    param = new String[] {"2"};
    reExe.setParam(param);
    reExe.executeCommand();

    assertEquals("test", fs.getCurrFolder().getName());

    // recall cd back to root
    param = new String[] {"3"};
    reExe.setParam(param);
    reExe.executeCommand();

    assertEquals("/", fs.getCurrFolder().getName());
  }

  /**
   * test Recall execute command with an invalid history size
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   * @throws NumberFormatException
   * @throws InvalidCommandException
   */
  @Test(expected = InvalidCommandHistorySizeException.class)
  public void testRecallInvalidCommand()
      throws NumberFormatException, InvalidNumberOfArgument,
      InvalidCommandHistorySizeException, InvalidCommandException {

    // Set param
    param = new String[] {"5"};
    reExe.setParam(param);
    // Execute invalid size of command history
    reExe.executeCommand();
  }

  /**
   * test Recall execute command with an invalid number argument
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   * @throws NumberFormatException
   * @throws InvalidCommandException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testRecallInvalidNumberOfArgument()
      throws NumberFormatException, InvalidNumberOfArgument,
      InvalidCommandHistorySizeException, InvalidCommandException {

    // Set param
    param = new String[] {"1", "3"};
    reExe.setParam(param);
    // Execute with multiple param
    reExe.executeCommand();
  }

  /**
   * test Recall execute command with no parameters
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   * @throws NumberFormatException
   * @throws InvalidCommandException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testRecallNoParam()
      throws NumberFormatException, InvalidNumberOfArgument,
      InvalidCommandHistorySizeException, InvalidCommandException {

    // Set param
    param = new String[] {};
    reExe.setParam(param);
    // Execute with no parameters
    reExe.executeCommand();
  }

  /**
   * test Recall execute command with a non-integer type argument
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   * @throws NumberFormatException
   * @throws InvalidCommandException
   */
  @Test(expected = InvalidCommandException.class)
  public void testRecallNumberFormatException()
      throws NumberFormatException, InvalidNumberOfArgument,
      InvalidCommandHistorySizeException, InvalidCommandException {

    // Set param
    param = new String[] {"abc"};
    reExe.setParam(param);
    // Execute with invalid type argument
    reExe.executeCommand();
  }


}
