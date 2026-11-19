/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Main {
    
    private static final String STORED_FILE = "storedMessages.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== User Registration ===");

        System.out.print("Enter first name: ");
        login.setFirstName(scanner.nextLine());

        System.out.print("Enter last name: ");
        login.setLastName(scanner.nextLine());

        System.out.print("Enter a username (must contain an underscore "
                + "and be no more than 5 characters): ");
        login.setUsername(scanner.nextLine());

        System.out.print("Enter a password (min 8 characters, 1 capital "
                + "letter, 1 number, 1 special character): ");
        login.setPassword(scanner.nextLine());

        System.out.print("Enter a South African cell phone number "
                + "(e.g. +27838968976): ");
        login.setCellPhoneNumber(scanner.nextLine());

        System.out.println();
        System.out.println(login.registerUser());

        boolean registeredOk = login.checkUserName(login.getUsername())
                && login.checkPasswordComplexity(login.getPassword())
                && login.checkCellPhoneNumber(login.getCellPhoneNumber());

        if (!registeredOk) {
            System.out.println("Registration unsuccessful. "
                    + "Please restart the program and try again.");
            return;
        }

        System.out.println();
        System.out.println("=== User Login ===");

        boolean loggedIn = false;
        int attempts = 0;

        while (!loggedIn && attempts < 3) {
            System.out.print("Enter your username: ");
            String enteredUsername = scanner.nextLine();
            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            loggedIn = login.loginUser(enteredUsername, enteredPassword);
            System.out.println(login.returnLoginStatus(loggedIn));
            attempts++;
        }

        if (!loggedIn) {
            System.out.println("Too many failed login attempts. "
                    + "You cannot send messages.");
            return;
        }

        String sender = login.getFirstName() + " " + login.getLastName();
        runQuickChat(scanner, sender);
    }

    public static void runQuickChat(Scanner scanner, String sender) {
        MessageStore store = new MessageStore();

        System.out.println();
        System.out.println("Welcome to QuickChat.");

        int loaded = store.loadStoredMessages(STORED_FILE);
        if (loaded > 0) {
            System.out.println(loaded + " stored message(s) loaded from "
                    + STORED_FILE + ".");
        }

        int maxMessages = readNumber(scanner,
                "How many messages do you wish to enter? ");
        boolean messagesCaptured = false;
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            int option = readNumber(scanner, "Choose an option: ");

            if (option == 1) {
                if (messagesCaptured) {
                    System.out.println("You have already entered the "
                            + maxMessages + " message(s) you set.");
                } else {
                    for (int i = 0; i < maxMessages; i++) {
                        System.out.println();
                        System.out.println("--- Message " + (i + 1) + " of "
                                + maxMessages + " ---");
                        processNewMessage(scanner, i, sender, store);
                    }
                    messagesCaptured = true;

                    System.out.println();
                    System.out.println("All " + maxMessages
                            + " message(s) have been entered.");
                    System.out.println("Total number of messages sent: "
                            + Message.returnTotalMessages(
                                    store.getSentMessages()));
                }
            } else if (option == 2) {
                System.out.println("Coming Soon.");
            } else if (option == 3) {
                System.out.println("Total number of messages sent: "
                        + Message.returnTotalMessages(store.getSentMessages()));
                System.out.println("Goodbye!");
                running = false;
            } else if (option == 4) {
                storedMessagesMenu(scanner, store);
            } else {
                System.out.println("Invalid option; please choose 1, 2, 3 "
                        + "or 4.");
            }
        }
    }

    public static void storedMessagesMenu(Scanner scanner,
            MessageStore store) {

        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("=== Stored Messages ===");
            System.out.println("a) Display the sender and recipient of all "
                    + "stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search for all messages for a recipient");
            System.out.println("e) Delete a message using its message hash");
            System.out.println("f) Report: full details of all stored "
                    + "messages");
            System.out.println("g) Report: all sent messages");
            System.out.println("h) Back to the main menu");
            System.out.print("Choose an option (a-h): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("a")) {
                System.out.println(store.displaySenderAndRecipient());

            } else if (choice.equals("b")) {
                System.out.println("Longest stored message:");
                System.out.println(store.longestMessage());

            } else if (choice.equals("c")) {
                System.out.print("Enter the message ID: ");
                String id = scanner.nextLine().trim();
                Message found = store.findMessageByID(id);
                if (found == null) {
                    System.out.println("No message found with that ID.");
                } else {
                    System.out.println("Recipient: " + found.getRecipient());
                    System.out.println("Message: " + found.getMessageText());
                }

            } else if (choice.equals("d")) {
                System.out.print("Enter the recipient's cell number: ");
                String recipient = scanner.nextLine().trim();
                ArrayList<String> results = store.searchByRecipient(recipient);
                if (results.size() == 0) {
                    System.out.println("No messages found for that recipient.");
                } else {
                    for (int i = 0; i < results.size(); i++) {
                        System.out.println((i + 1) + ") " + results.get(i));
                    }
                }

            } else if (choice.equals("e")) {
                System.out.print("Enter the message hash: ");
                String hash = scanner.nextLine().trim().toUpperCase();
                boolean exists = store.getMessageHashes().contains(hash);
                System.out.println(store.deleteMessageByHash(hash));
                if (exists) {
                    Message.storeMessage(store.getStoredMessages(),
                            STORED_FILE);
                }

            } else if (choice.equals("f")) {
                System.out.println(store.storedMessagesReport());

            } else if (choice.equals("g")) {
                System.out.println(store.sentMessagesReport());

            } else if (choice.equals("h")) {
                back = true;

            } else {
                System.out.println("Invalid option; please choose a to h.");
            }
        }
    }

    public static void processNewMessage(Scanner scanner, int messageNumber,
            String sender, MessageStore store) {

        Message message = new Message();
        message.setMessageNumber(messageNumber);
        message.setMessageID(Message.generateMessageID());
        message.setSender(sender);

        boolean recipientOk = false;
        while (!recipientOk) {
            System.out.print("Enter the recipient's cell number "
                    + "(e.g. +27718693002): ");
            message.setRecipient(scanner.nextLine().trim());
            System.out.println(message.checkRecipientCell());
            recipientOk = message.isRecipientValid();
        }

        boolean lengthOk = false;
        while (!lengthOk) {
            System.out.print("Enter your message (max 250 characters): ");
            message.setMessageText(scanner.nextLine());
            lengthOk = message.isMessageLengthValid();

            if (lengthOk) {
                System.out.println("Message sent");
            } else {
                System.out.println("Please enter a message of less than "
                        + "250 characters.");
            }
        }

        System.out.println(message.messageIDCreated());
        message.createMessageHash();

        boolean finished = false;
        while (!finished) {
            System.out.println();
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            int choice = readNumber(scanner, "Choose an option: ");

            System.out.println(message.sentMessage(choice));

            if (choice == 1) {
                store.addSentMessage(message);
                System.out.println();
                System.out.println(message.getDetails());
                finished = true;
            } else if (choice == 2) {
                System.out.print("> ");
                String answer = scanner.nextLine().trim();
                if (answer.equals("0")) {
                    store.addDisregardedMessage(message);
                    System.out.println("Message deleted.");
                    finished = true;
                }
            } else if (choice == 3) {
                store.addStoredMessage(message);
                Message.storeMessage(store.getStoredMessages(), STORED_FILE);
                finished = true;
            }
        }
    }

    public static int readNumber(Scanner scanner, String prompt) {
        int number = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                number = Integer.parseInt(input);
                if (number >= 1) {
                    valid = true;
                } else {
                    System.out.println("Please enter a number of 1 or more.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
        return number;
    }
}
