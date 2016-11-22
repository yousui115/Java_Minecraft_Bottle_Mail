package yousui115.bottlemail.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerMail extends Container
{
    protected int nMeta;

    public ContainerMail(int meta)
    {
        nMeta = meta;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
}
