package ua.rozipp.bantnt.blockcomponents;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import ua.rozipp.core.blocks.CustomBlock;
import ua.rozipp.core.config.RConfig;
import ua.rozipp.core.exception.InvalidConfiguration;
import ua.rozipp.core.util.LinearMovement;

public class TNTJumper extends TNTAbstract {

    public int height_jump = 0;
    public int time = 3;

    public TNTJumper(RConfig compInfo) throws InvalidConfiguration {
        super(compInfo);
        height_jump = compInfo.getInt("height_jump", height_jump, "");
        time = compInfo.getInt("time", time, "");
    }

    @Override
    public TNTPrimed onTNTExplode(TNTPrimeEvent event, TNTPrimed tNTPrimed, Block block, CustomBlock cBlock) {
        if (tNTPrimed == null)
            tNTPrimed = super.onTNTExplode(event, null, block, cBlock);

        if (this.height_jump > 0) {
            tNTPrimed.setGravity(false);
            TNTPrimed finalTNTPrimed = tNTPrimed;
            final boolean[] levity = {true};
            (new LinearMovement(tNTPrimed.getLocation(), tNTPrimed.getLocation().add(0, this.height_jump, 0), this.time, location -> {
                if (!levity[0]) return;
                Block block1 = location.getWorld().getBlockAt(location);
                if (!block1.getType().equals(Material.AIR) && !block1.getType().equals(Material.CAVE_AIR))
                    levity[0] = false;
                finalTNTPrimed.teleport(location);
            }
            )).beginDrawNow();
        }

        return tNTPrimed;
    }

}
