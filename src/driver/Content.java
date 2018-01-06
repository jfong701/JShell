package driver;

/**
 * Represent a content in a file system.
 * 
 * @author Sin Chi Chiu
 *
 */
public class Content implements Comparable<Content> {

  /**
   * name of the content
   */
  private String name;

  /**
   * The full path of this content
   */
  protected String path;



  /**
   * Default Constructor.
   */
  public Content(String path, String name) {
    this.name = name;
    this.path = path;
  }

  public String toString() {

    return this.getName();
  }

  /**
   * Change the name of this content
   * 
   * @param new_name
   */
  public void changeName(String new_name) {
    this.name = new_name;
  }

  /**
   * Return the name of this content
   * 
   * @return Name of content
   */
  public String getName() {
    return name;
  }

  /**
   * Changes the path of this object
   * 
   * @param new_path New path for this content
   */
  public void changePath(String new_path) {
    this.path = new_path;
  }

  /**
   * Return the path of this object
   * 
   * @param new_path New path for this content
   * @return The path of this content
   */
  public String getPath() {
    return path;
  }

  /**
   * Check if the two content has the same name, return true if so, false
   * otherwise
   * 
   * @param other The other content to be compared
   * @return Whether or not the two contents has the same name
   */
  public boolean equals(Content other) {

    return this.getName().equals(other.getName());
  }

  public Content clone(){
    Content clone = new Content(this.getName(), this.getPath());
    return clone;
  }

  /**
   * Compare this content object with another content object for order. It is
   * ordered by alphabetical order.
   */
  public int compareTo(Content c2) {
    return this.getName().compareTo(c2.getName());
  }

}
