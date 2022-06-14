package agentSim.cosmos;

public class Planet  {
    private final int planetId;
    private int planetOwnedBy;

    public Planet(int planetId){
        this.planetId = planetId;
    }

    public int getPlanetId() {
        return planetId;
    }


    public void setPlanetOwnedBy(int planetOwnedBy) {
        this.planetOwnedBy = planetOwnedBy;
    }


    public int getPlanetOwnedBy() {
        return planetOwnedBy;
    }
}
