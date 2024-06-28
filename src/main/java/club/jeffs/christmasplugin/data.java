
package club.jeffs.christmasplugin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class data {
    
    Plugin plugin = null;
    
    public data(Plugin plugin){
        this.plugin = plugin;
    }
    
    
    private Connection connect() {
        // SQLite connection string
        String url = null;
        
        try {
            url = "jdbc:sqlite:" + plugin.getDataFolder().getCanonicalPath() + "/players.db";
        } catch (IOException ex) {
            Logger.getLogger(data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    private void close(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
     public boolean playerInList(Player player){
        String sql = "SELECT uuid FROM players WHERE uuid = '" + player.getUniqueId().toString() + "'";
        int count = 0;
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                count++;
            }
            
            close(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        
        return (count > 0);
    }
     
     public List<OfflinePlayer> getClaimedPlayerList(){
         
        List<OfflinePlayer> players = new ArrayList();
         
        String sql = "SELECT * FROM players;";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                
                players.add(Bukkit.getOfflinePlayer(UUID.fromString(rs.getString(1))));
                
            }

            close(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return players;
     }
     
     public void addPlayer(Player player) {
        String sql = "INSERT INTO players(uuid) VALUES(?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getUniqueId().toString());
            pstmt.executeUpdate();
            
            close(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    

}
