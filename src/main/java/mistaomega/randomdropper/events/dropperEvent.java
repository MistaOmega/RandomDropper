package mistaomega.randomdropper.events;

import mistaomega.randomdropper.RandomDropper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class dropperEvent implements Listener
{
    private final JavaPlugin pl = RandomDropper.getInstance();
    private final FileConfiguration config = pl.getConfig();
    private final Logger logger = Bukkit.getLogger();

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e)
    {
        Random random = new Random();
        Player p = e.getPlayer();
        World w = p.getWorld();
        Block block = e.getBlock();
        List<String> blackList = null;
        int minDropVal = 1; // if no values specified in config, drop one item
        int maxDropVal = 1;
        try
        {
            blackList = (List<String>) config.getList("itemblacklist");
            minDropVal = config.getInt("minDropValue");
            maxDropVal = config.getInt("maxDropValue");
        } catch (Exception exception)
        {
            logger.warning("Error caught in loading values from config");
        }


        e.setCancelled(true);
        block.getDrops().clear();
        block.setType(Material.AIR);

        Material[] potentialItemsArr = Material.values();
        List<Material> potentialItems = new LinkedList<>(Arrays.asList(potentialItemsArr));
        if (blackList != null)
        {
            for (String item : blackList)
            {
                potentialItems.remove(Material.valueOf(item));
            }
        }
        int randItemVal = random.nextInt(potentialItems.size());
        int randCount = random.nextInt(maxDropVal);
        if (randCount < minDropVal)
            randCount = minDropVal;

        ItemStack itemStack = new ItemStack(potentialItems.get(randItemVal));
        itemStack.setAmount(randCount);

        w.dropItem(block.getLocation(), itemStack);
    }
}
