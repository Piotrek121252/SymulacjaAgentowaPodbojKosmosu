package agentSim.cosmos;

import agentSim.species.Aliens;
import agentSim.species.Species;
import agentSim.species.People;
import agentSim.species.Robots;

import java.util.*;

public class Cosmos {

    private int maxX;
    private int maxY;
    private Map<Species, Coordinates> unitsPosition;
    private Species[][] mapOfSpecies;
    private Planet[][] mapOfPlanets;
    private List<PlanetStats> planetStats;

    public Cosmos(int maxX, int maxY, int numOfSpecies) {
        this.maxX = maxX;
        this.maxY = maxY;
        initalizeState(numOfSpecies);
    }

    private void initalizeState(int numOfSpecies) {
        this.mapOfSpecies = new Species[maxX][maxY];
        this.mapOfPlanets = new Planet[maxX][maxY];
        this.unitsPosition = new HashMap<>();
        this.planetStats = new ArrayList<>();
        for (int i = 0; i < numOfSpecies + 1; i++) {
            planetStats.add(i, new PlanetStats());
        }
    }

    public boolean placePlanet(Planet planet, int x, int y) {
        if (mapOfPlanets[x][y] != null)
            return false;
        else {
            mapOfPlanets[x][y] = planet;

            if (planet.getPlanetId() == 1)
                planetStats.get(0).changeDesertPlanetsOwned(1);
            else if (planet.getPlanetId() == 2)
                planetStats.get(0).changeIcePlanetsOwned(1);

            return true;
        }

    }

    public boolean placeUnit(Species unit, int x, int y) {
        if (mapOfSpecies[x][y] != null)
            return false;
        else {
            mapOfSpecies[x][y] = unit;
            unitsPosition.put(unit, new Coordinates(x, y));
            planetStats.get(unit.getSpeciesId()).changeUnitCount(1);
            return true;
        }

    }

    public int[] collectSimulationStats() {

       int[] stats = new int[planetStats.size()*3];

       for(int i=0, j=0; i<planetStats.size(); i++){
           stats[j++] = planetStats.get(i).getUnitCount();
           stats[j++] = planetStats.get(i).getDesertPlanetsOwned();
           stats[j++] = planetStats.get(i).getIcePlanetsOwned();
       }

        return stats;
    }

    public void printMap() {

        System.out.println("Mapy: \n1.Rozmieszczenie jednostek   \n2.Rozmieszczenie planet (rodzaj)   \n3.Status przejęcia planet\n");

        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (mapOfSpecies[i][j] == null)
                    System.out.print("0");
                else System.out.print(mapOfSpecies[i][j].getSpeciesId());
            }
            System.out.print("        ");

            for (int j = 0; j < maxY; j++) {
                if (mapOfPlanets[i][j] == null)
                    System.out.print("x");
                else System.out.print(mapOfPlanets[i][j].getPlanetId());
            }
            System.out.print("        ");

            for (int j = 0; j < maxY; j++) {
                if (mapOfPlanets[i][j] == null)
                    System.out.print("x");
                else System.out.print(mapOfPlanets[i][j].getPlanetOwnedBy());
            }
            System.out.print("\n");
        }

    }

    public boolean isMovePossible(Species unit, int x1, int y1) {
        Coordinates cords = unitsPosition.get(unit);
        int x = cords.getAxisX() + x1;
        int y = cords.getAxisY() + y1;
        if ((x >= 0 && x < maxX) && (y >= 0 && y < maxY))
            return true;
        else
            return false;

    }

    public void changePosition(Species unit, int x1, int y1) {

        if (x1 == 0 && y1 == 0)
            return;

        Coordinates cords = unitsPosition.get(unit);
        int x = cords.getAxisX() + x1;
        int y = cords.getAxisY() + y1;

        if (mapOfSpecies[x][y] == null) {
            mapOfSpecies[x][y] = unit;
            mapOfSpecies[cords.getAxisX()][cords.getAxisY()] = null;
            unitsPosition.replace(unit, new Coordinates(x, y));

        } else if (mapOfSpecies[x][y].getSpeciesId() != unit.getSpeciesId()) {
            fight(unit, mapOfSpecies[x][y]);

        } else if (mapOfSpecies[x][y].getSpeciesId() == unit.getSpeciesId()) {
            if (maxX * maxY > unitsPosition.size())
                reproduce(unit, mapOfSpecies[x][y]);
        }


    }

    private void fight(Species attacker, Species defender) {

        double attackerStr = attacker.getOffensiveStrength() +
                planetStats.get(attacker.getSpeciesId()).getDesertPlanetsOwned() * 1.5;
        double defenderStr = defender.getDefensiveStrength() +
                planetStats.get(defender.getSpeciesId()).getIcePlanetsOwned() * 2;

        if (attackerStr >= defenderStr) {
            Coordinates cords = unitsPosition.get(defender);
            mapOfSpecies[cords.getAxisX()][cords.getAxisY()] = null;
            unitsPosition.remove(defender, cords);
            defender.killUnit();
            planetStats.get(defender.getSpeciesId()).changeUnitCount(-1);
        } else {
            Coordinates cords = unitsPosition.get(attacker);
            mapOfSpecies[cords.getAxisX()][cords.getAxisY()] = null;
            unitsPosition.remove(attacker, cords);
            attacker.killUnit();
            planetStats.get(attacker.getSpeciesId()).changeUnitCount(-1);
        }

    }

    private void reproduce(Species unit1, Species unit2) {

        if (!(unit1.isReproductionPossible() && unit2.isReproductionPossible())) return;

        unit1.setReproductionTimer(1);
        unit2.setReproductionTimer(1);

        Random rnd = new Random();
        Species newUnit;

        switch (unit1.getSpeciesId()) {
            case 1:
                newUnit = new People(23, 21, this, 1);
                newUnit.setReproductionTimer(2);
                while (!placeUnit(newUnit, rnd.nextInt(maxX), rnd.nextInt(maxY)));
                break;
            case 2:
                newUnit = new Aliens(21, 22, this, 2);
                newUnit.setReproductionTimer(2);
                while (!placeUnit(newUnit, rnd.nextInt(maxX), rnd.nextInt(maxY)));
                break;
            case 3:
                newUnit = new Robots(21, 20, this);
                newUnit.setReproductionTimer(2);
                while (!placeUnit(newUnit, rnd.nextInt(maxX), rnd.nextInt(maxY)));
                break;
        }

    }


    public List<Species> getUnitList() {
        return new ArrayList<>(unitsPosition.keySet());
    }

    public int isStandingOnPlanet(Species unit) {
        Coordinates cords = unitsPosition.get(unit);
        if (mapOfPlanets[cords.getAxisX()][cords.getAxisY()] == null)
            return -1;
        else
            return mapOfPlanets[cords.getAxisX()][cords.getAxisY()].getPlanetOwnedBy();

    }

    public void changePlanetOwner(Species unit) {

        Coordinates cords = unitsPosition.get(unit);
        Planet planet = mapOfPlanets[cords.getAxisX()][cords.getAxisY()];

        if (planet.getPlanetId() == 1) {
            planetStats.get(planet.getPlanetOwnedBy()).changeDesertPlanetsOwned(-1);
            planet.setPlanetOwnedBy(unit.getSpeciesId());
            planetStats.get(unit.getSpeciesId()).changeDesertPlanetsOwned(1);
        } else if (planet.getPlanetId() == 2) {
            planetStats.get(planet.getPlanetOwnedBy()).changeIcePlanetsOwned(-1);
            planet.setPlanetOwnedBy(unit.getSpeciesId());
            planetStats.get(unit.getSpeciesId()).changeIcePlanetsOwned(1);
        }

    }
}
