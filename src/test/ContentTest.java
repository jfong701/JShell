package test;

/**
 * Tests the methods and behaviour of Content Class
 * 
 * @author Kevin Bato
 */


import driver.Content;
import driver.Folder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ContentTest {

  // Declaration Variables

  public Content testContent;
  public Content testContent1;
  public Content testContent2;

  /**
   * Before each test, create new Content
   */
  @Before
  public void setUp() {

    testContent = new Content("/home", "TestContent");

  }

  /**
   * Test Content Constructor
   */
  @Test
  public void testContentConstructor() {

    // Checks for Content path
    assertEquals("/home", testContent.getPath());

    // Checks for Content name
    assertEquals("TestContent", testContent.getName());

  }

  /**
   * Test changeName
   */
  @Test
  public void testChangeName() {

    // Change content name
    testContent.changeName("NewTestContent");
    assertEquals("NewTestContent", testContent.getName());

  }

  /**
   * Test changePath
   */
  @Test
  public void testChangePath() {

    // Change content path
    testContent.changePath("/home");
    assertEquals("/home", testContent.getPath());

  }

  /**
   * Test equals
   */
  @Test
  public void testEquals() {

    testContent1 = new Content("/home", "TestContent");
    testContent2 = new Content("/home", "NewTestContent");

    // Tests if the Content names are the same
    assertTrue(testContent.equals(testContent1));
    assertFalse(testContent.equals(testContent2));

  }

}
