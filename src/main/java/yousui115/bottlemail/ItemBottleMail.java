package yousui115.bottlemail;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBottleMail extends Item
{
    /**
     * Called when a Block is right-clicked with this Item
     *
     * @param pos The block being right-clicked
     * @param side The side being right-clicked
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        //ブロックの上や再び水上に置いて、インテリアなんかに出来るといいよね。やらないけど！
//        if (!worldIn.isRemote)    //ServerSide
//        {
//            //Test
//            Entity ent = new EntityBottleMail(worldIn, pos.getX(), pos.getY() + 1, pos.getZ());
//            worldIn.spawnEntityInWorld(ent);
//            return true;
//        }
        return false;
    }

    /**
     * ■右クリックを押すと呼ばれる。
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (player.isSneaking())
        {
            if (!world.isRemote)    //ServerSide
            {
                //■きゅぽん
                world.playSoundEffect(player.posX, player.posY, player.posZ, BottleMail.MOD_ID + ":kyupon", 0.5F, 1.0f);
                itemStack.stackSize--;

                //■紙切れを出現させる
                ItemStack stackPaper = new ItemStack(BottleMail.itemPieceOfPaper, 1, world.rand.nextInt(ItemPieceOfPaper.getMailMax()));
                world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, stackPaper));
            }
        }
        return itemStack;
    }
}
