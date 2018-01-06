package driver;

import java.util.EmptyStackException;
import java.util.Stack;

import exception.EmptyDirectoryStackException;

/**
 * Represents a stack of directories. Directories can be pushed onto or popped
 * off the Stack. Follows Standard LIFO behaviour of a stack.
 * 
 * @author Jason Chow Fong
 */
public class DirectoryStack {

  /**
   * Use a Java Stack holding Folder objects as the INTERNAL representation of
   * DirectoryStack.
   */
  private Stack<Folder> curStack;

  /**
   * Initialize a Java Stack that holds Objects of type Folder (will hold
   * directories that used to be working directories)
   */
  public DirectoryStack() {
    curStack = new Stack<Folder>();
  }

  /**
   * Push the current working directory onto the DirectoryStack
   * 
   * NOTE: does not change the current working directory, another class must
   * handle that
   * 
   * @param curr should be the current working directory. (will be pushed onto
   *        directory stack)
   */
  public void pushd(Folder curr) {
    curStack.push(curr);
  }

  /**
   * Removes and returns the top entry from the DirectoryStack (the one most
   * recently pushed in by pushd()) Throws an EmptyStackException if the
   * DirectoryStack is empty.
   * 
   * NOTE: Does not change the current working directory to the one that is
   * popped. Another class must handle that.
   * 
   * @return the Folder that has just been removed from the top of the
   *         DirectoryStack.
   * @throws EmptyDirectoryStackException If you try to pop an empty
   *         DirectoryStack.
   */
  public Folder popd() throws EmptyDirectoryStackException {
    Folder curFolder = null;

    // try to pop the top Folder off the stack. If the DirectoryStack is empty
    // then return an EmptyStackException
    try {
      curFolder = curStack.pop();
    } catch (EmptyStackException e) {
      throw new EmptyDirectoryStackException();
    }
    return curFolder;
  }

  /**
   * Returns the Folder Object from the top of the DirectoryStack. Note: It
   * does not remove that Folder Object from the DirectoryStack. Throws an
   * EmptyStackException if the DirectoryStack is empty.
   * 
   * @return The Folder currently at the top of the DirectoryStack.
   * @throws EmptyDirectoryStackException If you try to peek the top Folder of
   *         an empty DirectoryStack
   */
  public Folder peek() throws EmptyDirectoryStackException {
    Folder stackFolder = null;
    // try to pop the top Folder off the DirectoryStack.
    // If the DirectoryStack is empty, then return a custom Exception.
    try {
      stackFolder = curStack.peek();
    } catch (EmptyStackException e) {
      throw new EmptyDirectoryStackException();
    }
    return stackFolder;
  }

  /**
   * Returns the number of Folders (old working directories) currently sitting
   * in the DirectoryStack.
   * 
   * @return number of items in DirectoryStack
   */
  public int numItems() {
    return curStack.size();
  }

  /**
   * Returns true if the DirectoryStack is empty, and false otherwise
   * 
   * @return isEmpty status of DirectoryStack.
   */
  public boolean isEmpty() {
    return curStack.isEmpty();
  }

  /**
   * Returns a String that offers a view of the current DirectoryStack, with a
   * list of Folder names in the DirectoryStack, separated by newline
   * characters.
   * 
   * Newest elements are at the top of the String, Oldest elements are at the
   * bottom of the String.
   * 
   * @return A string representation of what the Stack looks like.
   */
  public String stringView() {
    String stackString = "";
    int stackSize = curStack.size();

    for (int i = stackSize - 1; i >= 0; i--) {
      stackString += curStack.elementAt(i).getName();
      if (i != 0) {
        stackString += "\n";
      }
    }
    return stackString;
  }
}
