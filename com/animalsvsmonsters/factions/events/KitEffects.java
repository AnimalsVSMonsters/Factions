package com.animalsvsmonsters.factions.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.animalsvsmonsters.factions.Main;
import com.animalsvsmonsters.factions.kit.Kit;
import com.animalsvsmonsters.factions.kit.KitManager;
import com.animalsvsmonsters.factions.storage.AVMPlayer;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.utils.ItemBuilder;
import com.animalsvsmonsters.factions.utils.LogUtil;

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
 * Created on 2/1/2016 at 7:01 PM.
 * ******************************************************************************************
 */
public class KitEffects implements Listener {

    /**
     * //RABBIT//
     */

    private List<AVMPlayer> onCooldown = new ArrayList<AVMPlayer>();

    @EventHandler
    public void onCarrorEat(PlayerItemConsumeEvent event){
        final AVMPlayer player = AVMPlayerManager.getManager().getPlayer(event.getPlayer());
        Kit kit = player.getKit();
        if(kit == KitManager.getManager().getKit("Rabbit") && event.getItem().getType() == Material.CARROT_ITEM && (!(onCooldown.contains(player)))){
            player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1));
            onCooldown.add(player);
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(onCooldown.contains(player))
                        onCooldown.remove(player);
                }
            }.runTaskLater(Main.getInstance(), 900);
        }
    }

    /**
     * //ZOMBIE AND WOLF//
     */

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            AVMPlayer player = AVMPlayerManager.getManager().getPlayer((Player) event.getDamager());
            Kit kit = player.getKit();
            if(kit == KitManager.getManager().getKit("Wolf") || kit == KitManager.getManager().getKit("Zombie")){
                event.setDamage(event.getDamage() * 1.5);
            }
        }
    }

    @EventHandler
    public void onRottenFleshConsume(PlayerItemConsumeEvent event){
        final AVMPlayer player = AVMPlayerManager.getManager().getPlayer(event.getPlayer());
        Kit kit = player.getKit();
        if((kit == KitManager.getManager().getKit("Wolf") || kit == KitManager.getManager().getKit("Zombie")) && event.getItem().getType() == Material.ROTTEN_FLESH){
            event.setCancelled(true);
            ItemStack stack = event.getItem();
            int amount = stack.getAmount();
            LogUtil.log(LogUtil.LogType.DEBUG, String.valueOf(amount));
            if(amount > 1){
                stack.setAmount(amount - 1);
            }else{
                player.getPlayer().getInventory().remove(stack);
            }
            player.getPlayer().getInventory().setItemInMainHand(stack);
            player.getPlayer().setFoodLevel(player.getPlayer().getFoodLevel() + 4);
            player.getPlayer().setSaturation(0.8F);
        } else
        {
        	player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 1200, 1));
        }
    }

    /**
     * //SKELETON AND PIG//
     */

    @EventHandler
    public void onBowEquip(PlayerItemHeldEvent event){
        final AVMPlayer player = AVMPlayerManager.getManager().getPlayer(event.getPlayer());
        Kit kit = player.getKit();
        if(player.getPlayer().getInventory().getItem(event.getNewSlot()) == null) return;
        if((kit == KitManager.getManager().getKit("Skeleton") || kit == KitManager.getManager().getKit("Pig")) && player.getPlayer().getInventory().getItem(event.getNewSlot()).getType() == Material.BOW){
            ItemStack stack = player.getPlayer().getInventory().getItem(event.getNewSlot());
            if(!stack.getEnchantments().containsKey(Enchantment.ARROW_INFINITE)){
                stack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
            }
        }else{
            if(player.getPlayer().getInventory().getItem(event.getPreviousSlot()) == null) return;
            if(player.getPlayer().getInventory().getItem(event.getPreviousSlot()).getType() == Material.BOW){
                ItemStack stack = player.getPlayer().getInventory().getItem(event.getPreviousSlot());
                if(stack.getEnchantments().containsKey(Enchantment.ARROW_INFINITE) && stack.getEnchantmentLevel(Enchantment.ARROW_INFINITE) == 1){
                    stack.removeEnchantment(Enchantment.ARROW_INFINITE);
                }
            }
        }
    }

    @EventHandler
    public void onDropBow(PlayerDropItemEvent event){
        final AVMPlayer player = AVMPlayerManager.getManager().getPlayer(event.getPlayer());
        Kit kit = player.getKit();
        if((kit == KitManager.getManager().getKit("Skeleton") || kit == KitManager.getManager().getKit("Pig")) && event.getItemDrop().getItemStack().getType() == Material.BOW){
            ItemStack stack = event.getItemDrop().getItemStack();
            if(stack.getEnchantments().containsKey(Enchantment.ARROW_INFINITE) && stack.getEnchantmentLevel(Enchantment.ARROW_INFINITE) == 133){
                stack.removeEnchantment(Enchantment.ARROW_INFINITE);
                event.getItemDrop().setItemStack(stack);
            }
        }
    }

    /**
     * //ENDERMAN//
     */

    @EventHandler
    public void onCraft(InventoryClickEvent event){
        if(event.getInventory().getType() == InventoryType.WORKBENCH){
            if(event.getCurrentItem() == null) return;
            if(KitManager.getManager().getKit((Player) event.getWhoClicked()) != KitManager.getManager().getKit("Enderman") && event.getCurrentItem().getType() == Material.ENDER_PEARL && event.getSlotType() == InventoryType.SlotType.RESULT)
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnderpearl(EntityDamageByEntityEvent event){
        if(event.getDamager().getType() == EntityType.ENDER_PEARL){
            if(event.getEntity() instanceof Player){
                Kit kit = KitManager.getManager().getKit((Player) event.getEntity());
                if(kit == KitManager.getManager().getKit("Enderman")) event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEnderPearlThrow(ProjectileLaunchEvent event){
        if(event.getEntity() instanceof EnderPearl){
            EnderPearl pearl = (EnderPearl) event.getEntity();
            assert pearl.getShooter() instanceof Player;
            final AVMPlayer player = AVMPlayerManager.getManager().getPlayer((Player) pearl.getShooter());
            Kit kit = player.getKit();
            if(player.getPlayer().getInventory().getItemInMainHand().getType() == Material.ENDER_PEARL && kit == KitManager.getManager().getKit("Enderman")){
                if(player.getPlayer().getInventory().getItemInMainHand().hasItemMeta() && player.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()){
                    for(String string : player.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore()){
                        if(string.contains("/" + Main.getInstance().getConfiguration().getInt("enderpearl_durability"))){
                            String[] atts = string.split("/");
                            int durability = Integer.parseInt(atts[0].replace("§7", ""));
                            ItemStack stack = player.getPlayer().getInventory().getItemInMainHand();
                            if(durability > 1){
                                durability -= 1;
                                final int finalDurability = durability;
                                new BukkitRunnable(){
                                    @Override
                                    public void run() {
                                        player.getPlayer().getInventory().setItemInMainHand(new ItemBuilder(Material.ENDER_PEARL).name("§eEnderman's Enderpearl").lore("§dThis special enderpearl can be used by enderman", "§7" + finalDurability + "/" + Main.getInstance().getConfiguration().getInt("enderpearl_durability")).build());
                                    }
                                }.runTaskLater(Main.getInstance(), 3);
                            }else{
                                player.getPlayer().getInventory().remove(stack);
                                player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1F, 1F);
                            }
                        }
                    }
                }
            }
        }
    }
}
