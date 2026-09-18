/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    public void testCheckUserName_CorrectlyFormatted() {
        Login login = new Login();
        boolean result = login.checkUserName("kyl_1");
        assertTrue(result);
    }
 
    public void testCheckUserName_IncorrectlyFormatted() {
        Login login = new Login();
        boolean result = login.checkUserName("kyle!!!!!");
        assertFalse(result);
    }
    
    public void testCheckPasswordComplexity_Meets() {
        Login login = new Login();
        boolean result = login.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(result);
    }

    public void testCheckPasswordComplexity_DoesNotMeet() {
        Login login = new Login();
        boolean result = login.checkPasswordComplexity("password");
        assertFalse(result);
    }
 
    public void testCheckCellPhoneNumber_CorrectlyFormatted() {
        Login login = new Login();
        boolean result = login.checkCellPhoneNumber("+27838968976");
        assertTrue(result);
    }
 
    public void testCheckCellPhoneNumber_IncorrectlyFormatted() {
        Login login = new Login();
        boolean result = login.checkCellPhoneNumber("08966553");
        assertFalse(result);
    }

    public void testCaptureUsernameMessage_IncorrectlyFormatted() {
        Login login = new Login();
        String expResult = "Username is not correctly formatted; please "
                + "ensure that your username contains an underscore and "
                + "is no more than five characters in length.";
        String result = login.captureUsernameMessage("kyle!!!!!");
        assertEquals(expResult, result);
    }
 
    public void testCapturePasswordMessage_Meets() {
        Login login = new Login();
        String expResult = "Password successfully captured.";
        String result = login.capturePasswordMessage("Ch&&sec@ke99!");
        assertEquals(expResult, result);
    }
 
    public void testCapturePasswordMessage_DoesNotMeet() {
        Login login = new Login();
        String expResult = "Password is not correctly formatted; please "
                + "ensure that the password contains at least eight "
                + "characters, a capital letter, a number, and a special "
                + "character.";
        String result = login.capturePasswordMessage("password");
        assertEquals(expResult, result);
    }
 
    public void testCaptureCellPhoneMessage_CorrectlyFormatted() {
        Login login = new Login();
        String expResult = "Cell number successfully captured.";
        String result = login.captureCellPhoneMessage("+27838968976");
        assertEquals(expResult, result);
    }
 
    public void testCaptureCellPhoneMessage_IncorrectlyFormatted() {
        Login login = new Login();
        String expResult = "Cell number is incorrectly formatted or does "
                + "not contain an international code; please correct the "
                + "number and try again.";
        String result = login.captureCellPhoneMessage("08966553");
        assertEquals(expResult, result);
    }

    public void testLoginUser_Successful() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976",
                "Kyle", "Peters");
        boolean result = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(result);
    }

    public void testLoginUser_Failed() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976",
                "Kyle", "Peters");
        boolean result = login.loginUser("kyl_1", "WrongPassword1!");
        assertFalse(result);
    }
 
    public void testReturnLoginStatus_Successful() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976",
                "Kyle", "Peters");
        String expResult = "Welcome Kyle, Peters it is great to see you again.";
        String result = login.returnLoginStatus(true);
        assertEquals(expResult, result);
    }
 
    public void testReturnLoginStatus_Failed() {
        Login login = new Login();
        String expResult = "Username or password incorrect, please try again.";
        String result = login.returnLoginStatus(false);
        assertEquals(expResult, result);
    }
}
