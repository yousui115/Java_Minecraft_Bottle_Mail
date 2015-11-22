package yousui115.bottlemail.gui;

import yousui115.bottlemail.BottleMail;
import yousui115.bottlemail.ItemPieceOfPaper;
import yousui115.bottlemail.Mail;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MailGuiContainer extends GuiContainer
{
    private static final ResourceLocation textures = new ResourceLocation(BottleMail.MOD_ID + ":textures/gui/gui_paper.png");
    protected int nMeta;

    /**
     * ■コンストラクタ
     * @param meta
     */
    public MailGuiContainer(int meta)
    {
        super(new MailContainer(meta));
        nMeta = meta;

        ySize = 180;
    }

    /**
     * ■GUIの背景の描画処理
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseZ)
    {
        this.mc.renderEngine.bindTexture(textures);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
    }

    /**
     * ■GUIの文字等の描画処理
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ)
    {
        //this.fontRendererObj.drawString(I18n.format("昔々、あるところにおじいさんとおばあさんが棲んでいませんでした。"), 32, 20, 0x404040);
        Mail mail = ItemPieceOfPaper.getMail(nMeta);
        if (mail == null) { return; }

        for (int i = 0; i < mail.strMsg.size(); i++)
        {
            this.fontRendererObj.drawString(I18n.format(mail.strMsg.get(i)), 32, 20 + (i * 10), 0x404040);
        }
    }


    /**
     * ■GUIが開いている時にゲームの処理を止めるかどうか。
     */
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}