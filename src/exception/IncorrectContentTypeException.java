package exception;

public class IncorrectContentTypeException extends Exception {

  /**
   * Construct an Exception Object which fills in a default string message.
   */
  public IncorrectContentTypeException() {
    super("Invalid operation on this content type");
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public IncorrectContentTypeException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message
   * @param cause
   */
  public IncorrectContentTypeException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message
   */
  public IncorrectContentTypeException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param cause
   */
  public IncorrectContentTypeException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

}
