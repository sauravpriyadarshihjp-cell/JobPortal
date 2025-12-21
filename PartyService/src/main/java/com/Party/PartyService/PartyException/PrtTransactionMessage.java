package com.Party.PartyService.PartyException;

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
public class PrtTransactionMessage {
    private String strMessageType;
    private String strMessageId;
    private String strMessageSource;
    private String ProbableCause;
    private String strMessageSeverity;
}
