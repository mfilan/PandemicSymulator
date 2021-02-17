package entity;

import core.Direction;
import core.Position;
import core.Size;
import core.Vector2D;
import map.JMap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Crossing extends CounterObject{
    private boolean free;
    private Direction direction;
    public Crossing(Position position, Vector2D size, JMap map) {
        super(position,map);
        this.collisionBoxSize = new Size((int) (size.getX()*32), (int) (size.getY()*32));
        this.free = true;
        this.direction = direction.UP;
        setPosition(position);
        setBounds(new Rectangle(position.intX(),position.intY(),collisionBoxSize.getWidth(),collisionBoxSize.getHeight()));
//        setIcon(getSprite());
    }


    @Override
    public ImageIcon getSprite() {
        return getCollisionBoxSprite();
    }

    /**
     * Creates collision bounds image
     * @return ImageIcon
     */
    public ImageIcon getCollisionBoxSprite(){
        BufferedImage image = new BufferedImage(collisionBoxSize.getWidth(),collisionBoxSize.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.red);
        graphics.drawRect(0,0,collisionBoxSize.getWidth()-1,collisionBoxSize.getHeight()-1);
        graphics.dispose();
        return new ImageIcon(image);
    }

    /**
     * check if entity is contained by it's bounds
     * @param movingEntity
     * @return
     */
    public boolean isOnCrossing(MovingEntity movingEntity){
        return this.contains(movingEntity);
    }


    /**
     * Checks if crossing is free
     */
    public synchronized void enterCrossing() {
        while (this.free == false) {
            try {
                wait();

            } catch (InterruptedException e) {
            }
        }
        this.free = false;
        notifyAll();
    }
    public synchronized void leaveCrossing() {
        this.free = true;
        notifyAll();
    }



}
