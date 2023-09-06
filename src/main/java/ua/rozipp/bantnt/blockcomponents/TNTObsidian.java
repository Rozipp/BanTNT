package ua.rozipp.bantnt.blockcomponents;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import ua.rozipp.bantnt.BanTNT;
import ua.rozipp.core.blocks.CustomBlock;
import ua.rozipp.core.config.RConfig;
import ua.rozipp.core.exception.InvalidConfiguration;
import ua.rozipp.core.util.GeometryUtils;

public class TNTObsidian extends TNTAbstract {

    public TNTObsidian(RConfig compInfo) throws InvalidConfiguration {
        super(compInfo);
    }

    public void onEntityExplode(EntityExplodeEvent event, CustomBlock cBlock) {
//        event.blockList().clear();
        Entity entity = event.getEntity();
        int radius = (int) (entity instanceof Explosive ? ((Explosive) entity).getYield() : 4.0f);
        Block centerBlock = event.getLocation().getBlock();
        for (Location location : GeometryUtils.getBallBlocks(centerBlock.getLocation(), radius)) {
            Block block = location.getBlock();
            if ((block.getType().equals(Material.OBSIDIAN) || block.getType().equals(Material.CRYING_OBSIDIAN)))
                event.blockList().add(block);
        }
    }

}
