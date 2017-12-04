/**
 * 
 */
package com.allinone.service.impl;

import java.util.ArrayList;

import java.util.Collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.allinone.dao.api.GameDaoAPI;
import com.allinone.dao.api.LeagueDaoAPI;
import com.allinone.dao.api.SportUtilityDaoAPI;
import com.allinone.pojos.Game;
import com.allinone.pojos.League;
import com.allinone.pojos.Player;
import com.allinone.pojos.UserTeam;
import com.allinone.service.api.LeagueServiceAPI;
import com.allinone.service.api.ListToJsonTwoColumnsServiceAPI;
import com.allinone.service.api.StatisticCalculatorServiceAPI;
import com.allinone.service.api.TeamListToJsonServiceAPI;
import com.allinone.service.api.TeamSetToJsonAPI;

/**
 * @author rahul
 *
 */
@Transactional
@Service
public class LeagueServiceImpl implements LeagueServiceAPI {

	@Autowired
	ListToJsonTwoColumnsServiceAPI objListToJson;
	
	@Autowired
	TeamSetToJsonAPI objTeamSetToJson;
	
	@Autowired
	TeamListToJsonServiceAPI objTeamListToJson;
	
	@Autowired
	SetOfGamesToJsonAPI objSetOfGamesToJson;
	
	@Autowired
	LeagueDaoAPI objLeagueDao;
	
	@Autowired
	SportUtilityDaoAPI objSportUtilityDao;
	
	@Autowired
	StatisticCalculatorServiceAPI objStatisticCalculatorAPI;
	
	@Autowired
	GameDaoAPI objGameDao;

	@Override
	public String fetchLeagues(String sport,String comparator,String userId) {
		// TODO Auto-generated method stub
		
		List<League> listOfLeagues = new ArrayList<League>();
		String sportId;
		if(comparator.equals("redirectText")) {
			listOfLeagues = objLeagueDao.fetchLikeLeagues(sport);
			System.out.println(sport);
			return listOfLeagues.get(0).getLeagueName();
		}
		if(comparator.equals("searchText")) {
			listOfLeagues = objLeagueDao.fetchLikeLeagues(sport);
			System.out.println(sport);
						
			String jsonString = objListToJson.listToJson("League", listOfLeagues);
			System.out.println(jsonString + "returning result " + comparator);
			
			return jsonString;
		}
		if (sport.equals("CRICKET")) {
			sportId=objSportUtilityDao.getsportID("CRICKET");
		}
		else {sportId=objSportUtilityDao.getsportID("SOCCER");}
		
		listOfLeagues = objLeagueDao.fetchLeagues(sportId,userId);
		
		String jsonString = objListToJson.listToJson("League", listOfLeagues);
		System.out.println(jsonString + "returning result " + comparator);
		
		return jsonString;
		
	}

	@Override
	public List<Player> playerList(String leagueId,String userId){
		List<Player> listOfPlayer=new ArrayList<Player>();
		List<League> listOfLeagues = new ArrayList<League>();
		String modifiedLeagueId=leagueId;
		String returnedParentCheck=objLeagueDao.checkIfThereIsParentLeague(leagueId);
		if(!returnedParentCheck.equals("false")) {
			modifiedLeagueId=returnedParentCheck;
		}
		listOfLeagues = objLeagueDao.fetchLikeLeagues(modifiedLeagueId);
		
		for(Player i: listOfLeagues.get(0).getSetOfPlayers()) {
			listOfPlayer.add(i);
		}
		
		Set<Player> returnSet = objLeagueDao.userTeamSet(leagueId, userId);
		List<Player> returnList = new ArrayList<Player>(returnSet);
		listOfPlayer.removeAll(returnList);
		return listOfPlayer;
	}

	@Override
	public boolean hasUserJoinedLeague(String userId, String leagueId) {
		// TODO Auto-generated method stub
		
		return objLeagueDao.hasUserJoinedLeague(userId, leagueId);
	}
	
	@Override
	public String fetchUserTeams(String leagueId) {
		// TODO Auto-generated method stub
		
		Set<UserTeam> returnSet = objLeagueDao.fetchUserTeams(leagueId);
		String jsonString=objTeamSetToJson.listToJson("userTeam", returnSet);
		return jsonString;
	}
	

	@Override
	public String userTeamSet(String leagueId,String userId) {
		// TODO Auto-generated method stub
		
		Set<Player> returnSet = objLeagueDao.userTeamSet(leagueId, userId);
		List<Player> returnList = new ArrayList<Player>(returnSet);
		String jsonString=objTeamListToJson.listToJson("usersTeam", returnList);
		return jsonString;
	}
	
	@Override
	public Map<String,Double> updateUserScoresForAGame(String leagueId, String gameId) {
		
		Map<String,Double> mapOfUserIdAndScore = new HashMap<String,Double>();
		Set<UserTeam> setOfUserTeams = objLeagueDao.fetchUserTeams(leagueId);
		Game objGame = objGameDao.fetchGameObject(gameId);
		Map<String,Map<String, Double>> mapOfPlayerAndRoleScores = objSportUtilityDao.readGameScores(objGame);
		
		for(UserTeam objTeam : setOfUserTeams) {
			double score = objStatisticCalculatorAPI.calculateScore(leagueId, objTeam.getSetOfPlayers(), mapOfPlayerAndRoleScores);
			score += objTeam.getScore() + score;
			objTeam.setScore(score);
			mapOfUserIdAndScore.put(objTeam.getUsert().getUserId(), score);
		}
		objLeagueDao.saveUserScores(leagueId, mapOfUserIdAndScore);
		
		return mapOfUserIdAndScore;
	}
	

	@Override
	public String rulesMap(String leagueId){
		String modifiedLeagueId=leagueId;
		String returnedParentCheck=objLeagueDao.checkIfThereIsParentLeague(leagueId);
		if(!returnedParentCheck.equals("false")) {
			modifiedLeagueId=returnedParentCheck;
		}
		
		Map<String,Integer> rulesMap = objLeagueDao.rulesMap(modifiedLeagueId);
		System.out.println(rulesMap.size());
		for(String key:rulesMap.keySet()) {
			System.out.println("Key value=  "+ key + "  Pair value =  "+ rulesMap.get(key));
		}
		JSONArray allData = new JSONArray();
		for(String key:rulesMap.keySet()) {
			JSONObject eachData = new JSONObject();
            try {
                eachData.put("role",key);
                eachData.put("number", rulesMap.get(key));
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
            allData.put(eachData);
		}
		JSONObject root= new JSONObject();
		try {
			root.put("rulesMap", allData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "false";
		}
		System.out.println(allData.toString());
		return root.toString();
	}
	
	@Override
	public String gamesList(String leagueId){
		String modifiedLeagueId=leagueId;
		String returnedParentCheck=objLeagueDao.checkIfThereIsParentLeague(leagueId);
		if(!returnedParentCheck.equals("false")) {
			modifiedLeagueId=returnedParentCheck;
		}
		Set<Game> setOfGames=objLeagueDao.gamesList(modifiedLeagueId);
		TreeSet<Game> treeSetOfGames= new TreeSet<Game>();
		treeSetOfGames.addAll(setOfGames);
		System.out.println(treeSetOfGames.isEmpty());
		for (Game game :treeSetOfGames) {
			System.out.println(game.getScheduledStartTime());
		}
		//Collections.sort(listOfGames);
		//System.out.println(listOfGames.toString());
		return objSetOfGamesToJson.convertSetOfGamesToJson("listOfMatches", treeSetOfGames);
		
	}
	
	@Override
	public String checkIfUserIsAdmin(String leagueId,String userId){
		
		return objLeagueDao.checkIfUserIsAdmin(leagueId, userId);
		
}
	@Override
	public String fetchAvailableLeagueNames(String sport,String comparator) {
		// TODO Auto-generated method stub
		
		List<League> listOfLeagues = new ArrayList<League>();
		String sportId;
		if(comparator.equals("redirectText")) {
			listOfLeagues = objLeagueDao.fetchLikeLeagues(sport);
			System.out.println(sport);
			return listOfLeagues.get(0).getLeagueName();
		}
		if(comparator.equals("searchText")) {
			listOfLeagues = objLeagueDao.fetchLikeLeagues(sport);
			System.out.println(sport);
						
			String jsonString = objListToJson.listToJson("League", listOfLeagues);
			System.out.println(jsonString + "returning result " + comparator);
			
			return jsonString;
		}
		if (sport.equals("CRICKET")) {
			sportId=objSportUtilityDao.getsportID("CRICKET");
		}
		else {sportId=objSportUtilityDao.getsportID("SOCCER");}
		
		listOfLeagues = objLeagueDao.fetchAvailableLeagueNames(sportId);
		
		String jsonString = objListToJson.listToJson("League", listOfLeagues);
		System.out.println(jsonString + "returning result " + comparator);
		
		return jsonString;
		
}
}
