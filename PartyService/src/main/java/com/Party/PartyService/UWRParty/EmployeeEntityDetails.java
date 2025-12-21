package com.Party.PartyService.UWRParty;

import com.Party.PartyService.PartyException.PrtTransactionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntityDetails {
    private List<EntityParameter> lstparameter;
    private String flag;
    private Long partyId;
    private Long partyCode;
    private String userCode;
    private String partyType;
    private PrtTransactionMessage prtTransactionMessageMessage;
}
