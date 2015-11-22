package yousui115.bottlemail.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class MailContainer extends Container
{
    protected int nMeta;

    public MailContainer(int meta)
    {
        nMeta = meta;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
}
