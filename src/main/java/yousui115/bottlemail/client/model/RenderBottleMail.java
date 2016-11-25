package yousui115.bottlemail.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yousui115.bottlemail.BottleMail;
import yousui115.bottlemail.entity.EntityBottleMail;

@SideOnly(Side.CLIENT)
public class RenderBottleMail extends Render<EntityBottleMail>
{
    protected ModelBottleMail modelBottle;

    /**
     * ■コンストラクタ
     */
    public RenderBottleMail(RenderManager renderManager)
    {
        super(renderManager);
        modelBottle = new ModelBottleMail();
    }

    /**
     * ■描画処理
     */
    @Override
    public void doRender(EntityBottleMail bottle, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        //memo:  Techneで出力したModelBottleMailを呼ぶ際、
        //      Modelのソースを弄ると、モデルを手直しした時に修正が大変なので、
        //      このRenderクラスで回転やら縮小やらを行い、手間を省いてる。
        //       後、GlStateManagerを用いて回転縮小を設定する際に
        //      登録とは逆の順番で処理される為（FILO）、注意が必要。
        //       実はもっと楽な方法があるかもしれない。誰かおせーておせーて

        //■行列のコピー
        GlStateManager.pushMatrix();

        //■？
        GlStateManager.enableRescaleNormal();

        //■画像をバインド
        this.bindEntityTexture(bottle);

//        //▼ライティング処理On
//        GlStateManager.enableLighting();
//        //▼グラデーション設定On
//        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        //■FILOで処理される
        // ▼4. 適正位置
        GlStateManager.translate((float)x, (float)(y + 0.1 + Math.sin(bottle.dUD) * 0.1d), (float)z);

        // ▼3. X軸回転
        GlStateManager.rotate(100.0F, 1.0F, 0.0F, 0.0F);

        // ▼2. Y軸回転
        GlStateManager.rotate(bottle.fRot, 0.0F, 1.0F, 0.0F);

        // ▼1. サイズ調整
        GlStateManager.scale(0.05, 0.05, 0.05);

//        worldrenderer.startDrawingQuads();
//        worldrenderer.setNormal(0.0F, 1.0F, 0.0F);

        //★Techneで出力したクラスをそのまま呼んでる。
        modelBottle.render(bottle, 0, 0, 0, 0, 0, 1);

//        GlStateManager.shadeModel(GL11.GL_FLAT);
//        GlStateManager.disableBlend();
//        GlStateManager.disableAlpha();
//        GlStateManager.disableLighting();

//        GlStateManager.disableRescaleNormal();

        //■行列の削除
        GlStateManager.popMatrix();
    }

    /**
     * ■テクスチャURI
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityBottleMail entity)
    {
        return new ResourceLocation(BottleMail.MOD_ID + ":textures/models/ModelBottleMail.png");
    }

    /**
     * ■頭の上に名前が付くか否か
     */
    @Override
    protected boolean canRenderName(EntityBottleMail entity)
    {
        return false;
    }

    @Override
    public void doRenderShadowAndFire(Entity p_76979_1_, double p_76979_2_, double p_76979_4_, double p_76979_6_, float p_76979_8_, float p_76979_9_)
    {
        //super.doRenderShadowAndFire(p_76979_1_, p_76979_2_, p_76979_4_, p_76979_6_, p_76979_8_, p_76979_9_);
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IRenderFactory<EntityBottleMail>
    {
        @Override
        public Render<? super EntityBottleMail> createRenderFor(RenderManager manager)
        {
            return new RenderBottleMail(manager);
        }
    }
}
