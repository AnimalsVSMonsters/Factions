package com.animalsvsmonsters.factions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import com.animalsvsmonsters.factions.commands.LimboCommand;
import com.animalsvsmonsters.factions.commands.ResetClassCommand;
import com.animalsvsmonsters.factions.commands.ResetCommand;
import com.animalsvsmonsters.factions.commands.SelectClassCommand;
import com.animalsvsmonsters.factions.commands.SetAnimalCommand;
import com.animalsvsmonsters.factions.commands.SetLimboCommand;
import com.animalsvsmonsters.factions.commands.SetMonsterCommand;
import com.animalsvsmonsters.factions.events.ArmorUtil;
import com.animalsvsmonsters.factions.events.DeathEvent;
import com.animalsvsmonsters.factions.events.JoinEvent;
import com.animalsvsmonsters.factions.events.KitEffects;
import com.animalsvsmonsters.factions.events.MoveEvent;
import com.animalsvsmonsters.factions.events.OverwriteFactionInvite;
import com.animalsvsmonsters.factions.events.OverwriteSpawnCommand;
import com.animalsvsmonsters.factions.events.QuitEvent;
import com.animalsvsmonsters.factions.events.RespawnEvent;
import com.animalsvsmonsters.factions.kit.KitManager;
import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.team.TeamManager;
import com.animalsvsmonsters.factions.utils.ItemBuilder;
import com.animalsvsmonsters.factions.utils.Lang;
import com.animalsvsmonsters.factions.utils.LocationUtil;
import com.animalsvsmonsters.factions.utils.LogUtil;
import com.animalsvsmonsters.factions.utils.ScoreboardManagement;
import com.animalsvsmonsters.factions.utils.TimeParse;
import com.animalsvsmonsters.factions.utils.Database.Database;
import com.animalsvsmonsters.factions.utils.Menu.MenuAPI;

public class Main extends JavaPlugin {

    private static Main instance;

    private List<String> commands = new ArrayList<String>();
    private List<Listener> events = new ArrayList<Listener>();

    private Recipe recipe;

    private Files langFile;
    private Files spawns;
    private Files configuration;

    @Override
    public void onEnable() {
        LogUtil.log(LogUtil.LogType.INFO, "AVM Factions is enabling...");
        long start = System.currentTimeMillis();

        instance = this;
        langFile = new Files(getDataFolder(), "lang");
        loadLang();
        langFile.loadFile();
        spawns = new Files(getDataFolder(), "spawns");
        if(!spawns.fileExists()){
            spawns.createFile();
            spawns.set("Limbo", LocationUtil.serializeLocation(new Location(Bukkit.getWorlds().get(0), 0, 65, 0)));
            spawns.set("Animals", LocationUtil.serializeLocation(new Location(Bukkit.getWorlds().get(0), 0, 65, 0)));
            spawns.set("Monsters", LocationUtil.serializeLocation(new Location(Bukkit.getWorlds().get(0), 0, 65, 0)));
            spawns.saveFile();
        }
        spawns.loadFile();
        configuration = new Files(getDataFolder(), "config");
        if(!configuration.fileExists()){
            configuration.createFile();
            configuration.set("spawn_delay", 3);
            configuration.set("enderpearl_durability", 250);
            configuration.set("enderpearl_recipe.5.item", 368);
            configuration.set("enderpearl_recipe.5.meta", 368);
            configuration.saveFile();
        }
        configuration.loadFile();
        ScoreboardManagement.getManager().setScoreboardManager(Bukkit.getScoreboardManager());
        createEnderpearlRecipe();
        registerCommands();
        registerEvents();
        Database.get();
        KitManager.getManager().registerKits();
        TeamManager.getManager().registerTeams();

        long end = System.currentTimeMillis() - start;
        LogUtil.log(LogUtil.LogType.INFO, "AVM Factions has enabled in " + TimeParse.parse(end));
    }

    @Override
    public void onDisable() {
        for(Team team : TeamManager.getManager().getTeams().values()){
            Database.get().syncUpdate("UPDATE team_info SET kills = ? AND deaths = ? WHERE team = ?", new Object[]{team.getKills(), team.getDeaths(), team.getTeamName()});
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public Files getLangFile() {
        return langFile;
    }

    public Files getSpawns() {
        return spawns;
    }

    public Files getConfiguration() {
        return configuration;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    private void registerCommands(){
        registerCommand("setlimbo", new SetLimboCommand());
        registerCommand("setanimalspawn", new SetAnimalCommand());
        registerCommand("setmonsterspawn", new SetMonsterCommand());
        registerCommand("reset", new ResetCommand());
        registerCommand("resetclass", new ResetClassCommand());
        registerCommand("limbo", new LimboCommand());
        registerCommand("selectclass", new SelectClassCommand());
        LogUtil.log(LogUtil.LogType.INFO, "A total of " + commands.size() + " commands were registered!");
    }

    private void registerEvents() {
        registerEvent(new JoinEvent());
        registerEvent(new OverwriteSpawnCommand());
        registerEvent(new DeathEvent());
        registerEvent(new KitEffects());
        registerEvent(new OverwriteFactionInvite());
        registerEvent(new QuitEvent());
        registerEvent(new MenuAPI());
        registerEvent(new RespawnEvent());
        registerEvent(new ArmorUtil());
        registerEvent(new MoveEvent());
        LogUtil.log(LogUtil.LogType.INFO, "A total of " + events.size() + " event listeners were registered");
    }

    private void registerCommand(String command, CommandExecutor executor){
        try {
            getCommand(command).setExecutor(executor);
            commands.add(command);
        }catch (Exception e){
            LogUtil.log(LogUtil.LogType.WARN, "The command '" + command + "' was not properly registered!");
        }
    }

    private void registerEvent(Listener listener){
        try {
            Bukkit.getPluginManager().registerEvents(listener, this);
            events.add(listener);
        }catch (Exception e){
            LogUtil.log(LogUtil.LogType.WARN, "The event listener '" + listener.toString() + "' was not properly registered!");
        }
    }

    public void registerDatabaseConfig(){
        Files database = new Files(getDataFolder(), "database");
        if(database.fileExists()){
            if(database.loadFile() && database.getString("sql.serverHost").equalsIgnoreCase("0.0.0.0")){
                LogUtil.log(LogUtil.LogType.SEVERE, "The database information in database.yml. Shutting the server down.");
                Bukkit.shutdown();
            }
            return;
        }
        database.createFile();
        database.set("sql.serverHost", "0.0.0.0");
        database.set("sql.port", "3306");
        database.set("sql.databaseName", "database");
        database.set("sql.username", "databaseUsername");
        database.set("sql.password", "databasePassword");
        database.saveFile();
        LogUtil.log(LogUtil.LogType.SEVERE, "The database information in database.yml. Shutting the server down.");
        Bukkit.shutdown();
    }

    @SuppressWarnings("deprecation")
	private void loadLang() {
        OutputStream out = null;
        InputStream defLangStream = this.getResource("lang.yml");
        if (!langFile.fileExists()) {
            try {
                getDataFolder().mkdir();
                langFile.createFile();
                if (defLangStream != null) {
                    out = new FileOutputStream(langFile.getFile());
                    int read;
                    byte[] bytes = new byte[1024];

                    while ((read = defLangStream.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defLangStream);
                    Lang.setFile(defConfig);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.setEnabled(false);
            } finally {
                if (defLangStream != null) {
                    try {
                        defLangStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(langFile.getFile());
        for (Lang item : Lang.values()) {
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getDefault());
            }
        }

        Lang.setFile(conf);
        try {
            conf.save(langFile.getFile());
        } catch (IOException e) {
            LogUtil.log(LogUtil.LogType.SEVERE, "lang.yml failed to save! Please report this to MikeTheDev or @MikesCode");
            e.printStackTrace();
        }
    }

    private void createEnderpearlRecipe(){
        ItemStack enderpearl = new ItemBuilder(Material.ENDER_PEARL).name("§eEnderman's Enderpearl").lore("§dThis special enderpearl can be used by enderman", "§7" + getConfiguration().getInt("enderpearl_durability") + "/" + getConfiguration().getInt("enderpearl_durability")).build();
        ShapelessRecipe recipe = new ShapelessRecipe(enderpearl);
        recipe.addIngredient(Material.ENDER_PEARL);
        getServer().addRecipe(recipe);
    }

}
