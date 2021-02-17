package entity;

import UI.InformationWindow;
import core.Position;
import gfx.SpriteLibrary;
import map.JMap;
import symulator.ControlPanel;

import java.util.Date;

public class RetailShop extends Shop{

    public RetailShop(SpriteLibrary spriteLibrary, Position position, JMap map, String name, Integer ID,
                      InformationWindow informationWindow, String address, int storageCapacity, int clientCapacity, String shopName, ControlPanel controlPanel) {
        super(spriteLibrary, position,map, name,ID,
                 informationWindow,address,storageCapacity,clientCapacity,shopName,controlPanel);

    }

    /**
     * removes expired products
     */
    public synchronized void removeExpiredProducts() {
        this.currentSupply.removeIf(product -> product.getBeforeDate() < new Date().getTime() + 1000);
    }





}
