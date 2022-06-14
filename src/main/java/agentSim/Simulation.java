package agentSim;

import agentSim.cosmos.Cosmos;
import agentSim.creators.IUnitCreator;
import agentSim.creators.PlanetCreator;
import agentSim.creators.UnitCreator;
import agentSim.species.Species;

import java.util.List;

public class Simulation {

    private Cosmos cosmos;
    private List<Species> units;
    private int duration;

    public Simulation(IUnitCreator unitCreator, PlanetCreator planetCreator, Cosmos cosmos, int duration) {
        this.duration = duration;
        this.cosmos = cosmos;
        units = unitCreator.createUnits(cosmos);
        planetCreator.createPlanets(cosmos);

    }

    private boolean checkEndingCondition() {

        if (units.isEmpty())
            return true;

        int id = units.get(0).getSpeciesId();
        for (Species unit : units) {
            if (id != unit.getSpeciesId()) return false;
        }

        return true;
    }

    public void startSimulation() {

        displaySimulationStats(cosmos.collectSimulationStats(), 0);

        for (int i = 1; i <= duration; i++) {

            for (Species unit : units) {
                unit.move();
            }

            units = cosmos.getUnitList();
            displaySimulationStats(cosmos.collectSimulationStats(), i);
            if (checkEndingCondition()) {
                System.out.println("\nWarunek końcowy został spełniony.");
                return;
            }
        }
        System.out.println("\nLimit tur osiągnięty.");
    }

    private void displaySimulationStats(int[] stats, int turn) {

        if(turn == 0) System.out.println("Stan przed rozpoczęciem.\n");
        else System.out.println("\nTura " + turn);
        System.out.println("Frakcje: ");
        System.out.println("1.Ludzie " + " Jednostki na planszy: " + stats[3] + " Pustynne planety: " + stats[4] + " Lodowe planety: " + stats[5]);
        System.out.println("2.Kosmici " + " Jednostki na planszy: " + stats[6] + " Pustynne planety: " + stats[7] + " Lodowe planety: " + stats[8]);
        System.out.println("3.Roboty " + " Jednostki na planszy: " + stats[9] + " Pustynne planety: " + stats[10] + " Lodowe planety: " + stats[11]);
        System.out.println("Nieprzejęte planety: " + " Pustynne: " + stats[1] + " Lodowe: " + stats[2]);
        System.out.println("");
        cosmos.printMap();

    }

    public static void main(String[] args) {
        int[] argInt = new int[args.length];
        for(int i=0; i<args.length; i++) {
            try {
                argInt[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException e){
                System.out.println("Argumentami powinny być liczby typu integer.");
            }

        }

        Cosmos cosmos = new Cosmos(argInt[0], argInt[1], argInt[2]);
        IUnitCreator unitCreator = new UnitCreator(argInt[3], argInt[4], argInt[5], argInt[0], argInt[1]);
        PlanetCreator planetCreator = new PlanetCreator(argInt[6], argInt[7], argInt[0], argInt[1]);
        Simulation simulation = new Simulation(unitCreator, planetCreator, cosmos, argInt[8]);

        simulation.startSimulation();

    }

}
