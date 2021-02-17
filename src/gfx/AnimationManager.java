package gfx;

import core.Direction;
import symulator.Symulator;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class AnimationManager {

    private SpriteSet spriteSet;
    private BufferedImage currentAnimationSheet;
    private int currentFrameTime;
    private int updatePerFrame;
    private int frameIndex;
    private int directionIndex;
    private String currentAnimationName = "";

    public AnimationManager(SpriteSet spriteSet){
        this.spriteSet = spriteSet;
        this.updatePerFrame = 5;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        this.directionIndex = 2;
        playAnimation("linkformatted");
    }

    public ImageIcon getSprite(){
        return new ImageIcon(currentAnimationSheet.getSubimage(
                frameIndex * Symulator.SPRITE_SIZE,
                directionIndex*Symulator.SPRITE_SIZE,
                Symulator.SPRITE_SIZE,
                Symulator.SPRITE_SIZE
        ));
    }

    /**
     * update frame index to decide which image to show
     * @param direction
     */
    public void update(Direction direction){
        currentFrameTime++;
        directionIndex = direction.getAnimationRow();
        if(currentFrameTime >= updatePerFrame){
            currentFrameTime = 0;
            frameIndex ++;

            if(frameIndex >= currentAnimationSheet.getWidth()/Symulator.SPRITE_SIZE){
                frameIndex = 0;
            }
        }
    }


    /**
     * sets appropriate image row as a current Animation row
     * @param name
     */
    public void playAnimation(String name){
        if(!name.equals(currentAnimationName)){
            this.currentAnimationSheet = (BufferedImage) spriteSet.get(name);
            currentAnimationName = name;
            frameIndex = 0;
        }

    }
}
