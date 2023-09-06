package ua.rozipp.bantnt.blockcomponents;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import ua.rozipp.bantnt.BanTNT;
import ua.rozipp.core.blocks.BlockComponent;
import ua.rozipp.core.blocks.ComponentedCustomBlock;
import ua.rozipp.core.blocks.CustomBlock;
import ua.rozipp.core.config.RConfig;

public abstract class TNTAbstract extends BlockComponent {

    public TNTAbstract(RConfig compInfo) {
        super(compInfo);
    }

    public TNTPrimed onTNTExplode(TNTPrimeEvent event, TNTPrimed tNTPrimed, Block block, CustomBlock cBlock) {
        if (tNTPrimed == null) {
            event.setCancelled(true);
            event.getBlock().setBlockData(Material.AIR.createBlockData());

            tNTPrimed = (TNTPrimed) block.getWorld().spawnEntity(block.getLocation().toCenterLocation(), EntityType.PRIMED_TNT);
            tNTPrimed.setMetadata("TNTBid", new FixedMetadataValue(BanTNT.getInstance(), cBlock.getId()));
        }
        return tNTPrimed;
    }

    public void onEntityExplode(EntityExplodeEvent event, CustomBlock cBlock) {

    }

    @Override
    public void onBlockExplodeEvent(BlockExplodeEvent event) {
        super.onBlockExplodeEvent(event);
        Block block = event.getBlock();
        CustomBlock cBlock = CustomBlock.getCustomBlock(block);
        if (!(cBlock instanceof ComponentedCustomBlock)) return;
        TNTExplode tntExplode = ((ComponentedCustomBlock) cBlock).getComponent(TNTExplode.class);
        if (tntExplode != null)
            event.setYield((float) tntExplode.range);
    }

}
