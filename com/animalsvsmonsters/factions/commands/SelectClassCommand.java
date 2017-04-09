package com.animalsvsmonsters.factions.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.animalsvsmonsters.factions.kit.KitManager;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.team.TeamManager;
import com.animalsvsmonsters.factions.utils.Lang;
import com.animalsvsmonsters.factions.utils.Menu.Menu;
import com.animalsvsmonsters.factions.utils.Menu.MenuItem;

import java.util.Arrays;

public class SelectClassCommand implements CommandExecutor {

    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("selectclass")){
            if(sender instanceof Player){
                final Player player = (Player) sender;
                if(args.length == 0){
                    if(AVMPlayerManager.getManager().getPlayer(player).getTeam() != TeamManager.getManager().getTeam("Limbo") && (!(player.hasPermission("avmfactions.admin.changeclass")))) return true;
                    final Menu pleaseSelectAClass = new Menu("§7Please Select a Kit", 6, null);
                    MenuItem pig = new MenuItem("§aPig Kit", new MaterialData(Material.MONSTER_EGG, (byte) 90)) {
                        @Override
                        public void onClick(Player paramPlayer) {
                            AVMPlayerManager.getManager().getPlayer(player).setKit(KitManager.getManager().getKit("Pig"));
                            AVMPlayerManager.getManager().getPlayer(player).setTeam(TeamManager.getManager().getTeam("Animals"));
                            pleaseSelectAClass.closeMenu(player);
                        }
                    };
                    pig.setDescriptions(Arrays.asList("§cAnimals Team"));
                    pleaseSelectAClass.addMenuItem(pig, 12);
                    MenuItem rabbit = new MenuItem("§aRabbit Kit", new MaterialData(Material.MONSTER_EGG, (byte) 101)) {
                        @Override
                        public void onClick(Player paramPlayer) {
                            AVMPlayerManager.getManager().getPlayer(player).setKit(KitManager.getManager().getKit("Rabbit"));
                            AVMPlayerManager.getManager().getPlayer(player).setTeam(TeamManager.getManager().getTeam("Animals"));
                            pleaseSelectAClass.closeMenu(player);
                        }
                    };
                    rabbit.setDescriptions(Arrays.asList("§cAnimals Team"));
                    pleaseSelectAClass.addMenuItem(rabbit, 13);
                    MenuItem wolf = new MenuItem("§aWolf Kit", new MaterialData(Material.MONSTER_EGG, (byte) 95)) {
                        @Override
                        public void onClick(Player paramPlayer) {
                            AVMPlayerManager.getManager().getPlayer(player).setKit(KitManager.getManager().getKit("Wolf"));
                            AVMPlayerManager.getManager().getPlayer(player).setTeam(TeamManager.getManager().getTeam("Animals"));
                            pleaseSelectAClass.closeMenu(player);
                        }
                    };
                    wolf.setDescriptions(Arrays.asList("§cAnimals Team"));
                    pleaseSelectAClass.addMenuItem(wolf, 14);

                    MenuItem enderman = new MenuItem("§aEnderman Kit", new MaterialData(Material.MONSTER_EGG, (byte) 58)) {
                        @Override
                        public void onClick(Player paramPlayer) {
                            AVMPlayerManager.getManager().getPlayer(player).setKit(KitManager.getManager().getKit("Enderman"));
                            AVMPlayerManager.getManager().getPlayer(player).setTeam(TeamManager.getManager().getTeam("Monsters"));
                            pleaseSelectAClass.closeMenu(player);
                        }
                    };
                    enderman.setDescriptions(Arrays.asList("§9Monsters Team"));
                    pleaseSelectAClass.addMenuItem(enderman, 39);
                    MenuItem skeleton = new MenuItem("§aSkeleton Kit", new MaterialData(Material.MONSTER_EGG, (byte) 51)) {
                        @Override
                        public void onClick(Player paramPlayer) {
                            AVMPlayerManager.getManager().getPlayer(player).setKit(KitManager.getManager().getKit("Skeleton"));
                            AVMPlayerManager.getManager().getPlayer(player).setTeam(TeamManager.getManager().getTeam("Monsters"));
                            pleaseSelectAClass.closeMenu(player);
                        }
                    };
                    skeleton.setDescriptions(Arrays.asList("§9Monsters Team"));
                    pleaseSelectAClass.addMenuItem(skeleton, 40);
                    MenuItem zombie = new MenuItem("§aZombie Kit", new MaterialData(Material.MONSTER_EGG, (byte) 54)) {
                        @Override
                        public void onClick(Player paramPlayer) {
                            AVMPlayerManager.getManager().getPlayer(player).setKit(KitManager.getManager().getKit("Zombie"));
                            AVMPlayerManager.getManager().getPlayer(player).setTeam(TeamManager.getManager().getTeam("Monsters"));
                            pleaseSelectAClass.closeMenu(player);
                        }
                    };
                    zombie.setDescriptions(Arrays.asList("§9Monsters Team"));
                    pleaseSelectAClass.addMenuItem(zombie, 41);

                    MenuItem red = new MenuItem("  ", new MaterialData(Material.STAINED_GLASS_PANE, (byte) 14)) {
                        @Override
                        public void onClick(Player paramPlayer) {
                            getMenu().closeMenu(player);
                        }
                    };
                    pleaseSelectAClass.addMenuItem(red, 2);
                    pleaseSelectAClass.addMenuItem(red, 3);
                    pleaseSelectAClass.addMenuItem(red, 4);
                    pleaseSelectAClass.addMenuItem(red, 5);
                    pleaseSelectAClass.addMenuItem(red, 6);
                    pleaseSelectAClass.addMenuItem(red, 11);
                    pleaseSelectAClass.addMenuItem(red, 15);
                    pleaseSelectAClass.addMenuItem(red, 20);
                    pleaseSelectAClass.addMenuItem(red, 21);
                    pleaseSelectAClass.addMenuItem(red, 22);
                    pleaseSelectAClass.addMenuItem(red, 23);
                    pleaseSelectAClass.addMenuItem(red, 24);

                    MenuItem blue = new MenuItem("  ", new MaterialData(Material.STAINED_GLASS_PANE, (byte) 11)) {
                        @Override
                        public void onClick(Player paramPlayer) {
                            getMenu().closeMenu(player);
                        }
                    };
                    pleaseSelectAClass.addMenuItem(blue, 29);
                    pleaseSelectAClass.addMenuItem(blue, 30);
                    pleaseSelectAClass.addMenuItem(blue, 31);
                    pleaseSelectAClass.addMenuItem(blue, 32);
                    pleaseSelectAClass.addMenuItem(blue, 33);
                    pleaseSelectAClass.addMenuItem(blue, 38);
                    pleaseSelectAClass.addMenuItem(blue, 42);
                    pleaseSelectAClass.addMenuItem(blue, 47);
                    pleaseSelectAClass.addMenuItem(blue, 48);
                    pleaseSelectAClass.addMenuItem(blue, 49);
                    pleaseSelectAClass.addMenuItem(blue, 50);
                    pleaseSelectAClass.addMenuItem(blue, 51);

                    MenuItem dark = new MenuItem("  ", new MaterialData(Material.STAINED_GLASS_PANE, (byte) 7)) {
                        @Override
                        public void onClick(Player paramPlayer) {
                            getMenu().closeMenu(player);
                        }
                    };
                    pleaseSelectAClass.addMenuItem(dark, 0);
                    pleaseSelectAClass.addMenuItem(dark, 8);
                    pleaseSelectAClass.addMenuItem(dark, 10);
                    pleaseSelectAClass.addMenuItem(dark, 16);
                    pleaseSelectAClass.addMenuItem(dark, 18);
                    pleaseSelectAClass.addMenuItem(dark, 26);
                    pleaseSelectAClass.addMenuItem(dark, 28);
                    pleaseSelectAClass.addMenuItem(dark, 34);
                    pleaseSelectAClass.addMenuItem(dark, 36);
                    pleaseSelectAClass.addMenuItem(dark, 44);
                    pleaseSelectAClass.addMenuItem(dark, 46);
                    pleaseSelectAClass.addMenuItem(dark, 52);

                    MenuItem light = new MenuItem("  ", new MaterialData(Material.STAINED_GLASS_PANE, (byte) 8)) {
                        @Override
                        public void onClick(Player paramPlayer) {
                            getMenu().closeMenu(player);
                        }
                    };
                    pleaseSelectAClass.addMenuItem(light, 1);
                    pleaseSelectAClass.addMenuItem(light, 7);
                    pleaseSelectAClass.addMenuItem(light, 9);
                    pleaseSelectAClass.addMenuItem(light, 17);
                    pleaseSelectAClass.addMenuItem(light, 19);
                    pleaseSelectAClass.addMenuItem(light, 25);
                    pleaseSelectAClass.addMenuItem(light, 27);
                    pleaseSelectAClass.addMenuItem(light, 35);
                    pleaseSelectAClass.addMenuItem(light, 37);
                    pleaseSelectAClass.addMenuItem(light, 43);
                    pleaseSelectAClass.addMenuItem(light, 45);
                    pleaseSelectAClass.addMenuItem(light, 53);

                    pleaseSelectAClass.openMenu(player);
                    return true;
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
