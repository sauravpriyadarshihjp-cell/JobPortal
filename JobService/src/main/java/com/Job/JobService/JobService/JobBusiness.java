package com.Job.JobService.JobService;

import com.Job.JobService.EntityDetails.JobEntityDetails;
import com.Job.JobService.EntityDetails.LstJobDetail;
import com.Job.JobService.UWException.JobException;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
public interface JobBusiness {
    public JobEntityDetails PostJob(JobEntityDetails jobEntityDetails) throws JobException;
    public JobEntityDetails getJob(Long id);
    public LstJobDetail getAllJob(String title, int page) throws JobException;
    public String UploadResume(Long PartyId, MultipartFile file);
    public List<Long> getPartyMatchingSkille(String jobTitle);
    public String askQuery(String text);
    public String getResumeScore(Long partyId, String jobDesc);
    public JobEntityDetails ApplyJob(JobEntityDetails jobEntityDetails) throws JobException;
    public JobEntityDetails TakeAction(JobEntityDetails jobEntityDetails) throws JobException;
    public String findApplyJobs(String prompt, Long partyId);
}
