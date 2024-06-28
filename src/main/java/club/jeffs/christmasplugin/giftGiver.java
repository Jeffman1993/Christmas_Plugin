
package club.jeffs.christmasplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Colorable;

public class giftGiver {

    Random rand;
    
    Material[] shulkers = new Material[]{Material.LIME_SHULKER_BOX, Material.RED_SHULKER_BOX, Material.WHITE_SHULKER_BOX};
    
    ItemStack[] baseItems = null;
    Material[] records = new Material[]{Material.MUSIC_DISC_13,Material.MUSIC_DISC_BLOCKS,Material.MUSIC_DISC_CAT,Material.MUSIC_DISC_CHIRP,Material.MUSIC_DISC_FAR,Material.MUSIC_DISC_MALL,Material.MUSIC_DISC_MELLOHI,Material.MUSIC_DISC_PIGSTEP,Material.MUSIC_DISC_STAL,Material.MUSIC_DISC_STRAD,Material.MUSIC_DISC_WAIT,Material.MUSIC_DISC_WARD};
    Material[] skulls = new Material[]{Material.CREEPER_HEAD,Material.SKELETON_SKULL,Material.WITHER_SKELETON_SKULL,Material.DRAGON_HEAD};
    Material[] eggs = new Material[]{Material.WOLF_SPAWN_EGG,Material.MOOSHROOM_SPAWN_EGG,Material.COW_SPAWN_EGG,Material.SHEEP_SPAWN_EGG,Material.OCELOT_SPAWN_EGG,Material.PARROT_SPAWN_EGG,Material.PANDA_SPAWN_EGG};
    
    public giftGiver(){
        
        baseItems = new ItemStack[]{
        getItem(Material.MILK_BUCKET, 1),
        getItem(Material.COOKIE, 12),
        getItem(Material.DIAMOND, 4),
        getItem(Material.COAL, 25),
        getItem(Material.CAMPFIRE, 1),
        getItem(Material.EXPERIENCE_BOTTLE, 5),
        getItem(Material.ENCHANTED_GOLDEN_APPLE, 1),
        getItem(Material.PUMPKIN_PIE, 1),
        getItem(Material.BELL, 2),
        getItem(Material.SWEET_BERRIES, 10),
        getItem(Material.JUKEBOX, 1)
        };
        
        rand = new Random();
    }
    
    public boolean giveGift(Player player){
        
        if(playerInvClear(player) && player.getWorld().getName().equalsIgnoreCase("62686J_K")){
        
            ItemStack shulker = getShulker(getRandomItems());

            player.getInventory().addItem(shulker);

            player.sendMessage(ChatColor.GREEN + "Merry Christmas!");
            player.sendMessage(ChatColor.RED + "You have been given a gift from Santa!");
            
            return true;
        }
        
        return false;
    }
    
    public boolean playerInvClear(Player player){
        
        Inventory inv = player.getInventory();
        
        return (inv.firstEmpty() > -1);
    }
    
    private ItemStack getItem(Material mat, int qty){
        return new ItemStack(mat, qty);
    }
    
    private List<ItemStack> getRandomItems(){
        
        List<ItemStack> items = new ArrayList();
        
        //Music Disc
        items.add(getItem(getRandomMaterial(records), 1));
        
        //Skulls
        items.add(getItem(getRandomMaterial(skulls), 1));
        
        //Spawn Eggs
        items.add(getItem(getRandomMaterial(eggs), 1));
        items.add(getItem(getRandomMaterial(eggs), 1));
        items.add(getItem(getRandomMaterial(eggs), 1));
        
        return items;
    }
    
    private Material getRandomMaterial(Material[] matArray){
        
        int i = rand.nextInt(matArray.length);
        
        return matArray[i];
        
    }
    
    private ItemStack getShulker(List<ItemStack> randomItems){
        
        ItemStack shulker = getItem(getRandomMaterial(shulkers), 1);
        
        ItemMeta meta = shulker.getItemMeta();
        BlockStateMeta bsm = (BlockStateMeta) meta;
        ShulkerBox shulkerBox = (ShulkerBox) bsm.getBlockState();
        
        meta.setDisplayName("Santa's Gifts");
        
        Inventory inv = shulkerBox.getInventory();
        
        inv.setContents((ItemStack[]) baseItems);
        
        for(ItemStack i : randomItems){
            inv.addItem(i);
        }
        
        bsm.setBlockState(shulkerBox);
        shulker.setItemMeta(meta);
        
        return shulker;
    }
}
