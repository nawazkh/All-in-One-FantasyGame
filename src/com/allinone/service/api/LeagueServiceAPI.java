/**
 * 
 */
package com.allinone.service.api;

import java.util.List;
import java.util.Set;

import com.allinone.pojos.League;
import com.allinone.pojos.Player;

/**
 * @author rahul
 *
 */
public interface LeagueServiceAPI {

	public String fetchLeagues(String sport,String Comparator);

	
	
	public boolean hasUserJoinedLeague(String userId, String leagueId);

	public String fetchUserTeams(String leagueId);

	public String userTeamSet(String leagueId, String userId);

	public List<Player> playerList(String leagueId, String userId);
	
	
}
