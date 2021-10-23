package com.pmihaylov.exercises._01_energySource;

public class EnergySource {
    // Assignment:
    //
    // EnergySource should maintain two variables - level and usage.
    //  * usage is tracking the number of times useEnergy is invoked.
    //  * level is the current energy available and it should begin at its max value 100
    //
    // Every 1s, the level should increment automatically by 5 units but should not exceed MAXLEVEL.
    //
    // Add or modify fields as necessary.
    // Check out the Hints class if you get stuck.

    private final long MAXLEVEL = 100;
    private long level = MAXLEVEL;
    private long usage = 0;

    /**
     * Creates a new instance of the energy source.
     */
    public static EnergySource create() {
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Tears down any resources the class is maintaining
     */
    public void teardown() {
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * returns the current energy available.
     */
    public long getUnitsAvailable() {
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * returns the # of times useEnergy was invoked.
     * @return
     */
    public long getUsageCount() {
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Uses part of the energy source's energy, specified by the "units" parameter.
     * Returns false if there is not enough energy or "units" is negative.
     */
    public boolean useEnergy(final long units) {
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Stops the energy source from replenishing itself.
     */
    public void stopEnergySource() {
        throw new UnsupportedOperationException("unimplemented");
    }
}
