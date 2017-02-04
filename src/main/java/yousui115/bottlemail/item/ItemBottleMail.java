package yousui115.bottlemail.item;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yousui115.bottlemail.BottleMail;
import yousui115.bottlemail.Mail;

public class ItemBottleMail extends Item
{
    public ItemBottleMail()
    {
        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    ItemStack itemstack = entityIn.getActiveItemStack();
                    return itemstack != null && itemstack.getItem() == BottleMail.itemBottleMail ? (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

//    /**
//     * Called when a Block is right-clicked with this Item
//     *
//     * @param pos The block being right-clicked
//     * @param side The side being right-clicked
//     */
//    @Override
//    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
//    {
//        //ブロックの上や再び水上に置いて、インテリアなんかに出来るといいよね。やらないけど！
////        if (!worldIn.isRemote)    //ServerSide
////        {
////            //Test
////            Entity ent = new EntityBottleMail(worldIn, pos.getX(), pos.getY() + 1, pos.getZ());
////            worldIn.spawnEntityInWorld(ent);
////            return true;
////        }
//        return false;
//    }
//
    /**
     * ■右クリックを押すと呼ばれる。
     */
    @Override
//    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
//    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        //■スニーキング かつ メインハンド
        if (/*playerIn.isSneaking() &&*/ EnumHand.MAIN_HAND == handIn)
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult(EnumActionResult.PASS, stack);
    }

    /**
     * Called each tick while using an item.
     * ■EnumAction中は毎Tick呼ばれるはず
     */
    @Override
    public void onUsingTick(ItemStack stackIn, EntityLivingBase livingIn, int timeLeft)
    {
        if (!(livingIn instanceof EntityPlayer)) { return; }
        EntityPlayer player = (EntityPlayer)livingIn;

        if (timeLeft == 71980)
        {
            //ギュギュ1
            player.playSound(BottleMail.Gyu1, 1.0F, 1.0f);
        }
        else if (timeLeft == 71950)
        {
            //ギュギュ2
            player.playSound(BottleMail.Gyu2, 1.0F, 1.0f);
        }
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void onPlayerStoppedUsing(ItemStack stackIn, World worldIn, EntityLivingBase livingIn, int timeLeft)
    {
        //※timeLeft 72000 -> 0

        if (!(livingIn instanceof EntityPlayer)) { return; }
        EntityPlayer player = (EntityPlayer)livingIn;

        if (timeLeft < 71930)
        {
            //■アイテム消費
//            --stackIn.stackSize;
            int size = stackIn.func_190916_E() - 1;


//            if (stackIn.stackSize == 0)
            if (size <= 0)
            {
                player.inventory.deleteStack(stackIn);
            }
            else
            {
                stackIn.func_190920_e(size);
            }

            if (!worldIn.isRemote)
            {
                //■重み付けから、アイテムを抽選する。
                int weightSum = 0;
                for (int idx = 0; idx < ItemPieceOfPaper.getMailMax(); idx++)
                {
                    weightSum += ItemPieceOfPaper.getMail(idx).weight;
                }

                int targetNum = worldIn.rand.nextInt(weightSum) + 1; //(new Random()).nextInt(weightSum) + 1;
                //System.out.println(targetNum);
                int subID = 0;

                for (int idx = 0; idx < ItemPieceOfPaper.getMailMax(); idx++)
                {
                    if (targetNum <= ItemPieceOfPaper.getMail(idx).weight)
                    {
                        subID = idx;
                        break;
                    }
                    targetNum -= ItemPieceOfPaper.getMail(idx).weight;
                }

                ItemStack stackPaper = new ItemStack(BottleMail.itemPieceOfPaper, 1, subID);

                Mail mail = ItemPieceOfPaper.getMail(subID);

                //■メッセージが書かれているなら、紙切れ顕現
                if (!StringUtils.isNullOrEmpty(mail.strMsg))
                {
                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, livingIn.posX, livingIn.posY, livingIn.posZ, stackPaper));
                }

                //■同梱アイテムがあれば顕現
                ItemStack stack = ItemStack.field_190927_a;
                if (!mail.stack.func_190926_b())
                {
                    stack = mail.stack.copy();
                }
                else if (mail.strItem.equals("map_mansion"))
                {
                    stack = Mail.createMansionMap(worldIn, livingIn.getPosition(), "Mansion");
                }
                else if (mail.strItem.equals("map_village"))
                {
                    stack = Mail.createMansionMap(worldIn, livingIn.getPosition(), "Village");
                }

                if (!stack.func_190926_b())
                {
                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, livingIn.posX, livingIn.posY, livingIn.posZ, stack));
                }
            }

            //■ぽん！
            livingIn.playSound(BottleMail.PON, 1.0F, 1.0f);
        }
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

}
