package yousui115.bottlemail.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import yousui115.bottlemail.BottleMail;
import yousui115.bottlemail.entity.EntityBottleMail;
import yousui115.bottlemail.entity.EntityBottleMail.EntityBottleMailFloat;
import yousui115.bottlemail.item.ItemBottleMail;
import yousui115.bottlemail.item.ItemPieceOfPaper;

@EventBusSubscriber
public class Register
{
    /**
     * ■アイテムの登録
     * @param eventIn
     */
    @SubscribeEvent
    protected static void registerItem(RegistryEvent.Register<Item> eventIn)
    {
        //■生成
        // ★紙切れ
        BottleMail.rlPOP = new ResourceLocation(BottleMail.MOD_ID, BottleMail.NAME_POP);
        BottleMail.itemPieceOfPaper = (new ItemPieceOfPaper())
                                .setUnlocalizedName(BottleMail.rlPOP.getResourcePath())
                                .setRegistryName(BottleMail.rlPOP)
                                .setCreativeTab(CreativeTabs.MISC)
                                .setHasSubtypes(true);

        // ★ボトルメール
        BottleMail.rlBM = new ResourceLocation(BottleMail.MOD_ID, BottleMail.NAME_BM);
        BottleMail.itemBottleMail   = (new ItemBottleMail())
                                .setMaxStackSize(1)
                                .setUnlocalizedName(BottleMail.rlBM.getResourcePath())
                                .setRegistryName(BottleMail.rlBM)
                                .setCreativeTab(CreativeTabs.MISC)
                                .setHasSubtypes(false);

        //■登録
        eventIn.getRegistry().registerAll(BottleMail.itemPieceOfPaper, BottleMail.itemBottleMail);
    }

    /**
     * ■モデルの登録
     * @param event
     */
    @SubscribeEvent
    protected static void registerItemModel(ModelRegistryEvent eventIn)
    {
        BottleMail.proxy.registerItemModels();
    }

    /**
     * ■エンティティの登録
     * @param event
     */
    @SubscribeEvent
    public static void registerEntity(final RegistryEvent.Register<EntityEntry> event)
    {
        int weightSpawn = 3;

        //■
        event.getRegistry().register(
                EntityEntryBuilder.create()
                    .entity(EntityBottleMail.class)
                    .id(new ResourceLocation(BottleMail.MOD_ID, "bm_bottlemail"), 1)
                    .name("bm_bottlemail")
                    .tracker(50, 5, false)
                    .spawn(EnumCreatureType.CREATURE, weightSpawn, 1, 1, Biomes.BEACH)
                    .build()
            );

        //■
        event.getRegistry().register(
                EntityEntryBuilder.create()
                    .entity(EntityBottleMailFloat.class)
                    .id(new ResourceLocation(BottleMail.MOD_ID, "bm_bottlemail_float"), 2)
                    .name("bm_bottlemail_float")
                    .tracker(50, 5, false)
                    .spawn(EnumCreatureType.WATER_CREATURE, weightSpawn, 1, 1, Biomes.OCEAN, Biomes.DEEP_OCEAN)
                    .build()
            );

        EntitySpawnPlacementRegistry.setPlacementType(EntityBottleMail.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityBottleMailFloat.class, EntityLiving.SpawnPlacementType.IN_WATER);
    }

}
