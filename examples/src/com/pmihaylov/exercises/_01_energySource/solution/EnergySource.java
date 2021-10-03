package com.pmihaylov.exercises._01_energySource.solution;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.pmihaylov.Common.ignoreCheckedExceptions;

public class EnergySource {
    private final long MAXLEVEL = 100;
    private long level = MAXLEVEL;
    private long usage = 0;
    private final ReadWriteLock monitor = new ReentrantReadWriteLock();
    private final ScheduledExecutorService replenishTimer =
            Executors.newScheduledThreadPool(10);
    private ScheduledFuture<?> replenishTask;

    private EnergySource() {}

    private void init() {
        replenishTask =
                replenishTimer.scheduleAtFixedRate(this::replenish, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Creates a new instance of the energy source.
     */
    public static EnergySource create() {
        final EnergySource energySource = new EnergySource();
        energySource.init();

        return energySource;
    }

    /**
     * Tears down any resources the class is maintaining
     */
    public void teardown() {
        replenishTimer.shutdown();
        ignoreCheckedExceptions(() -> replenishTimer.awaitTermination(5, TimeUnit.SECONDS));
    }

    /**
     * returns the current energy available.
     */
    public long getUnitsAvailable() {
        monitor.readLock().lock();
        try {
            return level;
        } finally {
            monitor.readLock().unlock();
        }
    }

    /**
     * returns the # of times useEnergy was invoked.
     * @return
     */
    public long getUsageCount() {
        monitor.readLock().lock();
        try {
            return usage;
        } finally {
            monitor.readLock().unlock();
        }
    }

    /**
     * Uses part of the energy source's energy, specified by the "units" parameter.
     * Returns false if there is not enough energy or "units" is negative.
     */
    public boolean useEnergy(final long units) {
        monitor.writeLock().lock();
        try {
            if (units > 0 && level >= units) {
                level -= units;
                usage++;

                return true;
            } else {
                return false;
            }
        } finally {
            monitor.writeLock().unlock();
        }
    }

    /**
     * Stops the energy source from replenishing itself.
     */
    public synchronized void stopEnergySource() {
        replenishTask.cancel(false);
    }

    private void replenish() {
        monitor.writeLock().lock();
        try {
            if (level < MAXLEVEL) { level += 5;  }
        } finally {
            monitor.writeLock().unlock();
        }
    }
}
