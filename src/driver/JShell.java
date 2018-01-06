// **********************************************************
// Assignment2:
// Student1: Sin Chi Chiu
// UTORID user_name: chiusin2
// UT Student #: 1002312543
// Author: Sin Chi Chiu
//
// Student2: Kevin Bato
// UTORID user_name: batokevi
// UT Student #: 1001264507
// Author: Kevin Bato
//
// Student3: Jason Chow Fong
// UTORID user_name: fongjas3
// UT Student #: 1002672146
// Author: Jason Chow Fong
//
// Student4: Haosen Xu
// UTORID user_name: xuhaosen
// UT Student #: 1002631976
// Author: Haosen Xu
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

/**
 * A java program that simulates the UNIX shell
 * 
 * @author Sin Chi Chiu, Kevin Bato, Jason Chow Fong, Haosen Xu
 *
 */
public class JShell {

  private static UI ui = new UI();
  private static FileSystem fileSys = FileSystem.createNewFileSystem();
  private static CommandHistory ch = new CommandHistory();
  private static PrintFormatter pf = new PrintFormatter();
  private static CommandInterpreter ci = new CommandInterpreter(fileSys);
  private static CommandExecution exec = new CommandExecution(fileSys, ch, pf);

  private static boolean exit = false;
  private static String command;
  private static String[][] formatted_cmd;

  public static void main(String[] args) {
    // Keep the JShell running
    while (!exit) {
      // get an input from the user
      command = ui.getInput();

      if (command.trim().indexOf("!") != 0) {
        // add the command to history
        ch.addCommand(command);
      }
      // check if the command ask for exit
      if (command.trim().equals("exit")) {
        exit = true;
      } else {
        // get a formatted command
        formatted_cmd = ci.interpretCommand(command);
        
        if (!formatted_cmd[0][0].equals("invalid")) {
          exec.execute(formatted_cmd);
        }
      }
    }
  }
}
