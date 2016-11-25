package yousui115.bottlemail;

import java.util.List;

import net.minecraft.item.ItemStack;
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
        if (strItem != "")
        {
            List<ItemStack> stacks = OreDictionary.getOres(strItem);
            if (stacks != null && stacks.size() != 0)
            {
                stack = stacks.get(0).copy();
            }
            else
            {
                System.out.println("[BottleMail Err] \"" + strItem + "\" is not registered in OreDictionary.");
            }
        }
    }
}
