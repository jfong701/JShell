package test;

/**
 * Tests the methods of CommandHistory Class
 * 
 * @author Kevin Bato
 */

import driver.CommandHistory;
import exception.InvalidCommandHistorySizeException;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CommandHistoryTest {

  // Declaration Variables

  public CommandHistory commandList;
  public ArrayList<String> expectOutput;
  public ArrayList<String> actualOutput;

  /**
   * Before each test, create new objects
   */
  @Before
  public void setUp() {

    // Instantiate new CommandHistory
    commandList = new CommandHistory();

    // Create expected and actual values
    expectOutput = new ArrayList<String>();
    actualOutput = new ArrayList<String>();
  }

  /**
   * Test basic functionality of CommandHistory constructor
   */
  @Test
  public void testCommandHistoryConstructor() {
    // Checks empty command history
    assertEquals(0, commandList.getSize());

  }

  /**
   * Test basic functionality of addCommand method
   */
  @Test
  public void testAddCommand() {

    commandList.addCommand("cd ..");
    assertEquals(1, commandList.getSize());
    commandList.addCommand("mkdir Jshell");
    assertEquals(2, commandList.getSize());
    commandList.addCommand("akfqiueg");
    assertEquals(3, commandList.getSize());

  }

  /**
   * Test basic functionality of getLastCommands with valid size
   * 
   * @throws InvalidCommandHistorySizeException
   */
  @Test
  public void testGetLastCommands() throws InvalidCommandHistorySizeException {

    // Fill expected value array
    expectOutput.add("cd ..");
    expectOutput.add("cd root");

    // Fill command list with commands
    commandList.addCommand("cd");
    commandList.addCommand("cd ..");
    commandList.addCommand("cd root");

    // Let actualOutput be the output of getLastCommands
    actualOutput = commandList.getLastCommands(2);

    // Checks each element
    for (int i = 0; i < commandList.getSize() - 1; i++) {
      assertEquals(expectOutput.get(i), actualOutput.get(i));
    }

    // Checks everything
    assertEquals(expectOutput, actualOutput);
  }

  /**
   * Test basic functionality of getLastCommands with a positive integer
   * parameter
   * 
   * @throws InvalidCommandHistorySizeException
   */
  @Test
  public void testGetLastCommandsWithPositiveInt()
      throws InvalidCommandHistorySizeException {

    // Fill expected value array
    expectOutput.add("cd ..");
    expectOutput.add("cd root");

    // Fill command list with commands
    commandList.addCommand("cd");
    commandList.addCommand("cd ..");
    commandList.addCommand("cd root");

    // Let actualOutput be the output of getLastCommands
    actualOutput = commandList.getLastCommands(2);

    // Checks everything
    assertEquals(expectOutput, actualOutput);
  }

  /**
   * Test basic functionality of getLastCommands with a zero
   * 
   * @throws InvalidCommandHistorySizeException
   */
  @Test
  public void testGetLastCommandsWithZero()
      throws InvalidCommandHistorySizeException {

    // Fill command list with commands
    commandList.addCommand("cd");
    commandList.addCommand("cd ..");
    commandList.addCommand("cd root");

    // Let actualOutput be the output of getLastCommands
    actualOutput = commandList.getLastCommands(0);

    // Checks everything
    assertEquals(expectOutput, actualOutput);
  }

  /**
   * Test basic functionality of getLastCommands with the same size integer
   * 
   * @throws InvalidCommandHistorySizeException
   */
  @Test
  public void testGetLastCommandsWithSameSizeInt()
      throws InvalidCommandHistorySizeException {

    // Fill expected value array
    expectOutput.add("cd");
    expectOutput.add("cd ..");
    expectOutput.add("cd root");

    // Fill command list with commands
    commandList.addCommand("cd");
    commandList.addCommand("cd ..");
    commandList.addCommand("cd root");

    // Let actualOutput be the output of getLastCommands
    actualOutput = commandList.getLastCommands(3);

    // Checks each element
    for (int i = 0; i < commandList.getSize() - 1; i++) {
      assertEquals(expectOutput.get(i), actualOutput.get(i));
    }

    // Checks everything
    assertEquals(expectOutput, actualOutput);

  }

  /**
   * Test basic functionality of getLastCommands with a negative integer
   * 
   * Expected Output: Raise an Exception
   * 
   * @throws InvalidCommandHistorySizeException
   */
  @Test(expected = InvalidCommandHistorySizeException.class)
  public void testGetLastCommandsWithNegInt()
      throws InvalidCommandHistorySizeException {

    // Fill command list with commands
    commandList.addCommand("cd");
    commandList.addCommand("cd ..");
    commandList.addCommand("cd root");

    // Let actualOutput be the output of getLastCommands
    actualOutput = commandList.getLastCommands(-1);

  }

  /**
   * Test basic functionality of getLastCommands with an integer greater than
   * the size of commandList
   * 
   * Expected Output: Raise an Exception
   * 
   * @throws InvalidCommandHistorySizeException
   */
  @Test(expected = InvalidCommandHistorySizeException.class)
  public void testGetLastCommandsWithIntGreaterThanSize()
      throws InvalidCommandHistorySizeException {

    // Fill command list with commands
    commandList.addCommand("cd");
    commandList.addCommand("cd ..");
    commandList.addCommand("cd root");

    // Let actualOutput be the output of getLastCommands
    actualOutput = commandList.getLastCommands(4);
  }
  
  /**
   * Test basic functionality of getCommandString with an integer 
   * @throws InvalidCommandHistorySizeException 
   * 
   */
  @Test
  public void testGetCommandStringWithValidInteger() throws
  InvalidCommandHistorySizeException {

    // Fill command list with commands
    commandList.addCommand("cd");
    commandList.addCommand("cd ..");
    commandList.addCommand("cd root");

    // Let actualOutputString be the output of getCommandString
    String actualOutputString = commandList.getCommandString(1);
  
    assertEquals("cd ..", actualOutputString);
  }
  
  /**
   * Test basic functionality of getCommandString with an invalid integer 
   * 
   * @throws InvalidCommandHistorySizeException 
   * 
   */
  @Test(expected=InvalidCommandHistorySizeException.class)
  public void testGetCommandStringWithInvalidInteger() throws
  InvalidCommandHistorySizeException {

    // Fill command list with commands
    commandList.addCommand("cd");
    commandList.addCommand("cd ..");
    commandList.addCommand("cd root");

    // Let actualOutputString be the output of getCommandString
    String actualOutputString = commandList.getCommandString(4);
  
  }
}
