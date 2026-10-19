/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class MessageTest {
    
   
    private Message message1;
    private Message message2;
 
    public void testMessageLength_Success() {
        String expResult = "Message ready to send.";
        String result = message1.checkMessageLength();
        assertEquals(expResult, result);
    }
 
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
 
    public void testRecipientCell_Success() {
        String expResult = "Cell phone number successfully captured.";
        String result = message1.checkRecipientCell();
        assertEquals(expResult, result);
    }
 
    public void testRecipientCell_Failure() {
        String expResult = "Cell phone number is incorrectly formatted or "
                + "does not contain an international code. Please correct "
                + "the number and try again.";
        String result = message2.checkRecipientCell();
        assertEquals(expResult, result);
    }
 
    public void testMessageHash_TestCase1() {
        String expResult = "00:0:HITONIGHT";
        String result = message1.createMessageHash();
        assertEquals(expResult, result);
    }
 
    public void testMessageHash_AllMessagesInLoop() {
        Message[] messages = {message1, message2};
        String[] expectedHashes = {"00:0:HITONIGHT", "01:1:HIPAYMENT"};
 
        for (int i = 0; i < messages.length; i++) {
            String result = messages[i].createMessageHash();
            assertEquals(expectedHashes[i], result);
        }
    }
 
    public void testMessageID_Created() {
        assertTrue(message1.checkMessageID());
        assertEquals("Message ID generated: 0012345678",
                message1.messageIDCreated());
    }
    
    public void testGenerateMessageID_IsTenDigits() {
        String id = Message.generateMessageID();
        assertEquals(10, id.length());
 
        for (int i = 0; i < id.length(); i++) {
            assertTrue(Character.isDigit(id.charAt(i)));
        }
    }

    public void testSentMessage_Send() {
        String expResult = "Message successfully sent.";
        String result = message1.sentMessage(1);
        assertEquals(expResult, result);
    }
 
    public void testSentMessage_Disregard() {
        String expResult = "Press 0 to delete the message.";
        String result = message2.sentMessage(2);
        assertEquals(expResult, result);
    }
 
    public void testSentMessage_Store() {
        String expResult = "Message successfully stored.";
        String result = message1.sentMessage(3);
        assertEquals(expResult, result);
    }
 
    public void testReturnTotalMessages() {
        ArrayList<Message> sentMessages = new ArrayList<Message>();
 
        message1.sentMessage(1);
        sentMessages.add(message1);
 
        message2.sentMessage(2);
 
        assertEquals(1, Message.returnTotalMessages(sentMessages));
    }
 
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

    private void assertTrue(boolean contains) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertEquals(int i, int returnTotalMessages) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertEquals(String expResult, String result) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
