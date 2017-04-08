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

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 2/1/2016 at 12:08 AM.
 * ******************************************************************************************
 */
public class SetMonsterCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("setmonsterspawn")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                Files f = Main.getInstance().getSpawns();
                if(player.hasPermission("avmfactions.admin")){
                    if(args.length == 0){
                        f.loadFile();
                        f.set("Monsters", LocationUtil.serializeLocation(player.getLocation()));
                        f.saveFile();
                        TeamManager.getManager().getTeam("Monsters").setSpawn(player.getLocation());
                        player.sendMessage(Lang.SET_MONSTER.toString());
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
