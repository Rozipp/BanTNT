package ua.rozipp.bantnt.blockcomponents;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import ua.rozipp.core.blocks.CustomBlock;
import ua.rozipp.core.config.RConfig;
import ua.rozipp.core.exception.InvalidConfiguration;
import ua.rozipp.core.util.GeometryUtils;

import java.util.Collection;

public class TNTFrozen extends TNTAbstract {

    public int range = 20;

    public TNTFrozen(RConfig compInfo) throws InvalidConfiguration {
        super(compInfo);
        range = compInfo.getInt("range", range, "");
    }

    @Override
    public void onEntityExplode(EntityExplodeEvent event, CustomBlock cBlock) {
        event.setCancelled(true);
        Location centerBlock = event.getLocation();
        Collection<Location> c = GeometryUtils.getBallBlocks(centerBlock, range);
        for (Location location : c) {
            Block block = location.getBlock();
            if (block.getType() == Material.WATER)
                block.setType(Material.ICE);
        }
    }

    @Override
    public void onBlockIgniteEvent(BlockIgniteEvent event, Block block) {
        super.onBlockIgniteEvent(event, block);
        Bukkit.getPluginManager().callEvent(new TNTPrimeEvent(block, TNTPrimeEvent.PrimeReason.ITEM, event.getIgnitingEntity()));
    }

    @Override
    public void onBlockRedstoneEvent(BlockRedstoneEvent event) {
        super.onBlockRedstoneEvent(event);
        if (event.getNewCurrent() > event.getOldCurrent()) {
            Bukkit.getPluginManager().callEvent(new TNTPrimeEvent(event.getBlock(), TNTPrimeEvent.PrimeReason.REDSTONE, null));
        }
    }
}
