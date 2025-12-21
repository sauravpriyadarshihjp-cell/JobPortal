package com.Job.JobService.JobService;

import com.Job.JobService.EntityDetails.ResumeScoreRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class JobResumeConsumer {
    @Autowired
    private JobBusinessImpl jobBusiness;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @KafkaListener(topics = "resume-score-topic", groupId = "job-service-group")
    public void consumeResumeScore(String resumeFeedback){
        String[] list = resumeFeedback.split("#####");
        String partyId = "";
        String resumeScore = "";
        partyId = list[0];
        resumeScore = list[1];
        log.info("I am Happy to announce your resume score"+resumeScore);
        redisTemplate.opsForValue().set(partyId,resumeScore,10, TimeUnit.MINUTES);
    }
}
