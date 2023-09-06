package ua.rozipp.bantnt.blockcomponents;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import ua.rozipp.bantnt.BanTNT;
import ua.rozipp.core.blocks.CustomBlock;
import ua.rozipp.core.config.RConfig;
import ua.rozipp.core.exception.InvalidConfiguration;

public class TNTExplode extends TNTAbstract {

    public boolean glowing = true;
    public boolean fire = false;
    public int interval = 8;
    public int range = 20;

    public TNTExplode(RConfig compInfo) throws InvalidConfiguration {
        super(compInfo);
        glowing = compInfo.getBoolean("glowing", glowing, "");
        fire = compInfo.getBoolean("fire", fire, "");
        interval = compInfo.getInt("interval", interval, "");
        range = compInfo.getInt("range", range, "");
    }

    public TNTPrimed onTNTExplode(TNTPrimeEvent event, TNTPrimed tNTPrimed, Block block, CustomBlock cBlock) {
        if (tNTPrimed == null)
            tNTPrimed = super.onTNTExplode(event, null, block, cBlock);

        tNTPrimed.setCustomNameVisible(true);
        tNTPrimed.setYield((float) this.range);
        tNTPrimed.setFuseTicks(this.interval * 20);
        tNTPrimed.setIsIncendiary(this.fire);
        tNTPrimed.setGlowing(this.glowing);
        tNTPrimed.setMetadata("TNTBid", new FixedMetadataValue(BanTNT.getInstance(), cBlock.getId()));

        return tNTPrimed;
    }

    public void onEntityExplode(EntityExplodeEvent event, CustomBlock cBlock) {

    }

}
