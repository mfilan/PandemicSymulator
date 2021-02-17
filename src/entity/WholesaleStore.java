package entity;

import UI.InformationWindow;
import core.Position;
import core.RandomString;
import gfx.SpriteLibrary;
import map.JMap;
import symulator.ControlPanel;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class WholesaleStore extends Shop{


    public WholesaleStore(SpriteLibrary spriteLibrary, Position position, JMap map, String name, Integer ID,
                          InformationWindow informationWindow, String address, int storageCapacity, int clientCapacity, String shopName, ControlPanel controlPanel) {
        super(spriteLibrary, position,map, name,ID,
                 informationWindow,address,storageCapacity,clientCapacity, shopName, controlPanel);
    }
    public void createNewProducts(int number_to_create){
        for (int i = 0; i <number_to_create ; i++) {
            String name = new RandomString().getAlphaNumericString(6);
            String brand = new RandomString().getAlphaNumericString(4);
            long expirationDate = new Date().getTime() +ThreadLocalRandom.current().nextInt(5, 250) * 1000L;
            int id = ThreadLocalRandom.current().nextInt(1, 1000 + 1);
            double price = ThreadLocalRandom.current().nextInt(1, 1000 + 1);
            currentSupply.add(new Product(id,name,brand,expirationDate,price));
        }
    }


    /**
     * creates products
     */
    public synchronized void startManufacturing() {
        while (this.getCurrentSupply().size() < this.getStorageCapacity()){
            createNewProducts(1);
        }
    }

    @Override
    public boolean contains(SymulatorObject symulatorObject) {
        return false;
    }
}
