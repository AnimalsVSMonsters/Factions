package com.animalsvsmonsters.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.factions.Files;
import com.animalsvsmonsters.factions.Main;
import com.animalsvsmonsters.factions.team.TeamManager;
import com.animalsvsmonsters.factions.utils.Lang;
import com.animalsvsmonsters.factions.utils.LocationUtil;

public class SetAnimalCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("setanimalspawn")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                Files f = Main.getInstance().getSpawns();
                if(player.hasPermission("avmfactions.admin")){
                    if(args.length == 0){
                        f.loadFile();
                        f.set("Animals", LocationUtil.serializeLocation(player.getLocation()));
                        f.saveFile();
                        TeamManager.getManager().getTeam("Animals").setSpawn(player.getLocation());
                        player.sendMessage(Lang.SET_ANIMAL.toString());
                        return true;
                    }
                    player.sendMessage(Lang.MANY_ARGS.toString());
                    return false;
                }
                player.sendMessage(Lang.NO_PERMS.toString());
                return false;
            }
            sender.sendMessage(Lang.CONSOLE_SENDER.toString());
            return false;
        }
        return false;
    }
}
