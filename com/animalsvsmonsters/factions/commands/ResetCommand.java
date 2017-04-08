package com.animalsvsmonsters.factions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.animalsvsmonsters.factions.Main;
import com.animalsvsmonsters.factions.storage.AVMPlayer;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.utils.Lang;

import java.util.ArrayList;
import java.util.List;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 2/1/2016 at 4:41 PM.
 * ******************************************************************************************
 */
public class ResetCommand implements CommandExecutor{

    private List<AVMPlayer> resetting = new ArrayList<AVMPlayer>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("reset")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(args.length <= 1){
                    if(args.length == 0){
                        final AVMPlayer p = AVMPlayerManager.getManager().getPlayer(player);
                        if(AVMPlayerManager.getManager().hasReset(p)){
                            if(resetting.contains(p)){
                                resetting.remove(p);
                                p.reset(AVMPlayerManager.getManager().getResetKey(p));
                            }else{
                                resetting.add(p);
                                new BukkitRunnable(){
                                    @Override
                                    public void run() {
                                        if(resetting.contains(p))
                                            resetting.remove(p);
                                    }
                                }.runTaskLater(Main.getInstance(), 15*20);
                                player.sendMessage("§aYou are attempting to reset you team and kit.");
                                player.sendMessage("§c§lWARNING: §eThis will also clear your inventory and homes, and remove you from your curremt faction!");
                                player.sendMessage("§6Please type §2/reset §6again to reset yourself. Upon reset you will be teleported to spawn and prompted to select a kit.");
                            }
                            return true;
                        }
                        sender.sendMessage(Lang.NO_RESET.toString());
                        return false;
                    }
                }
                sender.sendMessage(Lang.MANY_ARGS.toString());
                return false;
            }
            sender.sendMessage(Lang.CONSOLE_SENDER.toString());
            return false;
        }
        return false;
    }
}
