package yousui115.bottlemail.gui;

import yousui115.bottlemail.BottleMail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        ItemStack stack = player.inventory.getCurrentItem();
        if (BottleMail.STATUS_GUI_ID == ID &&
            stack != null &&
            stack.getItem() == BottleMail.itemPieceOfPaper)
        {
            return new MailContainer(stack.getMetadata());
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        ItemStack stack = player.inventory.getCurrentItem();
        if (BottleMail.STATUS_GUI_ID == ID &&
            stack != null &&
            stack.getItem() == BottleMail.itemPieceOfPaper)
        {
            return new MailGuiContainer(stack.getMetadata());
        }

        return null;
    }

}
