package me.codegamertech.worldgen.generation;

import com.github.shynixn.structureblocklib.bukkit.api.StructureBlockApi;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.PersistenceStructureService;
import com.github.shynixn.structureblocklib.bukkit.api.persistence.entity.StructureSaveConfiguration;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

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
        int centerX = (chunk.getX() << 4) + random.nextInt(16);
        int centerZ = (chunk.getZ() << 4) + random.nextInt(16);
        int centerY = world.getHighestBlockYAt(centerX, centerZ);
        Block check = world.getBlockAt(centerX, centerY, centerZ);
        Block origin = world.getBlockAt(centerX, centerY - 1, centerZ);

        if(!biomeCampsite.containsKey(check.getBiome()))
            return;

        if(!isValid(check.getLocation()))
            return;

        Location oreStruct = hasOreStructure(check.getLocation());
        if(oreStruct == null)
            return;

        System.out.println("SUCCESS: valid location " + check.getLocation().toString());
        boolean load = service.load(biomeCampsite.get(origin.getBiome()), origin.getLocation());

        BlockState[] tileEntities = chunk.getTileEntities();
        for(BlockState state : tileEntities) {
            if(state.getType() == Material.CHEST) {
                /*Chest chest = (Chest) state.getBlock();
                chest.getBlockInventory().addItem(new ItemStack(Material.MAP));
                chest.update(true);*/
                System.out.println(state.getLocation());
                Chest chest = (Chest) world.getBlockAt(state.getLocation()).getState();
                chest.getInventory().addItem(new ItemStack(Material.MAP));
                chest.update(true);
            }
        }

    }

    private boolean isValid(Location location) {
        int centerY = location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ());

        // Checking to make sure all of the ground area is air
        for (int x = location.getBlockX(); x <= location.getBlockX() + 10; x++) {
            for (int z = location.getBlockZ(); z <= location.getBlockZ() + 10; z++) {
                Block b = location.getWorld().getBlockAt(x, centerY, z);
                if (b.getType() == Material.AIR || b.getType() == Material.WATER) {
                    System.out.println("FAILED AT: grounded check");
                    return false;
                }
            }
        }

        // Checking to make sure location is relatively flat
        Location location1 = location.add(0,1,0);
        for (int x = location.getBlockX(); x <= location.getBlockX() + 10; x++) {
            for (int z = location.getBlockZ(); z <= location.getBlockZ() + 10; z++) {
                Block b = location.getWorld().getBlockAt(x, centerY + 2, z);
                if (b.getType() != Material.AIR && b.getType() != Material.GRASS && b.getType() != Material.TALL_GRASS && b.getType() != Material.CACTUS && b.getType() != Material.DEAD_BUSH) {
                    System.out.println("FAILED AT: flat check");
                    return false;
                }
            }
        }

        return true;
    }

    private Location hasOreStructure(Location location) {
        if(HugeOreStructure.hugeOreStructures.isEmpty()) {
            System.out.println("FAILED AT: no ore struct generated");
            return null;
        }

        Location closest = null;
        for(Location loc : HugeOreStructure.hugeOreStructures) {
            if(closest == null)
                closest = loc;

            int disX = Math.abs(loc.getBlockX() - location.getBlockX());
            int disZ = Math.abs(loc.getBlockZ() - location.getBlockZ());
            int disXC = Math.abs(closest.getBlockX() - location.getBlockX());
            int disZC = Math.abs(closest.getBlockZ() - location.getBlockZ());
            if(disX < disXC && disZ < disZC)
                closest = loc;
        }

        int disX = Math.abs(closest.getBlockX() - location.getBlockX());
        int disZ = Math.abs(closest.getBlockZ() - location.getBlockZ());
        if(disX > 2000 && disZ > 2000) {
            System.out.println("FAILED AT: ore struct to far");
            return null;
        } else {
            HugeOreStructure.hugeOreStructuresUSED.add(closest);
            HugeOreStructure.hugeOreStructures.remove(closest);
            return closest;
        }
    }

}
