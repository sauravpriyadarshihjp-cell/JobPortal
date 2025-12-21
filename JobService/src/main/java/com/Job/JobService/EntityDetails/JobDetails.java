package com.Job.JobService.EntityDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDetails {
    private Long jobId;
    private String jobTitle;
    private String jobCompany;
    private String location;
    private String salary;
}
