package yousui115.bottlemail.structure;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import yousui115.bottlemail.BottleMail;

/**
 * ■ComponentScatteredFeaturePieces を参考に作成
 *
 */
public class ComponentTreasurePieces
{
    public static void registerTreasurePieces()
    {
        MapGenStructureIO.registerStructureComponent(ComponentTreasurePieces.WoodChest.class, "TrWC");
    }

    /**
     *
     * ■■■■■ Feature ■■■■■
     *
     */
    abstract static class Feature extends StructureComponent
    {
        /** The size of the bounding box for this feature in the X axis */
        protected int scatteredFeatureSizeX;
        /** The size of the bounding box for this feature in the Y axis */
        protected int scatteredFeatureSizeY;
        /** The size of the bounding box for this feature in the Z axis */
        protected int scatteredFeatureSizeZ;
        protected int horizontalPos = -1;

        public Feature()
        {
        }

        protected Feature(Random rand, int x, int y, int z, int sizeX, int sizeY, int sizeZ)
        {
            super(0);
            this.scatteredFeatureSizeX = sizeX;
            this.scatteredFeatureSizeY = sizeY;
            this.scatteredFeatureSizeZ = sizeZ;
//            this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));
            this.setCoordBaseMode(EnumFacing.NORTH);

            if (this.getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
            {
                this.boundingBox = new StructureBoundingBox(x, y, z, x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1);
            }
            else
            {
                this.boundingBox = new StructureBoundingBox(x, y, z, x + sizeZ - 1, y + sizeY - 1, z + sizeX - 1);
            }
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            tagCompound.setInteger("Width", this.scatteredFeatureSizeX);
            tagCompound.setInteger("Height", this.scatteredFeatureSizeY);
            tagCompound.setInteger("Depth", this.scatteredFeatureSizeZ);
            tagCompound.setInteger("HPos", this.horizontalPos);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            this.scatteredFeatureSizeX = tagCompound.getInteger("Width");
            this.scatteredFeatureSizeY = tagCompound.getInteger("Height");
            this.scatteredFeatureSizeZ = tagCompound.getInteger("Depth");
            this.horizontalPos = tagCompound.getInteger("HPos");
        }

        /**
         * Calculates and offsets this structure boundingbox to average ground level
         */
        protected boolean offsetToAverageGroundLevel(World worldIn, StructureBoundingBox structurebb, int yOffset)
        {
            if (this.horizontalPos >= 0)
            {
                return true;
            }
            else
            {
                int i = 0;
                int j = 0;
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int z = this.boundingBox.minZ; z <= this.boundingBox.maxZ; ++z)
                {
                    for (int x = this.boundingBox.minX; x <= this.boundingBox.maxX; ++x)
                    {
                        blockpos$mutableblockpos.setPos(x, 64, z);

                        if (structurebb.isVecInside(blockpos$mutableblockpos))
                        {
                            i += Math.max(worldIn.getTopSolidOrLiquidBlock(blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel());
                            ++j;
                        }
                    }
                }

                if (j == 0)
                {
                    return false;
                }
                else
                {
                    this.horizontalPos = i / j;
                    this.boundingBox.offset(0, this.horizontalPos - this.boundingBox.minY + yOffset, 0);
                    return true;
                }
            }
        }
    }

    /**
     *
     * ■■■■■ WoodChest ■■■■■
     *
     */
    public static class WoodChest extends ComponentTreasurePieces.Feature
    {
        private boolean placedHiddenChest;

        public WoodChest(){}

        public WoodChest(Random rand, int x, int z)
        {
            super(rand, x, 64, z, 1, 8, 1);
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        @Override
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("placedHiddenChest", this.placedHiddenChest);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        @Override
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.placedHiddenChest = tagCompound.getBoolean("placedHiddenChest");
        }

        @Override
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (!this.offsetToAverageGroundLevel(worldIn, structureBoundingBoxIn, -1))
            {
                return false;
            }
            else
            {
                //■宝箱
                if (!this.placedHiddenChest)
                {
                    this.placedHiddenChest = this.generateChest(worldIn,
                                                                structureBoundingBoxIn,
                                                                randomIn,
                                                                0, -2, 0,
//                                                                LootTableList.CHESTS_NETHER_BRIDGE);
                                                                BottleMail.WOOD_CHEST);
                }

                //■土
                for (int x = 2; x <= 4; x++)
                {
                    for (int z = 0; z <= 2; z++)
                    {
                        this.replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), x, -1, z, structureBoundingBoxIn);
                    }
                }
                //■葉上
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 5, 2, Blocks.LEAVES.getDefaultState(),Blocks.LEAVES.getDefaultState(), false);
                //■葉下
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, -1, 5, 4, 3, Blocks.LEAVES.getDefaultState(),Blocks.LEAVES.getDefaultState(), false);
                //■木
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 4, 1, Blocks.LOG.getDefaultState(),Blocks.LOG.getDefaultState(), false);

                //■土
                for (int x = -1; x <= 1; x++)
                {
                    for (int z = -1; z <= 1; z++)
                    {
                        this.replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), x, -1, z, structureBoundingBoxIn);
                    }
                }
//                this.setBlockState(worldIn, Blocks.DIRT.getDefaultState(), x, -1, z, structureBoundingBoxIn);

                //■葉
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, -1, 5, -1, 1, 7, 1, Blocks.LEAVES.getDefaultState(),Blocks.LEAVES.getDefaultState(), false);
                //■木
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 6, 0, Blocks.LOG.getDefaultState(),Blocks.LOG.getDefaultState(), false);

                return true;
            }
        }
    }


}
