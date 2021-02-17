package UI;

public class Spacing {

    private int top;
    private int down;
    private int right;
    private int left;

    public Spacing(int spacing) {
        this(spacing,spacing); //calling chained constructors
    }

    public Spacing(int horizontal, int vertical) {
        this(vertical,vertical,horizontal,horizontal);
    }

    public Spacing(int top, int down, int right, int left) {
        this.top = top;
        this.down = down;
        this.right = right;
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public int getDown() {
        return down;
    }

    public int getRight() {
        return right;
    }

    public int getLeft() {
        return left;
    }

    public int getVertical(){
        return top+down;
    }
    public int getHorizontal(){
        return left+right;
    }
}
