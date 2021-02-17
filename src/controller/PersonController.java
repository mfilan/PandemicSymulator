package controller;


import core.Position;

public class PersonController implements EntityController {

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    @Override
    public boolean isRequestingUp() {
        return up;
    }

    @Override
    public boolean isRequestingDown() {
        return down;
    }

    @Override
    public boolean isRequestingLeft() {
        return left;
    }

    @Override
    public boolean isRequestingRight() {
        return right;
    }

    /**
     * check in which direction there should be movement
     * @param target - target position
     * @param current - current position
     */
    public void moveToTarget(Position target, Position current) {
        double deltaX = target.getX() - current.getX();
        double deltaY = target.getY() - current.getY();
        up = deltaY < 0 && Math.abs(deltaY) > Position.PROXIMITY_RANGE;
        down = deltaY >0 && Math.abs(deltaY) > Position.PROXIMITY_RANGE;
        left = deltaX <0 && Math.abs(deltaX) > Position.PROXIMITY_RANGE;
        right = deltaX >0 && Math.abs(deltaX) > Position.PROXIMITY_RANGE;

    }

    public void stop() {
        up = false;
        down = false;
        left = false;
        right = false;
    }
}
