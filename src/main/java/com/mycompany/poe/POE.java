/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class POE {

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
 
        runQuickChat(scanner);
    }
 
    public static void runQuickChat(Scanner scanner) {
        ArrayList<Message> sentMessages = new ArrayList<Message>();
        ArrayList<Message> storedMessages = new ArrayList<Message>();
 
        System.out.println();
        System.out.println("Welcome to QuickChat.");
 
        int maxMessages = readNumber(scanner,
                "How many messages do you wish to enter? ");
        int messagesEntered = 0;
        boolean running = true;
 
        while (running) {
            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            int option = readNumber(scanner, "Choose an option: ");
 
            if (option == 1) {
                if (messagesEntered >= maxMessages) {
                    System.out.println("You have already entered the "
                            + maxMessages + " message(s) you set.");
                } else {
                    processNewMessage(scanner, messagesEntered,
                            sentMessages, storedMessages);
                    messagesEntered++;
 
                    if (messagesEntered == maxMessages) {
                        System.out.println();
                        System.out.println("All " + maxMessages
                                + " message(s) have been entered.");
                        System.out.println("Total number of messages sent: "
                                + Message.returnTotalMessages(sentMessages));
                    }
                }
            } else if (option == 2) {
                System.out.println("Coming Soon.");
            } else if (option == 3) {
                System.out.println("Total number of messages sent: "
                        + Message.returnTotalMessages(sentMessages));
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option; please choose 1, 2 or 3.");
            }
        }
    }
 
    public static void processNewMessage(Scanner scanner, int messageNumber,
            ArrayList<Message> sentMessages,
            ArrayList<Message> storedMessages) {
 
        Message message = new Message();
        message.setMessageNumber(messageNumber);
        message.setMessageID(Message.generateMessageID());
 
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
                sentMessages.add(message);
                System.out.println();
                System.out.println(message.getDetails());
                finished = true;
            } else if (choice == 2) {
                System.out.print("> ");
                String answer = scanner.nextLine().trim();
                if (answer.equals("0")) {
                    System.out.println("Message deleted.");
                    finished = true;
                }
            } else if (choice == 3) {
                storedMessages.add(message);
                Message.storeMessage(storedMessages, "storedMessages.json");
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

