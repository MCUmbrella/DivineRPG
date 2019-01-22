package naturix.divinerpg.client;

import naturix.divinerpg.client.render.RenderItemParasectaAltar;
import naturix.divinerpg.registry.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TEISRRender {
	
	public static void init() {
		Item.getItemFromBlock(ModBlocks.altarParasecta).setTileEntityItemStackRenderer(new RenderItemParasectaAltar("altar_dramix"));
		
	}
}
