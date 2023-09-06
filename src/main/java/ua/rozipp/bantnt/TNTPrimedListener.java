package ua.rozipp.bantnt;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.MetadataValue;
import ua.rozipp.bantnt.blockcomponents.*;
import ua.rozipp.core.blocks.ComponentedCustomBlock;
import ua.rozipp.core.blocks.CustomBlock;

import java.util.List;

public class TNTPrimedListener implements Listener {

    @EventHandler
    public void onTNTExplode(TNTPrimeEvent event) {
        Block block = event.getBlock();
        CustomBlock cBlock = CustomBlock.getCustomBlock(block);

        if (!(cBlock instanceof ComponentedCustomBlock)) return;
        ComponentedCustomBlock compBlock = (ComponentedCustomBlock) cBlock;

        TNTPrimed tntPrimed = null;

        TNTAbstract tntComponent = compBlock.getComponent(TNTJumper.class);
        if (tntComponent != null) tntPrimed = tntComponent.onTNTExplode(event, tntPrimed, block, cBlock);

        tntComponent = compBlock.getComponent(TNTExplode.class);
        if (tntComponent != null) tntPrimed = tntComponent.onTNTExplode(event, tntPrimed, block, cBlock);

        tntComponent = compBlock.getComponent(TNTFrozen.class);
        if (tntComponent != null) tntPrimed = tntComponent.onTNTExplode(event, tntPrimed, block, cBlock);

        tntComponent = compBlock.getComponent(TNTObsidian.class);
        if (tntComponent != null) tntPrimed = tntComponent.onTNTExplode(event, tntPrimed, block, cBlock);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity().getType() == EntityType.PRIMED_TNT && event.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            List<MetadataValue> list = event.getEntity().getMetadata("TNTBid");
            if (list.isEmpty()) return;
            String bid = list.get(0).asString();
            CustomBlock cBlock = CustomBlock.getCustomBlock(bid);
            if (!(cBlock instanceof ComponentedCustomBlock)) return;
            ComponentedCustomBlock compBlock = (ComponentedCustomBlock) cBlock;

            TNTAbstract tntComponent = compBlock.getComponent(TNTJumper.class);
            if (tntComponent != null) tntComponent.onEntityExplode(event, cBlock);

            tntComponent = compBlock.getComponent(TNTObsidian.class);
            if (tntComponent != null) tntComponent.onEntityExplode(event, cBlock);

            tntComponent = compBlock.getComponent(TNTExplode.class);
            if (tntComponent != null) tntComponent.onEntityExplode(event, cBlock);

            tntComponent = compBlock.getComponent(TNTFrozen.class);
            if (tntComponent != null) tntComponent.onEntityExplode(event, cBlock);
        }
    }

}

