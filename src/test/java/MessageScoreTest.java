
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.mycompany.poe.Message;
import com.mycompany.poe.MessageStore;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Student
 */
public class MessageScoreTest {
    
    private MessageStore store;
    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    private Message makeMessage(String id, int number, String recipient,
            String text) {
        Message message = new Message(id, number, recipient, text);
        message.setSender("Kyle Peters");
        return message;
    }

    @Before
    public void setUp() {
        store = new MessageStore();

        message1 = makeMessage("0012345678", 0, "+27834557896",
                "Did you get the cake?");
        message2 = makeMessage("0187654321", 1, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        message3 = makeMessage("0234567891", 2, "+27834484567",
                "Yohoooo, I am at your gate.");
        message4 = makeMessage("0838884567", 3, "0838884567",
                "It is dinner time!");
        message5 = makeMessage("0345678912", 4, "+27838884567",
                "Ok, I am leaving without you.");

        store.addSentMessage(message1); 
        store.addStoredMessage(message2);
        store.addDisregardedMessage(message3); 
        store.addSentMessage(message4);
        store.addStoredMessage(message5);     
    }

    // ---------- Arrays correctly populated ----------

    @Test
    public void testSentMessagesArray_CorrectlyPopulated() {
        ArrayList<String> expResult = new ArrayList<String>(Arrays.asList(
                "Did you get the cake?", "It is dinner time!"));
        ArrayList<String> result = store.getSentMessageTexts();
        assertEquals(expResult, result);
    }

    @Test
    public void testDisregardedAndStoredArrays_CorrectlyPopulated() {
        assertEquals(1, store.getDisregardedMessages().size());
        assertEquals("Yohoooo, I am at your gate.",
                store.getDisregardedMessages().get(0).getMessageText());

        assertEquals(2, store.getStoredMessages().size());
    }

    @Test
    public void testHashAndIDArrays_ContainAllMessages() {
        assertEquals(5, store.getMessageHashes().size());
        assertEquals(5, store.getMessageIDs().size());

        assertTrue(store.getMessageIDs().contains("0838884567"));
        assertTrue(store.getMessageHashes().contains(
                message1.getMessageHash()));
    }

    // ---------- Display the longest message ----------

    @Test
    public void testLongestMessage() {
        String expResult = "Where are you? You are late! "
                + "I have asked you to be on time.";
        String result = store.longestMessage();
        assertEquals(expResult, result);
    }

    // ---------- Search for a message ID ----------

    @Test
    public void testSearchForMessageID() {
        Message found = store.findMessageByID("0838884567");

        assertNotNull(found);
        assertEquals("It is dinner time!", found.getMessageText());
    }

    @Test
    public void testSearchForMessageID_NotFound() {
        assertNull(store.findMessageByID("9999999999"));
    }

    // ---------- Search all messages for a recipient ----------

    @Test
    public void testSearchByRecipient() {
        ArrayList<String> expResult = new ArrayList<String>(Arrays.asList(
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."));
        ArrayList<String> result = store.searchByRecipient("+27838884567");
        assertEquals(expResult, result);
    }

    // ---------- Delete a message using its hash ----------

    @Test
    public void testDeleteMessageByHash() {
        String expResult = "Message: \"Where are you? You are late! "
                + "I have asked you to be on time\" successfully deleted.";
        String result = store.deleteMessageByHash(message2.getMessageHash());
        assertEquals(expResult, result);

        assertEquals(1, store.getStoredMessages().size());
        assertEquals(4, store.getMessageHashes().size());
        assertEquals(4, store.getMessageIDs().size());
        assertFalse(store.getMessageIDs().contains("0187654321"));
        assertNull(store.findMessageByID("0187654321"));
    }

    @Test
    public void testDeleteMessageByHash_NotFound() {
        String expResult = "Message hash not found.";
        String result = store.deleteMessageByHash("99:9:NOTHING");
        assertEquals(expResult, result);
        assertEquals(5, store.getMessageHashes().size());
    }

    // ---------- Display sender and recipient ----------

    @Test
    public void testDisplaySenderAndRecipient() {
        String expResult = "Sender: Kyle Peters | Recipient: +27838884567\n"
                + "Sender: Kyle Peters | Recipient: +27838884567\n";
        String result = store.displaySenderAndRecipient();
        assertEquals(expResult, result);
    }

    // ---------- Reports ----------

    @Test
    public void testSentMessagesReport() {
        String report = store.sentMessagesReport();

        assertTrue(report.contains("Message Hash: "
                + message1.getMessageHash()));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));

        assertTrue(report.contains("Message Hash: "
                + message4.getMessageHash()));
        assertTrue(report.contains("Message: It is dinner time!"));

        assertFalse(report.contains("Where are you?"));
        assertFalse(report.contains("Yohoooo"));
    }

    @Test
    public void testStoredMessagesReport() {
        String report = store.storedMessagesReport();

        assertTrue(report.contains("Message ID: 0187654321"));
        assertTrue(report.contains("Message Hash: "
                + message2.getMessageHash()));
        assertTrue(report.contains("Sender: Kyle Peters"));
        assertTrue(report.contains("Recipient: +27838884567"));
        assertTrue(report.contains("Message: Ok, I am leaving without you."));

        assertFalse(report.contains("Did you get the cake?"));
    }

    // ---------- Reading the stored messages back from the JSON file ----------

    @Test
    public void testLoadStoredMessagesFromJson() {
        String fileName = "test_storedMessages.json";

        Message.storeMessage(store.getStoredMessages(), fileName);

        MessageStore loadedStore = new MessageStore();
        int count = loadedStore.loadStoredMessages(fileName);

        assertEquals(2, count);
        assertEquals(2, loadedStore.getStoredMessages().size());

        Message loaded = loadedStore.getStoredMessages().get(0);
        assertEquals("0187654321", loaded.getMessageID());
        assertEquals(message2.getMessageHash(), loaded.getMessageHash());
        assertEquals("Kyle Peters", loaded.getSender());
        assertEquals("+27838884567", loaded.getRecipient());
        assertEquals(message2.getMessageText(), loaded.getMessageText());

        assertEquals(2, loadedStore.getMessageIDs().size());
        assertEquals(2, loadedStore.getMessageHashes().size());

        new File(fileName).delete();
    }

    @Test
    public void testLoadStoredMessages_NoFile() {
        MessageStore emptyStore = new MessageStore();
        int count = emptyStore.loadStoredMessages("this_file_does_not_exist.json");

        assertEquals(0, count);
        assertEquals(0, emptyStore.getStoredMessages().size());
    }
    
}
