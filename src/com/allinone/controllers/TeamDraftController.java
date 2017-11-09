package com.allinone.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allinone.pojos.League;
import com.allinone.service.api.LeagueServiceAPI;
import com.allinone.service.api.ListToJsonTwoColumnsServiceAPI;
import com.allinone.service.api.SportUtilityServiceAPI;
import com.allinone.service.api.TeamListToJsonServiceAPI;
import com.allinone.service.api.UserTeamDraftServiceAPI;
import com.google.gson.Gson;

@Controller

public class TeamDraftController {
	
	@Autowired
	SportUtilityServiceAPI objSportUtility;
	
	@Autowired
	UserTeamDraftServiceAPI objUserTeamDraftAPI;
	
	@Autowired
	ListToJsonTwoColumnsServiceAPI objListToJson;
	
	@Autowired
	TeamListToJsonServiceAPI objTeamListToJson;
	
	@RequestMapping(value="/createTeam")
	@ResponseBody
	public String fetchLeagues(HttpServletRequest objRequest, HttpServletResponse objResponse) {
		
		String userId = objRequest.getParameter("userId");
		String leagueId = objRequest.getParameter("leagueId");
		String listOfPlayerIds = objRequest.getParameter("listOfPlayerIds");
		List<String> listOfPlayerIdsList = new Gson().fromJson( listOfPlayerIds, List.class);
		Set playerSet=new HashSet(listOfPlayerIdsList);
		System.out.println("Done");
		String returnString =objUserTeamDraftAPI.createTeam(playerSet,leagueId, userId);
		if(returnString.equals("false"))
		{
			return "false";
		}
		return "true";
	}
	
	

}
