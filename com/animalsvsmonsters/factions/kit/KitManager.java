package com.animalsvsmonsters.factions.kit;

import org.bukkit.entity.Player;

import com.animalsvsmonsters.factions.kit.AnimalKits.Pig;
import com.animalsvsmonsters.factions.kit.AnimalKits.Rabbit;
import com.animalsvsmonsters.factions.kit.AnimalKits.Wolf;
import com.animalsvsmonsters.factions.kit.MonsterKits.Enderman;
import com.animalsvsmonsters.factions.kit.MonsterKits.Skeleton;
import com.animalsvsmonsters.factions.kit.MonsterKits.Zombie;
import com.animalsvsmonsters.factions.storage.AVMPlayer;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.team.Team;

import java.util.HashMap;

public class KitManager {

    private HashMap<String, Kit> kits = new HashMap<String, Kit>();

    public HashMap<String, Kit> getKits() {
        return kits;
    }

    public Kit getKit(String teamName){
        for(Kit kit : kits.values()){
            if(kit.getName().equalsIgnoreCase(teamName))
                return kit;
        }
        return null;
    }

    public Kit getKit(Player player){
        for(AVMPlayer avmPlayer : AVMPlayerManager.getManager().getPlayerHashMap().values()){
            if(avmPlayer.getPlayer() == player)
                return avmPlayer.getKit();
        }
        return null;
    }

    public Kit getKit(Team team, int number){
        for(Kit kit : kits.values()){
            if(kit.getTeam() == team && kit.getKitNumber() == number)
                return kit;
        }
        return null;
    }

    private void createKit(Kit kit){
        kits.put(kit.getName(), kit);
    }

    public void registerKit(Kit kit){
        createKit(kit);
    }

    public void registerKits(){
        createKit(new Default());
        createKit(new Pig());
        createKit(new Rabbit());
        createKit(new Wolf());
        createKit(new Enderman());
        createKit(new Skeleton());
        createKit(new Zombie());
    }

    private static KitManager manager = new KitManager();

    public static KitManager getManager() {
        return manager;
    }
}
