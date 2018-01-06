package driver;

/**
 * Represents a file content. It holds text(string) data
 * 
 * @author Sin
 *
 */
public class File extends Content {

  /**
   * The string data in this file object
   */
  private String data;

  /**
   * Default constructor
   * 
   * @param path The full path of this File
   * @param name The name of this File
   * @param data The data of this file
   */
  public File(String path, String name, String data) {
    super(path, name);
    this.data = data;
  }

  /**
   * Overwrite the file's data with the new_data
   * 
   * @param new_data
   */
  public void overwriteData(String new_data) {
    data = new_data;
  }

  /**
   * Return the data stored in this file
   * 
   * @return The data of this file
   */
  public String getData() {
    return data;
  }

  /**
   * Create a copy of this file
   */
  public File clone() {
    File clone = new File(this.getPath(), this.getName(), this.getData());
    return clone;
  }

}
