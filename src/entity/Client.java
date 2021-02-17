package entity;

import AI.AIManager;
import UI.InformationWindow;
import controller.PersonController;
import core.Position;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import map.JMap;
import symulator.ControlPanel;

import java.util.concurrent.ThreadLocalRandom;

public class Client extends Person{
    private ControlPanel controlPanel;
    private int visitsBeforeRecovery;
    public Client(PersonController personController, SpriteLibrary spriteLibrary, JMap map, int ID,
                  InformationWindow informationWindow, String firstName, String lastName, int maxCapacity, ControlPanel controlPanel) {
        super(personController, spriteLibrary, map, ID,informationWindow,firstName,lastName, maxCapacity,controlPanel);
        animationManager = new AnimationManager(spriteLibrary.getObject("GreenElf"));
        this.aiManager = new AIManager("pavement");
        this.visitsBeforeRecovery = controlPanel.getVisitsBeforeRecovery();
        this.controlPanel = controlPanel;

    }

    @Override
    public synchronized void update() {
//        System.out.println(visitsBeforeRecovery);
        this.visitsBeforeRecovery = controlPanel.getVisitsBeforeRecovery();
        super.update();
        removeSomeProducts();
        setSick();
    }


    /**
     * buy random number of products if is in shop
     * @param shop
     */
    protected void buyProducts(Shop shop){
        int num_to_buy = ThreadLocalRandom.current().nextInt(1, maxCapacity + 1);
        for (int i = 0; i < num_to_buy; i++) {
            Product product = shop.sellSomeProducts();
            if (product != null){
                currentProducts.add(product);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    private void setSick(){
        if (visitsBeforeRecovery <0){
            this.setSick(false);
            visitsBeforeRecovery = controlPanel.getVisitsBeforeRecovery();
        }
    }

    /**
     * Is infecting other entities in shop according to the transmition rates
     * @param shop
     */
    private void handleVirus(Shop shop){

            for (SymulatorObject person : shop.getObjectsInside()) {
                if (((Person) person).isVaccinated()) {
                    if (Math.random() < controlPanel.getTransmissionVaccinated()) {
                        ((Person) person).setSick(true);
                    }
                } else if(((Person) person).isMask() ){
                    if (Math.random() < controlPanel.getTransmissionRateMas()) {
                        ((Person) person).setSick(true);
                    }
                }else{
                    if (Math.random() < controlPanel.getTransmissionRate()) {
                        ((Person) person).setSick(true);
                    }
                }
            }
    }

    /**
     * checks if person is in shop, if so, performs buying and infecting
     * @param other
     * @throws InterruptedException
     */
    private void handleShops(SymulatorObject other) throws InterruptedException {
        if (other instanceof Shop && other !=this ){
            RetailShop retailShop = ((RetailShop) other);
            Position tempPosition = Position.copyOf(position);
            tempPosition.applyToCheckCollisions(movement);
            if(!isInShop()){
                if(retailShop.enter(this)){
                    if (retailShop.isInShop(this)){
                        this.setInShop(true);
                        this.setVisible(false);
                    }
                }
            }
            if (!retailShop.isInShop(this)){
                retailShop.leave(this);
                this.setInShop(false);
                this.setVisible(true);
            }
            if (isInShop()){
                buyProducts(retailShop);
                handleVirus(retailShop);
                visitsBeforeRecovery--;
            }

        }
    }

    /**
     * check if person's bounds are colliding with other bounds.
     * @param other
     * @throws InterruptedException
     */
    protected synchronized void handleCollisions(SymulatorObject other) throws InterruptedException {
        handleCrossings(other);
        handleShops(other);
        handleMovingObjects(other);


    }
}
