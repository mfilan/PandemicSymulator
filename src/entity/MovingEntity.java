package entity;

import AI.AIManager;
import UI.InformationWindow;
import controller.PersonController;
import core.*;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import map.JMap;

import javax.swing.*;
import java.awt.*;

public abstract class MovingEntity extends SymulatorObject{

    protected PersonController entityController;
    public Movement movement;
    protected AnimationManager animationManager;
    protected Direction direction;
    protected Rectangle labelPosition;
    protected AIManager aiManager;
    protected Crossing lastCrossing;
    protected JPanel informationWindow;

    protected boolean isOnCrossing = false;
    protected boolean isInShop = false;


    public MovingEntity(PersonController personController, SpriteLibrary spriteLibrary, JMap map, int ID, InformationWindow informationWindow) {
        super(map);
        this.ID = ID;
        this.informationWindow = informationWindow;
        this.entityController = personController;
        this.movement = new Movement(2);
        this.direction = Direction.DOWN;

        this.collisionBoxSize = new Size(32,32);
        collisionBox = CollisionBox.of(position,collisionBoxSize);
        labelPosition = new Rectangle(position.intX(), position.intY(), collisionBoxSize.getWidth(), collisionBoxSize.getHeight());
        this.animationManager = new AnimationManager(spriteLibrary.getObject("GreenElf"));

    }

    public AIManager getAiManager() {
        return aiManager;
    }

    public synchronized void update(){
        handleMotion();
        manageDirection();
        handleCollisions();
        animationManager.update(direction);
        setIcon(animationManager.getSprite());
        position.apply(movement);
        labelPosition.setLocation(position.intX(), position.intY());
        aiManager.update(map.getSymulatorMap(),this);
        setBounds(labelPosition);
        repaint();

    }

    public Direction getDirection() {
        return direction;
    }


    /**
     * performs collision handling in colliding entities
     */
    private synchronized void handleCollisions() {
        map.getCollidingSymulatorObjects(this).forEach(symulatorObject -> {
            try {
                handleCollisions(symulatorObject);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * performs collision handling
     * @param other
     * @throws InterruptedException
     */
    protected abstract void handleCollisions( SymulatorObject other) throws InterruptedException;




    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    /**
     * Returns current animation image
     * @return ImageIcon
     */
    @Override
    public ImageIcon getSprite() {

        return animationManager.getSprite();
    }

    /**
     * Sets the direction from movement
     */
    protected void manageDirection(){
        if (movement.isMoving()){
            this.direction = Direction.fromMotion(movement);
        }
    }

    /**
     * moves entity
     */
    protected void handleMotion() {
        movement.update(entityController);

    }

    public PersonController getController() {
        return entityController;
    }

    @Override
    public boolean collidesWith(SymulatorObject other) {
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithMovement = Position.copyOf(position);
        positionWithMovement.applyToCheckCollisions(movement);
        return CollisionBox.of(positionWithMovement,collisionBoxSize).collidesWith(otherBox);
    }
    @Override
    public boolean contains(SymulatorObject other) {
        return getCollisionBox().contains(other.getCollisionBox());
    }
    public synchronized boolean willColideWithMovingObjectX(MovingEntity other){
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithX = Position.copyOf(position);
        positionWithX.applyX(movement);
        if (CollisionBox.of(positionWithX,collisionBoxSize).collidesWith(otherBox)){
            if (otherBox.getBounds().getX() - CollisionBox.of(positionWithX,collisionBoxSize).getBounds().getX() <=0){
                if (direction == Direction.LEFT){
                    return true;
                }else{
                    return false;
                }
            }else{
                if ( direction == Direction.RIGHT){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public synchronized boolean willColideWithMovingObjectY(SymulatorObject other){
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithY = Position.copyOf(position);
        positionWithY.applyY(movement);
        if (CollisionBox.of(positionWithY,collisionBoxSize).collidesWith(otherBox)){
            if (otherBox.getBounds().getY() - CollisionBox.of(positionWithY,collisionBoxSize).getBounds().getY() <=0){
                if ( direction == Direction.UP){
                    return true;
                }else{
                    return false;
                }
            }else{
                if (  direction == Direction.DOWN){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public Movement getMovement() {
        return movement;
    }

    public boolean willColideX(SymulatorObject other){
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithX = Position.copyOf(position);
        positionWithX.applyToCheckCollisions(movement);;
        return CollisionBox.of(positionWithX,collisionBoxSize).collidesWith(otherBox);
    }

    public boolean willColideY(SymulatorObject other){
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithY = Position.copyOf(position);
        positionWithY.applyToCheckCollisions(movement);
        return CollisionBox.of(positionWithY,collisionBoxSize).collidesWith(otherBox);
    }
    public void setOnCrossing(boolean b) {
        isOnCrossing = b;
    }

    public void setInShop(boolean inShop) {
        isInShop = inShop;
    }

    public boolean isInShop() {
        return isInShop;
    }
}
