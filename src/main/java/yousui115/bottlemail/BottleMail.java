package yousui115.bottlemail;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import yousui115.bottlemail.client.gui.GuiHandler;
import yousui115.bottlemail.entity.EntityBottleMail;
import yousui115.bottlemail.item.ItemBottleMail;
import yousui115.bottlemail.item.ItemPieceOfPaper;

@Mod(modid = BottleMail.MOD_ID, version = BottleMail.VERSION)
public class BottleMail
{
    //■Mod Infomation
    public static final String MOD_ID = "bottlemail";
    public static final String MOD_DOMAIN = "yousui115." + MOD_ID;
    public static final String VERSION = "MC1102_F2099_v4";

    @Instance(BottleMail.MOD_ID)
    public static BottleMail instance;

    //■クライアント側とサーバー側で異なるインスタンスを生成
    @SidedProxy(clientSide = MOD_DOMAIN + ".client.ClientProxy", serverSide = MOD_DOMAIN + ".CommonProxy")
    public static CommonProxy proxy;

    public static final int STATUS_GUI_ID = 0;

    public static Item itemPieceOfPaper;
    public static final String NAME_POP = "piece_of_paper";
    public static ResourceLocation rlPOP;

    public static Item itemBottleMail;
    public static final String NAME_BM  = "bottle_mail";
    public static ResourceLocation rlBM;

    public static SoundEvent Gyu1;
    public static SoundEvent Gyu2;
    public static SoundEvent PON;

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

        //■アイテムの設定・登録
        // ★紙切れ
        this.itemPieceOfPaper = (new ItemPieceOfPaper())
                                .setUnlocalizedName(BottleMail.NAME_POP)
                                .setCreativeTab(CreativeTabs.MISC)
                                .setHasSubtypes(true);
        rlPOP = new ResourceLocation(MOD_ID, BottleMail.NAME_POP);
        GameRegistry.register(itemPieceOfPaper, rlPOP);

        // ★ボトルメール
        this.itemBottleMail   = (new ItemBottleMail())
                                .setMaxStackSize(1)
                                .setUnlocalizedName(BottleMail.NAME_BM)
                                .setCreativeTab(CreativeTabs.MISC)
                                .setHasSubtypes(false);
        rlBM = new ResourceLocation(MOD_ID, BottleMail.NAME_BM);
        GameRegistry.register(itemBottleMail, rlBM);

        //■Entityの登録
        EntityRegistry.registerModEntity(EntityBottleMail.class, "BottleMail", 1, this, 64, 10, false);
        EntityRegistry.addSpawn(EntityBottleMail.class, 2, 1, 1, EnumCreatureType.AMBIENT, (Biome[]) Biome.EXPLORATION_BIOMES_LIST.toArray(new Biome[0]));

        //■テクスチャ・モデル指定JSONファイル名の登録。
        proxy.registerModels();

        //■レンダラーの生成とEntityとの関連付け
        proxy.registerRenderers();
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

        //■効果音の登録
        Gyu1 = new SoundEvent(new ResourceLocation(BottleMail.MOD_ID, "gyugyu1"));
        Gyu2 = new SoundEvent(new ResourceLocation(BottleMail.MOD_ID, "gyugyu2"));
        PON = new SoundEvent(new ResourceLocation(BottleMail.MOD_ID, "pon"));
    }

    /**
     * ■初期化処理（後処理）
     * @param event
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        //■鉱石辞書よりItemStackを生成する
        for(Mail mail : ItemPieceOfPaper.getMails())
        {
            mail.createStackFromOD();
        }
    }
}
