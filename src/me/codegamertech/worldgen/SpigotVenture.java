package me.codegamertech.worldgen;

import me.codegamertech.worldgen.generation.GenManager;
import me.codegamertech.worldgen.generation.SurvivalCampStructure;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getPluginManager;

public class SpigotVenture extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        SurvivalCampStructure.biomeCampsite.put(Biome.PLAINS, SurvivalCampStructure.campsiteOak);
        SurvivalCampStructure.biomeCampsite.put(Biome.SUNFLOWER_PLAINS, SurvivalCampStructure.campsiteOak);
        SurvivalCampStructure.biomeCampsite.put(Biome.FOREST, SurvivalCampStructure.campsiteOak);
        SurvivalCampStructure.biomeCampsite.put(Biome.BIRCH_FOREST, SurvivalCampStructure.campsiteOak);
        SurvivalCampStructure.biomeCampsite.put(Biome.SAVANNA, SurvivalCampStructure.campsiteAcacia);
        SurvivalCampStructure.biomeCampsite.put(Biome.SAVANNA_PLATEAU, SurvivalCampStructure.campsiteAcacia);
        SurvivalCampStructure.biomeCampsite.put(Biome.DARK_FOREST, SurvivalCampStructure.campsiteDarkOak);
        SurvivalCampStructure.biomeCampsite.put(Biome.DESERT, SurvivalCampStructure.campsiteDesert);
        SurvivalCampStructure.biomeCampsite.put(Biome.BEACH, SurvivalCampStructure.campsiteDesert);
        System.out.println(SurvivalCampStructure.biomeCampsite);

        getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void worldLoadEvent(WorldLoadEvent event) {
        World world = event.getWorld();
        if (world != Bukkit.getWorld("world")) return;
        world.getPopulators().add(new GenManager());
    }

}
