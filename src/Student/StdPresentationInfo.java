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
public class StdPresentationInfo {
    private final String preID;
    private final LocalDate preDate;
    private final String preTime;
    private final String preStu;
    private final String preSuper;
    private final String preSecond;
    public StdPresentationInfo(String preID, LocalDate preDate, String preTime, String preStu, String preSuper, String preSecond) {
        this.preID = preID;
        this.preDate = preDate;
        this.preTime = preTime;
        this.preStu = preStu;
        this.preSuper = preSuper;
        this.preSecond = preSecond;
    }
    // Getters for variables
    public String getPresentationID() {
        return preID;
    }
    public LocalDate getPresentationDate() {
        return preDate;
    }
    public String getPresentationTime() {
        return preTime;
    }
    public String getStudentAcceptance() {
        return preStu;
    }
    public String getSupervisorAcceptance() {
        return preSuper;
    }
    public String getSecondMarkerAcceptance() {
        return preSecond;
    }
}
