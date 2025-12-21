package com.Party.PartyService.UWRController;

import com.Party.PartyService.UWRParty.EmployeeEntityDetails;
import com.Party.PartyService.UWRService.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/party")

public class PartyController {

        @Autowired
        private PartyService partyService;
        @PostMapping("/saveEmplDtls")
        public EmployeeEntityDetails saveEmplDtls(@RequestBody EmployeeEntityDetails employeeEntityDetails){
           try {
               if(employeeEntityDetails.getFlag().equals("C")){
                   employeeEntityDetails = partyService.saveEmplDtls(employeeEntityDetails);
               }else {
                   partyService.updateEmplDtls(employeeEntityDetails);
               }
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

        @GetMapping(value = "/getEmplDtls")
        public EmployeeEntityDetails getEmplDtls(@RequestParam Long id, @RequestParam String user){
            EmployeeEntityDetails details = new EmployeeEntityDetails();
            details = partyService.getEmplDtls(id, user);

            return details;
        }
        @GetMapping(value ="/getPartyMatchingskills")
        public String getPartyMatchingSkills(@RequestParam String skills){
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


}
