package map;
//import core.Position;
//import gfx.SpriteLibrary;
//import symulator.Symulator;

import core.Position;
import gfx.SpriteLibrary;
import symulator.Symulator;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Tile {
    private Image sprite;
    private Position centerPosition;
    private Position position;
    private String tileType;
    private List<List<Integer>> avaiableMoves;
    public Tile(SpriteLibrary spriteLibrary, Position position, String name){
        this.sprite = spriteLibrary.getTile(name);
        this.position = position;
        this.tileType = name;

    }
    public Tile(SpriteLibrary spriteLibrary, Position position, String name, List avaiableMoves){
        this.sprite = spriteLibrary.getTile(name);
        this.position = position;
        this.tileType = name;
        this.avaiableMoves = avaiableMoves;


    }

    public List<List<Integer>> getAvaiableMoves() {
        if (avaiableMoves != null){
            return avaiableMoves;
        }
        return  Arrays.asList(Arrays.asList(0,-1),Arrays.asList(-1,0),Arrays.asList(1,0),Arrays.asList(0,1));
    }

    public Image getSprite(){
        return sprite;
    }
    public Position getCenterPosition(){
//        System.out.println("map.Tile positiom: "+position.intX()/symulator.Symulator.SPRITE_SIZE);
        return new Position(this.position.intX()/ Symulator.SPRITE_SIZE,this.position.intY()/ Symulator.SPRITE_SIZE);
    }

    public String getTileType() {
        return tileType;
    }
}
