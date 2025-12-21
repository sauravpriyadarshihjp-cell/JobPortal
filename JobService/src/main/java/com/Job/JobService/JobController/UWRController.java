package com.Job.JobService.JobController;

import com.Job.JobService.EntityDetails.JobEntityDetails;
import com.Job.JobService.EntityDetails.LstJobDetail;
import com.Job.JobService.JobService.JobBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/job")
public class UWRController {
    @Autowired
    private JobBusiness jobBusiness;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/PostJob")
    public JobEntityDetails PostJob(@RequestBody JobEntityDetails jobEntityDetails){
        log.info("Happily Entered inside method PostJob with value - "+jobEntityDetails);
        //JobEntityDetails returnjobdetails = new JobEntityDetails();
        try{
            jobEntityDetails = jobBusiness.PostJob(jobEntityDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobEntityDetails;

    }
    @PostMapping("ApplyJob")
    public JobEntityDetails ApplyJob(@RequestBody JobEntityDetails jobEntityDetails){
        log.info("Happily Entered inside method ApplyJob with value - " + jobEntityDetails);
        try{
            jobEntityDetails = jobBusiness.ApplyJob(jobEntityDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Exiting from method ApplyJob");
        return jobEntityDetails;
    }
    @PostMapping("TakeAction")
    public void TakeAction(JobEntityDetails jobEntityDetails){
        log.info("Happily Entered in method TakeAction of UWRController");

        try{
            jobBusiness.TakeAction(jobEntityDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @GetMapping("/GetJob")
    public JobEntityDetails getJob(@RequestParam Long id){
        log.info("Happily Entered inside method getJob with id - "+id);
        JobEntityDetails jobEntityDetails = new JobEntityDetails();
        try {
            jobEntityDetails = jobBusiness.getJob(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       log.info("Exiting method getJob");
        return jobEntityDetails;
    }
    @GetMapping("/GetAllJob")
    public LstJobDetail getAllJob(
            @RequestParam(name = "title",required = false,defaultValue = "SAP Developer") String title, @RequestParam(name = "page", required = false,defaultValue = "0") Integer page){
        log.info("Happily Entered inside method getAllJob with values -"+title +","+page);

        LstJobDetail lstJobDetail = new LstJobDetail();

        try{
            lstJobDetail = jobBusiness.getAllJob(title,page);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("Exiting method getAllJob");
        return lstJobDetail;
    }
    @PostMapping("/UploadResume")
    public String UploadResume(@RequestParam Long partyId, @RequestParam MultipartFile file){

        log.info("Entered in uploadResume method of UWRController with partId - " + partyId);
        String msg = "";

        try{
            msg = jobBusiness.UploadResume(partyId, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Exiting method Upload Resume");
        return msg;
    }
    @GetMapping("/getPartyMatchingSkills")
    public List<Long> getPartyMatchingSkille(@RequestParam String jobTitle){
        log.info("Inside getpartymatchingskills method of controller with jobtitle - "+jobTitle);
        System.out.println("Inside getpartymatchingskills method of controller");
        List<Long> lstPartyId = new ArrayList<>();

        try {
              lstPartyId = jobBusiness.getPartyMatchingSkille(jobTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Exiting method getpartymatchingskillls");
        return lstPartyId;
    }

    @GetMapping("/askQuery")
    public String askQuery(@RequestParam String text){
        log.info("Inside askQuery method of controller class");
            String ans = "";
        try {
            ans = jobBusiness.askQuery(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Exiting method askQuery");

        return ans;
    }
    @PostMapping("/resumeScore")
    public String getResumeScore(@RequestParam String partyId, @RequestParam String jobDesc){
        log.info("Inside getResumeSCore method of controller class with value of partId and jobDescriptions are - "+partyId +", "+jobDesc);
        String scoreExplanation = "";
        try {
            scoreExplanation = jobBusiness.getResumeScore(Long.valueOf(partyId),jobDesc);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("Exiting method getResumeScore");

        return scoreExplanation;
    }

    @GetMapping("/kafkaResumeScore")
    public String getResumeScoreByKafka(@RequestParam String partyId){
        String score = null;
        score = redisTemplate.opsForValue().get(partyId);
        if(score == null){
            return "Still processing or expired;";
        }
        return score;
    }
    @GetMapping("/testredis")
    public String testredis(){
        redisTemplate.opsForValue().set("testKey","hello",10, TimeUnit.MINUTES);
        return redisTemplate.opsForValue().get("testKey");
    }

    @PostMapping("/AgentAiSearchApplyJobs")
    public String findApplyJobs(@RequestParam String prompt, Long partyId){
        log.info("Happily Entered in method findApplyJobs inside UWRController");
        String response = "";
        try{
            response = jobBusiness.findApplyJobs(prompt,partyId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
