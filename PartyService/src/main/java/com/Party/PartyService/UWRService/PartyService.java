package com.Party.PartyService.UWRService;

import com.Party.PartyService.PartyException.PrtException;
import com.Party.PartyService.UWRParty.EmployeeEntityDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


public interface PartyService {
    public EmployeeEntityDetails saveEmplDtls(EmployeeEntityDetails employeeEntityDetails) throws PrtException;
    public EmployeeEntityDetails updateEmplDtls(EmployeeEntityDetails employeeEntityDetails) throws PrtException;
    public EmployeeEntityDetails getEmplDtls(Long id, String user) throws PrtException;
    public EmployeeEntityDetails getPartyMatchingSkills(String skills) throws PrtException;
    public Map<String, Object> register(Map<String, String> body);
    public Map<String, Object> login(Map<String, String> body);
    public String UploadResume(Long partyId, MultipartFile file);
}
