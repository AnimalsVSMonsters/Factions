package com.animalsvsmonsters.factions.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.factions.callbacks.ResetQueueCallback;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.utils.Lang;
import com.animalsvsmonsters.factions.utils.database.Database;

import java.util.UUID;

public class ResetClassCommand implements CommandExecutor {

    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("resetclass")){
            if(!(sender instanceof Player)){
                if(args.length == 1){
                    String playerName = args[0];
                    if(Bukkit.getOfflinePlayer(playerName) != null){
                        UUID uuid = Bukkit.getOfflinePlayer(playerName).getUniqueId();
                        Database.get().syncUpdate("INSERT INTO reset_queue (uuid) VALUES (?)", new Object[]{uuid.toString()});
                        if(Bukkit.getOfflinePlayer(playerName).isOnline()){
                            Player player = Bukkit.getPlayer(playerName);
                            ResetQueueCallback callback = new ResetQueueCallback(uuid);
                            Database.get().syncQuery("SELECT resetkey FROM reset_queue WHERE uuid = ?", new Object[]{uuid.toString()}, callback);
                            AVMPlayerManager.getManager().addReset(AVMPlayerManager.getManager().getPlayer(uuid), (Integer) callback.result());
                            player.sendMessage("§aYou have a reset available. Use §6/reset §ato reset your team and kit.");
                            player.sendMessage("§c§lWARNING: §eThe reset is irreversible. Your inventory and homes will be cleared and you will be removed from your faction!");
                        }
                        return true;
                    }
                }
                sender.sendMessage(Lang.MANY_ARGS.toString());
                return false;
            }
            sender.sendMessage(Lang.NO_PERMS.toString());
            return false;
        }
        return false;
    }
}
