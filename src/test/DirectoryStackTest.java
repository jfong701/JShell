package test;

import driver.DirectoryStack;
import driver.Folder;
import exception.EmptyDirectoryStackException;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods and behaviour of DirectoryStack Class
 * @author Jason Chow Fong
 */
public class DirectoryStackTest {

  // Declare variables for DirectoryStack, testing Folder objects
  // and for testing convenience.
  private final String SLASH = "/";
  private final Folder ROOT = new Folder(SLASH, SLASH);
  private final int NUM_FOLDERS = 5;

  private DirectoryStack ds;

  private Folder[] testFolders;

  /**
   * For each test case, create new Folder Objects, and a new DirectoryStack
   * instance.
   */
  @Before
  public void setUp()
  {
    // create DirectoryStack, and Folders for every test.
    ds = new DirectoryStack();
    testFolders = new Folder[NUM_FOLDERS];

    testFolders[0] = new Folder(SLASH, "folder1a");
    testFolders[1] = new Folder(SLASH, "folder1b");
    testFolders[2] = new Folder("/folder1a", "folder2a");
    testFolders[3] = new Folder("/folder1a", "folder2b");
    testFolders[4] = new Folder("/folder1a/folder2a", "folder3a");

    // tree view of the setUp(), with their Array Indices written next to them
    /*
     * Root
     *   |
     *   --------folder1a [0]
     *   |           |
     *   |           --------------folder2a [2]
     *   |           |                  |
     *   |           |                  ----------folder 3a [4]
     *   |           |
     *   |           --------------folder2b [3]
     *   |
     *   --------folder1b [1]
     */ 
  }

  /**
   * Test to see if pushing to the DirectoryStack works properly.
   * This method only puts one Folder in the DirectoryStack at a time.
   * @throws EmptyDirectoryStackException 
   */
  @Test
  public void testPushdOneCap() throws EmptyDirectoryStackException
  {
    ds.pushd(testFolders[2]);
    assertEquals(testFolders[2], ds.popd());

    ds.pushd(testFolders[2]);
    assertEquals(testFolders[2], ds.popd());

    ds.pushd(testFolders[0]);
    assertEquals(testFolders[0], ds.popd());

    ds.pushd(testFolders[1]);
    assertEquals(testFolders[1], ds.popd());
  }

  /**
   * Test to see if popping from the DirectoryStack works.
   * @throws EmptyDirectoryStackException 
   * @throws Exception
   */
  @Test
  public void testPopd() throws EmptyDirectoryStackException
  {
    ds.pushd(testFolders[1]);
    assertEquals(testFolders[1], ds.popd());

    ds.pushd(testFolders[0]);
    ds.pushd(testFolders[1]);
    ds.pushd(testFolders[2]);
    assertEquals(testFolders[2], ds.popd());
    assertEquals(testFolders[1], ds.popd());
    assertEquals(testFolders[0], ds.popd());

    // push the same thing 3 times, pop it out 3 times
    ds.pushd(testFolders[0]);
    ds.pushd(testFolders[0]);
    ds.pushd(testFolders[0]);
    assertEquals(testFolders[0], ds.popd());
    assertEquals(testFolders[0], ds.popd());
    assertEquals(testFolders[0], ds.popd());
  }

  /**
   * Test to see if DirectoryStack follows Last-In-First-Out rules,
   * even with multiple push statements and pop statements
   * @throws Exception
   */
  @Test
  public void testLIFO() throws EmptyDirectoryStackException
  {
    // push everything in
    for (int i = 0; i < NUM_FOLDERS; i++)
    {
      ds.pushd(testFolders[i]);
    }

    // pop everything out
    for (int i = NUM_FOLDERS-1; i >=0; i--)
    {
      assertEquals(testFolders[i], ds.popd());
    }

    // mix of pushing and popping.
    ds.pushd(testFolders[0]);
    ds.pushd(testFolders[3]);
    ds.pushd(testFolders[4]);
    assertEquals(testFolders[4], ds.popd());

    ds.pushd(testFolders[2]);
    assertEquals(testFolders[2], ds.popd());
    assertEquals(testFolders[3], ds.popd());
    assertEquals(testFolders[0], ds.popd());
  }

  /**
   * Check if we can peek the top element of the DirectoryStack
   * without removing anything from it.
   */
  @Test
  public void testPeek() throws Exception
  {
    // see that we can still popd() after peeking a DirectoryStack with only
    // one element
    ds.pushd(testFolders[0]);
    assertEquals(testFolders[0], ds.peek());
    assertEquals(testFolders[0], ds.popd());
  }

  /**
   * Check if the DirectoryStack is returns the proper number of elements
   * it contains.
   * @throws EmptyDirectoryStackException 
   */
  @Test
  public void testNumItems() throws EmptyDirectoryStackException
  {
    // should be 0 items initially.
    assertEquals(0, ds.numItems());

    // add one Folder, and check result
    ds.pushd(testFolders[0]);
    assertEquals(1, ds.numItems());

    // add another four folders, check result
    ds.pushd(testFolders[0]);
    ds.pushd(testFolders[0]);
    ds.pushd(testFolders[0]);
    ds.pushd(testFolders[1]);
    assertEquals(5, ds.numItems());

    // remove two folders, check result
    ds.popd();
    ds.popd();
    assertEquals(3, ds.numItems());
  }

  /**
   * Check if the DirectoryStack returns the proper values for isEmpty() when
   * the DirectoryStack is empty, or has Objects.
   */
  @Test
  public void testIsEmpty()
  {
    assertTrue(ds.isEmpty());
    ds.pushd(testFolders[0]);
    assertFalse(ds.isEmpty());
  }

  /**
   * Check that we get an EmptyDirectoryStackException when we try to pop an
   * empty DirectoryStack
   * @throws Exception
   */
  @Test(expected = EmptyDirectoryStackException.class)
  public void testEmptyDSPopException() throws Exception
  {
    ds.popd();
  }

  /**
   * Check that we get an EmptyDirectoryStackException when we try to peek an
   * empty DirectoryStack
   * @throws Exception
   */
  @Test(expected = EmptyDirectoryStackException.class)
  public void testEmptyDSPeekException() throws Exception
  {
    ds.peek();
  }

}
