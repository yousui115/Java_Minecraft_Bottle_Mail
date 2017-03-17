package yousui115.bottlemail;

import java.util.List;
import java.util.Locale;

import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.oredict.OreDictionary;



public class Mail
{
    public String strTitle = "";
    public String strAuthor = "";
    public String strMsg = "";
    public String strItem = "";
    public ItemStack stack = null;
    public int weight = 0;
    //public ArrayList<String> strMsg;
    //public String[] strMsg = new String[14];

    public void createStackFromOD()
    {
        if (StringUtils.isNullOrEmpty(strItem) || strItem.substring(0, 4).equals("map_"))
        {
            stack = ItemStack.EMPTY;
        }
        else
        {
            List<ItemStack> stacks = OreDictionary.getOres(strItem);
            if (stacks != null && stacks.size() != 0)
            {
                stack = stacks.get(0).copy();
            }
            else
            {
                stack = ItemStack.EMPTY;
                System.out.println("[BottleMail Err] \"" + strItem + "\" is not registered in OreDictionary.");
            }
        }
    }


    /**
     * ■EntityVillage.TreasureMapForEmeralds.func_190888_a()を参考に作成
     * @param worldIn
     * @param posIn
     * @return
     */
    public static ItemStack createMansionMap(World worldIn, BlockPos posIn, String strType)
    {
        ItemStack stack = ItemStack.EMPTY;
        if (worldIn.isRemote) { return stack; }

        MapDecoration.Type type = MapDecoration.Type.TARGET_POINT;
        if (strType.equals("Mansion"))
        {
            type = MapDecoration.Type.MANSION;
        }

        BlockPos blockpos = worldIn.findNearestStructure(strType, posIn, true);
        if (blockpos != null)
        {
            stack = ItemMap.setupNewMap(worldIn, (double)blockpos.getX(), (double)blockpos.getZ(), (byte)2, true, true);
            ItemMap.renderBiomePreviewMap(worldIn, stack);
            MapData.addTargetDecoration(stack, blockpos, "+", type);
            stack.setTranslatableName("filled_map." + strType.toLowerCase(Locale.ROOT));
        }
        return stack;
    }
}
