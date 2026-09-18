/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class POE {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
         Login login = new Login();
 
        System.out.println("=== User Registration ===");
 
        System.out.print("Enter first name: ");
        login.setFirstName(scanner.nextLine());
 
        System.out.print("Enter last name: ");
        login.setLastName(scanner.nextLine());
 
        System.out.print("Enter a username (must contain an underscore "
                + "and be no more than 5 characters): ");
        String username = scanner.nextLine();
        login.setUsername(username);
 
        System.out.print("Enter a password (min 8 characters, 1 capital "
                + "letter, 1 number, 1 special character): ");
        String password = scanner.nextLine();
        login.setPassword(password);
 
        System.out.print("Enter a South African cell phone number "
                + "(e.g. +27838968976): ");
        String cellPhoneNumber = scanner.nextLine();
        login.setCellPhoneNumber(cellPhoneNumber);
 
        System.out.println();
        System.out.println(login.registerUser());
 
        boolean registeredOk = login.checkUserName(username)
                && login.checkPasswordComplexity(password)
                && login.checkCellPhoneNumber(cellPhoneNumber);
 
        if (registeredOk) {
            System.out.println();
            System.out.println("=== User Login ===");
            System.out.print("Enter your username: ");
            String loginUsername = scanner.nextLine();
            System.out.print("Enter your password: ");
            String loginPassword = scanner.nextLine();
 
            boolean loginSuccess = login.loginUser(loginUsername, loginPassword);
            System.out.println(login.returnLoginStatus(loginSuccess));
        }
 
        scanner.close();
    }
}

