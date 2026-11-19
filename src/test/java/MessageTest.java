/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Student
 */
public class MessageTest {
    
    private Message message1;
    private Message message2;

    @Before
    public void setUp() {
        message1 = new Message("0012345678", 0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
        message2 = new Message("0187654321", 1, "08575975889",
                "Hi Keegan, did you receive the payment?");
    }

    // ---------- Message length ----------

    @Test
    public void testMessageLength_Success() {
        String expResult = "Message ready to send.";
        String result = message1.checkMessageLength();
        assertEquals(expResult, result);
    }

    @Test
    public void testMessageLength_Failure() {
        String longText = "";
        for (int i = 0; i < 260; i++) {
            longText = longText + "a";
        }
        Message longMessage = new Message("0012345678", 0, "+27718693002",
                longText);

        String expResult = "Message exceeds 250 characters by 10; "
                + "please reduce the size.";
        String result = longMessage.checkMessageLength();
        assertEquals(expResult, result);
    }

    // ---------- Recipient number ----------

    @Test
    public void testRecipientCell_Success() {
        String expResult = "Cell phone number successfully captured.";
        String result = message1.checkRecipientCell();
        assertEquals(expResult, result);
    }

    @Test
    public void testRecipientCell_Failure() {
        String expResult = "Cell phone number is incorrectly formatted or "
                + "does not contain an international code. Please correct "
                + "the number and try again.";
        String result = message2.checkRecipientCell();
        assertEquals(expResult, result);
    }

    // ---------- Message hash ----------

    @Test
    public void testMessageHash_TestCase1() {
        String expResult = "00:0:HITONIGHT";
        String result = message1.createMessageHash();
        assertEquals(expResult, result);
    }

    @Test
    public void testMessageHash_AllMessagesInLoop() {
        Message[] messages = {message1, message2};
        String[] expectedHashes = {"00:0:HITONIGHT", "01:1:HIPAYMENT"};

        for (int i = 0; i < messages.length; i++) {
            String result = messages[i].createMessageHash();
            assertEquals(expectedHashes[i], result);
        }
    }

    // ---------- Message ID ----------

    @Test
    public void testMessageID_Created() {
        assertTrue(message1.checkMessageID());
        assertEquals("Message ID generated: 0012345678",
                message1.messageIDCreated());
    }

    @Test
    public void testGenerateMessageID_IsTenDigits() {
        for (int i = 0; i < 50; i++) {
            String id = Message.generateMessageID();
            assertEquals(10, id.length());
            assertTrue(id.matches("[0-9]{10}")); // digits only
        }
    }

    // ---------- Send, disregard, store ----------

    @Test
    public void testSentMessage_Send() {
        String expResult = "Message successfully sent.";
        String result = message1.sentMessage(1);
        assertEquals(expResult, result);
    }

    @Test
    public void testSentMessage_Disregard() {
        String expResult = "Press 0 to delete the message.";
        String result = message2.sentMessage(2);
        assertEquals(expResult, result);
    }

    @Test
    public void testSentMessage_Store() {
        String expResult = "Message successfully stored.";
        String result = message1.sentMessage(3);
        assertEquals(expResult, result);
    }

    // ---------- Totals and printing ----------

    @Test
    public void testReturnTotalMessages() {
        ArrayList<Message> sentMessages = new ArrayList<Message>();

        message1.sentMessage(1);
        sentMessages.add(message1);

        message2.sentMessage(2);

        assertEquals(1, Message.returnTotalMessages(sentMessages));
    }

    @Test
    public void testPrintMessages() {
        ArrayList<Message> sentMessages = new ArrayList<Message>();
        sentMessages.add(message1);

        String printed = Message.printMessages(sentMessages);

        assertTrue(printed.contains("Message ID: 0012345678"));
        assertTrue(printed.contains("Message Hash: 00:0:HITONIGHT"));
        assertTrue(printed.contains("Recipient: +27718693002"));
        assertTrue(printed.contains(
                "Message: Hi Mike, can you join us for dinner tonight?"));
    }

    // ---------- Sender ----------

    @Test
    public void testSender_SetAndGet() {
        assertEquals("", message1.getSender());
        message1.setSender("Kyle Peters");
        assertEquals("Kyle Peters", message1.getSender());
    }

    // ---------- Storing in a JSON file ----------

    @Test
    public void testStoreMessage() throws Exception {
        String fileName = "test_storedMessages.json";
        ArrayList<Message> storedMessages = new ArrayList<Message>();
        storedMessages.add(message1);

        Message.storeMessage(storedMessages, fileName);

        File file = new File(fileName);
        assertTrue(file.exists());

        String contents = "";
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            contents = contents + reader.nextLine() + "\n";
        }
        reader.close();

        assertTrue(contents.contains("\"messageID\": \"0012345678\""));
        assertTrue(contents.contains("\"messageHash\": \"00:0:HITONIGHT\""));

        file.delete(); 
    }
}
