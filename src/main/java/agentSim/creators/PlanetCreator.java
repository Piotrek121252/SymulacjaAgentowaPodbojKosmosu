package agentSim.creators;

import agentSim.cosmos.Cosmos;
import agentSim.cosmos.Planet;

import java.util.ArrayList;
import java.util.Random;

public class PlanetCreator {
    private int numOfDesertPlanets;
    private int numOfIcePlanets;
    private int maxX;
    private int maxY;

    public PlanetCreator(int numOfDesertPlanets, int numOfIcePlanets, int maxX, int maxY) {
        this.numOfDesertPlanets = numOfDesertPlanets;
        this.numOfIcePlanets = numOfIcePlanets;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void createPlanets(Cosmos cosmos) {
        ArrayList<Planet> listOfPlanets = new ArrayList<>(numOfDesertPlanets + numOfIcePlanets);
        int index = 0;
        for (int i = 0; i < numOfDesertPlanets; i++) {
            listOfPlanets.add(index, new Planet(1));
            index++;
        }

        for (int i = 0; i < numOfIcePlanets; i++) {
            listOfPlanets.add(index, new Planet(2));
            index++;
        }

        Random rnd = new Random();
        for (Planet planet : listOfPlanets) {

            boolean planetPlacementSuccess = false;
            while (!planetPlacementSuccess) {
                planetPlacementSuccess = cosmos.placePlanet(planet, rnd.nextInt(maxX), rnd.nextInt(maxY));
            }
        }
    }
}
