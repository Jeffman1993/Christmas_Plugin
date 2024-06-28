
package club.jeffs.christmasplugin;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener{

    
    
    private giftGiver gg = null;
    private jingleHelper j = null;
    private data d = null;
    
    @Override
    public void onEnable(){
        Bukkit.getLogger().info("Preparing to spread Christmas cheer! :)");
        Bukkit.getPluginManager().registerEvents(this, this);
        
        gg = new giftGiver();
        j = new jingleHelper();
        d = new data(this);
        
        if(main.super.getDataFolder().exists()){
            
            
            
        }
        else{
            main.super.getDataFolder().mkdir();
        }
    }
    
    @Override
    public void onDisable(){
        Bukkit.getLogger().info("Santa is no longer coming to town. :(");
        
    }
    
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        
        if(isChrimbus() && !d.playerInList(e.getPlayer())){
            
            e.getPlayer().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Merry Christmas! Claim your present by going to the survival world and typing /santa");
        }
        
    }
    
    private boolean isChrimbus(){
        
        LocalDate date = java.time.LocalDate.now();
        
        //Is Christmas?
        if(date.getMonth() == Month.DECEMBER && date.getDayOfMonth() >= 25)
            return true;
        
        return false;
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        
        if (sender instanceof Player){
            
            
            Player player = (Player) sender;
            
            if(cmd.getName().equalsIgnoreCase("santa")){
                
                
                Bukkit.getLogger().info("Is Chrimbus? " + isChrimbus() + " " + java.time.LocalDate.now().toString());

                if(isChrimbus()){
                    if(!d.playerInList(player)){

                        if(gg.giveGift(player)){
                            j.playJingle(player);
                            d.addPlayer(player);
                        }
                        else{
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You must have one inventory slot open and be in the survival world!");
                        }
                    }
                    else{
                        player.sendMessage("You've already claimed your gift!");
                    }
                }
                else{
                    player.sendMessage("It isn't Chrimbus yet! :P");
                }
            }
            
            else if(cmd.getName().equalsIgnoreCase("santalist")){
                
                
                List<OfflinePlayer> players = d.getClaimedPlayerList();
                
                try {
                    String name = webHelper.getPlayerName(player.getUniqueId());
                    player.sendMessage(name);
                    
                } catch (IOException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        }
        
        return true;
    }
}
