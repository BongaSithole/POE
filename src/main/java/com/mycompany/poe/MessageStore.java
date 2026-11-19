/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Student
 */
public class MessageStore {
    
    private ArrayList<Message> sentMessages = new ArrayList<Message>();
    private ArrayList<Message> disregardedMessages = new ArrayList<Message>();
    private ArrayList<Message> storedMessages = new ArrayList<Message>();
    private ArrayList<String> messageHashes = new ArrayList<String>();
    private ArrayList<String> messageIDs = new ArrayList<String>();
    private ArrayList<Message> allMessages = new ArrayList<Message>();

    // ---------- Getters for the arrays ----------
    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<Message> getDisregardedMessages() {
        return disregardedMessages;
    }

    public ArrayList<Message> getStoredMessages() {
        return storedMessages;
    }

    public ArrayList<String> getMessageHashes() {
        return messageHashes;
    }

    public ArrayList<String> getMessageIDs() {
        return messageIDs;
    }

    public ArrayList<String> getSentMessageTexts() {
        ArrayList<String> texts = new ArrayList<String>();
        for (Message m : sentMessages) {
            texts.add(m.getMessageText());
        }
        return texts;
    }

    // ---------- Populating the arrays ----------

    public void addSentMessage(Message message) {
        sentMessages.add(message);
        recordMessage(message);
    }

    public void addDisregardedMessage(Message message) {
        disregardedMessages.add(message);
        recordMessage(message);
    }

    public void addStoredMessage(Message message) {
        storedMessages.add(message);
        recordMessage(message);
    }

    private void recordMessage(Message message) {
        allMessages.add(message);
        messageIDs.add(message.getMessageID());
        messageHashes.add(message.getMessageHash());
    }

    // ---------- Reading the JSON file into the stored messages array ----------

    public int loadStoredMessages(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return 0;
        }

        int count = 0;
        try {
            FileReader reader = new FileReader(file);
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            reader.close();

            for (int i = 0; i < array.size(); i++) {
                JsonObject item = array.get(i).getAsJsonObject();

                String id = item.get("messageID").getAsString();
                int number = item.get("messageNumber").getAsInt();
                String recipient = item.get("recipient").getAsString();
                String text = item.get("message").getAsString();

                Message message = new Message(id, number, recipient, text);

                if (item.has("sender")) {
                    message.setSender(item.get("sender").getAsString());
                }

                addStoredMessage(message);
                count++;
            }
        } catch (Exception e) {
            System.out.println("Could not read the stored messages file: "
                    + e.getMessage());
        }
        return count;
    }

    // ---------- Stored Messages menu features ----------

    public String displaySenderAndRecipient() {
        if (storedMessages.size() == 0) {
            return "There are no stored messages.\n";
        }
        String result = "";
        for (Message m : storedMessages) {
            result = result + "Sender: " + m.getSender()
                    + " | Recipient: " + m.getRecipient() + "\n";
        }
        return result;
    }

    public String longestMessage() {
        if (storedMessages.size() == 0) {
            return "There are no stored messages.";
        }
        String longest = storedMessages.get(0).getMessageText();
        for (Message m : storedMessages) {
            if (m.getMessageText().length() > longest.length()) {
                longest = m.getMessageText();
            }
        }
        return longest;
    }

    public Message findMessageByID(String messageID) {
        int position = messageIDs.indexOf(messageID);
        if (position < 0) {
            return null;
        }
        return allMessages.get(position);
    }

    public ArrayList<String> searchByRecipient(String recipient) {
        ArrayList<String> results = new ArrayList<String>();

        for (Message m : sentMessages) {
            if (m.getRecipient().equals(recipient)) {
                results.add(m.getMessageText());
            }
        }
        for (Message m : storedMessages) {
            if (m.getRecipient().equals(recipient)) {
                results.add(m.getMessageText());
            }
        }
        return results;
    }

    public String deleteMessageByHash(String messageHash) {
        int position = messageHashes.indexOf(messageHash);
        if (position < 0) {
            return "Message hash not found.";
        }

        Message message = allMessages.get(position);

        allMessages.remove(position);
        messageIDs.remove(position);
        messageHashes.remove(position);

        sentMessages.remove(message);
        storedMessages.remove(message);
        disregardedMessages.remove(message);

        String text = message.getMessageText();
        if (text.endsWith(".")) {
            text = text.substring(0, text.length() - 1);
        }
        return "Message: \"" + text + "\" successfully deleted.";
    }

    public String storedMessagesReport() {
        return buildReport(storedMessages, "STORED MESSAGES REPORT");
    }

    public String sentMessagesReport() {
        return buildReport(sentMessages, "SENT MESSAGES REPORT");
    }

    private String buildReport(ArrayList<Message> messages, String title) {
        String report = "===== " + title + " =====\n";

        if (messages.size() == 0) {
            return report + "There are no messages to show.\n";
        }

        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            report = report + "\nMessage " + (i + 1) + "\n";
            report = report + "Message ID: " + m.getMessageID() + "\n";
            report = report + "Message Hash: " + m.getMessageHash() + "\n";
            report = report + "Sender: " + m.getSender() + "\n";
            report = report + "Recipient: " + m.getRecipient() + "\n";
            report = report + "Message: " + m.getMessageText() + "\n";
        }
        return report;
    }
}
