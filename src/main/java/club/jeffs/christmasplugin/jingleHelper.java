
package club.jeffs.christmasplugin;

import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class jingleHelper {

    public void playJingle(Player player){
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
        player.spawnParticle(Particle.HEART, player.getLocation().add(0, 2, 0), 1);
        player.spawnParticle(Particle.CRIT, player.getLocation().add(0, 1.6, 0), 9);
    }
    
}
