package me.codegamertech.worldgen.generation;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import java.util.Random;

public class GenManager implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void populate(ChunkPopulateEvent event) {
        Random random = new Random();

        if(random.nextInt(400) <= 1) {
            new HugeOreStructure(event.getWorld(), random, event.getChunk());
        }

        if(random.nextInt(200) < 50) {
            new SurvivalCampStructure(event.getWorld(), random, event.getChunk());
        }
    }

}
