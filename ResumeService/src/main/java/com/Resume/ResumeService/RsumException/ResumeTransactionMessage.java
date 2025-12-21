package com.Resume.ResumeService.RsumException;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeTransactionMessage {
    private String strMessageId;
    private String strMessageSource;
    private String strProbableCause;
    private String strMessageType;
    private String strMessageSeverity;
}
