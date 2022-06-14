package agentSim.species;

import agentSim.cosmos.Cosmos;

public class People extends ASpecies {

    private static final int speciesId = 1;
    private final int timeToColonise;
    private int colonisationTimer;

    public People(int offensiveStrength, int defensiveStrength, Cosmos cosmos, int timeToColonise) {
        super(offensiveStrength, defensiveStrength, cosmos);
        this.timeToColonise = timeToColonise;

    }

    @Override
    protected boolean duringColonisation() {
        if (colonisationTimer>0) {
            colonisationTimer--;
            if(colonisationTimer==0)
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
    public int getSpeciesId() {
        return speciesId;
    }
}
