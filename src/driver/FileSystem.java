package driver;

import java.util.ArrayList;

import exception.HasSameContentNameException;
import exception.IncorrectContentTypeException;
import exception.InvalidNameException;
import exception.InvalidPathException;

/**
 * Provides a java file system that manages Content objects. It allows adding
 * and returning contents.
 * 
 * @author Sin Chi Chiu
 *
 */

public class FileSystem {


  private static FileSystem referencedSystem = null;
  
  /**
   * Current working directory path
   */

  private String currPath;
  /**
   * Current working directory
   */
  private Folder currFolder;

  /**
   * Exceptions used in this class
   */
  private IncorrectContentTypeException incorrect_content_type_exception =
      new IncorrectContentTypeException();
  private InvalidPathException invalid_path_exception =
      new InvalidPathException("Error: Invalid path entered");
  private InvalidNameException invalid_name_exception =
      new InvalidNameException("Error: Name of a file contains invalid "
          + "characters: !@$&*()?:[]\"<>'`|={}\\/,;");

  /**
   * The root Folder object
   */
  private Folder root;

  /**
   * The directory indicator
   */
  private final String SLASH = "/";

  /**
   * The string of all invalid characters
   */
  private final String INVALID_CHAR = "/!@$&*()?:[]\"<>'`|={}\\,;";

  /**
   * Default Constructor
   */
  private FileSystem() {
    // Creates a root directory
    root = new Folder(SLASH, SLASH);
    currFolder = root;
    // Current path is the root directory
    currPath = SLASH;
  }
  
  /**
   * 
   * @return
   */
  public static FileSystem createNewFileSystem(){
    if(referencedSystem == null){
      referencedSystem = new FileSystem();
    }
    return referencedSystem;
  }

  /**
   * Creates a new directory for a given path. If the path does not exist then
   * an exception is thrown stating that the path does not exist.
   * 
   * @param path Location to create a new directory
   * @throws InvalidNameException
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   * 
   */
  public void makeDirectory(String path) throws InvalidNameException,
      InvalidPathException, HasSameContentNameException {

    // create a new folder
    Folder new_folder = null;
    // create a target location for the new folder
    Folder adding_location = null;

    // if the path is at root
    if (path.lastIndexOf(SLASH) == 0) {
      adding_location = getRoot();
      new_folder = new Folder(path, path.substring(1));
    }
    // if the path is not at root
    else {
      String parent_path = path.substring(0, path.lastIndexOf(SLASH));
      adding_location = getDirectory(parent_path);
      new_folder = new Folder(path, path.substring(path.lastIndexOf(SLASH) + 1));
    }
    if (isValidName(new_folder.getName())) {
      // Add the file if the name contains no invalid characters
      adding_location.addContent(new_folder);
    } else {
      throw invalid_name_exception;
    }
  }

  /**
   * Create a file at the current directory with the name of file_name
   * 
   * @param path The full path of where the file will be created
   * @throws InvalidPathException
   * @throws HasSameContentNameException
   * @throws InvalidNameException
   */
  public void makeFile(String path) throws InvalidPathException,
      HasSameContentNameException, InvalidNameException {
    boolean is_valid = isValidName(path.substring(path.lastIndexOf("/") + 1));
    if (!is_valid) {
      throw invalid_name_exception;
    }
    // get the parent path
    String parent_path = path.substring(0, path.lastIndexOf(SLASH));
    // get the parent folder
    Folder parent = getDirectory(parent_path);
    // create the new file with empty data
    File new_file =
        new File(path, path.substring(path.lastIndexOf(SLASH) + 1), "");

    // add the file to the folder
    parent.addContent(new_file);
  }

  /**
   * Change the current working directory to the given path directory
   * 
   * @param path Full path to change directory
   * @throws InvalidPathException
   * 
   */
  public void changeDirectory(String path) throws InvalidPathException {
    // if it goes back to root with the path SLASH
    if (path.equals(SLASH)) {
      setCurrFolder(getRoot());
    }
    // if it goes somewhere else
    else {
      //
      Folder dir = getDirectory(path);
      setCurrFolder(dir);
    }
    // set the path
    setCurrPath(currFolder.getPath());
  }

  /**
   * Return a content object at the given path
   * 
   * @param path The full path of the content wanted
   * @return Content at the given path
   * @throws InvalidPathException
   */
  public Content getPathContent(String path) throws InvalidPathException {
    String parent_path = path.substring(0, path.lastIndexOf(SLASH));
    String file_name = path.substring(path.lastIndexOf(SLASH) + 1);
    Folder parent = getDirectory(parent_path);
    Content wanted;
    // if the parent is at root and there is nothing else to search
    if (parent.equals(getRoot()) && file_name.equals("")) {
      // set wanted to parent
      wanted = parent;
    } else {
      // else get the content in the parent folder
      wanted = parent.getContent(file_name);
    }
    // if nothing is found, throw an invalid path exception
    if (wanted == null) {
      throw invalid_path_exception;
    }
    return wanted;
  }

  /**
   * Return the Folder object at the given path,
   * 
   * @param path The full path of the desired folder
   * @return Folder at the give path
   * @throws InvalidPathException
   */
  private Folder getDirectory(String path) throws InvalidPathException {
    Folder final_dir = getRoot();

    // if it is a relative path
    if (path.length() > currPath.length()
        && path.equals(path.substring(0, currPath.length()))) {

      int path_mod = 1;
      if (currPath.equals(SLASH)) {
        path_mod = 0;
      }
      // get the path after the current path
      String sub_path = path.substring(currPath.length() + path_mod);
      String[] directories = sub_path.split(SLASH);

      if (directories.length == 1) {
        final_dir = (Folder) currFolder.getContent(sub_path);
      }
      // If it moves into directories
      else {
        final_dir = getSubDirectory(currFolder, directories, 0);
      }
    }
    // If it is a full path
    else if (!path.equals("")) {
      String[] directories = path.substring(1).split(SLASH);
      final_dir = getSubDirectory(root, directories, 0);
    }
    return final_dir;
  }

  /**
   * Return the folder object with a given path starting at current directory.
   * 
   * @param curr the current working folder
   * @param path the path to change to
   * @param mod 1 if current directory is at root, 0 other wise
   * @return
   * @throws InvalidPathException
   */
  private Folder getSubDirectory(Folder curr, String[] path, int mod)
      throws InvalidPathException {
    Folder destination = curr;
    for (int i = 0; i < path.length - mod; i++) {

      try {
        destination = (Folder) destination.getContent(path[i]);
      }
      // if the content is not a folder object
      catch (ClassCastException e) {
        throw invalid_path_exception;
      }
      // if there is no folder object found
      if (destination == null) {
        throw invalid_path_exception;
      }
    }
    return destination;
  }

  /**
   * Return a File object at the given path
   * 
   * @param path
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  public File getFile(String path)
      throws InvalidPathException, IncorrectContentTypeException {
    Content wanted = getPathContent(path);

    // Check if the content received is a File object
    if (!(wanted instanceof File)) {
      throw incorrect_content_type_exception;
    }

    return (File) wanted;
  }

  /**
   * Return a Folder object at the given path
   * 
   * @param path
   * @return
   * @throws InvalidPathException
   * @throws IncorrectContentTypeException
   */
  public Folder getFolder(String path)
      throws IncorrectContentTypeException, InvalidPathException {
    Content wanted = getPathContent(path);
    // Check if the content received is a Folder object
    if (!(wanted instanceof Folder)) {
      throw incorrect_content_type_exception;
    }
    return (Folder) wanted;
  }

  /**
   * Return the parent Folder of a given content.
   * 
   * @param content
   * @return
   * @throws IncorrectContentTypeException
   * @throws InvalidPathException
   */
  public Folder getParentFolder(Content content)
      throws IncorrectContentTypeException, InvalidPathException {
    
    String path = content.getPath();
    String name = content.getName();
    int index = path.indexOf(name);
    path = path.substring(0, index - 1);
    Folder wanted;
    
    // if the path is not empty, get the folder at path
    if (!path.equals("")) {
      wanted = this.getFolder(path);
    }
    // Otherwise return the root
    else {
      wanted = this.getRoot();
    }

    return wanted;
  }

  /**
   * Create a copy of the content.
   * 
   * @param original The content to be copy
   * @return The copy of the original content
   */
  public Content createCopy(Content original) {
    // return the cloned content
    return original.clone();
  }


  /**
   * Checks if the path content exists within the file system
   * 
   * @param path Full path
   * @return True iff path exists, otherwise false
   */
  public boolean pathExist(String path) {
    // if the path is at root, return true
    if (path.equals(SLASH)) {
      return true;
    }
    // get the root folder
    Folder root = getRoot();
    Content object = null;
    String[] paths = path.split(SLASH);
    int i = 1;
    // keep looking for the next Folder, and check if it is a folder object
    while (root instanceof Folder && i < paths.length - 1) {
      root = (Folder) root.getContent(paths[i]);
      i++;
    }
    // check if an content is found
    if(paths.length != 1){
    object = root.getContent(paths[i]);
    }
    
    return object != null;
  }

  /**
   * Return true iff the given file name is a valid name
   * 
   * @param name
   * @return true if the name is valid, false otherwise
   */
  private boolean isValidName(String name) {
    // if the name contain noting
    if (name.length() != 1) {
      name = name.substring(1);
    }

    boolean is_valid = true;
    int i = 0;
    // keep checking for each character in the name
    while (i < name.length() && is_valid) {
      for (int j = 0; j < INVALID_CHAR.length(); j++) {
        is_valid = !name.contains(INVALID_CHAR.substring(j, j + 1));
        // if at any point the name has an invalid character, break the loop
        if (!is_valid) {
          break;
        }
      }
      i++;
    }
    return is_valid && i != 0;
  }

  /**
   * Set the current working path to the given path
   * 
   * @param path
   */
  public void setCurrPath(String path) {
    this.currPath = path;
  }

  /**
   * Return the current working path
   * 
   * @return currPath
   */
  public String getCurrPath() {
    return this.currPath;
  }

  /**
   * Set the current working folder to the given folder
   * 
   * @param file The folder object to change to
   */
  public void setCurrFolder(Folder file) {
    this.currFolder = file;
  }

  /**
   * Return the current working folder object
   * 
   * @return currFolder
   */
  public Folder getCurrFolder() {
    return this.currFolder;
  }
  
  /**
   * Reset the fileSystem to contain nothing 
   */
  public void reset(){
    root = new Folder(SLASH, SLASH);
    setCurrFolder(root);
    setCurrPath(root.getPath());
  }

  /**
   * Return the root folder object
   * 
   * @return ROOT
   */
  private Folder getRoot() {
    return root;
  }

}
