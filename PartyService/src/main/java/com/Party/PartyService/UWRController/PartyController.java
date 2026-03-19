package com.Party.PartyService.UWRController;

import com.Party.PartyService.PartyException.PrtException;
import com.Party.PartyService.UWRParty.EmployeeEntityDetails;
import com.Party.PartyService.UWRService.PartyService;
import com.Party.PartyService.UWRService.ResumeClient;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("/party")

public class PartyController {

        @Autowired
        private PartyService partyService;
        @Autowired
        private ResumeClient resumeClient;
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
    @GetMapping(value = "/downloadResume")
    public ResponseEntity<byte[]> downloadResume(@RequestParam Long partyId){
        ResponseEntity<Resource> resource = null;
        String url = "";
        try{
            url = resumeClient.downloadResume(partyId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        return ResponseEntity
//                .status(HttpStatus.FOUND)
//                .location(URI.create(url))
//                .build();
        try {
            URL fileUrl = new URL(url);
            InputStream inputStream = fileUrl.openStream();
            byte[] bytes = inputStream.readAllBytes();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf")
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .body(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        //return resource;
    }


}
