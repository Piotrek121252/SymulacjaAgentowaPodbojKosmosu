package agentSim.cosmos;

public class PlanetStats {
    private int unitCount;
    private int desertPlanetsOwned;
    private int icePlanetsOwned;


    public void changeUnitCount(int value) {
        this.unitCount += value;
    }
    public void changeDesertPlanetsOwned(int value) {
        desertPlanetsOwned += value;
    }
    public void changeIcePlanetsOwned(int value) {
        icePlanetsOwned += value;
    }
    public int getUnitCount() {
        return unitCount;
    }
    public int getDesertPlanetsOwned() {
        return desertPlanetsOwned;
    }
    public int getIcePlanetsOwned() {
        return icePlanetsOwned;
    }
}
