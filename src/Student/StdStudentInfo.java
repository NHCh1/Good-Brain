package Student;


import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nightinggale
 */
public class StdStudentInfo {
    private final String studentID;
    private final String intake;
    private final ArrayList<String> projectIDlist;
    private final String selectedProjectId;
    public StdStudentInfo(String studentID, String intake, ArrayList<String> projectIDlist, String selectedProjectId) {
        this.studentID = studentID;
        this.intake = intake;
        this.projectIDlist = (ArrayList<String>) projectIDlist;
        this.selectedProjectId = selectedProjectId;
    }
    // Getters for variables
    public String getStudentID() {
        return studentID;
    }
    public String getIntake() {
        return intake;
    }
    public ArrayList<String> getProjectIDlist() {
        return projectIDlist;
    }
    public String getSelectedProjectId() {
        return selectedProjectId;
    }
}
