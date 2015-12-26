package uk.me.eastmans.tabella.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.me.eastmans.tabella.domain.User;
import uk.me.eastmans.tabella.services.BallotService;
import uk.me.eastmans.tabella.services.CurrentUser;

/**
 * Created by meastman on 22/12/15.
 */
@Controller
public class HomeController {

    private BallotService ballotService;

    @Autowired
    public void setBallotService(BallotService ballotService)
    {
        this.ballotService = ballotService;
    }

    @RequestMapping("/home")
    public String home(Model model, Authentication authentication)
    {
        User u = ((CurrentUser)authentication.getPrincipal()).getUser();
        model.addAttribute("ballots", ballotService.getUnasweredBallotsForUser(u));
        return "home";
    }
}
