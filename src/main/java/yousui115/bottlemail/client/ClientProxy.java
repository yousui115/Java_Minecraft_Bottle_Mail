package yousui115.bottlemail.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import yousui115.bottlemail.BottleMail;
import yousui115.bottlemail.CommonProxy;
import yousui115.bottlemail.client.model.RenderBottleMail;
import yousui115.bottlemail.entity.EntityBottleMail;
import yousui115.bottlemail.item.ItemPieceOfPaper;

public class ClientProxy extends CommonProxy
{
    /**
     * ■モデルの登録
     */
    @Override
    public void registerModels()
    {
        // ★紙切れ
        for (int i = 0; i < ItemPieceOfPaper.getMailMax(); i++)
        {
            //1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
            ModelLoader.setCustomModelResourceLocation(BottleMail.itemPieceOfPaper, i,
                    new ModelResourceLocation(BottleMail.MOD_ID + ":" + BottleMail.NAME_POP, "inventory"));
        }

        // ★ボトルメール
        ModelLoader.setCustomModelResourceLocation(BottleMail.itemBottleMail, 0,
                new ModelResourceLocation(BottleMail.MOD_ID + ":" + BottleMail.NAME_BM, "inventory"));
    }

    /**
     * ■レンダラの登録
     */
    @Override
    public void registerRenderers()
    {
        //■Renderの登録 及び EntityとRenderの関連付け
        RenderingRegistry.registerEntityRenderingHandler(EntityBottleMail.class, new RenderBottleMail.Factory());


    }
}
