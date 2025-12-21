package com.Job.JobService.EntityDetails;

import com.Job.JobService.UWException.JobTransactionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobEntityDetails {
    private Long jobId;
    private Long partyId;
    private Long applicationId;
    private String skills;
    private Long partyType;
    private Long proposalId;
    private List<EntityParameter> lstParameter;
    private String userCode;
    private String applicationStatus;
    private JobTransactionMessage objTransactionMesaage;



}
