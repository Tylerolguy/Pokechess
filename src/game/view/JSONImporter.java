package game.view;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.util.List;
import org.json.JSONObject;

import org.json.JSONArray;


public class JSONImporter {
    private final String imageLocation = "src/game/assets/pokemonImages/";
    private final String name;



    public JSONImporter(String name) {
        this.name = name;
    }




  // Load sprites using JSON atlas
    public List<BufferedImage> loadFramesFromJSON(String imageFile) throws Exception {
        
        BufferedImage sheet = ImageIO.read(new File(imageLocation + imageFile + ".png"));

        String jsonText = new String(java.nio.file.Files.readAllBytes(
                java.nio.file.Paths.get(imageLocation + imageFile + ".json")
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


    //hp
    //attack
    //defense
    //specialAttack
    //specialDefense
    //speed
    //manaGrowth
    //manaMax
    //attackRange




    public int[] getStats() throws IOException {
        int[] stats = new int[10];

        String jsonText = new String(
            java.nio.file.Files.readAllBytes(
                java.nio.file.Paths.get("src/game/gameData/PokemonData.json")
            ));

        JSONObject root = new JSONObject(jsonText);

        // "pokemon" is now an OBJECT, not an ARRAY
        JSONObject allPokemon = root.getJSONObject("pokemon");

        // lookup by NAME (not index)
        JSONObject pokemon = allPokemon.getJSONObject(this.name);

        JSONObject statBlock = pokemon.getJSONObject("stats");

        stats[0] = statBlock.getInt("hp");
        stats[1] = statBlock.getInt("attack");
        stats[2] = statBlock.getInt("defense");
        stats[3] = statBlock.getInt("specialAttack");
        stats[4] = statBlock.getInt("specialDefense");
        stats[5] = statBlock.getInt("speed");
        stats[6] = statBlock.getInt("manaGrowth");
        stats[7] = statBlock.getInt("manaMax");
        stats[8] = statBlock.getInt("attackRange");
        stats[9] = statBlock.getInt("movementPoints");

        return stats;

    }

    //needs to get the tags? if no other strings outside tags, can just convert this to a return String
    // [0] moveCategory
    public String[] getMoveStrings() throws IOException {
        String jsonText = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("src/game/gameData/MoveData.json")));

        JSONObject root = new JSONObject(jsonText);
        JSONObject move = root.getJSONObject(this.name);

        String[] strs = new String[1];

        strs[0] = move.getString("moveCategory");

        return strs;

    }

    //returns
    // [0] targetEnemy
    // [1] targetAlly
    // [2] targetSelf
    public boolean[] getTargetMove() throws IOException {

        String jsonText = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("src/game/gameData/MoveData.json")));

        JSONObject root = new JSONObject(jsonText);
        JSONObject move = root.getJSONObject(this.name);

        boolean[] bools = new boolean[3];

        bools[0] = move.getBoolean("targetEnemy");
        bools[1] = move.getBoolean("targetAlly");
        bools[2] = move.getBoolean("targetSelf");

        return bools;

    }


    public int[] getMoveInts() throws IOException {

        String jsonText = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("src/game/gameData/MoveData.json")));

        JSONObject root = new JSONObject(jsonText);
        JSONObject move = root.getJSONObject(this.name);

        int[] ints = new int[3];

        ints[0] = move.getInt("baseDamage");
        ints[1] = move.getInt("range");
        ints[2] = move.getInt("cost");

        return ints;




    }

    







}