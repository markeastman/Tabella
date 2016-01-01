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
import uk.me.eastmans.tabella.services.BallotResultService;
import uk.me.eastmans.tabella.services.BallotService;
import uk.me.eastmans.tabella.services.CurrentUser;

import java.util.*;

/**
 * Created by meastman on 22/12/15.
 */
@Controller
public class BallotController {

    static private String[] colors = { "#f56954", "#00a65a", "#f39c12", "#00c0ef", "#3c8dbc", "#d2d6de"};

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
    public String answerBallot(Long id, Integer answerIndex, float latitude, float longitude, Authentication authentication ) {
        // Get the ballot and set the answer index
        if (id != null && answerIndex != null) {
            BallotResult answer = new BallotResult();
            Ballot b = ballotService.getBallotById(id);
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
            answer.setUser(currentUser.getUser());
            answer.setBallot( b );
            answer.setAnswerIndex(answerIndex);
            answer.setLatitude(latitude);
            answer.setLongitude(longitude);
            ballotAnswerService.saveBallotAnswer(answer);
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
        // to help the ui lets calculate the sums for each answer
        long[] counts = new long[b.getAnswers().size()];
        for (BallotResult r : results)
        {
            counts[r.getAnswerIndex()] = counts[r.getAnswerIndex()] + 1;
        }
        model.addAttribute("counts", counts);
        // Build the pie data for the chart
        PieDataDTO[] pieData = new PieDataDTO[counts.length];
        for (int i = 0; i < pieData.length; i++) {
            pieData[i] = new PieDataDTO();
            pieData[i].setValue(counts[i]);
            pieData[i].setColor(colors[i % colors.length]);
            pieData[i].setHighlight(colors[i % colors.length]);
            pieData[i].setLabel(b.getAnswers().get(i));
        }
        model.addAttribute("pieData",pieData);
        Random r = new Random();
        MapDataDTO[] mapData = new MapDataDTO[results.size()];
        for (int i = 0; i < results.size(); i++) {
            BallotResult br = results.get(i);
            mapData[i] = new MapDataDTO();
            mapData[i].setLatLng( new float[] {br.getLatitude(), br.getLongitude() } );
            mapData[i].setName(b.getAnswers().get(br.getAnswerIndex()));
            MapMarkerStyle style = new MapMarkerStyle();
            style.setFill(colors[br.getAnswerIndex() % colors.length]);
            mapData[i].setStyle(style);
        }
        model.addAttribute("mapData",mapData);
        return "ballotAnalysis";
    }
}
