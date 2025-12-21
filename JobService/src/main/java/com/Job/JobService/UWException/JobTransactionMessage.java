package com.Job.JobService.UWException;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobTransactionMessage {
    private String strMessageType;
    private String strMessageId;
    private String strMessageSource;
    private String strProbableCause;
    private String strMessageSeverity;
}
