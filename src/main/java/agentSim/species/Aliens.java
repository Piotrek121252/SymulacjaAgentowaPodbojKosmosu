package agentSim.species;

import agentSim.cosmos.Cosmos;

import java.util.Random;

public class Aliens extends ASpecies {

    private static final int speciesId = 2;
    private final int timeToColonise;
    private int colonisationTimer;

    public Aliens(int offensiveStrength, int defensiveStrength, Cosmos cosmos, int timeToColonise) {
        super(offensiveStrength, defensiveStrength, cosmos);
        this.timeToColonise = timeToColonise;
    }

    @Override
    protected boolean duringColonisation() {
        if (colonisationTimer > 0) {
            colonisationTimer--;
            if (colonisationTimer == 0)
                cosmos.changePlanetOwner(this);
                return true;
        } else
            return false;
    }

    @Override
    protected boolean checkIfColonisationIsPossible() {
        int planetCapturedBy = cosmos.isStandingOnPlanet(this);
        if ((planetCapturedBy >= 0) && (planetCapturedBy != this.getSpeciesId())) {
            colonisationTimer = timeToColonise;
            return true;
        } else
            return false;
    }

    @Override
    protected int[] pickLocationToMove() {
        int x;
        int y;
        Random rnd = new Random();

        do {
            x = rnd.nextInt(4) - 2;
            y = rnd.nextInt(4) - 2;
        } while (!cosmos.isMovePossible(this, x, y));

        int[] locationCoordinates = new int[2];
        locationCoordinates[0] = x;
        locationCoordinates[1] = y;

        return locationCoordinates;
    }

    @Override
    public int getSpeciesId() {
        return speciesId;
    }
}
