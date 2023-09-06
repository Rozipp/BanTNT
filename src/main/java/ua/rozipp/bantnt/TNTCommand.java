package ua.rozipp.bantnt;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ua.rozipp.core.blocks.CustomBlock;
import ua.rozipp.core.command.CustomCommand;
import ua.rozipp.core.command.CustomMenuCommand;
import ua.rozipp.core.command.taber.CashedTaber;
import ua.rozipp.core.exception.ComponentException;
import ua.rozipp.core.items.CustomMaterial;
import ua.rozipp.core.recipes.CustomRecipe;

import java.util.ArrayList;
import java.util.List;

public class TNTCommand extends CustomMenuCommand {
    public TNTCommand(String perentCommand) {
        super(perentCommand);
        this.add(new CustomCommand("give").withPermission("bantnt.give").withExecutor((sender, label, args) -> {
            Player player = getPlayer(sender);
            String mid = getNamedString(args, 0, "Введите название кастомного предмета");
            int amaunt = 1;
            if (args.length == 2) {
                player = Bukkit.getPlayer(getNamedString(args, 1, ""));
                if (player == null) throw new ComponentException("игрок не найдет");
            }
            if (args.length == 3) {
                amaunt = getNamedInteger(args, 2);
            }
            CustomMaterial cmat = CustomMaterial.getCustomMaterial(mid);
            if (cmat == null) throw new ComponentException(Component.text("Предмет не найден"));
            player.getInventory().addItem(cmat.spawn(amaunt));
            player.sendMessage(Component.translatable("cmd_tnt_targetGive", NamedTextColor.GREEN).args(
                    Component.text(cmat.getName()),
                    Component.text(amaunt)));
            if (!player.equals(sender)) {
                sender.sendMessage(Component.translatable("cmd_tnt_senderGive", NamedTextColor.GREEN).args(
                        Component.text(cmat.getName()),
                        Component.text(amaunt),
                        Component.text(player.getName())));
            }
        }).withTabCompleter(new CashedTaber("give") {
                    @Override
                    protected List<String> newTabList(String arg) {
                        List<String> list = new ArrayList<>();
                        for (String mid : CustomMaterial.midValues()){
                            if (mid.startsWith(arg)) list.add(mid);
                        }
                        return list;
                    }
                })
        );
        this.add(new CustomCommand("reload").withPermission("bantnt.reload").withExecutor((sender, label, args) -> {
            CustomMaterial.removeAll(getPlugin());
            CustomRecipe.removeAll(getPlugin());
            CustomBlock.removeAll(getPlugin());
            BanTNT.getInstance().loadConfig();
        }));
    }

}
