package core;

import java.awt.*;

public class CollisionBox {

    private Rectangle bounds;
    private double nearestXPosition;
    private double nearestYPosition;

    public CollisionBox(Rectangle bounds) {
        this.bounds = bounds;
    }


    public static CollisionBox of(Position position, Size size) {
        return new CollisionBox(
                new Rectangle(
                        position.intX(),
                        position.intY(),
                        size.getWidth(),
                        size.getHeight()
                )
        );
    }

    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * @return Vector2D representing center position
     */
    public Vector2D getCenterPosition(){return new Vector2D(bounds.getCenterX(), bounds.getCenterY());}

    /**
     * check if rectangle contains other's rectanlge
     * @param other - CollisionBox of other object
     * @return boolean
     */
    public boolean containsCollisionBox(CollisionBox other){
        return getBounds().contains(other.getBounds());
    }

    /**
     * check if the point is contained in given bounds
     * @param other
     * @return boolean
     */
    public boolean contains(CollisionBox other){
        if (getBounds().getX()<= other.getCenterPosition().getX() && (getBounds().getX()+getBounds().getWidth()) >= other.getCenterPosition().getX()){
            if(getBounds().getY() <= other.getCenterPosition().getY() && (getBounds().getY()+ getBounds().getHeight()) >= other.getCenterPosition().getY()){
                return true;
            }
        }
        return false;
    }

    /**
     * check if two rectangels interesects
     * @param other
     * @return boolean
     */
    public boolean collidesWith(CollisionBox other){
        return bounds.intersects(other.getBounds());
    }


    /**
     * sets Location of bounds to the given location
     * @param x
     * @param y
     */
    public void setLocation(int x,int y) {
        bounds.setLocation(x,y);
    }
}
