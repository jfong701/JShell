package driver;

import java.util.Scanner;

/**
 * A text base user interface that ask user for inputs.
 * 
 * @author Sin Chi Chiu
 *
 */
public class UI {
  // scanner object for getting input from user
  private Scanner userInput = new Scanner(System.in);

  /**
   * The user's inputed command
   */
  private String inputCommand;

  /**
   * Receive input from user
   * 
   * @return inputed command from user
   * 
   */
  public String getInput() {
    // print the JShell message
    System.out.print("JShell$ ");
    // get the input command from user
    inputCommand = userInput.nextLine();

    return inputCommand;
  }

}
