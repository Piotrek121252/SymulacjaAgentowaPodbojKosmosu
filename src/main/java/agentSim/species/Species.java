package agentSim.species;

public interface Species {
     void move();
     int getSpeciesId();
     int getOffensiveStrength();
     int getDefensiveStrength();
     void killUnit();
     boolean isReproductionPossible();
     void setReproductionTimer(int timer);


}
