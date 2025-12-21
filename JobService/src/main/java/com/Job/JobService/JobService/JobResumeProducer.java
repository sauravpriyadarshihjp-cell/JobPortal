package com.Job.JobService.JobService;

import com.Job.JobService.EntityDetails.ResumeScoreRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class JobResumeProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public JobResumeProducer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendResume(String kafkaRequest){
        log.info("Entered sendResume method of JobResumeProducer class");

        kafkaTemplate.send("resume-topic", kafkaRequest);
        log.info("Exiting from sendResume method");
    }
}
