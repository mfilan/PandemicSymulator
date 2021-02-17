package map;

import core.Position;
import core.Size;
import gfx.SpriteLibrary;
import symulator.Symulator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SymulatorMap {
    private JMap jMap;
    private Tile[][] tiles;
    private int[][] mapArragngement;
    private static List<Tile> tile_list;
    private static List<Integer> pavement;
    private static List<Integer> road;
    private SpriteLibrary spriteLibrary;
    public SymulatorMap(Size size, SpriteLibrary spriteLibrary) {
        this.spriteLibrary = spriteLibrary;
        tiles = new Tile[size.getWidth()][size.getHeight()];
        tile_list = new ArrayList<>();
        initializeTiles(spriteLibrary);
    }


    private void initializeTiles(SpriteLibrary spriteLibrary) {
        mapArragngement = new int[][] {{7,6,6,6,6,6,6,6,6,6,6,6,6,11,10,6,6,6,6,6,6,11,10,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
                {7,9,9,9,9,9,9,9,9,9,9,9,9,11,12,9,9,9,9,9,9,11,12,9,9,9,9,9,9,11,9,9,9,9,9,9,9,9,7,8},
                {7,8,13,13,0,1,1,1,1,1,1,1,1,7,8,13,13,13,13,13,13,7,8,0,1,1,1,1,1,14,15,0,1,1,1,1,1,1,7,8},
                {7,8,13,13,0,3,3,3,3,3,3,0,2,7,8,13,13,13,13,13,13,7,8,0,3,3,3,0,2,16,17,0,3,3,3,3,0,2,7,8},
                {7,8,14,18,4,2,13,13,13,13,13,0,2,7,8,13,13,13,13,13,13,7,8,0,2,13,13,0,2,5,1,1,2,13,13,13,0,2,7,8},
                {7,12,15,19,0,2,1,1,1,13,13,0,2,7,12,20,17,7,6,6,6,6,8,0,2,13,13,3,3,3,3,3,2,13,13,13,0,2,7,8},
                {7,8,13,13,3,3,3,0,2,13,13,0,2,7,8,18,18,7,9,11,9,9,8,0,2,13,13,13,13,13,13,13,13,13,13,13,0,2,7,8},
                {25,27,6,6,6,6,6,0,2,13,13,0,2,7,8,6,6,6,8,14,15,13,13,0,2,13,13,13,13,13,0,1,1,1,1,1,1,2,7,8},
                {12,12,9,9,9,7,8,0,2,13,13,0,2,9,11,9,9,9,8,16,17,13,13,0,2,13,13,13,13,13,0,3,3,3,23,3,3,2,7,8},
                {7,8,13,20,18,28,8,0,2,13,13,0,2,13,14,19,13,0,1,5,1,1,1,4,5,1,1,1,13,13,0,2,13,13,14,16,13,13,7,8},
                {7,8,13,19,19,7,8,0,2,13,13,0,2,13,15,17,13,0,3,3,3,3,3,22,5,3,0,2,13,13,0,2,13,13,19,16,13,13,7,8},
                {25,27,6,11,10,6,8,0,2,13,13,0,2,1,5,1,1,4,4,13,13,13,13,13,13,13,0,2,13,13,0,2,7,6,27,6,6,6,28,28},
                {12,12,9,11,12,9,8,0,2,13,13,3,3,3,3,3,3,23,2,13,13,13,13,13,13,13,0,2,13,13,0,2,7,9,9,9,9,9,11,26},
                {7,8,13,7,8,13,13,0,2,13,13,13,13,13,13,13,13,0,2,13,13,13,13,13,14,18,4,2,1,1,1,2,7,12,20,17,13,13,7,8},
                {7,8,13,7,8,13,13,0,2,13,13,13,13,13,13,13,13,0,2,13,13,13,13,13,15,19,3,3,3,23,3,2,7,8,18,18,13,13,7,8},
                {7,8,13,7,8,13,13,0,2,13,13,13,13,7,6,6,6,21,21,6,6,6,6,6,27,6,6,6,13,14,16,13,7,8,13,13,13,13,7,8},
                {7,8,13,7,12,14,18,4,2,13,13,13,13,7,9,9,9,21,21,9,9,9,9,9,9,9,7,8,13,15,15,13,7,8,13,13,13,13,7,8},
                {7,8,13,7,8,15,19,0,2,13,13,13,13,7,12,14,18,4,2,13,13,13,13,13,13,13,7,8,6,27,6,6,6,8,13,13,13,13,7,8},
                {7,8,13,7,8,13,13,0,2,13,13,13,13,7,8,15,19,0,2,13,13,13,13,13,13,13,9,9,9,9,9,9,9,8,13,13,13,13,7,8},
                {7,8,13,7,8,13,13,0,2,1,1,1,1,21,21,1,1,1,24,14,17,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,7,8},
                {7,8,13,7,8,13,13,3,3,3,3,3,3,21,21,3,3,3,2,18,18,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,7,8},
                {7,8,6,28,27,6,6,6,6,6,6,6,6,28,27,6,6,6,6,27,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,8},
                {9,9,9,9,27,9,9,9,9,9,9,9,9,9,27,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,8}};

//        System.out.println(mapArragngement[0].length + " " +mapArragngement.length);
//        System.out.println(tiles[0].length + " "+ tiles.length);
        for (int x = 0; x < tiles.length ; x++) {
            for (int y = 0; y < tiles[0].length ; y++) {
                if (getTile(mapArragngement[y][x]).size() ==1){
                    Tile curr_tile = new Tile(spriteLibrary,new Position(x*32,y*32),(String) getTile(mapArragngement[y][x]).get(0) );
                    tiles[x][y] = curr_tile;
                    tile_list.add(curr_tile);
                }
                else{
//                    System.out.println();
                    Tile curr_tile = new Tile(spriteLibrary,new Position(x*32,y*32),(String) getTile(mapArragngement[y][x]).get(0), (List) getTile(mapArragngement[y][x]).get(1));
                    tiles[x][y] = curr_tile;
                    tile_list.add(curr_tile);
                }



            }
        }

    }

    public static List<Object> getTile(int number) {
        if(number == 8 || number == 26) return Arrays.asList("road",Arrays.asList(Arrays.asList(0,-1))); //up
        if(number == 7 || number == 25) return Arrays.asList("road",Arrays.asList(Arrays.asList(0,1))); //down 17
        if(number == 6 || number == 10 ) return Arrays.asList("road",Arrays.asList(Arrays.asList(-1,0))); //left
        if(number == 9) return Arrays.asList("road",Arrays.asList(Arrays.asList(1,0))); //right

        if(number == 27) return Arrays.asList("road",Arrays.asList(Arrays.asList(0,-1),Arrays.asList(-1,0),Arrays.asList(1,0))); // upCrossing
        if(number == 11) return Arrays.asList("road",Arrays.asList(Arrays.asList(0,1),Arrays.asList(-1,0),Arrays.asList(1,0))); // downCrossing
        if(number == 28) return Arrays.asList("road",Arrays.asList(Arrays.asList(0,-1),Arrays.asList(0,1),Arrays.asList(-1,0))); //leftCrossing
        if(number == 12) return Arrays.asList("road",Arrays.asList(Arrays.asList(0,-1),Arrays.asList(0,1),Arrays.asList(1,0))); //rightCrossing



        if(number == 2) return Arrays.asList("pavement",Arrays.asList(Arrays.asList(0,-1))); //up
        if(number == 0) return Arrays.asList("pavement",Arrays.asList(Arrays.asList(0,1))); //down
        if(number == 1) return Arrays.asList("pavement",Arrays.asList(Arrays.asList(-1,0))); //left
        if(number == 3 || number == 22) return Arrays.asList("pavement",Arrays.asList(Arrays.asList(1,0))); //right
//        System.out.println(Arrays.asList(Arrays.asList(0,-1),Arrays.asList(0,1),Arrays.asList(-1,0)));

        if(number == 5) return Arrays.asList("pavement",Arrays.asList(Arrays.asList(0,-1),Arrays.asList(-1,0),Arrays.asList(1,0))); //upCrossing
        if(number == 23) return Arrays.asList("pavement",Arrays.asList(Arrays.asList(0,1),Arrays.asList(-1,0),Arrays.asList(1,0))); //downCrossing
        if(number == 4) return Arrays.asList("pavement",Arrays.asList(Arrays.asList(0,1),Arrays.asList(0,-1),Arrays.asList(-1,0))); //leftCrossing
        if(number == 24) return Arrays.asList("pavement",Arrays.asList(Arrays.asList(0,1),Arrays.asList(0,-1),Arrays.asList(1,0))); //rightCrossing
//        if(number == 2) return Arrays.asList("pavement");

        if(number == 16) return Arrays.asList("else",Arrays.asList(Arrays.asList(0,-1))); //up
        if(number == 17) return Arrays.asList("else",Arrays.asList(Arrays.asList(0,1))); //down
        if(number == 18) return Arrays.asList("else",Arrays.asList(Arrays.asList(-1,0))); //left
        if(number == 19) return Arrays.asList("else",Arrays.asList(Arrays.asList(1,0))); //right

        if(number == 14) return Arrays.asList("shop");
        if(number == 15) return Arrays.asList("else");
        if(number == 20) return Arrays.asList("wholesale");
        if(number == 21) return Arrays.asList("crossing");
        if(number == 13) return Arrays.asList("grass");

        return null;

    }
    public Tile[][] getTiles() {
        return tiles;
    }

    public static List<Tile> getTile_list() {
        return tile_list;
    }

    public Position getRandomPavementPosition() {
        List<Position> avaiablePosition = tile_list.stream().filter(tile -> tile.getTileType().equals("pavement")).map(tile -> tile.getCenterPosition()).collect(Collectors.toList());
        Random rand = new Random();
        Position randomElement = avaiablePosition.get(rand.nextInt(avaiablePosition.size()));
        double y = randomElement.intY() * Symulator.SPRITE_SIZE;
        double x = randomElement.intX() * Symulator.SPRITE_SIZE;
        return new Position(x,y);
    }
    public Position getRandomRoadPosition() {
        List<Position> avaiablePosition = tile_list.stream().filter(tile -> tile.getTileType().equals("road")).map(tile -> tile.getCenterPosition()).collect(Collectors.toList());
        Random rand = new Random();
        Position randomElement = avaiablePosition.get(rand.nextInt(avaiablePosition.size()));
        double y = randomElement.intY() * Symulator.SPRITE_SIZE;
        double x = randomElement.intX() * Symulator.SPRITE_SIZE;
        return new Position(x,y);
    }
    public  static  Position getRandomShopPosition(){
        List<Position> avaiablePosition = getTile_list().stream().filter(tile -> tile.getTileType().equals("shop")).map(tile -> tile.getCenterPosition()).collect(Collectors.toList());
        Random rand = new Random();
        Position randomElement = avaiablePosition.get(rand.nextInt(avaiablePosition.size()));
        double y = randomElement.intY() * Symulator.SPRITE_SIZE;
        double x = randomElement.intX() * Symulator.SPRITE_SIZE;
        return new Position(x,y);
    }
    public boolean getWalkableTiles(Tile tile,String tileType){
        List<String> walkable = new ArrayList<>();
        if (tileType.equals("pavement")){
            walkable.add("pavement");
        }
        else{
            walkable.add("road");
        }

        walkable.add("crossing");
        walkable.add("shop");
        walkable.add("wholesale");
        walkable.add("else");
        return walkable.contains(tile.getTileType());
    }

    public List<Tile> getListOfPositionOfType(String name){
        List<Tile> x = tile_list.stream().filter(tile -> getWalkableTiles(tile,name)).collect(Collectors.toList());
        return x;
    }
    public ImageIcon getMapImage(Size size){
        BufferedImage image = new BufferedImage(size.getWidth(),size.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        for (int x = 0; x <tiles.length ; x++) {
            for (int y = 0; y <tiles[0].length ; y++) {
                graphics.drawImage(
                        tiles[x][y].getSprite(),
                        x*Symulator.SPRITE_SIZE,
                        y*Symulator.SPRITE_SIZE,
                        null
                );
            }

        }
        graphics.dispose();
        return new ImageIcon(image);

    }

    public Position getRandomWholeSalePosition() {
        List<Position> avaiablePosition = getTile_list().stream().filter(tile -> tile.getTileType().equals("wholesale")).map(tile -> tile.getCenterPosition()).collect(Collectors.toList());
        Random rand = new Random();
        Position randomElement = avaiablePosition.get(rand.nextInt(avaiablePosition.size()));
        double y = randomElement.intY() * Symulator.SPRITE_SIZE;
        double x = randomElement.intX() * Symulator.SPRITE_SIZE;
        return new Position(x,y);
    }
}
