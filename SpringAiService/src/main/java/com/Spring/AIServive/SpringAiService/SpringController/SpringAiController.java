package com.Spring.AIServive.SpringAiService.SpringController;

import com.Spring.AIServive.SpringAiService.SpringService.ResumeScoreRequest;
import com.Spring.AIServive.SpringAiService.SpringService.SpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/springAi")
public class SpringAiController {
    @Autowired
    private SpringService springService;

    @PostMapping("/scoreResume")
    public String scoreResume(@RequestBody ResumeScoreRequest resumeScoreRequest){

        System.out.println("Finally i am in controller to read Resume score");
        return springService.scoreResume(resumeScoreRequest);
    }

    @GetMapping("/askQuery")
    public String askQuery(@RequestParam String text){
        System.out.println("I am in method askquery of controller");
        return springService.askQuery(text);
    }

    @PostMapping("/findApplyJobs")
    public String findApplyJobs(@RequestParam String prompt){
        return springService.findApplyJobs(prompt);
    }

    @PostMapping("/sendNotification")
    public void SendNotification(@RequestParam String text){
        springService.SendNotification(text);
    }

}
