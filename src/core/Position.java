package core;

import symulator.Symulator;

public class Position {

    public static double PROXIMITY_RANGE = 0.3;

    private double x;
    private double y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Position copyOf(Position position) {
        return new Position(position.getX(), position.getY());
    }


    public int intX(){
        return (int) Math.round(x);
    }
    public int intY(){
        return (int) Math.round(y);
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int gridX(){
        return (int) x/ Symulator.SPRITE_SIZE;
    }
    public int gridY(){
        return (int) y/ Symulator.SPRITE_SIZE;
    }

    public static Position ofGridPosition(int gridX,int gridY){
        return new Position(gridX* Symulator.SPRITE_SIZE,gridY* Symulator.SPRITE_SIZE);
    }

    public void apply(Movement movement) {
        core.Vector2D vector = movement.getVector();
        x += vector.getX();
        y += vector.getY();
    }

    /**
     * applies multiplied movement
     * @param movement
     */
    public void applyToCheckCollisions(Movement movement) {
        core.Vector2D vector = movement.getVector();
        x += vector.getX()*8;
        y += vector.getY()*8;
    }

    public boolean isInRangeOf(Position position) {
        return Math.abs(x-position.getX()) < Position.PROXIMITY_RANGE && Math.abs(y-position.getY()) < Position.PROXIMITY_RANGE;
    }
//
    public void applyX(Movement movement) {
        x += movement.getVector().getX();
    }
    public void applyY(Movement movement) {
        y += movement.getVector().getY();
    }
}
