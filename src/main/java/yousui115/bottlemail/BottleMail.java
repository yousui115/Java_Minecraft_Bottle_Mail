package yousui115.bottlemail;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import yousui115.bottlemail.gui.GuiHandler;
import yousui115.bottlemail.model.RenderBottleMail;

@Mod(modid = BottleMail.MOD_ID, version = BottleMail.VERSION)
public class BottleMail
{
    //■Mod Infomation
    public static final String MOD_ID = "bottlemail";
    public static final String VERSION = "1.0";

    @Instance(BottleMail.MOD_ID)
    public static BottleMail instance;

    public static final int STATUS_GUI_ID = 0;

    public static Item itemPieceOfPaper;
    public static final String NAME_POP = "piece_of_paper";
    public static Item itemBottleMail;
    public static final String NAME_BM  = "bottle_mail";

    /**
     * ■初期化処理（前処理）
     * @param event
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println("###################### " + BottleMail.MOD_ID + ".preInit() ##########################");
        System.out.println(event.getSourceFile().getPath());

        //■切れ端に表示させたい情報の取得
        TextReader reader = new TextReader(event.getSourceFile().getPath(), "assets/bottlemail/texts/test2.xml");
        reader.readText();

        //■アイテムの設定
        // ★紙切れ
        this.itemPieceOfPaper = (new ItemPieceOfPaper())
                                .setUnlocalizedName(BottleMail.NAME_POP)
                                .setCreativeTab(CreativeTabs.tabMisc)
                                .setHasSubtypes(true);

        // ★ボトルメール
        this.itemBottleMail   = (new ItemBottleMail())
                                .setMaxStackSize(1)
                                .setUnlocalizedName(BottleMail.NAME_BM)
                                .setCreativeTab(CreativeTabs.tabMisc)
                                .setHasSubtypes(false);

        //■アイテムの登録
        // ★紙切れ
        GameRegistry.registerItem(itemPieceOfPaper, BottleMail.NAME_POP);
        // ★ボトルメール
        GameRegistry.registerItem(itemBottleMail  , BottleMail.NAME_BM);

        //■テクスチャ・モデル指定JSONファイル名の登録。
        if (event.getSide().isClient())
        {
            // ★紙切れ
            for (int i = 0; i < ItemPieceOfPaper.getMailMax(); i++)
            {
                //1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
                ModelLoader.setCustomModelResourceLocation(itemPieceOfPaper, i,
                            new ModelResourceLocation(BottleMail.MOD_ID + ":" + BottleMail.NAME_POP, "inventory"));
            }

            // ★ボトルメール
            ModelLoader.setCustomModelResourceLocation(itemBottleMail, 0,
                    new ModelResourceLocation(BottleMail.MOD_ID + ":" + BottleMail.NAME_BM, "inventory"));
        }

        //■Entityの登録
        EntityRegistry.registerGlobalEntityID(EntityBottleMail.class, "BottleMail", EntityRegistry.findGlobalUniqueEntityId(), 0xefeedf, 0xc2c1b8);
        EntityRegistry.registerModEntity(EntityBottleMail.class, "BottleMail", 1, this, 64, 10, false);
        EntityRegistry.addSpawn("BottleMail", 2, 1, 1, EnumCreatureType.AMBIENT, (BiomeGenBase[]) BiomeGenBase.explorationBiomesList.toArray(new BiomeGenBase[0]));

    }

    /**
     * ■初期化処理（本処理）
     * @param event
     */
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //■GUIの登録
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        //■
        if (event.getSide().isClient())
        {
            //■Renderの登録 及び EntityとRenderの関連付け
            RenderingRegistry.registerEntityRenderingHandler(EntityBottleMail.class, new RenderBottleMail(Minecraft.getMinecraft().getRenderManager()));
        }
    }

    /**
     * ■初期化処理（後処理）
     * @param event
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }

}
