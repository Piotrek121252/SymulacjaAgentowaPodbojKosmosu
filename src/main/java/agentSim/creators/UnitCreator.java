package agentSim.creators;

import agentSim.cosmos.Cosmos;
import agentSim.species.Aliens;
import agentSim.species.Species;
import agentSim.species.People;
import agentSim.species.Robots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UnitCreator implements IUnitCreator {

    private int numPeople;
    private int numAliens;
    private int numRobots;
    private int maxX;
    private int maxY;


    public UnitCreator(int numPeople, int numAliens, int numRobots, int maxX, int maxY) {
        this.numPeople = numPeople;
        this.numAliens = numAliens;
        this.numRobots = numRobots;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public List<Species> createUnits(Cosmos cosmos) {
        List<Species> listOfUnits = new ArrayList<>(numAliens + numPeople + numRobots);
        int index = 0;
        for (int i = 0; i < numPeople; i++) {
            listOfUnits.add(index, new People(23, 21, cosmos, 1));
            index++;
        }

        for (int i = 0; i < numAliens; i++) {
            listOfUnits.add(index, new Aliens(21, 22, cosmos, 2));
            index++;

        }

        for (int i = 0; i < numRobots; i++) {
            listOfUnits.add(index, new Robots(21, 20, cosmos));
            index++;
        }

        Random rnd = new Random();
        for (Species unit : listOfUnits) {

            boolean unitPlacementSuccess = false;
            while (!unitPlacementSuccess)
                unitPlacementSuccess = cosmos.placeUnit(unit, rnd.nextInt(maxX), rnd.nextInt(maxY));
        }

        return listOfUnits;
    }
}
