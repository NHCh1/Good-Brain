/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainProgram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class User {
    
    private String userID;
    private String password;
    private int role;
    
    public User(){
        
    }
    
    public User(String id, String password, int role){
        this.userID = id;
        this.password = password;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }
    
    public void setRole(int role) {
        this.role = role;
    }
    
    public int checkCredentials(String id, String password) {
        try {
            Scanner scanner = new Scanner(new File("user.txt"));
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] credentials = data.split(",");
                int role = Integer.parseInt(credentials[2]);

                if (id.equals(credentials[0]) && password.equals(credentials[1])) {
                    this.role = role;
                    return role;
                }
            }
//            JOptionPane.showMessageDialog(null, "Invalid user ID or password.", "Error",JOptionPane.ERROR_MESSAGE);
            scanner.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(loginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}
