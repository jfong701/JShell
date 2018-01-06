package driver;

import java.util.ArrayList;

import exception.InvalidPathException;

/**
 * Given a Content Object, ContentEditor can delete strings from files, delete
 * objects out of Folders, append to files, and set new Paths for Folders.
 * 
 * @author Jason Chow Fong
 *
 */
public class ContentEditor {

  /**
   * Constant for often used NEWLINE
   */
  private final String NEW_LINE = "\n";

  /**
   * Default Constructor
   */
  public ContentEditor() {

  }

  /**
   * Delete All Folder contents - make the Folder empty
   * 
   * @param curFolder A passed in Folder object.
   * @throws InvalidPathException throw the InvalidPathException from Folder
   */
  public void deleteAllContents(Folder curFolder) throws InvalidPathException {
    // access a method from Folder to delete its contents.
    ArrayList<Content> allContents = curFolder.getAllContents();
    for (Content eachItem : allContents) {
      String contentName = eachItem.getName();
      curFolder.removeContent(contentName);
    }
  }

  /**
   * Delete All File contents - make the File empty
   * 
   * @param curFile A passed in File object.
   */
  public void deleteAllContents(File curFile) {
    curFile.overwriteData("");
  }

  /**
   * Append the string 'appended' to the end of the file
   * 
   * @param curFile the File object we want to append a string to.
   * @param appended A String to append to the File object.
   */
  public void appendContents(File curFile, String appended) {
    String newData = curFile.getData();
    if (!newData.equals(""))
      newData = newData + NEW_LINE;
    newData = newData + appended;
    curFile.overwriteData(newData);
  }

  /**
   * Sets a new path for a Folder curFolder, which will be String newPath.
   * 
   * @param curFolder The folder we want to change the path of.
   * @param newPath The new path we want to set.
   */
  public void setPath(Folder curFolder, String newPath) {
    curFolder.changePath(newPath);
  }
}
