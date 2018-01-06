package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import commands.History;
import driver.CommandHistory;
import driver.PrintFormatter;
import exception.IncorrectCommandHistoryArgException;
import exception.InvalidCommandHistorySizeException;
import exception.InvalidNumberOfArgument;

/**
 * Tests the methods of History Class
 * 
 * @author Kevin Bato
 *
 */
public class HistoryTest {

  /**
   * Instantiate CommandHistory
   */
  private CommandHistory ch;

  /**
   * Instantiate PrintFormatter
   */
  private PrintFormatter pf;

  /**
   * Instantiate History
   */
  private History hist;

  /**
   * Instantiate param
   */
  private String[] param;

  /**
   * Create expected and actual output
   */
  private String expectOutput;
  private String actualOutput;

  /**
   * Before each test case
   */
  @Before
  public void setUp() {

    /**
     * Create new objects for test
     */
    ch = new CommandHistory();
    pf = new PrintFormatter();

    // Add command strings into command history object
    ch.addCommand("mkdir test");
    ch.addCommand("cd test");
    ch.addCommand("asdfghj");
    ch.addCommand("");

    // Create new History object
    hist = new History(ch, pf);

    // Create new actual Output
    actualOutput = "";

    // Create new expected Output
    expectOutput = "";

  }

  /**
   * test execute History command with no parameters
   *
   * @throws IncorrectCommandHistoryArgException
   * @throws InvalidNumberOfArgument
   * @throws InvalidCommandHistorySizeException
   */
  @Test
  public void testHistoryExecuteCommandNoParams()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument,
      IncorrectCommandHistoryArgException {

    // Create a new param string array
    param = new String[] {};

    // Set expected Output
    expectOutput = "1. mkdir test\n2. cd test\n3. asdfghj\n4. ";

    // Set param to hist
    hist.setParam(param);

    // Execute History
    hist.executeCommand();

    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

  }


  /**
   * test execute History command with one valid integer parameter
   *
   * @throws IncorrectCommandHistoryArgException
   * @throws InvalidNumberOfArgument
   * @throws InvalidCommandHistorySizeException
   */
  @Test
  public void testHistoryExecuteCommandOneValidParam()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument,
      IncorrectCommandHistoryArgException {

    // Create a new param string array
    param = new String[] {"3"};

    // Set expected Output
    expectOutput = "2. cd test\n3. asdfghj\n4. ";

    // Set param to hist
    hist.setParam(param);

    // Execute History
    hist.executeCommand();

    actualOutput = pf.getOutput();

    assertEquals(expectOutput, actualOutput);

  }


  /**
   * test execute History command with one invalid positive integer parameter
   *
   * @throws IncorrectCommandHistoryArgException
   * @throws InvalidNumberOfArgument
   * @throws InvalidCommandHistorySizeException
   */
  @Test(expected = InvalidCommandHistorySizeException.class)
  public void testHistoryExecuteCommandOneInvalidPosInteger()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument,
      IncorrectCommandHistoryArgException {

    // Create a new param string array
    param = new String[] {"100"};

    // Set param to hist
    hist.setParam(param);

    // Execute History
    hist.executeCommand();

  }

  /**
   * test execute History command with a negative integer parameter
   *
   * @throws IncorrectCommandHistoryArgException
   * @throws InvalidNumberOfArgument
   * @throws InvalidCommandHistorySizeException
   */
  @Test(expected = IncorrectCommandHistoryArgException.class)
  public void testHistoryExecuteCommandNegIntegerParam()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument,
      IncorrectCommandHistoryArgException {

    // Create a new param string array
    param = new String[] {"-100"};

    // Set param to hist
    hist.setParam(param);

    // Execute History
    hist.executeCommand();

  }

  /**
   * test execute History command with multiple invalid parameter
   *
   * @throws IncorrectCommandHistoryArgException
   * @throws InvalidNumberOfArgument
   * @throws InvalidCommandHistorySizeException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testHistoryExecuteCommandMultiInvalidParam()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument,
      IncorrectCommandHistoryArgException {

    // Create a new param string array
    param = new String[] {"-100", "100"};

    // Set param to hist
    hist.setParam(param);

    // Execute History
    hist.executeCommand();

  }

  /**
   * test execute History command with a valid and invalid parameter
   *
   * @throws IncorrectCommandHistoryArgException
   * @throws InvalidNumberOfArgument
   * @throws InvalidCommandHistorySizeException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testHistoryExecuteCommandValidAndInvalidParam()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument,
      IncorrectCommandHistoryArgException {

    // Create a new param string array
    param = new String[] {"2", "100"};

    // Set param to hist
    hist.setParam(param);

    // Execute History
    hist.executeCommand();

  }

  /**
   * test execute History command with a non-integer parameter
   *
   * @throws IncorrectCommandHistoryArgException
   * @throws InvalidNumberOfArgument
   * @throws InvalidCommandHistorySizeException
   */
  @Test(expected = IncorrectCommandHistoryArgException.class)
  public void testHistoryExecuteCommandNonIntegerParam()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument,
      IncorrectCommandHistoryArgException {

    // Create a new param string array
    param = new String[] {"abc"};

    // Set param to hist
    hist.setParam(param);

    // Execute History
    hist.executeCommand();

  }

  /**
   * test execute History command with mulitple non-integer parameter
   *
   * @throws IncorrectCommandHistoryArgException
   * @throws InvalidNumberOfArgument
   * @throws InvalidCommandHistorySizeException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testHistoryExecuteCommandMultiNonIntegerParam()
      throws InvalidCommandHistorySizeException, InvalidNumberOfArgument,
      IncorrectCommandHistoryArgException {

    // Create a new param string array
    param = new String[] {"abc", "dakfhga"};

    // Set param to hist
    hist.setParam(param);

    // Execute History
    hist.executeCommand();

  }

}
