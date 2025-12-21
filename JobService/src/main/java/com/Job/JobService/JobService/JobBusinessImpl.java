package com.Job.JobService.JobService;

import com.Job.JobService.EntityDetails.*;
import com.Job.JobService.JobEntity.IDEntity;
import com.Job.JobService.JobEntity.JobApplicationEntity;
import com.Job.JobService.JobEntity.JobEntity;
import com.Job.JobService.JobEntity.JobTitleEntity;
import com.Job.JobService.JobRepo.IDRepository;
import com.Job.JobService.JobRepo.JobApplyRepository;
import com.Job.JobService.JobRepo.JobRepository;
import com.Job.JobService.JobRepo.JobTitleEntityRepository;
import com.Job.JobService.UWException.JobException;
import com.Job.JobService.UWException.JobTransactionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.json.JsonObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class JobBusinessImpl implements JobBusiness{
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(JobBusinessImpl.class);
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private IDRepository idRepository;
    @Autowired
    private JobTitleEntityRepository jobTitleEntityRepository;
    @Autowired
    private JobClient jobClient;
    @Autowired
    private SpringAiClient aiClient;
    @Autowired
    private JobResumeProducer jobResumeProducer;
    @Autowired
    private JobApplyRepository jobApplyRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public JobEntityDetails PostJob(JobEntityDetails jobEntityDetails) {
        log.info("Entered in method PostJob in JobBusinessImpl with value - "+jobEntityDetails);
        JobTitleEntity jobTitleEntity = new JobTitleEntity();

        try {
            IDEntity idEntity = new IDEntity();
            idEntity.setUpdatedBy(jobEntityDetails.getUserCode());
            idEntity.setCreatedBy(jobEntityDetails.getUserCode());
            idEntity = idRepository.saveAndFlush(idEntity);
            Long presentjobid = idEntity.getJobId();
            Long presentProposalId = idEntity.getProposalid();
            for(EntityParameter entityParameter : jobEntityDetails.getLstParameter()){
                JobEntity entity = new JobEntity();
                if(entityParameter.getParameterName().equals("Exprience Required")){
                    if(entityParameter.getParameterValue().equals("1 Yrs")){
                        JobTransactionMessage jobExceptionMessage = new JobTransactionMessage();
                        jobExceptionMessage.setStrMessageId("500");
                        jobExceptionMessage.setStrMessageSource("SYSTEM");
                        jobExceptionMessage.setStrMessageSeverity("FATAL");
                        jobExceptionMessage.setStrMessageType("ERROR");
                        jobExceptionMessage.setStrProbableCause("1 Year Exprience is not Eligible");
                        jobEntityDetails.setObjTransactionMesaage(jobExceptionMessage);
                        throw new JobException(jobExceptionMessage);
                    }
                }
                entity.setPartyId(jobEntityDetails.getPartyId());
                entity.setInsertedBy(jobEntityDetails.getUserCode());
                entity.setUpdatedBy(jobEntityDetails.getUserCode());
                entity.setParameterName(entityParameter.getParameterName());
                entity.setParameterValue(entityParameter.getParameterValue());
                entity.setJobId(presentjobid);
                jobRepository.saveAndFlush(entity);

                //For inserting in JobTitleEntity
                if(entityParameter.getParameterName().equals("Employer Name")){
                    jobTitleEntity.setJobCompany(entityParameter.getParameterValue());
                }
                if(entityParameter.getParameterName().equals("Job Title")){
                    jobTitleEntity.setJobTitle(entityParameter.getParameterValue());
                }
                if(entityParameter.getParameterName().equals("Job Description")){
                    jobTitleEntity.setJobDesc(entityParameter.getParameterValue());
                }
                if(entityParameter.getParameterName().equals("Location")){
                    jobTitleEntity.setLocation(entityParameter.getParameterValue());
                }
                if(entityParameter.getParameterName().equals("Package")){
                    jobTitleEntity.setSalary(entityParameter.getParameterValue());
                }
            }

            //For inserting in JobTitleEntity
            jobTitleEntity.setJobId(presentjobid);
            jobTitleEntity.setUpdatedBy(jobEntityDetails.getUserCode());
            jobTitleEntity.setCreatedBy(jobEntityDetails.getUserCode());
            jobTitleEntityRepository.saveAndFlush(jobTitleEntity);
            //End


            jobEntityDetails.setProposalId(presentProposalId);
            jobEntityDetails.setJobId(presentjobid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("Exiting from method PostJob with value of jobEntotyDetails as - "+jobEntityDetails);
        publisher.publishEvent(new JobPostedEvent(this, jobEntityDetails));
        return jobEntityDetails;
    }

    @Override
    public JobEntityDetails getJob(Long id) {
        log.info("Entered in method with id - "+id);
        JobEntityDetails jobEntityDetails = new JobEntityDetails();
        try {
            List<IDEntity> idEntityList =idRepository.getByProposalId(id);
            if(idEntityList.isEmpty()){
                throw new RuntimeException("No Job exist with id "+id);
            }
            List<JobEntity> list = jobRepository.getByJobId(idEntityList.get(0).getJobId());
            jobEntityDetails.setJobId(idEntityList.get(0).getJobId());
            List<EntityParameter> parameterList = new ArrayList<>();
            jobEntityDetails.setPartyId(list.get(0).getPartyId());
            for(JobEntity str : list){
                EntityParameter entityParameter = new EntityParameter();
                entityParameter.setParameterName(str.getParameterName());
                entityParameter.setParameterValue(str.getParameterValue());
                parameterList.add(entityParameter);
            }
            jobEntityDetails.setLstParameter(parameterList);
            jobEntityDetails.setProposalId(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Exiting from method getJob of service class");
        return jobEntityDetails;
    }

    @Override
    public LstJobDetail getAllJob(String title, int page) {
        log.info("Entered in method getAllJob with value - "+title + ", "+page);
        LstJobDetail lstJobDetail = new LstJobDetail();
        try{
            int default_page_size = 5;
            Pageable pageable = PageRequest.of(page, default_page_size, Sort.by("creationTime"));
//            Page<JobEntity> list = jobRepository.findAll(pageable);
//            List<JobEntity> jobEntityList = list.getContent();
//
//            List<EntityParameter> parameterList = new ArrayList<>();
//            List<JobEntityDetails> jobEntityDetailsList = new ArrayList<>();
//
//            for(JobEntity entity : jobEntityList){
//                JobEntityDetails jobEntityDetails = new JobEntityDetails();
//                List<JobEntity> jobEntities = jobRepository.getByJobId(entity.getJobId());
//                EntityParameter entityParameter = new EntityParameter();
//                for(JobEntity entity1 : jobEntities){
//                    entityParameter.setParameterName(entity1.getParameterName());
//                    entityParameter.setParameterValue(entity1.getParameterValue());
//                    parameterList.add(entityParameter);
//                }
//                jobEntityDetails.setLstParameter(parameterList);
//                jobEntityDetails.setProposalId(idRepository.getByJobId(entity.getJobId()).get(0).getProposalid());
//                jobEntityDetails.setJobId(entity.getJobId());
//                jobEntityDetailsList.add(jobEntityDetails);
//
//            }
//            lstJobDetail.setLstJobEntityDetails(jobEntityDetailsList);
            List<JobDetails> jobDetailsList = new ArrayList<>();
            Page<JobTitleEntity> list = jobTitleEntityRepository.findByJobTitle(title, pageable);
            List<JobTitleEntity> jobTitleEntityList = list.getContent();
            for(JobTitleEntity entity : jobTitleEntityList){
                JobDetails jobDetails = new JobDetails();
                jobDetails.setJobCompany(entity.getJobCompany());
                jobDetails.setJobTitle(entity.getJobTitle());
                jobDetails.setJobId(entity.getJobId());
                jobDetails.setSalary(entity.getSalary());
                jobDetails.setLocation(entity.getLocation());
                jobDetailsList.add(jobDetails);
            }

            lstJobDetail.setLstjobdetail(jobDetailsList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("Exiting from method getAllJob from service class");
        return lstJobDetail;
    }

    @Override
    public String UploadResume(Long PartyId, MultipartFile file) {
        log.info("Entered in method uploadResume of service class");
        String message = "";
        try{
            String partyId = String.valueOf(PartyId);
           message = jobClient.UploadResume(partyId, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Exiting from method UploadResume from service class");
        return message;
    }

    @Override
    public List<Long> getPartyMatchingSkille(String jobTitle) {
        log.info("Inside getpartymatchingskills method of service class with jobTitle - "+ jobTitle);
        List<Long> lstParty = new ArrayList<>();
        try{
            lstParty = jobClient.GetPartyByResumeSkills(jobTitle);

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Exiting from method getPartyMatchingSkills form service class");
        return lstParty;
    }

    @Override
    public String askQuery(String text) {
        log.info("Inside askQuery method od service class with text " + text);
        String ans = "";

        try{
            ans = aiClient.askQuery(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Exiting from method askQuery from service class");
        return ans;

    }

    @Override
    public String getResumeScore(Long partyId, String jobDesc) {
        log.info("Inside getResumeScore method of service class with partyId ->"+partyId + "and jobDesc -> "+jobDesc);
        String scoreBrief = "";
        String kafkaRequest = null;
        try{
           String resumeText = jobClient.GetResumeTextByPartyId(String.valueOf(partyId));
           ResumeScoreRequest resumeScoreRequest = new ResumeScoreRequest();
           resumeScoreRequest.setResumeText(resumeText);
           resumeScoreRequest.setJobDesc(jobDesc);
           resumeScoreRequest.setPartyId(partyId);
          // scoreBrief = aiClient.scoreResume(resumeScoreRequest);
            kafkaRequest = partyId + "#####" +jobDesc +"#####" +resumeText;
            jobResumeProducer.sendResume(kafkaRequest);
           // scoreBrief = resumeConsumer.consumeResumeScore("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(scoreBrief == ""){
            scoreBrief = "Wait for response call getResponsefromkafka";
        }
        log.info("Exiting from method getResumeScore ");
        return scoreBrief;
    }

    @Override
    public JobEntityDetails ApplyJob(JobEntityDetails jobEntityDetails) {
        log.info("Entered in method ApplyJob inside JobBusinessImpl");

        try{
            JobApplicationEntity jobApplicationEntity = new JobApplicationEntity();
            jobApplicationEntity.setJobId(jobEntityDetails.getJobId());
            jobApplicationEntity.setPartyId(jobEntityDetails.getPartyId());
            jobApplicationEntity.setInsertedBy(jobEntityDetails.getUserCode());
            jobApplicationEntity.setApplicationStatus("Pending");
            jobApplyRepository.saveAndFlush(jobApplicationEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JobTransactionMessage jobTransactionMessage = new JobTransactionMessage();
        jobTransactionMessage.setStrMessageId("200");
        jobTransactionMessage.setStrMessageSource("Success");
        jobTransactionMessage.setStrMessageType("Info");
        jobTransactionMessage.setStrProbableCause("Application Submitted Successfully");
        jobEntityDetails.setObjTransactionMesaage(jobTransactionMessage);
         return jobEntityDetails;
    }

    @Override
    public void TakeAction(JobEntityDetails jobEntityDetails) {
        log.info("Happily Entered in method TakeAction of JobBusinessImpl");

        try{
            JobApplicationEntity jobApplicationEntity = new JobApplicationEntity();
            jobApplicationEntity = jobApplyRepository.getByjobIdandPArtyId(jobEntityDetails.getPartyId(), jobEntityDetails.getJobId());
            jobApplicationEntity.setApplicationStatus(jobEntityDetails.getApplicationStatus());
            jobApplyRepository.saveAndFlush(jobApplicationEntity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String findApplyJobs(String prompt, Long partyId) {
        log.info("Happily Entered inside findApplyJobs inside JobBusinessImpl");
        String response = "";
        try{
            response = aiClient.findApplyJobs(prompt);
            Map<String, Object> map = mapper.readValue(response, Map.class);
            String intent = (String) (map.getOrDefault("intent", ""));
            LstJobDetail lstJobDetail = new LstJobDetail();

            if(intent.equalsIgnoreCase("apply")){
                String jobId = (String) (map.getOrDefault("jobId",""));
                JobApplicationEntity jobApplicationEntity = new JobApplicationEntity();
                jobApplicationEntity.setPartyId(partyId);
                jobApplicationEntity.setApplicationStatus("Pending");
                jobApplicationEntity.setJobId(Long.valueOf(jobId));
                jobApplicationEntity.setInsertedBy("AiModel");
                jobApplyRepository.saveAndFlush(jobApplicationEntity);
                return "Applied Successfully";
            }else{
                String role = (String) (map.getOrDefault("role",""));
                lstJobDetail = getAllJob(role,3);
                StringBuilder sb = new StringBuilder("Here are some jobs for " + role +": \n\n");
                for(JobDetails jobDetails : lstJobDetail.getLstjobdetail()){
                    sb.append(" ! ")
                            .append("JobId is: ")
                            .append(jobDetails.getJobId())
                            .append(" ")
                            .append(jobDetails.getJobTitle())
                            .append(" at ")
                            .append(jobDetails.getJobCompany())
                            .append(" (")
                            .append(jobDetails.getLocation())
                            .append(") and Salary is : ")
                            .append(jobDetails.getSalary());
                }
                 //return mapper.writeValueAsString(lstJobDetail);
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
