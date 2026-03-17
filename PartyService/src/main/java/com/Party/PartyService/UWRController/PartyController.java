package com.Party.PartyService.UWRController;

import com.Party.PartyService.PartyException.PrtException;
import com.Party.PartyService.UWRParty.EmployeeEntityDetails;
import com.Party.PartyService.UWRService.PartyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/party")

public class PartyController {

        @Autowired
        private PartyService partyService;
        @PostMapping("/saveEmplDtls")
        public EmployeeEntityDetails saveEmplDtls(@RequestBody EmployeeEntityDetails employeeEntityDetails){
           try {
               employeeEntityDetails = partyService.saveEmplDtls(employeeEntityDetails);
           }

           catch (Exception e) {
               System.out.println("Inside catch block of method save EmplDtls");
//               PrtExceptionMessage msg = new PrtExceptionMessage();
//               msg.setProbableCause("Wrong value of Mobile Number");
//               employeeEntityDetails.setPrtExceptionMessage(msg);
//               employeeEntityDetails.setPrtExceptionMessage(msg);
               e.printStackTrace();
           }
            return employeeEntityDetails;
        }

    @PostMapping("/updateEmplDtls")
    public EmployeeEntityDetails updateEmplDtls(@RequestBody EmployeeEntityDetails employeeEntityDetails){
        try {
            employeeEntityDetails = partyService.updateEmplDtls(employeeEntityDetails);
        }

        catch (Exception e) {
            System.out.println("Inside catch block of method save EmplDtls");
            e.printStackTrace();
        }
        return employeeEntityDetails;
    }

        @GetMapping(value = "/getPartyByPartyId")
        public EmployeeEntityDetails getPartyByPartyId(@RequestParam Long id, @RequestParam String user) throws PrtException {
            EmployeeEntityDetails details = new EmployeeEntityDetails();
            details = partyService.getEmplDtls(id, user);

            return details;
        }
        @GetMapping(value ="/getPartyMatchingskills")
        public EmployeeEntityDetails getPartyMatchingSkills(@RequestParam String skills) throws PrtException {
            return partyService.getPartyMatchingSkills(skills);
        }

        @PostMapping(value = "/register")
        public Map<String, Object> register(@RequestBody Map<String, String> body){
            return partyService.register(body);
        }

        @PostMapping(value = "login")
        public Map<String, Object> login(@RequestBody Map<String, String> body){
            return partyService.login(body);
        }
    @Operation(summary = "Upload resume")
    @PostMapping(value = "/UploadResume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String UploadResume(@RequestParam Long partyId, @RequestParam MultipartFile file){

       // log.info("Entered in uploadResume method of UWRController with partId - " + partyId);
        String msg = "";

        try{
            msg = partyService.UploadResume(partyId, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //log.info("Exiting method Upload Resume");
        return msg;
    }


}
