package entity;

import UI.InformationWindow;
import controller.PersonController;
import core.Direction;
import gfx.SpriteLibrary;
import map.JMap;
import symulator.ControlPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Person extends MovingEntity {
    protected ArrayList<Product> currentProducts;
    protected int maxCapacity = 5;
    protected boolean sick;
    protected boolean mask;
    protected boolean vaccinated;
    protected int ID;
    protected String firstName;
    protected String lastName;
    protected boolean leaving =false;


    public Person(PersonController personController, SpriteLibrary spriteLibrary, JMap map, int ID,
                  InformationWindow informationWindow, String firstName, String lastName, int maxCapacity, ControlPanel controlPanel) {
        super(personController, spriteLibrary,map,ID,informationWindow);
        this.sick = sick;
        this.mask = mask;
        this.vaccinated = vaccinated;
        this.maxCapacity = maxCapacity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.currentProducts = new ArrayList<>();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                informationWindow.updatePersonInfo(Person.this);

            }
        });
    }


    public ArrayList<Product> getCurrentProducts() {
        return currentProducts;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isSick() {
        return sick;
    }

    public void setSick(boolean sick) {
        this.sick = sick;
    }

    public boolean isMask() {
        return mask;
    }

    public void setMask(boolean mask) {
        this.mask = mask;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    protected void handleCrossings(SymulatorObject other){
        if (other instanceof Crossing){
            if (!isOnCrossing){
                if (((Crossing) other).isOnCrossing(this)){
                    ((Crossing) other).enterCrossing();
                    isOnCrossing = true;
                }
            }else if (!((Crossing) other).isOnCrossing(this) && isOnCrossing){
                isOnCrossing = false;
                lastCrossing = ((Crossing) other);
                ((Crossing) other).leaveCrossing();
            }

        }
    }
    private boolean getLeaving(){
        return leaving;
    }

    @Override
    public synchronized void update() {
        super.update();
        removeSomeProducts();
        isLeaving();
    }

    protected void isLeaving(){
        for (int[] positon: map.getShopCoor()) {
            if (this.position.intX() == positon[0] && this.position.intY() == positon[1]){
                leaving = true;
                return;
            }
        }
        for (int[] positon: map.getWholeSaleCoor()) {
            if (this.position.intX() == positon[0] && this.position.intY() == positon[1]){
                leaving = true;
                return;
            }
        }
        leaving = false;
    }


    /**
     * stops if it collieds with other bounds, right-hand traffic implemented
     * @param other
     */
    protected void handleMovingObjects(SymulatorObject other){
        if (other instanceof MovingEntity && other !=this ){
            if (willColideWithMovingObjectX( (MovingEntity) other) || willColideWithMovingObjectY(((MovingEntity) other))){
                if(!(direction == Direction.RIGHT && ((MovingEntity) other).getDirection() == Direction.DOWN)&&
                        !(direction == Direction.UP && ((MovingEntity) other).getDirection() == Direction.RIGHT)&&
                        !(direction == Direction.LEFT && ((MovingEntity) other).getDirection() == Direction.UP)&&
                        !(direction == Direction.DOWN && ((MovingEntity) other).getDirection() == Direction.LEFT)&&
                        (!((MovingEntity) other).isInShop() && !((Person) other).getLeaving()) && !isOnCrossing ){
                    movement.stop(true,true);
                }

            }

        }


    }

    /**
     * removes random number of elements from the trank/capacity
     */
    protected void removeSomeProducts(){
        if ( currentProducts.size() > maxCapacity ){
            int num_to_remove = ThreadLocalRandom.current().nextInt(currentProducts.size() - maxCapacity, currentProducts.size());
            for (int i = 0; i < num_to_remove; i++) {
                currentProducts.remove(0);
            }
        }

    }

    /**
     * removes the entity
     */
    public void deleteMe(){
        List<Person> ls = this.map.getSymulatorObjectOfClass(Person.class);
        this.setVisible(false);
        this.setRunning(false);
        ls.remove(this);
    }

}
