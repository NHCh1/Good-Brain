package Student;


import java.time.LocalDate;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nightinggale
 */
public class StdProjectInfo {
    private String projName;
    private String superId;
    private String secondId;
    private LocalDate dueDate;
    public StdProjectInfo(String projName, String superId, String secondId, LocalDate dueDate) {
    this.projName = projName;
    this.superId = superId;
    this.secondId = secondId;
    this.dueDate = dueDate;
    }
    // Getters for variables
    public String getProjectName() {
        return projName;
    }
    public String getSupervisorId() {
        return superId;
    }
    public String getSecondMarkerId() {
        return secondId;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
}
