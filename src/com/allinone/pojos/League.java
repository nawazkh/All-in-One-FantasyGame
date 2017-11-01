/**
 * 
 */
package com.allinone.pojos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author rahul
 *
 */
public class League implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private int numSubstitutesAllowed;
	private String leagueName;
	private User leagueOwner;
	private Sport sport;
	private Set<Player> setOfPlayers = new HashSet<Player>();
	private Set<Team> setOfTeams = new HashSet<Team>();
	private List<Game> listOfGames;
	private Set<UserTeam> setOfUserTeams;
	private boolean isSystemLeague;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public Sport getSport() {
		return sport;
	}
	public void setSport(Sport sport) {
		this.sport = sport;
	}
	public Set<Team> getSetOfTeams() {
		return setOfTeams;
	}
	public void setSetOfTeams(Set<Team> setOfTeams) {
		this.setOfTeams = setOfTeams;
	}
	public List<Game> getListOfGames() {
		return listOfGames;
	}
	public void setListOfGames(List<Game> listOfGames) {
		this.listOfGames = listOfGames;
	}
	public boolean isSystemLeague() {
		return isSystemLeague;
	}
	public void setSystemLeague(boolean isSystemLeague) {
		this.isSystemLeague = isSystemLeague;
	}

	public Set<Player> getSetOfPlayers() {
		return setOfPlayers;
	}
	public void setSetOfPlayers(Set<Player> setOfPlayers) {
		this.setOfPlayers = setOfPlayers;
	}
	public Set<UserTeam> getSetOfUserTeams() {
		return setOfUserTeams;
	}
	public void setSetOfUserTeams(Set<UserTeam> setOfUserTeams) {
		this.setOfUserTeams = setOfUserTeams;
	}
	
	@Override
	public String toString() {
		return "League [id=" + id + ", leagueName=" + leagueName + ", setOfTeams=" + setOfTeams + ", isSystemLeague="
				+ isSystemLeague + "]";
	}
	public User getLeagueOwner() {
		return leagueOwner;
	}
	public void setLeagueOwner(User leagueOwner) {
		this.leagueOwner = leagueOwner;
	}
	public int getNumSubstitutesAllowed() {
		return numSubstitutesAllowed;
	}
	public void setNumSubstitutesAllowed(int numSubstitutesAllowed) {
		this.numSubstitutesAllowed = numSubstitutesAllowed;
	}
	

}
