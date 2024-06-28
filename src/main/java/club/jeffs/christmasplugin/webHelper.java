
package club.jeffs.christmasplugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import org.bukkit.Bukkit;

public class webHelper {

    
    public static String getPlayerName(UUID uuid) throws MalformedURLException, IOException{
        
        
        String urlString = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "");
        Bukkit.getLogger().info(uuid.toString().replace("-", ""));
        
        String output  = getUrlContents(urlString);
        
        return output;
    }
    
    private static String getUrlContents(String theUrl)
  {
    StringBuilder content = new StringBuilder();

    // many of these calls can throw exceptions, so i've just
    // wrapped them all in one try/catch statement.
    try
    {
      // create a url object
      URL url = new URL(theUrl);
      
      // create a urlconnection object
      URLConnection urlConnection = url.openConnection();

      // wrap the urlconnection in a bufferedreader
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

      String line;

      // read from the urlconnection via the bufferedreader
      while ((line = bufferedReader.readLine()) != null)
      {
        content.append(line + "\n");
      }
      bufferedReader.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return content.toString();
  }
    
}
