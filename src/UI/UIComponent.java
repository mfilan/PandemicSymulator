package UI;

import core.Position;
import core.Size;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class UIComponent extends JLabel{
    protected Position relativePosition;
    protected Position absolutePosition;
    protected Size size;
    protected Spacing marign;
    protected Spacing padding;
    protected List<UIComponent> inner;


    public Position getRelativePosition() {
        return relativePosition;
    }

    public UIComponent() {
        relativePosition = new Position(0,0);
        absolutePosition = new Position(0,400);
        size = new Size(1,1);
        marign = new Spacing(0);
        padding = new Spacing(0);
    }

    public abstract Image getSprite();


    public void setSize(Size size) {
        this.size = size;
    }


    protected abstract void update();
    public List<UIComponent> getInner() {
        return inner;
    }
}
