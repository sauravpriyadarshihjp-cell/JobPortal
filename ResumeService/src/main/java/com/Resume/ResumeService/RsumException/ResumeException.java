package com.Resume.ResumeService.RsumException;

public class ResumeException extends Exception{
    private ResumeTransactionMessage resumeTransactionMessage;
    public ResumeException(ResumeTransactionMessage resumeTransactionMessage){
        this.resumeTransactionMessage = resumeTransactionMessage;
    }
}
