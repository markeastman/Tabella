package uk.me.eastmans.tabella.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.BallotResult;
import uk.me.eastmans.tabella.services.BallotResultService;
import uk.me.eastmans.tabella.services.BallotService;
import uk.me.eastmans.tabella.services.CurrentUser;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by meastman on 22/12/15.
 */
@Controller
public class BallotController {

    private BallotService ballotService;

    private BallotResultService ballotAnswerService;

    @Autowired
    public void setBallotService(BallotService ballotService, BallotResultService ballotAnswerService)
    {
        this.ballotService = ballotService;
        this.ballotAnswerService = ballotAnswerService;
    }

    @RequestMapping("ballot/new")
    public String create()
    {
        return "create";
    }

    @RequestMapping(value = "ballot/save",method = RequestMethod.POST)
    public String saveBallot(Ballot ballot, Authentication authentication) {
        // trim any null answers
        List<String> answers = ballot.getAnswers();
        ListIterator<String> iterator = ballot.getAnswers().listIterator();
        while(iterator.hasNext())
        {
            String answer = iterator.next();
            if (answer == null || answer.trim().length() == 0)
                iterator.remove();
        }
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        ballot.setUser(currentUser.getUser());
        ballotService.saveBallot(ballot);
        return "redirect:/home";
    }

    @RequestMapping(value = "ballot/answer",method = RequestMethod.POST)
    public String answerBallot(Long id, Integer answerIndex, Authentication authentication ) {
        // Get the ballot and set the answer index
        if (id != null && answerIndex != null) {
            BallotResult answer = new BallotResult();
            Ballot b = ballotService.getBallotById(id);
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
            answer.setUser(currentUser.getUser());
            answer.setBallot( b );
            answer.setAnswerIndex(answerIndex);
            ballotAnswerService.saveBallotAnswer(answer);
        }
        return "redirect:/home";
    }
}
