package org.stormdev.prefixes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

public class PrefixManager implements Listener {
	
	public PrefixManager(){
		List<Team> existing = new ArrayList<Team>(Bukkit.getScoreboardManager().getMainScoreboard().getTeams());
		for(Team t:existing){
			t.unregister();
		}
		Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugins()[0]);
	}
	
	public void setPrefixSuffix(Player player, String prefix, String suffix){
		try {
			String tName = player.getName();
			
			Team team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(tName);
			team.setPrefix(prefix);
			team.setSuffix(suffix);
			
			/*
			Team team2 = board.registerNewTeam(tName);
			team2.setPrefix(prefix);
			team2.setSuffix(suffix);
			*/
			
			team.addPlayer(player);
			//team2.addPlayer(player);
		} catch (IllegalArgumentException e) {
			//Player with <name>2 is online, oh well
		}
	}
	
	@EventHandler
	void disconnect(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if(!player.hasMetadata("prefix.team")){
			return;
		}
		Team t = player.getScoreboard().getPlayerTeam(player);
		if(t != null){
			t.removePlayer(player);
			t.unregister();
		}
		
		t = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
		if(t != null){
			t.removePlayer(player);
			t.unregister();
		}
	}
}
