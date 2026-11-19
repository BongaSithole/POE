/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
public class Login {
    
     private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    public Login() {
        username = "";
        password = "";
        cellPhoneNumber = "";
        firstName = "";
        lastName = "";
    }

    public Login(String username, String password, String cellPhoneNumber,
            String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // ---------- Getters and setters ----------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // ---------- Validation methods ----------

    public boolean checkUserName(String username) {
        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        String specialCharacters = "!@#$%^&*()_+-=[]{};':\"\\|,.<>/?";

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (specialCharacters.indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber.matches("\\+27[0-9]{1,10}");
    }

    // ---------- Methods that return messages ----------

    public String captureUsernameMessage(String username) {
        if (checkUserName(username)) {
            return "Username successfully captured.";
        } else {
            return "Username is not correctly formatted; please ensure that "
                    + "your username contains an underscore and is no more "
                    + "than five characters in length.";
        }
    }

    public String capturePasswordMessage(String password) {
        if (checkPasswordComplexity(password)) {
            return "Password successfully captured.";
        } else {
            return "Password is not correctly formatted; please ensure that "
                    + "the password contains at least eight characters, a "
                    + "capital letter, a number, and a special character.";
        }
    }

    public String captureCellPhoneMessage(String cellPhoneNumber) {
        if (checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell number successfully captured.";
        } else {
            return "Cell number is incorrectly formatted or does not contain "
                    + "an international code; please correct the number and "
                    + "try again.";
        }
    }

    public String registerUser() {
        if (!checkUserName(username)) {
            return captureUsernameMessage(username);
        }
        if (!checkPasswordComplexity(password)) {
            return capturePasswordMessage(password);
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return captureCellPhoneMessage(cellPhoneNumber);
        }
        return "Username successfully captured. Password successfully "
                + "captured. Cell number successfully captured. "
                + "The two above conditions have been met, and the user "
                + "has been registered successfully.";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (username.equals(enteredUsername)
                && password.equals(enteredPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }       
}
