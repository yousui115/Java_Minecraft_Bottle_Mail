package yousui115.bottlemail.entity;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import yousui115.bottlemail.BottleMail;

//TODO: Entity継承だと自然スポーンの設定が
//      超めんどくせーっぽいからEntityLivingを継承してる。
//      詳しく調べてないので、もっとうまい方法があるかもしれない。
public class EntityBottleMail extends EntityLiving
{
    public float fRot = 0f;
    public double dUD = 0;

    public EntityBottleMail(World worldIn)
    {
        super(worldIn);

        //■角度
        fRot = this.worldObj.rand.nextFloat() * 90.0f;
    }

    public EntityBottleMail(World worldIn, double dx, double dy, double dz)
    {
        this(worldIn);

        dx += 0.5;
        dz += 0.5;

        //■位置、回転角度の調整
        setLocationAndAngles(dx, dy, dz, 0.0F, 0.0F);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();

        //■サイズ設定
        setSize(0.5F, 0.5F);

    }

    @Override
    public void onUpdate()
    {
        //this.onEntityUpdate();
        //super.onUpdate();

        //TODO: あまりにも海抜0mの海面に湧かないので
        //      砂浜に打ち上げられたものに変更。がっでむ
        //■ボトルくるくる
//        fRot += 1.0f;
//        if (fRot > 360f) { fRot = fRot % 360f; }
//
//        //■ボトルの浮き沈み
//        dUD += 0.05;
//        if (dUD > 360) { dUD = dUD % 360; }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
    }

    /**
     * ■プレイヤーが右クリックすると呼ばれる
     */
    @Override
    public boolean processInteract(EntityPlayer playerIn, EnumHand handIn, @Nullable ItemStack stackIn)
    {
        //■プレイヤーが何を持っているか確認。
        //ItemStack itemstack = playerIn.getCurrentEquippedItem();

        //■オフハンドでは取れない。
        if (handIn == EnumHand.OFF_HAND) { return false;}

        //■何か持ってるプレイヤーなど不要！
        if (stackIn != null) { return false; }

        if (!worldObj.isRemote)
        {
            //■紙切れを出現させる
            ItemStack stackBottle = new ItemStack(BottleMail.itemBottleMail, 1, 0);
            //worldObj.spawnEntityInWorld(new EntityItem(worldObj, playerIn.posX, playerIn.posY, playerIn.posZ, stackBottle));
            //playerIn.setCurrentItemOrArmor(0, stackBottle);
            playerIn.setHeldItem(handIn, stackBottle);

            //■消滅
            this.setDead();
        }

        return true;
    }

    /**
     * ■右クリックを有効にするか否か
     */
    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        //■スポーンの対象地点
        int nX = MathHelper.floor_double(posX);
        int nY = MathHelper.floor_double(posY);
        int nZ = MathHelper.floor_double(posZ);

        //■一つ下の段のブロック情報を取得
        BlockPos pos = new BlockPos(nX, nY - 1, nZ);
        Block block = worldObj.getBlockState(pos).getBlock();
        boolean isWater = false;

        //■周りに水があるか否か
        for (int idx = 0; idx < 3; idx++)
        {
            for (int idz = 0; idz < 3; idz++)
            {
                if(idx == 1 && idz == 1) { continue; }

                Block b = worldObj.getBlockState(new BlockPos(nX - 1 + idx, nY - 1, nZ - 1 + idz)).getBlock();
                isWater = Block.isEqualTo(b, Blocks.WATER);
                if (isWater == true) { break; }
            }
        }

        //■近くのEntityをリストアップ
        boolean isNearBottle = false;
        double dExpand = 15f;
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(dExpand, 0, dExpand));
        for (Entity entity : list)
        {
            //■EntityBonfireが既に置いてあるとfalse
            if (entity instanceof EntityBottleMail)
            {
                isNearBottle = true;
                break;
            }
        }

        //■「下が砂」かつ「空の下」かつ「下の８つのブロックの内、どれかが水」かつ「近くにボトルが落ちていない」かつ「海抜0m(海面が62.875)」 ならばスポーンしてやろう
        if (Block.isEqualTo(block, Blocks.SAND) == true &&
            !worldObj.canSeeSky(pos) &&
            isWater == true &&
            isNearBottle == false &&
            posY == 63)
        {
            return true;
        }

        return false;
    }

    /**
     * ■ダメージは入りません。
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }
}
