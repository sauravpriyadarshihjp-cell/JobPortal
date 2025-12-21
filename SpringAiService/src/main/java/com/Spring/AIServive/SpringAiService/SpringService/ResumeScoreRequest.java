package com.Spring.AIServive.SpringAiService.SpringService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResumeScoreRequest {
    private static final Logger log = LoggerFactory.getLogger(ResumeScoreRequest.class);
    private String resumeText;
    private String jobDesc;
    private String partyId;
    private String ResumeScore;


//    public ResumeScoreRequest(String resumeText, String jobDesc) {
//        this.resumeText = resumeText;
//        this.jobDesc = jobDesc;
//    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getResumeScore() {
        return ResumeScore;
    }

    public void setResumeScore(String resumeScore) {
        ResumeScore = resumeScore;
    }

    public String getResumeText() {
        return resumeText;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }
}
