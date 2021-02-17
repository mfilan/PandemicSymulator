package core;

public enum Direction {

    RIGHT(0),
    LEFT(1),
    DOWN(2),
    UP(3);

    private int animationRow;
    Direction(int animationRow){
        this.animationRow = animationRow;
    }

    /**
     * @param movement
     * @return animation row depending on movement
     */
    public static Direction fromMotion(Movement movement){
        double x = movement.getVector().getX();
        double y = movement.getVector().getY();

        if(x==0 && y> 0) return DOWN;
        if (x < 0 && y == 0) return  LEFT;
        if (x ==0 && y <0) return  UP;
        if (x > 0 && y == 0) return  RIGHT;
        return DOWN;

    }

    public int getAnimationRow(){
        return animationRow;
    }

}
