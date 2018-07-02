package yousui115.bottlemail.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
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
import yousui115.bottlemail.util.Mail;

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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        Mail mail = this.getMail(stack.getMetadata());
        if (mail != null && !StringUtils.isNullOrEmpty(mail.strAuthor))// && mail.strTitle != "")
        {
            tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("book.byAuthor", new Object[] {mail.strAuthor}));
        }
    }

    /**
     * ■右クリックを押すと呼ばれる。
     */
    @Override
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
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            int i = ItemPieceOfPaper.listMails.size();

            for (int j = 0; j < i; ++j)
            {
                items.add(new ItemStack(this, 1, j));
            }
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
