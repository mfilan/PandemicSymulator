package entity;

import core.RandomString;

import java.util.concurrent.ThreadLocalRandom;

public class Car {
    private String brand;
    private int gas;
    private int gasBurnRate = 1;
    private int tankCapacity;

    public Car() {
        this.brand = new RandomString().getAlphaNumericString(4);
        this.gas = ThreadLocalRandom.current().nextInt(500, 1000 + 1);
        this.gasBurnRate = 1;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public int getGasBurnRate() {
        return gasBurnRate;
    }

    public void setGasBurnRate(int gasBurnRate) {
        this.gasBurnRate = gasBurnRate;
    }

    public int getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(int tankCapacity) {
        this.tankCapacity = tankCapacity;
    }
    public void burnGas(){
        gas -= gasBurnRate;
    }

}
