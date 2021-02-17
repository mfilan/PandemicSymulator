package entity;

public class SymulatorObjectThread extends Thread{
    private SymulatorObject symulatorObject;
    private int sleepTime = 40;

    public SymulatorObjectThread(SymulatorObject symulatorObject) {
        this.symulatorObject = symulatorObject;
    }

    @Override
    public synchronized void run() {
        long time = System.currentTimeMillis();
        while (symulatorObject.isRunning()) {
            try {
                symulatorObject.update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long sleep = sleepTime - (System.currentTimeMillis() - time);
            time += sleepTime;
            if (sleep < 0) {
                sleep = sleepTime;
            }
            try {
                Thread.sleep(sleep);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
