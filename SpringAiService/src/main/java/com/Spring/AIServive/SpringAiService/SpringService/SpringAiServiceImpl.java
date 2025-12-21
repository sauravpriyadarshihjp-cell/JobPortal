package com.Spring.AIServive.SpringAiService.SpringService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
//import jakarta.mail.internet.MimeMessage;

import java.util.List;
import java.util.Map;

@Service
public class SpringAiServiceImpl implements SpringService {

    private final OpenAiChatModel chatModel;
//    @Autowired
//    private ChatClient chatClient;
    private final ChatClient chatClient;

    @Autowired
    private JavaMailSender mailSender;

    private final ObjectMapper mapper = new ObjectMapper();

    public SpringAiServiceImpl(OpenAiChatModel chatModel, ChatClient chatClient){
        this.chatModel = chatModel;
        this.chatClient = chatClient;
    }



    @Override
    public String scoreResume(ResumeScoreRequest resumeScoreRequest) {
        String resumeText = resumeScoreRequest.getResumeText();
        String jobDesc = resumeScoreRequest.getJobDesc();
        String promptText = String.format("""
                You are an AI recruiter. 
                Compare the following resume: %s 
                Job Description : %s 
                Give a score from 0-100 and explain briefly.
                """, resumeText, jobDesc);

        Prompt prompt = new Prompt(promptText);
        ChatResponse response = chatModel.call(prompt);
//        Generation generation = response.getResults().get(0);
//        Message message = generation.getOutput();
        String resumeScore = response.getResults().get(0).getOutput().getContent();

        return resumeScore;

    }

    @Override
    public String findApplyJobs(String prompt) {
         String promptText = String.format("""
                 You are an intent classifier for a job portal.
                 Determine if the user wants to search for job or apply for job.
                 After that think as a data parser.
                 Extract the following fields from the user prompt as json:
                 -intent
                 -role
                 -jobId
                 -skills
                 -exprience(in years)
                 -location
                 
                 Example Output:
                 {"intent":"apply",
                 "role":"java Developer",
                 "jobId":"12345",
                 "skills":"Java,sql",
                 "exprience":2,
                 "location:"Bangalore"}
                 User prompt : "%s"
                 """,prompt);
         String aiResponse = chatModel.call(promptText);

         return aiResponse;
    }


    @Override
    public void SendNotification(String text) {
        String[] list = text.split("####");
        for(String str : list){
            String message = chatClient.prompt().user("""
                    You are an email assistant. Write a short and friendly job notification email
                    for the user.Job Title is java Developer.""".formatted()).call().content();
            sendEmail(str," new job Match Java Developer", message);
        }
    }

    private void sendEmail(String email,String subject,String message) throws MessagingException {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            MimeMessage message1 = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message1, true);

            //MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message,true);
            mailSender.send(message1);
        }
        catch (MessagingException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String askQuery(String text) {
        String promptText = String.format(text);
        Prompt prompt = new Prompt(promptText);
        ChatResponse response = chatModel.call(prompt);
        return response.getResults().get(0).getOutput().getContent();
    }



//    @Override
//    public String askQueryOnResume(String resumeText, String ques) {
//        String promptText = String.format(resumeText, ques);
//        Prompt prompt = new Prompt(promptText);
//        ChatResponse response = chatModel.call(prompt);
//        return response.getResults().get(0).getOutput().getContent();
//        //return "";
//    }
}
