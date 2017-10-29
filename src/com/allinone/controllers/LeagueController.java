package com.allinone.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.allinone.pojos.League;
import com.allinone.pojos.Player;
import com.allinone.pojos.Team;
import com.allinone.service.api.LeagueServiceAPI;
import com.allinone.service.api.ListToJsonTwoColumnsServiceAPI;
import com.allinone.service.api.SportUtilityServiceAPI;

@Controller

public class LeagueController {
	
	@Autowired
	SportUtilityServiceAPI objSportUtility;
	
	@Autowired
	LeagueServiceAPI objLeagueService;
	
	@Autowired
	ListToJsonTwoColumnsServiceAPI objListToJson;
	
	@RequestMapping(value="/fetchLeagues")
	@ResponseBody
	public String fetchLeagues(HttpServletRequest objRequest, HttpServletResponse objResponse) {
		String comparator=objRequest.getParameter("VALUE");
		List<League> returnMessage = objLeagueService.fetchLeagues(objRequest.getParameter("SPORT_NAME"),comparator);
		
		
	    String jsonString = objListToJson.listToJson("League", returnMessage);
		//System.out.println(jsonString);
		return jsonString;
	}
	
	
	
	@RequestMapping(value="/redirectLeague")
	public String redirectLeague(HttpServletRequest objRequest, HttpServletResponse objResponse,ModelMap model) {
		//for search functionality
		List<League> returnMessage = objLeagueService.fetchLeagues(objRequest.getParameter("redirectValue"),"searchText");
		model.put("name",returnMessage.get(0).getLeagueName());
		return "LeagueInfoPage";
	}
	
	@RequestMapping(value="/playerList")
	@ResponseBody
	public String playerList(HttpServletRequest objRequest, HttpServletResponse objResponse) {
		
		List<Player> returnList = objLeagueService.playerList("4028b8815f69d617015f69d702b90001");
		
		StringBuilder alpha=new StringBuilder();
		for (Player i :returnList) {
			alpha.append(i.getFirstName()).append("").append(i.getLastName());
			
			System.out.println(i.getFirstName()+"  "+i.getLastName());
		}
		
		return alpha.toString();
	}
	
	@RequestMapping(value="/createLeague")
	@ResponseBody
	public String createLeague(HttpServletRequest objRequest, HttpServletResponse objResponse) {
		
		String returnMessage = objSportUtility.createLeague()?"Success":"Failed";
		
		
		return returnMessage;
	}
	
	@RequestMapping(value="/testPage")
	@ResponseBody
	public ModelAndView testMyPage(HttpServletRequest objRequest, HttpServletResponse objResponse) {
		
	
		return new ModelAndView("LeagueInfoPage");
	}
}
