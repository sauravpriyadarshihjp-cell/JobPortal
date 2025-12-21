package com.Spring.AIServive.SpringAiService.SpringService;

public interface SpringService {
    public String scoreResume(ResumeScoreRequest resumeScoreRequest);
    public String askQuery(String text);
    public String findApplyJobs(String prompt);
    public void SendNotification(String text);
    //public String askQueryOnResume(String resumeText, String ques);
}
