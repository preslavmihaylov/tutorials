package com.pmihaylov.exercises._01_energySource;

import static com.pmihaylov.Common.ignoreCheckedExceptions;

// Uncomment this line to see how the solution should work...
// import com.pmihaylov.exercises._01_energySource.solution.EnergySource;

public class Main {
    public static void main(String[] args) {
        EnergySource source = EnergySource.create();

        while (source.getUsageCount() < 10) {
            System.out.println("Using energy source...");
            System.out.printf("Units available: %d\n", source.getUnitsAvailable());
            System.out.printf("Usages: %d\n", source.getUsageCount());

            if (!source.useEnergy(20)) {
                System.out.println("couldn't use energy source. Waiting for it to replenish...");
                ignoreCheckedExceptions(() -> Thread.sleep(5000));
            } else {
                ignoreCheckedExceptions(() -> Thread.sleep(1000));
            }
        }

        System.out.println();
        System.out.println("Phase 1 complete!");
        System.out.println();
        System.out.println("Waiting for energy source to replenish a bit...");
        ignoreCheckedExceptions(() -> Thread.sleep(5000));

        System.out.println("Stopping energy source...");
        source.stopEnergySource();
        while (source.getUnitsAvailable() >= 5) {
            System.out.println("Using energy source...");
            System.out.printf("Units available: %d\n", source.getUnitsAvailable());
            System.out.printf("Usages: %d\n", source.getUsageCount());

            source.useEnergy(5);
            ignoreCheckedExceptions(() -> Thread.sleep(1000));
        }

        System.out.println("Exiting...");
        source.teardown();
    }
}
