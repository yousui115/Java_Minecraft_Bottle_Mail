package yousui115.bottlemail.client.gui;

import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yousui115.bottlemail.BottleMail;

@SideOnly(Side.CLIENT)
public class GuiScreenMail extends GuiScreenBook
{
    private static final ResourceLocation textures = new ResourceLocation(BottleMail.MOD_ID + ":textures/gui/gui_paper.png");

    protected int nMeta;

    public GuiScreenMail(EntityPlayer player, ItemStack book, boolean isUnsigned)
    {
        super(player, book, isUnsigned);
    }

    /**
     * Draws a textured rectangle at the current z-value.
     */
    @Override
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        //TODO: バニラのbindTextureの指定位置が全て悪いんや！
        this.mc.getTextureManager().bindTexture(textures);

        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

}