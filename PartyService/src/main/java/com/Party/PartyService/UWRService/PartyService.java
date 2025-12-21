package com.Party.PartyService.UWRService;

import com.Party.PartyService.UWRParty.EmployeeEntityDetails;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


public interface PartyService {
    public EmployeeEntityDetails saveEmplDtls(EmployeeEntityDetails employeeEntityDetails);
    public void updateEmplDtls(EmployeeEntityDetails employeeEntityDetails);
    public EmployeeEntityDetails getEmplDtls(Long id, String user);
    public String getPartyMatchingSkills(String skills);
    public Map<String, Object> register(Map<String, String> body);
    public Map<String, Object> login(Map<String, String> body);
}
