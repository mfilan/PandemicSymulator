package UI;

import core.Size;
import gfx.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UIText extends UIComponent{

    private BufferedImage[][] fontArray;
    private BufferedImage FONTSHEET = null;
    private String text;
    private int wLetter ;
    private  int hLetter;
    private int fontSize;
    private int w;
    private int h;
    private int xOffset = 15;

    public UIText(String text, int x, int y) {
        this.text = text;
        FONTSHEET = (BufferedImage) ImageUtils.loadImage("/sprites/font/font.png");
        w = 10;
        h = 10;
        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() /h;
        fontSize = 20;
        loadFontArray();
        calculateSize();
        setIcon(new ImageIcon(getSprite()));
        setBounds(x,y,size.getWidth(),size.getHeight());
        setVisible(true);
    }
    public UIText(String text,int y) {
        this.text = text;
        FONTSHEET = (BufferedImage) ImageUtils.loadImage("/sprites/font/font.png");
        w = 10;
        h = 10;
        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() /h;
        fontSize = 20;
        loadFontArray();
        calculateSize();
        setIcon(new ImageIcon(getSprite()));
        setBounds(10,y,size.getWidth(),size.getHeight());
        setVisible(true);
    }

    public UIText(String text, int x, int y, int fontSize,int xOffset) {
        this.text = text;
        FONTSHEET = (BufferedImage) ImageUtils.loadImage("/sprites/font/font.png");
        w = 10;
        h = 10;
        this.xOffset = xOffset;
        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() /h;
        this.fontSize = fontSize;

        loadFontArray();
        calculateSize();
        setIcon(new ImageIcon(getSprite()));
        setBounds(x,y,size.getWidth(),size.getHeight());
        setVisible(true);
    }
    public UIText(String text,int y, int fontSize,int xOffset,Size parentSize) {
        this.text = text;
        FONTSHEET = (BufferedImage) ImageUtils.loadImage("/sprites/font/font.png");
        w = 10;
        h = 10;
        this.xOffset = xOffset;
        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() /h;
        this.fontSize = fontSize;

        loadFontArray();
        calculateSize();
        setIcon(new ImageIcon(getSprite()));
        int x =(int) (parentSize.getWidth() - size.getWidth())/2;
        setBounds(x,y,size.getWidth(),size.getHeight());
        setVisible(true);
    }

    public UIText(String text, int i, int i1, int i2, int i3, Size size) {
        this.text = text;
        FONTSHEET = (BufferedImage) ImageUtils.loadImage("/sprites/font/font.png");
        w = 10;
        h = 10;
        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() /h;
        fontSize = 20;
        loadFontArray();
        calculateSize();
        setIcon(new ImageIcon(getSprite()));
        setBounds(0,0, this.size.getWidth(), this.size.getHeight());
        setVisible(true);
    }


    public BufferedImage getLetter(int x, int y) {
        return FONTSHEET.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage getFont(char letter){
        int value = letter;
        int x = value % wLetter;
        int y = value / wLetter;
        return getLetter(x,y);
    }

    @Override
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImageUtils.creatCompatibleImage(size);
        Graphics2D graphics = image.createGraphics();

        double x =  getRelativePosition().getX();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != 32){
                graphics.drawImage(getFont(text.charAt(i)), (int) x, 0, fontSize,fontSize, null);
            }
            x +=xOffset;
        }
        graphics.dispose();
        return image;
    }

    private void loadFontArray() {
        fontArray = new BufferedImage[wLetter][hLetter];

        for (int x = 0; x < wLetter; x++) {
            for (int y = 0; y < hLetter; y++) {
                fontArray[x][y] = getLetter(x, y);
            }

        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void update() {
        calculateSize();
    }

    private void calculateSize() {
        size = new Size(text.length()* fontSize + padding.getHorizontal(),
                fontSize+ padding.getVertical());
    }
    public Size getTextSize() {
        return new Size(text.length()* fontSize + padding.getHorizontal(),
                fontSize+ padding.getVertical());
    }
}
