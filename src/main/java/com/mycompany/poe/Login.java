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
    
    public String name;
    public String cell_num;
    public String username;
    public String password;

    public Login(String name, String cell_num, String Username, String password){

        this.name = name;
        this.cell_num = cell_num;
        this.username = Username;
        this.password = password;
    }

    public boolean checkUsername(){
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkCellnum(){
        return cell_num.startsWith("+27/0") && cell_num.length() == 10;
    }
    
    public boolean checkPasswordComplexity(){
        return checkPasswordComplexity.contains("Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
    }
}
