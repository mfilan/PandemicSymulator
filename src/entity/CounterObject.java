package entity;

import core.CollisionBox;
import core.Position;
import core.Size;
import map.JMap;

import java.awt.*;

public abstract class CounterObject extends SymulatorObject {


    @Override
    public int getID() {
        return ID;
    }

    protected Size collisionBoxSize;

    public CounterObject(Position position, JMap map) {
        super(map);
        this.collisionBoxSize = new Size(64,64);
        setPosition(position);
    }
    public void update() throws InterruptedException {


    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle(
                position.intX(),
                position.intY(),
                collisionBoxSize.getWidth(),
                collisionBoxSize.getHeight());
    }


    @Override
    public boolean collidesWith(SymulatorObject other) {
        return getCollisionBox().collidesWith(other.getCollisionBox());
    }
    @Override
    public boolean contains(SymulatorObject other) {
        return getCollisionBox().contains(other.getCollisionBox());
    }

    @Override
    public CollisionBox getCollisionBox(){
        Position tempPosition = Position.copyOf(position);
        return new CollisionBox( new Rectangle(
                tempPosition.intX(),
                tempPosition.intY(),
                collisionBoxSize.getWidth(),
                collisionBoxSize.getHeight()
        ));
    }

    protected void updateCollisionBox(Rectangle labelPosition) {}




}
