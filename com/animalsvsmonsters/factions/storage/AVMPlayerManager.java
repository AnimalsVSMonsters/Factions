package com.animalsvsmonsters.factions.storage;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AVMPlayerManager {


    private HashMap<Player, AVMPlayer> playerHashMap = new HashMap<Player, AVMPlayer>();
    private HashMap<AVMPlayer, Integer> resetKeys = new HashMap<AVMPlayer, Integer>();

    public AVMPlayer getPlayer(Player player){
        if(playerHashMap.containsKey(player)){
            return playerHashMap.get(player);
        }
        return null;
    }

    public AVMPlayer getPlayer(String playerName){
        if(playerHashMap.containsKey(Bukkit.getPlayer(playerName))){
            return playerHashMap.get(Bukkit.getPlayer(playerName));
        }
        return null;
    }

    public AVMPlayer getPlayer(UUID uuid){
        if(playerHashMap.containsKey(Bukkit.getPlayer(uuid))){
            return playerHashMap.get(Bukkit.getPlayer(uuid));
        }
        return null;
    }

    public void removePlayer(Player player){
        getPlayer(player).getHologram().clearLines();
        if(playerHashMap.containsKey(player)){
            playerHashMap.remove(player);
        }
    }

    public void removePlayer(String playerName){
        if(playerHashMap.containsKey(Bukkit.getPlayer(playerName))){
            playerHashMap.remove(Bukkit.getPlayer(playerName));
        }
    }

    public void removePlayer(UUID uuid){
        if(playerHashMap.containsKey(Bukkit.getPlayer(uuid))){
            playerHashMap.remove(Bukkit.getPlayer(uuid));
        }
    }

    public void addReset(AVMPlayer player, int key){
        resetKeys.put(player, key);
    }

    public boolean hasReset(AVMPlayer player){
        return resetKeys.containsKey(player);
    }

    public int getResetKey(AVMPlayer player){
        if(hasReset(player))
            return resetKeys.get(player);
        return -1;
    }

    public void removeReset(AVMPlayer player){
        if(hasReset(player)){
            resetKeys.remove(player);
        }
    }

    public HashMap<Player, AVMPlayer> getPlayerHashMap() {
        return playerHashMap;
    }

    private static AVMPlayerManager manager = new AVMPlayerManager();

    public static AVMPlayerManager getManager() {
        return manager;
    }
}
