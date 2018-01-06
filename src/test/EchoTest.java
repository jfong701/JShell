/**
 * Test the methods in Echo class
 */
package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import commands.Echo;
import driver.FileSystem;
import driver.PrintFormatter;
import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

/**
 * @author Haosen Xu
 *
 */

public class EchoTest {

  // Declaration of variables
  private PrintFormatter format;
  private FileSystem fileSys;
  private Echo echoCommand;
  String actual;
  String expected;

  /**
   * Before testing, initialize a FileSystem, PrintFormatter and an Echo
   * instance
   */
  @Before
  public void setUp() {
    fileSys = FileSystem.createNewFileSystem();
    format = new PrintFormatter();
    echoCommand = new Echo(fileSys, format);
  }

  /**
   * Test executeCommand method in echo
   */
  @Test
  public void testExecuteCommand() throws InvalidNumberOfArgument {
    expected = "hello there";
    String[] parameters = {"hello there"};

    echoCommand.setParam(parameters);
    echoCommand.executeCommand();

    actual = format.getOutput();

    assertEquals(expected, actual);
  }

  /**
   * Test executeCommand method in echo with an empty string in argument
   */
  @Test
  public void testExecuteCommandWithEmptyString()
      throws InvalidNumberOfArgument {
    expected = "";

    // Create a parameter with an empty string
    String[] parameters = {""};
    echoCommand.setParam(parameters);
    echoCommand.executeCommand();
    actual = format.getOutput();

    assertEquals(expected, actual);
  }

  /**
   * Test executeCommand to check if InvalidNumberOfArgument is thrown when
   * there's an argument size mismatch
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testEchoWithInvalidNumberOfArguments()
      throws InvalidNumberOfArgument {

    // Create a parameter of with two elements
    String[] parameters = {"hello there", "hi"};
    echoCommand.setParam(parameters);
    echoCommand.executeCommand();
  }
}
