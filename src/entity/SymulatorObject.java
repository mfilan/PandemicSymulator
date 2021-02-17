package entity;

import core.CollisionBox;
import core.Position;
import core.Size;
import map.JMap;

import javax.swing.*;

public abstract class SymulatorObject extends JLabel {
    protected Position position;
    protected Size size;
    protected Size collisionBoxSize;
    protected int ID;
    protected JMap map;
    protected CollisionBox collisionBox;
    protected boolean running = true;

    public SymulatorObject(JMap map) {
        this.position = new Position(32,32);
        this.size = new Size(50,50);
        this.map = map;
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * handles threads
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    public abstract ImageIcon getSprite();

    public abstract void update() throws InterruptedException;


    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public abstract CollisionBox getCollisionBox();


    public abstract boolean collidesWith(SymulatorObject other);
    public abstract boolean contains(SymulatorObject other);
    public int getID() {
        return ID;
    }

}
