package com.Job.JobService.EntityDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeScoreRequest {
    private String resumeText;
    private String jobDesc;
    private Long partyId;
    private String resumeScore;
}
