package driver;

import java.util.Arrays;
import java.util.Hashtable;

import commands.Cat;
import commands.Cd;
import commands.Command;
import commands.Cp;
import commands.Curl;
import commands.Echo;
import commands.Grep;
import commands.Man;
import commands.Mkdir;
import commands.Mv;
import commands.Popd;
import commands.Pushd;
import commands.Pwd;
import commands.Recall;
import exception.EmptyDirectoryStackException;
import exception.HasSameContentNameException;
import exception.IncorrectCommandHistoryArgException;
import exception.IncorrectContentTypeException;
import exception.InvalidCommandException;
import exception.InvalidCommandHistorySizeException;
import exception.InvalidNameException;
import exception.InvalidNumberOfArgument;
import exception.InvalidPathException;
import exception.InvalidRegexException;
import exception.InvalidURLException;
import exception.MkdirMultiException;
import commands.History;
import commands.Ls;

/**
 * Execute the command based on what the user had inputed. The output of the
 * command will either be printed to the user, or it will be written to a file.
 * Appropriate error message will display if any of the command syntax is
 * incorrect.
 * 
 * @author Sin
 *
 */
public class CommandExecution {

  /**
   * String constant for the redirection call on append
   */
  private final String REDIRECTION_APPEND = ">>";
  /**
   * String constant for the redirection call on overwrite
   */
  private final String REDIRECTION_OVERWRITE = ">";
  /**
   * String constant for the empty string
   */
  private final String EMPTY_STRING = "";

  /**
   * Instance of FileSystem object
   */
  private FileSystem fileSys;
  /**
   * Instance of PrintFormatter object
   */
  private PrintFormatter format;
  /**
   * Instance of DirectoryStack object
   */
  private DirectoryStack stackDir;
  /**
   * Instance of CommandHistory object
   */
  private CommandHistory history;
  /**
   * Instance of Command object
   */
  private Command commandObject;
  /**
   * Instance of ContentEditor object
   */
  private ContentEditor content_edit;
  /**
   * The user inputed command stores here
   */
  private String command;

  /**
   * Hashtable object that holds all the commands and their respective keys
   */
  private Hashtable<String, Command> commandList =
      new Hashtable<String, Command>();


  /**
   * Default constructor
   * 
   * @param fs A working single FileSystem
   * @param ch CommandHistory
   * @param pf PrintFormatter
   */
  public CommandExecution(FileSystem fs, CommandHistory ch,
      PrintFormatter pf) {
    fileSys = fs;
    history = ch;
    format = pf;
    stackDir = new DirectoryStack();
    content_edit = new ContentEditor();
    // All command objects being used
    Mkdir mkdir = new Mkdir(fileSys);
    Cd cd = new Cd(fileSys);
    Pushd pushd = new Pushd(fileSys, stackDir);
    Popd popd = new Popd(fileSys, stackDir);
    Pwd pwd = new Pwd(fileSys, format);
    Ls ls = new Ls(fileSys, format);
    Man man = new Man(history, format);
    History hist = new History(history, format);
    Cat cat = new Cat(fileSys, format);
    Curl curl = new Curl(fileSys);
    Mv mv = new Mv(fileSys);
    Cp cp = new Cp(fileSys);
    Grep grep = new Grep(fileSys, format);
    Echo echo = new Echo(fileSys, format);
    Recall recall = new Recall(this, history, fileSys);
    // put all commands into an array
    Command[] commands = {mkdir, cd, pushd, popd, pwd, ls, man, hist, cat,
        curl, recall, mv, cp, grep, echo};

    // add the commands to the hash table
    for (Command next_command : commands) {
      String command_name =
          next_command.getClass().getName().toLowerCase().substring(9);
      commandList.put(command_name, next_command);
    }
  }


  /**
   * Execute the command using the user inputed command. If the inputed command
   * is in an incorrect syntax, return an appropriate error message.
   * 
   * @param formattedCommand A properly formatted command, the first string
   *        array contains the command and the second contains the redirection
   *        part.
   */
  public void execute(String[][] formattedCommand) {
    command = formattedCommand[0][0];
    String[] redirection = formattedCommand[1];
    String[] parameters;
    // Reset the output message to empty
    format.setOutput(EMPTY_STRING);
    // For !number command
    if (command.startsWith("!")) {
      // Overwrite the command and parameter if the command is !
      parameters = new String[] {command.substring(1)};
      command = "recall";
    } else {
      // Get the command's parameters
      parameters = Arrays.copyOfRange(formattedCommand[0], 1,
          formattedCommand[0].length);
    }
    // get the proper command object to execute
    commandObject = commandList.get(command);
    commandObject.setParam(parameters);
    this.run(commandObject);

    output(redirection);
  }

  /**
   * Execute the executeCommand method in the Command object
   * 
   * @param command The Command object being execute
   */
  private void run(Command command) {
    try {
      // execute the command
      command.executeCommand();
    } catch (HasSameContentNameException | InvalidPathException
        | IncorrectContentTypeException | InvalidURLException
        | InvalidNameException | InvalidCommandHistorySizeException
        | InvalidRegexException | MkdirMultiException | InvalidNumberOfArgument
        | IncorrectCommandHistoryArgException | EmptyDirectoryStackException
        | NumberFormatException | InvalidCommandException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Check for redirection request and see if it asks to write output to a file
   * or print the output to user
   * 
   * @param redir The redirection part of the inputed command
   */
  private void output(String[] redir) {
    // if it has no redirection
    if (redir[0].equals(EMPTY_STRING)
        && !format.getOutput().equals(EMPTY_STRING)) {
      format.printOutput();
    }
    // if it has redirection
    else if ((redir[0].equals(REDIRECTION_APPEND)
        || redir[0].equals(REDIRECTION_OVERWRITE))) {
      // create a new set of parameter
      try {
        this.redirectOutput(format.getOutput(), redir[0], redir[1]);
      } catch (InvalidNameException | InvalidPathException
          | HasSameContentNameException | IncorrectContentTypeException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Direct the output to a file user desired, if the file does not exist,
   * create the file.
   * 
   * @param text The data to be written to outfile
   * @param symbol The redirection symbol
   * @param outfile The file to be written
   * @throws InvalidNameException
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   * @throws IncorrectContentTypeException
   */
  private void redirectOutput(String text, String symbol, String outfile)
      throws InvalidNameException, InvalidPathException,
      HasSameContentNameException, IncorrectContentTypeException {
    boolean fileExists;
    // Check if the file given in outfile path exist in FileSystem
    fileExists = fileSys.pathExist(outfile);
    // If it does not exist make a new file in FileSystem given the full path
    // and name from outfile
    if (!fileExists
        && (!text.equals(EMPTY_STRING) || command.equals("echo"))) {
      fileSys.makeFile(outfile);
      writeFile(text, symbol, outfile);
    } else if (fileExists) {
      writeFile(text, symbol, outfile);
    }
  }

  /**
   * Write the output to the file desired
   * 
   * @param text The data to be written to outfile
   * @param symbol The redirection symbol
   * @param outfile The file to be written
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  private void writeFile(String text, String symbol, String outfile)
      throws InvalidPathException, IncorrectContentTypeException {
    // Get the file from the FileSystem
    File textFile = fileSys.getFile(outfile);

    // If the symbol matches with '>>' append contents in text to the file
    if (symbol.equals(REDIRECTION_APPEND)) {
      content_edit.appendContents(textFile, text);
    }
    // Else if the symbol is '>' overwrite its contents instead
    else if (symbol.equals(REDIRECTION_OVERWRITE)) {
      textFile.overwriteData(text);
    }
  }
}


