package com.animalsvsmonsters.factions.utils;

import org.bukkit.scoreboard.*;

import com.animalsvsmonsters.factions.storage.AVMPlayer;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.team.TeamManager;

import java.util.HashMap;

public class ScoreboardManagement {

    private static ScoreboardManagement manager = new ScoreboardManagement();
    private ScoreboardManager scoreboardManager;

    private HashMap<AVMPlayer, Scoreboard> boards = new HashMap<AVMPlayer, Scoreboard>();

    public void updateAll(){
        for(AVMPlayer player : AVMPlayerManager.getManager().getPlayerHashMap().values()){
        	Scoreboard sb = scoreboardManager.getNewScoreboard();
            boards.put(player, sb);

            Team team = sb.registerNewTeam("Current Disguise");
            team.setPrefix("§2§l");

            Team monsters = sb.registerNewTeam("Monsters");
            monsters.setPrefix("§9§lTeam ");

            Team animals = sb.registerNewTeam("Animals");
            animals.setPrefix("§c§lTeam ");

            Objective o = sb.getObjective(DisplaySlot.SIDEBAR);
            if(o == null){
                o = sb.registerNewObjective("test", "dummy");
            }
            o.setDisplayName("§6§lAVM MC");
            o.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score score1 = o.getScore(player.getKit().getName());
            score1.setScore(1);

            Score score2 = o.getScore("------------");
            score2.setScore(2);

            Score score3 = o.getScore(team.getPrefix() + team.getDisplayName());
            score3.setScore(3);

            Score score4 = o.getScore("    ");
            score4.setScore(4);

            Score score5 = o.getScore("§2§lKills: §r" + TeamManager.getManager().getTeam("Monsters").getKills() + " ");
            score5.setScore(5);

            Score score6 = o.getScore("------------  ");
            score6.setScore(6);

            Score score7 = o.getScore(monsters.getPrefix() + monsters.getDisplayName());
            score7.setScore(7);

            Score score8 = o.getScore("        ");
            score8.setScore(8);

            Score score9 = o.getScore("§2§lKills: §r" + TeamManager.getManager().getTeam("Animals").getKills());
            score9.setScore(9);

            Score score10 = o.getScore("------------ ");
            score10.setScore(10);

            Score score11 = o.getScore(animals.getPrefix() + animals.getDisplayName());
            score11.setScore(11);

            Score score12 = o.getScore("            ");
            score12.setScore(12);

            player.getPlayer().setScoreboard(sb);
        }
    }

    public static ScoreboardManagement getManager() {
        return manager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public void setScoreboardManager(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }
}
