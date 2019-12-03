package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class NoteTest {

  @Rule public WireMockRule wireMockRule = new WireMockRule();

  @Test
  public void testProtectedMethods() {

    Connection conn = new Connection("", true);
    conn.testModeOn();
    Note note = conn.newNote();
    note.id = 312;
    assertTrue("Note Entity id is wrong", note.getEntityId().equals("312"));
  }

  @Test
  public void testList() {

    // references connection_rr_77.json

    Connection conn = new Connection("", true);
    conn.testModeOn();

    try {

      Note note = conn.newNote();
      EntityList<Note> notes = note.listAll();

      assertTrue("Total count is incorrect", notes.getTotalCount() == 2);
      assertTrue("Id1 is incorrect", notes.get(0).id == 501L);
      assertTrue("Id2 is incorrect", notes.get(1).id == 502L);

    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testRetrieveCustomerNotes() {

    // references connection_rr_11.json
    // references connection_rr_115.json

    Connection conn = new Connection("", true);
    conn.testModeOn();

    try {

      Customer cust = conn.newCustomer().retrieve(11);
      Note note = cust.newNote();

      EntityList<Note> notes = note.listAll();

      assertTrue("Total count is incorrect", notes.getTotalCount() == 2);
      assertTrue("Id1 is incorrect", notes.get(0).id == 50001L);
      assertTrue("Id2 is incorrect", notes.get(1).id == 50004L);

    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testRetrieveInvoiceNotes() {

    // references connection_rr_14.json
    // references connection_rr_116.json

    Connection conn = new Connection("", true);
    conn.testModeOn();

    try {

      Invoice invoice = conn.newInvoice().retrieve(46225);
      Note note = invoice.newNote();

      EntityList<Note> notes = note.listAll();

      assertTrue("Total count is incorrect", notes.getTotalCount() == 2);
      assertTrue("Id1 is incorrect", notes.get(0).id == 50011L);
      assertTrue("Id2 is incorrect", notes.get(1).id == 50013L);

    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}
