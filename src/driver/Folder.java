package driver;

import java.util.ArrayList;
import java.util.Collections;

import exception.HasSameContentNameException;
import exception.InvalidPathException;

/**
 * Represents a folder content. It holds a list of other content objects.
 * 
 * @author Sin Chi Chiu
 */
public class Folder extends Content {
  /**
   * The list of contents in this Folder object
   */
  private ArrayList<Content> list_contents;

  /**
   * Default Constructor.
   * 
   * @param path The full path of the folder
   * @param name The name of the folder
   * @param parent The parent folder of this folder
   */
  public Folder(String path, String name) {
    super(path, name);
    list_contents = new ArrayList<Content>();
  }

  /**
   * Return a list of all contents(Files and Folders) stored in this object
   * 
   * @return An ArrayList of all contents in this folder object
   */
  public ArrayList<Content> getAllContents() {
    ArrayList<Content> list = new ArrayList<Content>();
    for (Content next : list_contents) {
      // add the content that is being cloned
      list.add(next.clone());
    }
    return list_contents;
  }

  /**
   * Return a specific content(File or Folder) in this object
   * 
   * @param content_name The name of the content to return
   * 
   * @return A content object with the given name
   */
  public Content getContent(String content_name) {
    Content desired = null;
    // loop through the list and find the content
    for (Content next_content : list_contents) {
      // remove it from the list when it's found
      if (next_content.getName().equals(content_name)) {
        desired = next_content;
        break;
      }
    }
    return desired;
  }

  /**
   * Add a new content to this folder object, new content will not be added if
   * there exist another content with the same name.
   * 
   * @param new_content The new content to be added
   * @throws HasSameContentNameException
   */
  public void addContent(Content new_content)
      throws HasSameContentNameException {
    // check if the list already has a content with the same name
    boolean has_same_name = false;
    for (Content next : list_contents) {
      has_same_name = next.getName().equals(new_content.getName());
      if(has_same_name)
        break;
    }
    if (has_same_name) {
      // raise an error for another content already has the same name
      throw new HasSameContentNameException("Error: Cannot create directory, "
          + "another content with the same name already exist");
    } else {
      // Add the new content to the list
      list_contents.add(new_content);
    }
    // sort the list in alphabetical order
    Collections.sort(list_contents);
  }

  /**
   * Remove a content in this folder object
   * 
   * @param content_name The name of the content to be removed
   * @throws InvalidPathException
   */
  public void removeContent(String content_name) throws InvalidPathException {
    boolean is_removed = false;
    // loop through the list and find the content
    for (Content next_content : list_contents) {
      // remove it from the list when it's found
      if (next_content.getName().equals(content_name)) {
        list_contents.remove(next_content);
        is_removed = true;
        break;
      }
    }
    if (!is_removed) {
      // Throw an error of no content found if nothing has been removed
      throw new InvalidPathException();
    }
  }

  /**
   * Replace all content in this folder with new content
   * 
   * @param new_data Contents to be replaced with
   */
  public void overwriteAllContents(ArrayList<Content> new_data) {
    list_contents = new_data;
  }

  /**
   * Change the path of this folder object, it will also change all its sub
   * content's path
   * 
   * @param new_path The new path of this Folder
   */
  public void changePath(String new_path) {
    path = new_path;
    for (Content next : list_contents) {
      next.changePath(path + "/" + next.getName());
    }
  }


  /**
   * Create a copy of this Folder
   */
  public Folder clone() {
    // create a new folder for the clone
    Folder clone = new Folder(this.getPath(), this.getName());

    // add all the content
    for (Content next : list_contents) {
      try {
        // add the content that is being cloned
        clone.addContent(next.clone());
      } catch (HasSameContentNameException e) {
      }
    }
    return clone;

  }


}
