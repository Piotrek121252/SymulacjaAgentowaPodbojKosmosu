package agentSim.species;

import agentSim.cosmos.Cosmos;

public class Robots extends ASpecies {

    private static final int speciesId = 3;

    public Robots(int offensiveStrength, int defensiveStrength, Cosmos cosmos) {
        super(offensiveStrength, defensiveStrength, cosmos);
    }

    @Override
    protected boolean duringColonisation() {
        return false;
    }

    @Override
    protected boolean checkIfColonisationIsPossible() {
        int planetCapturedBy = cosmos.isStandingOnPlanet(this);
        if ((planetCapturedBy >= 0) && (planetCapturedBy != this.getSpeciesId())) {
            cosmos.changePlanetOwner(this);
            return true;
        } else
            return false;
    }

    @Override
    public int getSpeciesId() {
        return speciesId;
    }

}

