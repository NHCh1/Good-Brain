package Student;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nightinggale
 */
public class StdSubmissionInfo {
    private final String subID;
    private final String subStatus;
    private final String subGrade;
    private final String subName;
    private final String subPath;
    private final String subSuper;
    private final String subSecond;
    public StdSubmissionInfo(String subID, String subStatus, String subGrade, String subName, String subPath, String subSuper, String subSecond) {
        this.subID = subID;
        this.subStatus = subStatus;
        this.subGrade = subGrade;
        this.subName = subName;
        this.subPath = subPath;
        this.subSuper = subSuper;
        this.subSecond = subSecond;
    }
    public String getSubmissionID() {
        return subID;
    }
    public String getSubmissionStatus() {
        return subStatus;
    }
    public String getSubmissionGrade() {
        return subGrade;
    }
    public String getSubmissionName() {
        return subName;
    }
    public String getSubmissionPath() {
        return subPath;
    }
    public String getSupervisorFeedback() {
        return subSuper;
    }
    public String getSecondMarkerFeedback() {
        return subSecond;
    }
}
