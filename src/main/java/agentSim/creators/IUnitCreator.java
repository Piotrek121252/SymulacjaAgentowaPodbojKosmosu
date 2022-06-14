package agentSim.creators;

import agentSim.cosmos.Cosmos;
import agentSim.species.Species;

import java.util.List;

public interface IUnitCreator {

    List<Species> createUnits(Cosmos cosmos);
}
