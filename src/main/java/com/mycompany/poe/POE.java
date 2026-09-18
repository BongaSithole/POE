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

        System.out.print("Please enter your name: ");
        String name = input.nextLine();

        System.out.print("Please enter your Cellphone number: ");
        String cell_num = input.nextLine();

        System.out.print("Please enter your username: ");
        String username = input.nextLine();

        System.out.print("Please enter your password: ");
        String password = input.nextLine();

        Login login = new Login(name, cell_num, username, password);

        if (login.checkUsername()) {
            System.out.println("Username successfully captured");
        } else {
            System.out.println("Username is not successfully captured, please ensure that your username contains an underscore and is no more than five characters in length.");
        }

        if (login.checkCellnum()) {
            System.out.println("Cellphone number successfully captured");
        } else {
            System.out.println("Cellphone number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
        }

        if (login.checkPasswordComplexity()) {
            System.out.println("Password successfully captured");
        } else {
            System.out.println("Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
        }
    }
}

