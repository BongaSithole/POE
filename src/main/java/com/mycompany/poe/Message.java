/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author Student
 */
public class Message {
    
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    
    public Message() {
        messageID = "";
        messageNumber = 0;
        recipient = "";
        messageText = "";
        messageHash = "";
    }
     
    public Message(String messageID, int messageNumber, String recipient,
            String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        createMessageHash();
    }
     
     public String getMessageID() {
        return messageID;
    }
 
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
 
    public int getMessageNumber() {
        return messageNumber;
    }
 
    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }
 
    public String getRecipient() {
        return recipient;
    }
 
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
 
    public String getMessageText() {
        return messageText;
    }
 
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
 
    public String getMessageHash() {
        return messageHash;
    }
    
    public static String generateMessageID() {
        Random random = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id = id + random.nextInt(10);
        }
        return id;
    }
     
    public boolean checkMessageID() {
        if (messageID.length() <= 10) {
            return true;
        } else {
            return false;
        }
    }
     
    public String messageIDCreated() {
        return "Message ID generated: " + messageID;
    }
    
    public boolean isMessageLengthValid() {
        if (messageText.length() <= 250) {
            return true;
        } else {
            return false;
        }
    }
 
    public String checkMessageLength() {
        if (isMessageLengthValid()) {
            return "Message ready to send.";
        } else {
            int tooMany = messageText.length() - 250;
            return "Message exceeds 250 characters by " + tooMany
                    + "; please reduce the size.";
        }
    }
    
    public boolean isRecipientValid() {
        Login checker = new Login();
        return checker.checkCellPhoneNumber(recipient);
    }
    
    public String checkRecipientCell() {
        if (isRecipientValid()) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not "
                    + "contain an international code. Please correct the "
                    + "number and try again.";
        }
    }
    
    public String createMessageHash() {
        String firstTwoDigits = messageID;
        if (messageID.length() >= 2) {
            firstTwoDigits = messageID.substring(0, 2);
        }
 
        String firstWord = "";
        String lastWord = "";
        String text = messageText.trim();
 
        if (!text.equals("")) {
            String[] words = text.split(" ");
            firstWord = removePunctuation(words[0]);
            lastWord = removePunctuation(words[words.length - 1]);
        }
 
        messageHash = (firstTwoDigits + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
        return messageHash;
    }
    
    private String removePunctuation(String word) {
        String cleaned = "";
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                cleaned = cleaned + c;
            }
        }
        return cleaned;
    }
    
    public String sentMessage(int choice) {
        if (choice == 1) {
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            return "Message successfully stored.";
        } else {
            return "Invalid option; please choose 1, 2 or 3.";
        }
    }
    
    public String getDetails() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText + "\n";
    }
    
    public static String printMessages(ArrayList<Message> sentMessages) {
        String all = "";
        for (int i = 0; i < sentMessages.size(); i++) {
            all = all + sentMessages.get(i).getDetails() + "\n";
        }
        return all;
    }
    
    public static int returnTotalMessages(ArrayList<Message> sentMessages) {
        return sentMessages.size();
    }
     
     public static void storeMessage(ArrayList<Message> storedMessages,
            String fileName) {
 
        String json = "[\n";
 
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
 
            json = json + "  {\n";
            json = json + "    \"messageID\": \""
                    + escapeJson(m.getMessageID()) + "\",\n";
            json = json + "    \"messageNumber\": "
                    + m.getMessageNumber() + ",\n";
            json = json + "    \"messageHash\": \""
                    + escapeJson(m.getMessageHash()) + "\",\n";
            json = json + "    \"recipient\": \""
                    + escapeJson(m.getRecipient()) + "\",\n";
            json = json + "    \"message\": \""
                    + escapeJson(m.getMessageText()) + "\"\n";
            json = json + "  }";
 
            if (i < storedMessages.size() - 1) {
                json = json + ",";
            }
            json = json + "\n";
        }
 
        json = json + "]";
 
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not store messages: " + e.getMessage());
        }
    }
 
    private static String escapeJson(String text) {
        text = text.replace("\\", "\\\\");
        text = text.replace("\"", "\\\"");
        return text;
    }
}
