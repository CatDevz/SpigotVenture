package me.codegamertech.worldgen.generation;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Random;

public class HugeOreStructure {

    public static ArrayList<Location> hugeOreStructures = new ArrayList<Location>();

    public HugeOreStructure(World world, Random random, Chunk chunk) {

        int centerX = (chunk.getX() << 4) + random.nextInt(16);
        int centerZ = (chunk.getZ() << 4) + random.nextInt(16);
        int centerY;
        Block top = null;

        Boolean bool = false;
        int attempts = 0; // To make sure not to overload the server, will give up if attempts are too high
        while (!bool) {
            centerY = (int) Math.floor(Math.random() * 80);
            top = world.getBlockAt(centerX, centerY, centerZ);
            if(top.getType() == Material.STONE) bool = true;
            if(attempts > 40) return;
            attempts++;
        }

        for (int x = -4; x < 4; x++) {
            for (int y = -4; y < 4; y++) {
                for (int z = -4; z < 4; z++) {
                    world.getBlockAt(
                            top.getLocation().getBlockX() + x, top.getLocation().getBlockY() + y, top.getLocation().getBlockZ() + z
                    ).setType(Material.EMERALD_BLOCK);
                }
            }
        }

        hugeOreStructures.add(top.getLocation());
    }

}
