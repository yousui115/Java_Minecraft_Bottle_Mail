package yousui115.bottlemail.event;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import yousui115.bottlemail.BottleMail;

public class EventHndl
{
    @SubscribeEvent
    public void mm(PopulateChunkEvent.Pre event)
    {
        MapGenStructure chest = BottleMail.proxy.getTreasureMapGen();
        if (chest == null) { return; }

        chest.generate(event.getWorld(), event.getChunkX(), event.getChunkZ(), null);

        ChunkPos chunkpos = new ChunkPos(event.getChunkX(), event.getChunkZ());
        chest.generateStructure(event.getWorld(), event.getRand(), chunkpos);
    }
}
