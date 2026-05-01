package ca.sfu.cmpt276.dungeon.gameobjecthandlers;

import java.util.concurrent.TimeUnit;

import ca.sfu.cmpt276.dungeon.*;


public class ChestThread implements Runnable {

    //private Round round;
    private final ChestHandler chestHandler;

    public ChestThread(/*Round round,*/ ChestHandler chestHandler) {
        //this.round = round;
        this.chestHandler = chestHandler;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            System.out.println("new chest spawner waiting interrupt!");
        }
        SpawnBounds.chestLock.lock();
        try {
            chestHandler.spawn(1);
        } finally {
            SpawnBounds.chestLock.unlock();
        }
    }
}