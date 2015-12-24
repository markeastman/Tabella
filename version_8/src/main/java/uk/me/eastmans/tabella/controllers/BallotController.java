package uk.me.eastmans.tabella.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateEngine;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.services.BallotService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by meastman on 22/12/15.
 */
@Controller
public class BallotController {

    private BallotService ballotService;

    @Autowired
    public void setBallotService(BallotService ballotService)
    {
        this.ballotService = ballotService;
    }

    @RequestMapping("ballot/new")
    public String create()
    {
        return "create";
    }

    @RequestMapping(value = "ballot/save",method = RequestMethod.POST)
    public String saveBallot(Ballot ballot) {
        // trim any null answers
        List<String> answers = ballot.getAnswers();
        ListIterator<String> iterator = ballot.getAnswers().listIterator();
        while(iterator.hasNext())
        {
            String answer = iterator.next();
            if (answer == null || answer.trim().length() == 0)
                iterator.remove();
        }
        ballotService.saveBallot(ballot);
        return "redirect:/home";
    }

    @RequestMapping(value = "ballot/answer",method = RequestMethod.POST)
    public String answerBallot(Long id, Integer answerIndex ) {
        // Get the ballot and set the answer index
        if (id != null && answerIndex != null) {
            Ballot b = ballotService.getBallotById(id);
            b.setAnswerIndex(answerIndex);
            ballotService.saveBallot(b);
        }
        return "redirect:/home";
    }
}
