package gfx;

//import core.Size;

import core.Size;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {
    public static Image loadImage(String filePath) {
        try {
            Image imageFromDisk = ImageIO.read(ImageUtils.class.getResource(filePath));
            BufferedImage compatibleImage =(BufferedImage) creatCompatibleImage(new Size(imageFromDisk.getWidth(null),imageFromDisk.getHeight(null)));
            Graphics2D graphics = compatibleImage.createGraphics();
            graphics.drawImage(imageFromDisk,0,0,null);
            graphics.dispose();
            return compatibleImage;

        } catch (IOException e) {
            System.out.println("ERROR LOADING FILE: " + filePath);
        }

        return null;
    }

    /**
     * creates compatible image according to the OS
     * @param size
     * @return
     */
    public static Image creatCompatibleImage(Size size) {
        GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        return graphicsConfiguration.createCompatibleImage(size.getWidth(), size.getHeight(), 2);
    }
}

