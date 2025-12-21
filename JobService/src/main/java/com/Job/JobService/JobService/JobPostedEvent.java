package com.Job.JobService.JobService;

import com.Job.JobService.EntityDetails.JobEntityDetails;
import org.springframework.context.ApplicationEvent;

public class JobPostedEvent extends ApplicationEvent {
    private final JobEntityDetails jobEntityDetails;

    public JobPostedEvent(Object source, JobEntityDetails jobEntityDetails){
        super(source);
        this.jobEntityDetails = jobEntityDetails;
    }

    public JobEntityDetails getjob(){
        return jobEntityDetails;
    }
}
