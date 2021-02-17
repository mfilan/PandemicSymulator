package entity;
import UI.InformationWindow;
import core.Position;
import core.Size;
import gfx.SpriteLibrary;
import map.JMap;
import symulator.ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public abstract class Shop extends CounterObject{
    private final InformationWindow informationWindow;
    private final ControlPanel controlPanel;
    private Image sprite;
    protected String name;
    protected String shopName;
    protected String address;
    protected int clientsNow =0;
    protected int clientCapacity = 5;
    private int lockdownClientCapacity;
    private int nolockdownClientCapacity;
    private boolean lockdown;
    protected List<SymulatorObject> objectsInside = Collections.synchronizedList(new ArrayList<SymulatorObject>());


    protected int storageCapacity;
    protected ArrayList<Product> currentSupply = new ArrayList<Product>();
    private Integer ID;

    public Shop(SpriteLibrary spriteLibrary, Position position, JMap map, String name, Integer id,
                InformationWindow informationWindow, String address, int storageCapacity, int clientCapacity, String shopName, ControlPanel controlPanel) {
        super(position,map);
        this.collisionBoxSize = new Size(64,64);
        this.sprite = spriteLibrary.getObject("shop").get(name);
        this.ID = id;
        this.controlPanel = controlPanel;
        this.clientCapacity = clientCapacity;
        this.storageCapacity = storageCapacity;
        this.nolockdownClientCapacity = clientCapacity;
        this.address = address;
        this.shopName = shopName;
        this.informationWindow = informationWindow;
        this.lockdownClientCapacity = (int) ( clientCapacity *controlPanel.getLockdownTreshold());
        setPosition(position);
        setBounds(new Rectangle(position.intX(),position.intY(),64,64));
        setIcon(getSprite());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                informationWindow.updateShopInfo(Shop.this);

            }
        });
    }

    public void update(){
        lockdownClientCapacity = (int) ( clientCapacity *controlPanel.getLockdownTreshold());
        checkLockdown();
    }

    /**
     * check if # infected/# all  is greater than threshold, if so, sets threshold
     */
    public void checkLockdown(){
        if (objectsInside.size()!=0){
            int infected = objectsInside.stream().map(symulatorObject -> ((Person)symulatorObject)).
                    filter(person -> person.isSick()).collect(Collectors.toList()).size();
            if (infected / objectsInside.size() > controlPanel.getLockdownTreshold()) {
                setLockdown(true);
            } else {
                setLockdown(false);
            }
        }

    }

    /**
     * @return all objects that are contained inside
     */
    public List<SymulatorObject> getObjectsInside() {
        return objectsInside;
    }

    @Override
    public String getName() {
        return shopName;
    }

    public int getClientCapacity() {
        return clientCapacity;
    }

    /**
     * check if entity can enter the shop, if so counter ++
     * @param person
     * @return
     */
    public synchronized boolean enter(Person person) {
        if (clientsNow < clientCapacity) {
            clientsNow++;
            objectsInside.add(person);

            return true;
        }
        return false;
    }

    /**
     * removes entity from the currently inisde list
     * @param person
     */
    public synchronized void leave(Person person) {

        clientsNow--;
        objectsInside.remove(person);
    }
    public boolean getLockdown() {
        return lockdown;
    }

    /**
     * sets lockdown
     * @param l
     */
    public void setLockdown(boolean l) {
        lockdown = l;
        if (lockdown) {
            clientCapacity = lockdownClientCapacity;
        } else {
            clientCapacity = nolockdownClientCapacity;
        }
    }


    public ArrayList<Product> getCurrentSupply() {
        return currentSupply;
    }

    public void removeProduct(Product product) {
        currentSupply.remove(product);
    }

    public void addProduct(Product product) {
        currentSupply.add(product);
    }

    public String getAddress() {
        return address;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    @Override
    public ImageIcon getSprite() {
        return new ImageIcon(this.sprite);
    }

    public boolean isInShop(MovingEntity movingEntity){
        return  getCollisionBox().containsCollisionBox(movingEntity.getCollisionBox());
    }

    public int getID() {
        return ID;
    }


    /**
     * Remove and returns products to sell
     * @return
     */
    public synchronized Product sellSomeProducts() {
        ArrayList<Product> currentProducts = this.getCurrentSupply();
        if (currentProducts.size()>0){
            Product soldProduct = currentProducts.get(0);
            this.removeProduct(soldProduct);
            return soldProduct;
        }
        return null;
    }


}
