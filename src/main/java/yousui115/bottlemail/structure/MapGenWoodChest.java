package yousui115.bottlemail.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

/**
 * MapGenScatteredFeatureを参考に作成
 *
 */
public class MapGenWoodChest extends MapGenStructure
{
    private static final List<Biome> BIOMELIST = Arrays.<Biome>asList(new Biome[] {Biomes.FOREST});
//    private final List<Biome.SpawnListEntry> scatteredFeatureSpawnList;
    /** the maximum distance between scattered features */
    private int maxChunkDistanceBetweenF;
    /** the minimum distance between scattered features */
//    private final int minChunkDistanceBetweenF;

    public MapGenWoodChest()
    {
//        this.scatteredFeatureSpawnList = Lists.<Biome.SpawnListEntry>newArrayList();
        this.maxChunkDistanceBetweenF = 32;
//        this.minChunkDistanceBetweenF = 8;
//        this.scatteredFeatureSpawnList.add(new Biome.SpawnListEntry(EntityWitch.class, 1, 1, 1));
    }

    public MapGenWoodChest(Map<String, String> p_i2061_1_)
    {
        this();

        for (Entry<String, String> entry : p_i2061_1_.entrySet())
        {
            if (((String)entry.getKey()).equals("distance"))
            {
                this.maxChunkDistanceBetweenF = MathHelper.getInt((String)entry.getValue(), this.maxChunkDistanceBetweenF, 9);
            }
        }
    }

    @Override
    public String getStructureName()
    {
        return "WoodChest";
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkXIn, int chunkZIn)
    {
//        int chunkX = chunkXIn;
//        int chunkZ = chunkZIn;
//
//        if (chunkXIn < 0)
//        {
//            chunkXIn -= this.maxChunkDistanceBetweenF - 1;
//        }
//
//        if (chunkZIn < 0)
//        {
//            chunkZIn -= this.maxChunkDistanceBetweenF - 1;
//        }
//
//        int x = chunkXIn / this.maxChunkDistanceBetweenF;
//        int z = chunkZIn / this.maxChunkDistanceBetweenF;
//        Random random = this.world.setRandomSeed(x, z, 14357617);
//        x = x * this.maxChunkDistanceBetweenF;
//        z = z * this.maxChunkDistanceBetweenF;
//        x = x + random.nextInt(this.maxChunkDistanceBetweenF - 8);
//        z = z + random.nextInt(this.maxChunkDistanceBetweenF - 8);
//
//        if (chunkX == x && chunkZ == z)
//        {
//            //■チャンクの中央位置
//            BlockPos pos = new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8);
//
//            //■バイオームの調査
//            Biome biome = this.world.getBiomeProvider().getBiome(pos);
//            if (biome == null) { return false; }
//
//            for (Biome biome1 : BIOMELIST)
//            {
//                if (biome == biome1)
//                {
//                    //DEBUG
//                    //System.out.println("x = " + pos.getX() + " : z = " + pos.getZ());
//                    return true;
//                }
//            }
//        }
//
//        return false;
        return true;
    }

    @Override
    public BlockPos getClosestStrongholdPos(World worldIn, BlockPos posIn, boolean findUnexplored)
    {
        this.world = worldIn;

        int cntMaxTrial = 100; //試行回数

        return findNearestStructurePosBySpacing(worldIn, this, posIn, this.maxChunkDistanceBetweenF, 8, 14357617, false, cntMaxTrial, findUnexplored);
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new MapGenWoodChest.Start(this.world, this.rand, chunkX, chunkZ);
    }

    public static class Start extends StructureStart
    {
        public Start()
        {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ)
        {
            this(worldIn, random, chunkX, chunkZ, worldIn.getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8)));
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ, Biome biomeIn)
        {
            super(chunkX, chunkZ);

            if (biomeIn == Biomes.FOREST)
            {
                ComponentTreasurePieces.WoodChest woodchest = new ComponentTreasurePieces.WoodChest(random, chunkX * 16, chunkZ * 16);
                this.components.add(woodchest);
            }

            this.updateBoundingBox();
        }
    }
}
