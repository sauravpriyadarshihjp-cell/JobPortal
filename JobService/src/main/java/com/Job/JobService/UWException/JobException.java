package com.Job.JobService.UWException;

public class JobException extends Exception{
    private JobTransactionMessage jobExceptionMessage;
    public JobException(JobTransactionMessage jobExceptionMessage){
        this.jobExceptionMessage = jobExceptionMessage;
    }
}
