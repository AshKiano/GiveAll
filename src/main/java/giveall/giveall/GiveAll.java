package giveall.giveall;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GiveAll extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        this.getCommand("giveall").setExecutor(this);
        Metrics metrics = new Metrics(this, 21920);
        this.getLogger().info("Thank you for using the GiveAll plugin! If you enjoy using this plugin, please consider making a donation to support the development. You can donate at: https://donate.ashkiano.com");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("giveall.use")) {
            player.sendMessage("You do not have permission to use this command.");
            return true;
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand == null || itemInHand.getType().isAir()) {
            player.sendMessage("You need to hold an item in your hand to use this command.");
            return true;
        }

        for (Player target : getServer().getOnlinePlayers()) {
            target.getInventory().addItem(itemInHand.clone());
            target.sendMessage("You have received an item from " + player.getDisplayName() + ".");
        }

        player.sendMessage("All online players have received your item.");
        return true;
    }
}
