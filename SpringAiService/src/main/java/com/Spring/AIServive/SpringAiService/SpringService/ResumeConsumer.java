package com.Spring.AIServive.SpringAiService.SpringService;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class ResumeConsumer {
    private static final Logger log = LoggerFactory.getLogger(ResumeConsumer.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private SpringAiServiceImpl springAiService;
    @KafkaListener(topics = "resume-topic", groupId = "ai-service-group")
    public void consumeResume(String  requestByKafka){
       log.info("Happily Entered in method consumeResume to analyze Resume");
       //ResumeScoreRequest resumeScoreRequest = new ResumeScoreRequest(resumeText, jobDesc);
       //Ai will analyze resume and comment it
        ResumeScoreRequest resumeScoreRequest = new ResumeScoreRequest();
        String[] list = requestByKafka.split("#####");
        String partyId = list[0];
        String jobDesc = list[1];
        String resumeText = list[2];
        resumeScoreRequest.setResumeText(resumeText);
        resumeScoreRequest.setJobDesc(jobDesc);
       String resumeScore = springAiService.scoreResume(resumeScoreRequest);
       resumeScore = partyId+ "#####" +resumeScore;
       //Now send back score to job service
        kafkaTemplate.send("resume-score-topic", resumeScore);
        log.info("Ai Service send resume Score back to job service");
    }
}
