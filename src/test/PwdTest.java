package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import commands.Pwd;
import driver.FileSystem;
import driver.PrintFormatter;
import exception.HasSameContentNameException;
import exception.InvalidNameException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;

public class PwdTest {
  private FileSystem fileSys;
  private PrintFormatter format;
  private Pwd dirPrinter;
  private String[] param;

  @Before
  public void setUp() throws InvalidNameException, InvalidPathException,
      HasSameContentNameException {
    fileSys = FileSystem.createNewFileSystem();
    format = new PrintFormatter();
    dirPrinter = new Pwd(fileSys, format);

  }

  /**
   * Test if pwd prints the correct working directory.
   * 
   * @throws InvalidNumberOfArgument
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test
  public void testExecuteCommand() throws InvalidNumberOfArgument,
      InvalidNameException, InvalidPathException, HasSameContentNameException {
    
    // set param
    param = new String[]{};
    dirPrinter.setParam(param);
    
    // should start off in root directory.
    dirPrinter.executeCommand();
    assertEquals("/", format.getOutput());

    fileSys.makeDirectory("/folder1");
    fileSys.makeDirectory("/folder1/folder2");

    // if entering a folder, should print path of that folder
    fileSys.setCurrPath("/folder1");
    dirPrinter.executeCommand();
    assertEquals("/folder1", format.getOutput());

    // if entering a nested folder, should print path of that folder
    fileSys.setCurrPath("/folder1/folder2");
    dirPrinter.executeCommand();
    assertEquals("/folder1/folder2", format.getOutput());
  }


  /**
   * Test if InvalidNumberOfArgument exception is thrown when there's one or
   * more parameters
   * 
   * @throws InvalidNumberOfArgument
   * @throws HasSameContentNameException
   * @throws InvalidPathException
   * @throws InvalidNameException
   */
  @Test(expected = InvalidNumberOfArgument.class)
  public void testExecuteCommandWithInvalidNumberOfArgument()
      throws InvalidNumberOfArgument {

    // Add an element to the parameter
    String parameters[] = {""};

    dirPrinter.setParam(parameters);
    dirPrinter.executeCommand();
  }

}
