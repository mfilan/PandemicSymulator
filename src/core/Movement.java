package core;

import controller.EntityController;

public class Movement {

    private Vector2D vector;
    private double velocity;

    public Movement(double velocity) {
        this.vector = new Vector2D(0,0);
        this.velocity = velocity;
    }

    /**
     * updates movement vector depending on the controller
     * @param entityController
     */
    public void update(EntityController entityController){
        int deltaX=0;
        int deltaY=0;

        if(entityController.isRequestingUp()){
            deltaY--;
        }
        if(entityController.isRequestingDown()){
            deltaY++;
        }
        if(entityController.isRequestingLeft()){
            deltaX--;
        }
        if(entityController.isRequestingRight()){
            deltaX++;
        }
        vector = new Vector2D(deltaX,deltaY);
        vector.normalize();
        vector.multiply(velocity);
    }

    public Vector2D getVector() {
        return vector;
    }

    public void setVector(Vector2D vector) {
        this.vector = vector;
    }

    public boolean isMoving() {
        return vector.lenght() >0;
    }

    public void stop(boolean stopX, boolean stopY) {
        vector = new Vector2D(
                stopX ? 0: vector.getX(),
                stopY ? 0: vector.getY());
    }
}
