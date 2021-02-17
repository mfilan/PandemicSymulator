package entity;

import AI.AIManager;
import UI.InformationWindow;
import controller.PersonController;
import core.Position;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import map.JMap;
import symulator.ControlPanel;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Supplier extends Person{

    private Car car;

    public Supplier(PersonController personController, SpriteLibrary spriteLibrary, JMap map, int ID,
                    InformationWindow informationWindow, String firstName, String lastName, int maxCapacity, ControlPanel controlPanel) {
        super(personController, spriteLibrary, map, ID,informationWindow,firstName,lastName, maxCapacity, controlPanel);
        animationManager = new AnimationManager(spriteLibrary.getObject("GreenElf"));
        aiManager = new AIManager("road");
        this.car = new Car();

        this.currentProducts = new ArrayList<>();
    }

    public Car getCar() {
        return car;
    }

    @Override
    public synchronized void update() {
        super.update();
        getCar().burnGas();
    }


    /**
     * gets product from the wholesale
     * @param shop
     * @throws InterruptedException
     */
    public void getProducts(WholesaleStore shop) throws InterruptedException {
        while (currentProducts.size() < maxCapacity && shop.getCurrentSupply().size() >0){
            Product prod = shop.sellSomeProducts();
            if (prod != null) {
                currentProducts.add(prod);
            sleep(200);
        }

        }

    }

    /**
     * sells products to retail shops
     * @param shop
     * @throws InterruptedException
     */
    public void giveProducts(RetailShop shop) throws InterruptedException {
//        System.out.println(shop.getCurrentSupply().size());
        while (currentProducts.size() >0 && (shop.getCurrentSupply().size() < shop.getStorageCapacity())){
            Product product = currentProducts.get(0);
            currentProducts.remove(product);
            shop.addProduct(product);
            sleep(200);
        }


    }
    protected synchronized void handleCollisions(SymulatorObject other) throws InterruptedException {
        handleCrossings(other);
        handleShops(other);
        handleMovingObjects(other);
    }
    private void handleShops(SymulatorObject other) throws InterruptedException {
        if (other instanceof Shop && other !=this ){
            Position tempPosition = Position.copyOf(position);
            tempPosition.applyToCheckCollisions(movement);
            if(!isInShop()){
                if(((Shop) other).enter(this)){
                    if (((Shop) other).isInShop(this)){
                        this.setInShop(true);
                        this.setVisible(false);
                        sleep(1000);
                    }
                }
            }
            if (!((Shop) other).isInShop(this)){
                ((Shop) other).leave(this);
                this.setInShop(false);
                this.setVisible(true);
            }
            if (isInShop()){
                if(other instanceof RetailShop){
                    giveProducts(((RetailShop) other));
                }else if(other instanceof WholesaleStore){
                    getProducts(((WholesaleStore) other));
                }
                getCar().setGas(getCar().getTankCapacity());

            }

        }
    }



}
