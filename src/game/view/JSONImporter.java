package game.view;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;


public class JSONImporter {



  // Load sprites using JSON atlas
    public List<BufferedImage> loadFromJSON(String imageFile, String jsonFile) throws Exception {
        BufferedImage sheet = ImageIO.read(new File(imageFile));

        String jsonText = new String(java.nio.file.Files.readAllBytes(
                java.nio.file.Paths.get(jsonFile)
        ));

        JSONObject root = new JSONObject(jsonText);
        JSONArray textures = root.getJSONArray("textures");
        JSONObject first = textures.getJSONObject(0);
        JSONArray frames = first.getJSONArray("frames");
        

        List<BufferedImage> result = new ArrayList<BufferedImage>();

        Set<String> seen = new HashSet<>();

        for (int frameNumber = 0; frameNumber < frames.length(); frameNumber += 2) {
            JSONObject f = frames.getJSONObject(frameNumber).getJSONObject("frame");
            String[] filenames = frames.getJSONObject(frameNumber).getString("filename").split("/");

            


            int x = f.getInt("x");
            int y = f.getInt("y");
            int w = f.getInt("w");
            int h = f.getInt("h");

            // Create a unique signature
            String signature = x + "," + y + "," + w + "," + h;
        
            // Skip if already seen
            if (!seen.contains(signature) && filenames[0].equals("Normal")) {

                
                seen.add(signature);
                if (filenames[1].equals("Idle")) {
                    result.add(0, sheet.getSubimage(x, y, w, h));
                }
                else {
                    result.add(sheet.getSubimage(x, y, w, h));
                }

                
            }


            
        }

        return result;
    }





}