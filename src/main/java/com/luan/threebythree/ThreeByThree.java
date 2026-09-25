package com.luan.threebythree;

import com.luan.threebythree.commands.GiveCommand;
import com.luan.threebythree.listeners.BreakListener;
import com.luan.threebythree.items.ToolManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ThreeByThree extends JavaPlugin {

    private static ThreeByThree instance;
    private ToolManager toolManager;

    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        this.toolManager = new ToolManager(this);
        this.toolManager.registerRecipes(); 

        GiveCommand giveCommand = new GiveCommand(this);
        if (getCommand("give3x3") != null) {
            getCommand("give3x3").setExecutor(giveCommand);
            getCommand("give3x3").setTabCompleter(giveCommand);
        }
        getServer().getPluginManager().registerEvents(new BreakListener(this), this);
        
        getLogger().info("ThreeByThree plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ThreeByThree plugin disabled!");
    }

    public static ThreeByThree getInstance() {
        return instance;
    }
    
    public ToolManager getToolManager() {
        return toolManager;
    }
}
