package yousui115.bottlemail.proxy;

import net.minecraft.world.gen.structure.MapGenStructure;
import yousui115.bottlemail.structure.MapGenWoodChest;

public class CommonProxy
{
    public void registerItemModels(){}
    public void registerRenderers(){}

    public MapGenWoodChest chest = new MapGenWoodChest();
    public MapGenStructure getTreasureMapGen()
    {
        return chest;
    }
}
