package uk.me.eastmans.tabella.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.BallotResult;
import uk.me.eastmans.tabella.services.BallotAnswerNotificationService;
import uk.me.eastmans.tabella.services.BallotResultService;
import uk.me.eastmans.tabella.services.BallotService;
import uk.me.eastmans.tabella.services.CurrentUser;
import uk.me.eastmans.tabella.views.MapDataDTO;
import uk.me.eastmans.tabella.views.MapMarkerStyle;
import uk.me.eastmans.tabella.views.PieDataDTO;
import uk.me.eastmans.tabella.views.ViewDTOHelper;

import java.util.*;

/**
 * Created by meastman on 22/12/15.
 */
@Controller
public class BallotController {

    private BallotService ballotService;

    private BallotResultService ballotAnswerService;

    private BallotAnswerNotificationService notificationService;

    private Random r = new Random(); // Used for getting random positions

    @Autowired
    public void setBallotService(BallotService ballotService, BallotResultService ballotAnswerService,
                                 BallotAnswerNotificationService notificationService)
    {
        this.ballotService = ballotService;
        this.ballotAnswerService = ballotAnswerService;
        this.notificationService = notificationService;
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
    public String answerBallot(Long id, Integer answerIndex, float latitude, float longitude, Authentication authentication ) {
        // Get the ballot and set the answer index
        if (id != null && answerIndex != null) {
            BallotResult answer = new BallotResult();
            Ballot b = ballotService.getBallotById(id);
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
            answer.setUser(currentUser.getUser());
            answer.setBallot( b );
            answer.setAnswerIndex(answerIndex);
            /* We should use the passed in values but to simulate coordinates better we will randomize these
            answer.setLatitude(latitude);
            answer.setLongitude(longitude);
            */
            answer.setLatitude(r.nextFloat() * 80);
            answer.setLongitude(r.nextFloat() * 180 - 90);
            ballotAnswerService.saveBallotAnswer(answer);
            // We need to convert the result to view DTO objects
            MapDataDTO mapDataDTO = ViewDTOHelper.convertToMapDataDTO(answer);
            notificationService.notifyBallotResult(answer,ViewDTOHelper.convertToJSONString(mapDataDTO));
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "ballot/results")
    public String getAnsweredBallots(Model model, Authentication authentication ) {
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        Map<Ballot,Map<Integer,Long>> ballots = ballotService.getAnsweredBallots(currentUser.getUser());
        System.out.println( "Ballots are " + ballots );
        model.addAttribute("ballots", ballots);
        return "answeredBallots";
    }

    @RequestMapping(value = "ballotResult/{id}")
    public String getBallotAnalysis(@PathVariable Long id, Model model)
    {
        Ballot b = ballotService.getBallotById(id);
        model.addAttribute("ballot", b);
        List<BallotResult> results = ballotAnswerService.getBallotAnswers(b);
        model.addAttribute("results", results);
        model.addAttribute("resultsCount", results.size());
        model.addAttribute("colors",ViewDTOHelper.colors);
        model.addAttribute("colorsCount",ViewDTOHelper.colors.length);
        // to help the ui lets calculate the sums for each answer
        long[] counts = new long[b.getAnswers().size()];
        for (BallotResult r : results)
        {
            counts[r.getAnswerIndex()] = counts[r.getAnswerIndex()] + 1;
        }
        model.addAttribute("counts", counts);
        // Build the pie data for the chart
        PieDataDTO[] pieData = ViewDTOHelper.convertToPieData(b, counts);
        model.addAttribute("pieData",pieData);
        MapDataDTO[] mapData = ViewDTOHelper.convertToMapDataDTO(results);
        model.addAttribute("mapData",mapData);
        return "ballotAnalysis";
    }
}
