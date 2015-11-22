package yousui115.bottlemail;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPieceOfPaper extends Item
{
    private static ArrayList<Mail> listMails = new ArrayList<Mail>();

    /**
     * ■コンストラクタ
     */
    public ItemPieceOfPaper()
    {
    }

    /**
     * ■右クリックを押すと呼ばれる。
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        player.openGui(BottleMail.instance, BottleMail.STATUS_GUI_ID, world, MathHelper.ceiling_double_int(player.posX), MathHelper.ceiling_double_int(player.posY), MathHelper.ceiling_double_int(player.posZ));

        return itemStack;
    }

    /**
     * ■
     * @param subItems The List of sub-items. This is a List of ItemStacks.
     */
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
    {
        int i = ItemPieceOfPaper.listMails.size();

        for (int j = 0; j < i; ++j)
        {
            subItems.add(new ItemStack(this, 1, j));
        }
    }

    /**
     * ■メールの登録
     * @param mail
     */
    public static void addMail(Mail mail)
    {
        listMails.add(mail);
    }

    /**
     * ■登録されてるメールの取得
     * @param nID
     * @return
     */
    public static Mail getMail(int nID)
    {
        if (0 <= nID && nID < listMails.size())
        {
            return listMails.get(nID);
        }

        return null;
    }

    public static int getMailMax()
    {
        return listMails.size();
    }
}
