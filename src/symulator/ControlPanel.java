package symulator;

public class ControlPanel {
    private double transmissionRate;
    private double transmissionRateMas;
    private double transmissionVaccinated;
    private double lockdownTreshold;
    private int visitsBeforeRecovery;
    private int numOfVaccinated;
    private int numOfClients;
    private int numOfSuppliers;

    public ControlPanel(double transmissionRate, double transmissionRateMas, double transmissionVaccinated,
                        double lockdownTreshold, int visitsBeforeRecovery, int numOfVaccinated, int numOfClients,int numOfSuppliers) {
        this.transmissionRate = transmissionRate;
        this.transmissionRateMas = transmissionRateMas;
        this.transmissionVaccinated = transmissionVaccinated;
        this.lockdownTreshold = lockdownTreshold;
        this.visitsBeforeRecovery = visitsBeforeRecovery;
        this.numOfVaccinated = numOfVaccinated;
        this.numOfClients = numOfClients;
        this.numOfSuppliers = numOfSuppliers;
    }

    public int getNumOfSuppliers() {
        return numOfSuppliers;
    }

    public void setNumOfSuppliers(int numOfSuppliers) {
        this.numOfSuppliers = numOfSuppliers;
    }

    public double getTransmissionRate() {
        return transmissionRate;
    }

    public void setTransmissionRate(double transmissionRate) {
        this.transmissionRate = transmissionRate;
    }

    public double getTransmissionRateMas() {
        return transmissionRateMas;
    }

    public void setTransmissionRateMas(double transmissionRateMas) {
        this.transmissionRateMas = transmissionRateMas;
    }

    public double getTransmissionVaccinated() {
        return transmissionVaccinated;
    }

    public void setTransmissionVaccinated(double transmissionVaccinated) {
        this.transmissionVaccinated = transmissionVaccinated;
    }

    public double getLockdownTreshold() {
        return lockdownTreshold;
    }

    public void setLockdownTreshold(double lockdownTreshold) {
        this.lockdownTreshold = lockdownTreshold;
    }

    public int getVisitsBeforeRecovery() {
        return visitsBeforeRecovery;
    }

    public void setVisitsBeforeRecovery(int visitsBeforeRecovery) {
        this.visitsBeforeRecovery = visitsBeforeRecovery;
    }

    public int getNumOfVaccinated() {
        return numOfVaccinated;
    }

    public void setNumOfVaccinated(int numOfVaccinated) {
        this.numOfVaccinated = numOfVaccinated;
    }

    public int getNumOfClients() {
        return numOfClients;
    }

    public void setNumOfClients(int numOfClients) {
        this.numOfClients = numOfClients;
    }
}
