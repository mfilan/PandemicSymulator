package gfx;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {


    private Map<String, SpriteSet> units;
    private Map<String, Image> tiles;

    public SpriteLibrary(){
        units = new HashMap<>();
        tiles = new HashMap<>();
        loadsSpritesFromDisk();
    }

    private void loadsSpritesFromDisk() {
        loadUnits("/sprites/objects");
        loadTiles("/sprites/tiles");
    }

    private void loadTiles(String path) {
        String[] imagesInFolder = getImagesInFolder(path);
        for (String filename: imagesInFolder){
            System.out.println(path + "/" + filename);
            tiles.put(
                    filename.substring(0,filename.length()-4),
                    ImageUtils.loadImage(path + "/" + filename));

        }
    }

    private void loadUnits(String path){
        String[] folderNames = getFolderNames(path);
        for (String folderName: folderNames){
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = path +"/"+folderName;
            String[] sheetsInFolder = getImagesInFolder(pathToFolder);
            for (String sheetName: sheetsInFolder){
                System.out.println(pathToFolder + "/" + sheetName);
                spriteSet.addSheet(
                        sheetName.substring(0,sheetName.length()-4),
                        ImageUtils.loadImage(pathToFolder + "/" + sheetName));

            }
            System.out.println(folderName+ " " + spriteSet);
            units.put(folderName,spriteSet);
        }
    }

    private String[] getImagesInFolder(String basePath) {
        URL resource = SpriteLibrary.class.getResource(basePath);
        File file = new File(resource.getFile());
        return file.list((current,name) -> new File(current,name).isFile());//filter function

    }

    private String[] getFolderNames(String basePath) {
        URL resource = SpriteLibrary.class.getResource(basePath);
        File file = new File(resource.getFile());
        return file.list((current,name) -> new File(current,name).isDirectory());//filter function
    }

    public SpriteSet getObject(String name) {
        return units.get(name);
    }
    public Image getTile(String name) {
        return tiles.get(name);
    }
}
