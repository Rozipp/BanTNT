package ua.rozipp.bantnt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ua.rozipp.bantnt.blockcomponents.TNTExplode;
import ua.rozipp.bantnt.blockcomponents.TNTObsidian;
import ua.rozipp.bantnt.blockcomponents.TNTFrozen;
import ua.rozipp.bantnt.blockcomponents.TNTJumper;
import ua.rozipp.core.ConfigHelper;
import ua.rozipp.core.LogHelper;
import ua.rozipp.core.PluginHelper;
import ua.rozipp.core.blocks.BlockComponent;
import ua.rozipp.core.config.RConfig;
import ua.rozipp.core.exception.InvalidConfiguration;
import ua.rozipp.core.items.CustomMaterial;
import ua.rozipp.core.recipes.CustomRecipe;

import java.util.Random;

public final class BanTNT extends JavaPlugin {

    public static Random random = new Random();
    private static BanTNT instance;

    public static BanTNT getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        PluginHelper.onEnable(this);

        BlockComponent.register(TNTExplode.class);
        BlockComponent.register(TNTObsidian.class);
        BlockComponent.register(TNTJumper.class);
        BlockComponent.register(TNTFrozen.class);

        loadConfig();

        Bukkit.getPluginManager().registerEvents(new TNTPrimedListener(), this);

        (new TNTCommand("bantnt")).register(this);
    }

    public void loadConfig(){
        RConfig rConfig = ConfigHelper.loadConfigFile(this, "config.yml");
        try {
            CustomMaterial.load(this, rConfig);
            CustomRecipe.load(this, rConfig);
        } catch (InvalidConfiguration e) {
            LogHelper.error(e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        PluginHelper.onDisable(this);
    }
}
