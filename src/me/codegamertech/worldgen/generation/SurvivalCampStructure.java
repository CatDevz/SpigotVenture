package me.codegamertech.worldgen.generation;

import com.github.shynixn.structureblocklib.bukkit.api.StructureBlockApi;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.PersistenceStructureService;
import com.github.shynixn.structureblocklib.bukkit.api.persistence.entity.StructureSaveConfiguration;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Random;

public class SurvivalCampStructure {

    private static PersistenceStructureService service = StructureBlockApi.INSTANCE.getStructurePersistenceService();
    public static final StructureSaveConfiguration campsiteOak = service.createSaveConfiguration("minecraft", "campsite_oak", "world");
    public static final StructureSaveConfiguration campsiteDarkOak = service.createSaveConfiguration("minecraft", "campsite_darkoak", "world");
    public static final StructureSaveConfiguration campsiteAcacia = service.createSaveConfiguration("minecraft", "campsite_acacia", "world");
    public static final StructureSaveConfiguration campsiteDesert = service.createSaveConfiguration("minecraft", "campsite_desert", "world");

    public static HashMap<Biome, StructureSaveConfiguration> biomeCampsite = new HashMap<Biome, StructureSaveConfiguration>();

    public SurvivalCampStructure(World world, Random random, Chunk chunk) {
        System.out.println("CREATIONG");

        int centerX = (chunk.getX() << 4) + random.nextInt(16);
        int centerZ = (chunk.getZ() << 4) + random.nextInt(16);
        int centerY = world.getHighestBlockYAt(centerX, centerZ);
        Block check = world.getBlockAt(centerX, centerY, centerZ);
        Block origin = world.getBlockAt(centerX, centerY - 1, centerZ);
        Biome biome = world.getBiome(check.getLocation().getBlockX(), check.getLocation().getBlockY(), check.getLocation().getBlockZ());

        for(int x = check.getLocation().getBlockX(); x <= check.getLocation().getBlockX() + 10; x++) {
            for(int z = check.getLocation().getBlockZ(); z <= check.getLocation().getBlockZ() + 10; z++) {
                Block b = world.getBlockAt(x, centerY, z);
                if(b.getType() == Material.AIR || b.getType() == Material.WATER) {
                    System.out.println("Invalid Location");
                    return;
                }
            }
        }

        if(!service.load(biomeCampsite.get(origin.getBiome()), origin.getLocation()))
            System.out.println("Failed to create structure.");
    }

}
