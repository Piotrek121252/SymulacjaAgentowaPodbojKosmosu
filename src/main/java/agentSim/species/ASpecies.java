package agentSim.species;


import agentSim.cosmos.Cosmos;

import java.util.Random;

public abstract class ASpecies implements Species {
    private final int offensiveStrength;
    private final int defensiveStrength;
    protected final Cosmos cosmos;
    protected boolean isAlive;
    private int reproductionTimer;


    public ASpecies(int offensiveStrength, int defensiveStrength, Cosmos cosmos) {

        this.offensiveStrength = offensiveStrength;
        this.defensiveStrength = defensiveStrength;
        this.cosmos = cosmos;
        isAlive = true;
    }

    public void killUnit() {
        isAlive = false;
    }

    public void move() {

        if (!isAlive) return;

        if (reproductionTimer > 0) reproductionTimer--;

        if (duringColonisation()) return;

        if (checkIfColonisationIsPossible()) return;

        int[] locationCoordinates = pickLocationToMove();

        cosmos.changePosition(this, locationCoordinates[0], locationCoordinates[1]);

    }

    protected abstract boolean duringColonisation();

    protected abstract boolean checkIfColonisationIsPossible();

    protected int[] pickLocationToMove() {
        int x;
        int y;
        Random rnd = new Random();

        do {
            x = rnd.nextInt(3) - 1;
            y = rnd.nextInt(3) - 1;
        } while (!cosmos.isMovePossible(this, x, y));

        int[] locationCoordinates = new int[2];
        locationCoordinates[0] = x;
        locationCoordinates[1] = y;

        return locationCoordinates;
    }

    @Override
    public final boolean isReproductionPossible() {
        if(reproductionTimer > 0) return false;
        else
            return true;
    }

    @Override
    public final void setReproductionTimer(int timer) {
        reproductionTimer = timer;
    }

    @Override
    public int getOffensiveStrength() {
        return this.offensiveStrength;
    }

    @Override
    public int getDefensiveStrength() {
        return this.defensiveStrength;
    }
}
