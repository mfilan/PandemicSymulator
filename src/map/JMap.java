package map;

import UI.InformationWindow;
import controller.PersonController;
import core.Position;
import core.RandomString;
import core.Size;
import core.Vector2D;
import entity.*;
import gfx.SpriteLibrary;
import symulator.ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JMap extends JPanel {
    private final SymulatorMap symulatorMap;
    protected SpriteLibrary spriteLibrary;
    private Size size;
    private JLabel background;
    protected List<SymulatorObject> symulatorObjectList;
    private List<Client> clientList;
    private int[][] squareCrossing;
    private int[][] verticalCrossing;
    private int[][] horizontalCrossing;
    private InformationWindow informationWindow;
    private ControlPanel controlPanel;
    private int[][] shopCoor;
    private int[][] wholeSaleCoor;

    public JMap(Size size, SymulatorMap symulatorMap, InformationWindow informationWindow,ControlPanel controlPanel) {
        this.size = size;
        spriteLibrary = new SpriteLibrary();
        this.controlPanel = controlPanel;
        this.clientList = new ArrayList<>();
        this.symulatorMap = symulatorMap;
        setPreferredSize(new Dimension(size.getWidth(),size.getHeight()));
        setFocusable(false);
        background = new JLabel();
        this.informationWindow = informationWindow;
        informationWindow.setJmap(this);
        background.setIcon(symulatorMap.getMapImage(size));
        add(background);
        symulatorObjectList =  Collections.synchronizedList(new ArrayList<>());
        initializeEntities(background);
        initializeCrossings(background);
        initializeShops(background);
        startThreads();
        setVisible(true);

    }

    public int[][] getShopCoor() {
        return shopCoor;
    }

    public int[][] getWholeSaleCoor() {
        return wholeSaleCoor;
    }

    private void initializeShops(JLabel background) {
        shopCoor = new int[][]{{64, 128}, {448, 288}, {160, 512}, {608, 224}, {480, 544}, {608, 608}, {768, 416}, {928, 64}, {928, 480}, {1088, 288}};
//        {
        wholeSaleCoor = new int[][]{{96, 288}, {480, 160}, {1088, 416}};
        for (int i = 0; i <shopCoor.length ; i++) {
            String address = new RandomString().getAlphaNumericString(5);
            String shopName = new RandomString().getAlphaNumericString(5);
            int storageCapacity = ThreadLocalRandom.current().nextInt(5, 10 + 1);
            int maxCapacity = ThreadLocalRandom.current().nextInt(5, 10 + 1);
            RetailShop retailShop = new RetailShop(spriteLibrary,new Position(shopCoor[i][0],shopCoor[i][1]),this,"shop",
                    1000+i,informationWindow,address,storageCapacity,maxCapacity,shopName,controlPanel);
            symulatorObjectList.add(retailShop);
            background.add(retailShop);
        }
        for (int i = 0; i <wholeSaleCoor.length ; i++) {
            String address = new RandomString().getAlphaNumericString(5);
            String shopName = new RandomString().getAlphaNumericString(5);
            int storageCapacity = ThreadLocalRandom.current().nextInt(5, 10 + 1);
            int maxCapacity = ThreadLocalRandom.current().nextInt(5, 10 + 1);
            WholesaleStore wholesaleStore = new WholesaleStore(spriteLibrary,new Position(wholeSaleCoor[i][0],wholeSaleCoor[i][1]),this,"wholesale",
                    2000+i,informationWindow,address,storageCapacity,maxCapacity,shopName,controlPanel);
            wholesaleStore.createNewProducts(6);
//            ;
            System.out.println(wholesaleStore.getCurrentSupply().size());
            symulatorObjectList.add(wholesaleStore);
            background.add(wholesaleStore);
        }
    }

    private void startThreads() {
        for (SymulatorObject symulatorObject:getSymulatorObjectOfClass(MovingEntity.class)) {
            SymulatorObjectThread symulatorObjectThread = new SymulatorObjectThread(symulatorObject);
            symulatorObjectThread.start();
        }
        for (SymulatorObject symulatorObject:getSymulatorObjectOfClass(Shop.class)) {
            SymulatorObjectThread symulatorObjectThread = new SymulatorObjectThread(symulatorObject);
            symulatorObjectThread.start();
        }
        for (WholesaleStore shop:getSymulatorObjectOfClass(WholesaleStore.class)) {
            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
            scheduledExecutorService.scheduleAtFixedRate(shop::startManufacturing, 10, 15,
                    TimeUnit.SECONDS);
        }
        for (RetailShop shop:getSymulatorObjectOfClass(RetailShop.class)) {
            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
            scheduledExecutorService.scheduleAtFixedRate(shop::removeExpiredProducts, 10, 15,
                    TimeUnit.SECONDS);
        }


    }

    private void initializeCrossings(JLabel background) {
//        ,{544,544}
        horizontalCrossing = new int[][]{{128, 128}, {96, 512}, {416, 160}, {416, 544}, {544, 608}, {832, 416}, {160, 288}};
        for (int i = 0; i <horizontalCrossing.length ; i++) {
            Crossing crossing = new Crossing(new Position(horizontalCrossing[i][0],horizontalCrossing[i][1]),new Vector2D(2,1),this);
            symulatorObjectList.add(crossing);
            background.add(crossing);
        }
        verticalCrossing = new int[][]{{448, 352}, {448, 224}, {608, 160}, {608, 288}, {768, 480}, {928, 416}, {928, 544}, {928, 128}, {928, 0}, {1088, 228}, {1088, 352}};
        for (int i = 0; i <verticalCrossing.length ; i++) {
            Crossing crossing = new Crossing(new Position(verticalCrossing[i][0],verticalCrossing[i][1]),new Vector2D(1,2),this);
            symulatorObjectList.add(crossing);
            background.add(crossing);
        }
//
        squareCrossing = new int[][]{{416, 0}, {672, 0}, {0, 224}, {0, 352}, {96, 352}, {96, 672},  {1216, 352}, {416, 608}, {544, 480}, {544, 352}, {736, 288}};
        for (int i = 0; i <squareCrossing.length ; i++) {
            Crossing crossing =new Crossing(new Position(squareCrossing[i][0],squareCrossing[i][1]),new Vector2D(2,2),this);
            symulatorObjectList.add(crossing);
            background.add(crossing);
        }
    }

    public SymulatorMap getSymulatorMap() {
        return symulatorMap;
    }

    private void initializeEntities(JLabel background) {
        initializeClients(controlPanel.getNumOfClients(),background);
//        initializeClients(2,background);
        initializeSuppliers(controlPanel.getNumOfSuppliers(),background);
        setVaccinated(controlPanel.getNumOfVaccinated());
        setMasks((int) controlPanel.getNumOfClients()/5);
        setInfected((int) controlPanel.getNumOfClients());
    }

    private void setInfected(int k) {
        for (int i = 0; i < k; i++) {
            clientList.get(i).setSick(true);
        }
    }


    private void setMasks(int k) {
        for (int i = 0; i < k; i++) {
            clientList.get(i).setMask(true);
        }
    }


    private void setVaccinated(int numOfVaccinated) {
        for (int i = 0; i < numOfVaccinated; i++) {
            clientList.get(i).setVaccinated(true);
        }
    }

    private void initializeSuppliers(int numberOfPeople, JLabel background) {
        for (int i = 0; i < numberOfPeople; i++) {
            String firstName = new RandomString().getAlphaNumericString(5);
            String lastName = new RandomString().getAlphaNumericString(6);
            int ID = ThreadLocalRandom.current().nextInt(500, 1000 + 1);
            int maxCapacity = ThreadLocalRandom.current().nextInt(5, 10 + 1);
            Person person = new Supplier(new PersonController(),spriteLibrary,this,ID,
                    informationWindow,firstName,lastName,maxCapacity,controlPanel);
            Position randomPosition = symulatorMap.getRandomRoadPosition();
            person.setPosition(randomPosition);
            symulatorObjectList.add(person);
            background.add(person);
        }
    }


    private void initializeClients(int numberOfPeople,JLabel background) {
        for (int i = 0; i < numberOfPeople; i++) {
            String firstName = new RandomString().getAlphaNumericString(5);
            String lastName = new RandomString().getAlphaNumericString(6);
            int ID = ThreadLocalRandom.current().nextInt(500, 1000 + 1);
            int maxCapacity = ThreadLocalRandom.current().nextInt(5, 10 + 1);

            Client client = new Client(new PersonController(),spriteLibrary,this,ID,
                    informationWindow,firstName,lastName,maxCapacity,controlPanel);
            Position randomPosition = symulatorMap.getRandomPavementPosition();
            client.setPosition(randomPosition);
            clientList.add(client);
            symulatorObjectList.add(client);
            background.add(client);
        }

    }

    /**
     * return all objects that collide with given symulatorObject
     * @param symulatorObject
     * @return
     */
    public synchronized List<SymulatorObject> getCollidingSymulatorObjects(SymulatorObject symulatorObject) {
        return  symulatorObjectList.stream()
                .filter(other -> other.collidesWith(symulatorObject) && other != symulatorObject)
                .collect(Collectors.toList());
    }

    public <T extends SymulatorObject> List<T> getSymulatorObjectOfClass(Class<T> classToCheck){
        return symulatorObjectList.stream()
                .filter(classToCheck::isInstance)
                .map(symulatorObject -> (T) symulatorObject)
                .collect(Collectors.toList());
    }
    public void createPerson(Boolean bool) {
        String firstName = new RandomString().getAlphaNumericString(5);
        String lastName = new RandomString().getAlphaNumericString(6);
        int ID = ThreadLocalRandom.current().nextInt(500, 1000 + 1);
        int maxCapacity = ThreadLocalRandom.current().nextInt(5, 10 + 1);
        Person person;
        Position randomPosition;
        if (bool) {
            person = new Client(new PersonController(),spriteLibrary,this,ID,
                    informationWindow,firstName,lastName,maxCapacity,controlPanel);
            randomPosition = symulatorMap.getRandomPavementPosition();

        } else {

            person = new Supplier(new PersonController(),spriteLibrary,this,ID,
                    informationWindow,firstName,lastName,maxCapacity,controlPanel);
            randomPosition = symulatorMap.getRandomRoadPosition();
        }

        person.setPosition(randomPosition);
        symulatorObjectList.add(person);
        background.add(person);
        SymulatorObjectThread symulatorObjectThread = new SymulatorObjectThread(person);
        symulatorObjectThread.start();

    }


}
