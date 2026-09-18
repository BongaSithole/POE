/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

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
    }
 
    public Login(String username, String password, String cellPhoneNumber,
            String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }
 
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

    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }
 
    public boolean checkPasswordComplexity(String password) {
        if (password == null) {
            return false;
        }
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
        return Pattern.matches(regex, password);
    }
 
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        if (cellPhoneNumber == null) {
            return false;
        }
        String regex = "^\\+27[0-9]{1,10}$";
        return Pattern.matches(regex, cellPhoneNumber);
    }
 
    public String captureUsernameMessage(String username) {
        if (checkUserName(username)) {
            return "Username successfully captured.";
        }
        return "Username is not correctly formatted; please ensure that "
                + "your username contains an underscore and is no more "
                + "than five characters in length.";
    }
 
    /**
     * Returns the capture message for the password field.
     */
    public String capturePasswordMessage(String password) {
        if (checkPasswordComplexity(password)) {
            return "Password successfully captured.";
        }
        return "Password is not correctly formatted; please ensure that "
                + "the password contains at least eight characters, a "
                + "capital letter, a number, and a special character.";
    }
 
    public String captureCellPhoneMessage(String cellPhoneNumber) {
        if (checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell number successfully captured.";
        }
        return "Cell number is incorrectly formatted or does not contain "
                + "an international code; please correct the number and "
                + "try again.";
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
        return this.username != null && this.password != null
                && this.username.equals(enteredUsername)
                && this.password.equals(enteredPassword);
    }
 
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}
