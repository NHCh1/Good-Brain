package Student;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nightinggale
 */
public class StdFeedbackInfo {
    private final String feedID;
    private final String stuID;
    private final String projID;
    private final String content;
    public StdFeedbackInfo(String feedID, String stuID, String projID, String content) {
        this.feedID = feedID;
        this.stuID = stuID;
        this.projID = projID;
        this.content = content;
    }
    // Getters for variables
    public String getFeedbackID() {
        return feedID;
    }
    public String getStudentID() {
        return stuID;
    }
    public String getProjectID() {
        return projID;
    }
    public String getFeedbackContent() {
        return content;
    }
}
