package driver;

/**
 * Returns Documentation on specific commands
 * 
 * @author Jason Chow Fong
 */
public class Documentation {

  /**
   * All of the documentation strings
   */
  private static final String EXIT_MAN = "Usage: exit\n\n"
      + "Takes no arguments.\n" + "Quits the JShell Program.";
  private static final String MKDIR_MAN =
      "Usage: mkdir DIR [DIR2 DIR3 DIR4" + "...]\n\n"
          + "Takes in one required argument DIR, a directory name, and an\n"
          + "unlimited number of optional arguments of other directory\n"
          + "names.\n\n"
          + "Arguments are directory names if in the current path, or full\n"
          + "paths, with the directory name we want to add at the end.\n\n"
          + "Creates directories, new directories can be relative to current\n"
          + "directory or be a full path.\n\n"
          + "NOTE: Directory names cannot have SPACES, or special characters\n"
          + "such as: !@$&*()?:[]\"<>'`|={}\\/,;";
  private static final String CD_MAN = "Usage: cd DIR\n\n"
      + "Takes one required argument DIR, a directory name.\n\n"
      + "Changes directory to DIR, which can be relative to the current\n"
      + "directory, or can be a full path.\n"
      + "Forward slashes must be used in writing directory.\n\n"
      + "Similar to Unix Commands...\n"
      + "\"cd ..\" means to go to parent directory.\n"
      + "\"cd .\" means the current directory.\n"
      + "\"cd /\" means to go to the foot of the file system.";
  private static final String LS_MAN = "Usage: ls [-R][PATH ...]\n\n"
      + "Takes multiple optional arguments, PATH, a relative or absolute\n"
      + "file path, for a Folder or a File. Also takes in an optional flag\n"
      + "\"-R\".\n\n"
      + "If no path is specified, ls prints the contents (files and\n"
      + "directories) of the current directory.\n\n"
      + "Also takes a flag \"-R\" which if present, ls recursively lists\n"
      + "all subdirectories of PATH.\n\n" + "If the specified PATH is ...\n"
      + "--> a file, then that filename is printed.\n"
      + "--> a directory, the directory name is printed, a colon, and then\n"
      + "    the contents of that directory are printed.\n"
      + "--> not a file or directory that exists, returns an error message.";
  private static final String PWD_MAN =
      "Usage: pwd\n\n" + "Takes no arguments\n\n"
          + "Prints the current working directory's absolute path";
  private static final String MV_MAN =
      "Usage: OLDPATH NEWPATH\n\n" + "Takes no arguments\n\n"
          + "Moves an item (file or directory) from OLDPATH to NEWPATH,\n"
          + "where OLDPATH and NEWPATH can be relative or absolute file\n"
          + "paths.\n\n"
          + "After an item is moved from OLDPATH to NEWPATH, the original\n"
          + "item at OLDPATH is removed.\n\n"
          + "If NEWPATH is a directory, then the item from OLDPATH is moved\n"
          + "into directory NEWPATH.";
  private static final String CP_MAN =
      "Usage: OLDPATH NEWPATH\n\n" + "Takes no arguments\n\n"
          + "Moves an item (file or directory) from OLDPATH to NEWPATH,\n"
          + "where OLDPATH and NEWPATH can be relative or absolute file"
          + "paths.\n\n"
          + "After an item is moved from OLDPATH to NEWPATH, the original\n"
          + "item at OLDPATH still remains.\n\n"
          + "If OLDPATH is a directory, then its contents are recursively\n"
          + "copied to NEWPATH.\n\n"
          + "If NEWPATH is a directory, then the item from OLDPATH is moved\n"
          + "into directory NEWPATH.";
  private static final String PUSHD_MAN = "Usage: pushd DIR\n\n"
      + "Takes one required argument, DIR, where DIR is the name of the\n"
      + "directory you want to be the NEW working directory.\n\n"
      + "Saves the current working directory by pushing it onto the top of\n"
      + "the DIRECTORY STACK, and then changes the current working directory\n"
      + "to DIR.\n\n"
      + "NOTE: The DIRECTORY STACK follows the same LIFO - (last in first\n"
      + "out) behaviour of an ordinary stack.";
  private static final String POPD_MAN =
      "Usage: popd\n\n" + "Takes no arguments.\n\n"
          + "Changes the current working directory to the one at the top of\n"
          + "the DIRECTORY STACK, and removes that directory from the\n"
          + "DIRECTORY STACK.\n\n"
          + "NOTE: The DIRECTORY STACK follows the same LIFO -\n"
          + "(last in first out) behaviour of an ordinary stack.";
  private static final String HISTORY_MAN = "Usage: history [NUMBER]\n\n"
      + "Takes one optional argument NUMBER, where NUMBER is a number\n"
      + "greater or equal to zero.\n\n"
      + "history prints out recent commands, with one command per line, each\n"
      + "line is numbered.\n\n"
      + "Commands are listed in time order, the most recent command is\n"
      + "listed with the highest number.\n\n"
      + "If the argument NUMBER is specified, then only that NUMBER of most\n"
      + "recent commands is printed.\n"
      + "For example: \"history 3\" prints out the 3 most recent commands.";
  private static final String CAT_MAN = "Usage: cat FILE1 [FILE2 ...]\n\n"
      + "Takes one required argument, and an unlimited number of optional\n"
      + "arguments. Every argument is a filename.\n\n"
      + "cat displays the file contents of the filename(s) specified in the\n"
      + "command's arguments.\n\n"
      + "If multiple arguments (ie. multiple filenames) are specified, then\n"
      + "the contents of all the files are output to JShell, with each file\n"
      + "separated by three line breaks.";
  private static final String CURL_MAN = "Usage: curl URL\n\n"
      + "Takes in one required argument URL. URL is a web address.\n\n"
      + "curl retrieves the file at URL and adds it to the current working\n"
      + "directory, with the same file name it has online.\n\n"
      + "Be sure to include the full URL, with the \"http://\" part of the\n"
      + "URL as well.\n\n" + "EXAMPLES:\n" + "    example 1:\n"
      + "    curl http://www.cs.cmu.edu/~spok/grimmtmp/073.txt\n"
      + "         ^creates a file with name \"073.txt\"  with the contents\n"
      + "         of file .../grimmtmp/073.txt in the current working\n"
      + "         directory.\n\n" + "    example 2:\n"
      + "    curl http://www.ub.edu/gilcub/SIMPLE/simple.html\n"
      + "         ^creates a file with name \"simple.html\" with the\n"
      + "         contents of file .../SIMPLE/simple.html in the current\n"
      + "         working directory.";
  private static final String ECHO_MAN = "NOTE: THREE different usages of "
      + "echo.\n" + "The different usages are labelled in Documentation as:\n"
      + "\"USAGE_1\", \"USAGE_2\", and \"USAGE_3\"\n\n"
      + "USAGE_1 - \"printing to JShell\"\n" + "USAGE: echo STRING\n\n"
      + "In USAGE_1, echo takes in one mandatory argument STRING, a String,\n"
      + "and prints that String to the JShell.\n\n"
      + "<><><><><><><><><><><><><><><><><>\n\n"
      + "USAGE_2 - \"writing to a file\"\n"
      + "USAGE_2: echo STRING [> OUTFILE]\n\n"
      + "In USAGE_2, echo takes in one mandatory argument STRING, a String,\n"
      + "and one optional argument of > OUTFILE. (the angle bracket is\n"
      + "needed there).\n\n"
      + "with > OUTFILE, where OUTFILE is the name of a file that you want\n"
      + "to create, or already exists. STRING will be written in that file.\n"
      + "If OUTFILE is a file that already exists, then the current contents\n"
      + "of OUTFILE are ERASED, and replaced with STRING.\n\n"
      + "If a file with name OUTFILE doesn't exist, then the file OUTFILE\n"
      + "will be created with contents STRING.\n\n"
      + "NOTE: FILE names cannot have SPACES, or special characters\n"
      + "such as: !@$&*()?:[]\"<>'`|={}\\/,;\n\n"
      + "<><><><><><><><><><><><><><><><><>\n\n"
      + "USAGE_3 - \"Appending a file\"\n"
      + "USAGE_3: echo STRING >> OUTFILE\n\n"
      + "In USAGE_3, echo takes in mandatory argument STRING, and token >>,\n"
      + "and argument OUTFILE, where OUTFILE is the path of or name of an\n"
      + "existing file.\n\n"
      + "With echo STRING >> OUTFILE, the contents of an existing file\n"
      + "OUTFILE will have STRING appended at the of the file.\n\n"
      + "If the file OUTFILE does not exist in the current directory, then\n"
      + "the file OUTFILE will be created, with STRING as its contents.\n"
      + "(Similar to how USAGE_2 works. (See above for USAGE_2)).\n\n"
      + "THERE ARE 3 DIFFERENT WAYS TO USE ECHO. SCROLL UP TO SEE THEM ALL.";
  private static final String RECALL_MAN = "Usage: !NUMBER\n\n"
      + "Takes one required argument NUMBER, where NUMBER is an integer,\n"
      + "greater or equal to 1.\n\n"
      + "!NUMBER will execute that number entry from history.\n"
      + "! executes past commands in the JShell that are stored in "
      + "history.\n\n"
      + "NOTE: If the the NUMBER entry in history is not a valid command,\n"
      + "JShell will warn you that it is invalid.\n\n"
      + "Type \"man history\" to see more about the history command.\n\n"
      + "EXAMPLE:\n"
      + "!3    <-- will execute the third entry stored in history.";
  private static final String GREP_MAN =
      "" + "Usage: grep [-R] REGEX PATH [PATH2 ...]\n\n"
          + "Takes in two required arguments REGEX and PATH and UNLIMITED\n"
          + "optional arguments PATH2, PATH3, and so on.\n"
          + "In the above, REGEX is a regular expression, and PATH is the\n"
          + "path (absolute or relative) of a File or Directory.\n"
          + "Also takes in an optional flag \"-R\".\n\n"
          + "IMPORTANT: Be sure that REGEX, is enclosed in double quotes.\n\n"
          + "The \"-R\" flag can be thought of as signaling RECURSIVE\n"
          + "search.\n\n"
          + "If the \"-R\" flag is not supplied, PATH must be a path for an\n"
          + "existing file.\n\n"
          + "If PATH is a file, grep will print any lines containing the\n"
          + "string REGEX in the file PATH.\n"
          + "(NOTE: This will behave the same whether the \"-R\" flag is\n"
          + "used or not)\n\n"
          + "If the \"-R\" flag is supplied, PATH usually is a path for a\n"
          + "directory.\n"
          + "With the \"-R\" flag, the directory PATH will be recursively\n"
          + "traversed, and for every file under the PATH directory, grep\n"
          + "will print out the lines containing the string REGEX.";
  private static final String MAN_MAN = "Usage: man CMD\n\n"
      + "Prints out the documentation for a command CMD.\n\n"
      + "To get documentation for any command,\n"
      + "type man CMD, where CMD is the name of a command.";
  private static final String COM_LIST =
      "List of commands:\n" + "!\n" + "cat\n" + "cd\n" + "cp\n" + "curl\n"
          + "echo\n" + "exit\n" + "grep\n" + "history\n" + "ls\n" + "man\n"
          + "mkdir\n" + "mv\n" + "popd\n" + "pushd\n" + "pwd";

  /**
   * Given a command 'command', getManPage() returns the MANual Page describing
   * how to use that command. NOTE: getManPage() is static. You do not have to
   * initialize an object to use it. Eg: Documentation.getManPage(cat);
   * 
   * @param command in form of how a user would write it.
   */
  public static String getManPage(String command) {
    String message;
    switch (command) {
      case "exit":
        message = Documentation.EXIT_MAN;
        break;
      case "mkdir":
        message = Documentation.MKDIR_MAN;
        break;
      case "cd":
        message = Documentation.CD_MAN;
        break;
      case "ls":
        message = Documentation.LS_MAN;
        break;
      case "pwd":
        message = Documentation.PWD_MAN;
        break;
      case "mv":
        message = Documentation.MV_MAN;
        break;
      case "cp":
        message = Documentation.CP_MAN;
        break;
      case "pushd":
        message = Documentation.PUSHD_MAN;
        break;
      case "popd":
        message = Documentation.POPD_MAN;
        break;
      case "history":
        message = Documentation.HISTORY_MAN;
        break;
      case "cat":
        message = Documentation.CAT_MAN;
        break;
      case "curl":
        message = Documentation.CURL_MAN;
        break;
      case "echo":
        message = Documentation.ECHO_MAN;
        break;
      case "!":
        message = Documentation.RECALL_MAN;
        break;
      case "grep":
        message = Documentation.GREP_MAN;
        break;
      case "man":
        message = Documentation.MAN_MAN + "\n----\n" + Documentation.COM_LIST;
        break;
      default:
        message = "Command not found, try \"man man\" for a list of "
            + "all commands.";
        break;
    }
    return message;
  }
}
