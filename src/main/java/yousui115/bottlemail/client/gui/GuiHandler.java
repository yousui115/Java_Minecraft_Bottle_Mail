package yousui115.bottlemail.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import yousui115.bottlemail.BottleMail;
import yousui115.bottlemail.item.ItemPieceOfPaper;
import yousui115.bottlemail.util.Mail;

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
            int subID = stack.getMetadata();
            ItemStack stackPaper = new ItemStack(BottleMail.itemPieceOfPaper, 1, subID);
            Mail mail = ItemPieceOfPaper.getMail(subID);

            NBTTagCompound nbt = new NBTTagCompound();
            //■タイトル
            nbt.setString("title", mail.strTitle);
            //■著者
            nbt.setString("author", mail.strAuthor);
            //■内容
            NBTTagList pages = new NBTTagList();
            NBTTagString text = new NBTTagString("{\"text\":\"" + mail.strMsg + "\"}");
            pages.appendTag(text);
            nbt.setTag("pages", pages);

            //■ItemStackにNBT登録
            stackPaper.setTagCompound(nbt);

            return new GuiScreenMail(player, stackPaper, false);
        }

        return null;
    }

}
