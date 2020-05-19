package mistaomega.randomdropper;

import mistaomega.randomdropper.events.dropperEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomDropper extends JavaPlugin
{

    private static RandomDropper instance;

    public RandomDropper()
    {
        if (RandomDropper.instance != null)
        {
            throw new Error("RandomDropper has already been initialised");
        }
        RandomDropper.instance = this;
    }

    public static RandomDropper getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        getLogger().info("RandomDropper is live");
        this.getServer().getPluginManager().registerEvents(new dropperEvent(), this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }
}
