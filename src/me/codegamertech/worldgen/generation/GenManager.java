package me.codegamertech.worldgen.generation;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class GenManager extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if(random.nextInt(200) < 40) {
            new SurvivalCampStructure(world, random, chunk);
        }

        if(random.nextInt(200) < 20) {
            new HugeOreStructure(world, random, chunk);
        }
    }

}
