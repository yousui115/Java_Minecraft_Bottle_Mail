package yousui115.bottlemail.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import yousui115.bottlemail.BottleMail;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
//        ItemStack stack = player.inventory.getCurrentItem();
//        if (BottleMail.STATUS_GUI_ID == ID &&
//            stack != null &&
//            stack.getItem() == BottleMail.itemPieceOfPaper)
//        {
//            return new ContainerMail(stack.getMetadata());
//        }

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
            return new GuiScreenMail(player, stack, false);
        }

        return null;
    }

}
