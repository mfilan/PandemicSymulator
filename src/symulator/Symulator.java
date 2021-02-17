package symulator;
//import controller.SymulatorController;
//import core.core.Size;

import UI.InformationWindow;
import core.Size;
import display.Display;
import gfx.SpriteLibrary;
import map.JMap;
import map.SymulatorMap;

import java.awt.*;

public class Symulator {

    private Display display;
    private ControlPanel controlPanel;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int width;
    public int height;
    public static int SPRITE_SIZE = 32;
    private SymulatorMap symulatorMap;
    private SpriteLibrary spriteLibrary;



    public Symulator(int width, int height){
        spriteLibrary = new SpriteLibrary();
        symulatorMap = new SymulatorMap(new Size((width-300)/SPRITE_SIZE,height/SPRITE_SIZE),spriteLibrary);
        display = new Display(width, height);
        this.controlPanel = new ControlPanel(0.6,0.4,
                0.1,0.5,3,4,10,3);
        InformationWindow informationWindow = new InformationWindow(new Size(300,height),controlPanel);
        display.addElements(informationWindow, BorderLayout.EAST);
        display.addElements(new JMap(new Size(width-300,height),symulatorMap,informationWindow,controlPanel),BorderLayout.WEST);
        display.draw();


        this.width = width;
        this.height = height;
    }

}
