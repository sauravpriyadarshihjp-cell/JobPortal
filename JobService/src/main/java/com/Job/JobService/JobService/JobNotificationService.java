package com.Job.JobService.JobService;

import com.Job.JobService.EntityDetails.JobEntityDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class JobNotificationService {
    @Autowired
    private PartyClient partyClient;
    @Autowired
    private SpringAiClient springAiClient;

    @EventListener
    public void onJobPosted(JobPostedEvent event){
        JobEntityDetails jobEntityDetails = event.getjob();
        String parties = partyClient.getPartyBySkills(jobEntityDetails.getSkills());
        springAiClient.SendNotification(parties);
    }
}
