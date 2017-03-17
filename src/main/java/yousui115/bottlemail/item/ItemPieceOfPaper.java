package yousui115.bottlemail.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yousui115.bottlemail.BottleMail;
import yousui115.bottlemail.Mail;

public class ItemPieceOfPaper extends Item
{
    private static ArrayList<Mail> listMails = new ArrayList<Mail>();

    /**
     * ■コンストラクタ
     */
    public ItemPieceOfPaper(){}

    /**
     * ■ToolTip
     */
    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        Mail mail = this.getMail(stack.getMetadata());
        if (mail != null && !StringUtils.isNullOrEmpty(mail.strTitle))
        {
            return mail.strTitle;
        }

        return super.getItemStackDisplayName(stack);
//        if (stack.hasTagCompound())
//        {
//            NBTTagCompound nbttagcompound = stack.getTagCompound();
//            String s = nbttagcompound.getString("title");
//
//            if (!StringUtils.isNullOrEmpty(s))
//            {
//                return s;
//            }
//        }
//
//        return super.getItemStackDisplayName(stack);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        Mail mail = this.getMail(stack.getMetadata());
        if (mail != null && !StringUtils.isNullOrEmpty(mail.strAuthor))// && mail.strTitle != "")
        {
            tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("book.byAuthor", new Object[] {mail.strAuthor}));
        }

//        if (stack.hasTagCompound())
//        {
//            NBTTagCompound nbttagcompound = stack.getTagCompound();
//            String s = "abcde";//nbttagcompound.getString("author");
//
//            if (!StringUtils.isNullOrEmpty(s))
//            {
//                tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("book.byAuthor", new Object[] {s}));
//            }
//
//            tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("book.generation." + nbttagcompound.getInteger("generation")));
//        }
    }

    /**
     * ■右クリックを押すと呼ばれる。
     */
    @Override
//    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
//    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.openGui(BottleMail.instance, BottleMail.STATUS_GUI_ID, worldIn, MathHelper.ceil(playerIn.posX), MathHelper.ceil(playerIn.posY), MathHelper.ceil(playerIn.posZ));

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    /**
     * ■
     * @param subItems The List of sub-items. This is a List of ItemStacks.
     */
    @Override
    @SideOnly(Side.CLIENT)
//    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
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

    public static ArrayList<Mail> getMails()
    {
        return listMails;
    }

    public static int getMailMax()
    {
        return listMails.size();
    }
}
