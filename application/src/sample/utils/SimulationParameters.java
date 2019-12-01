package sample.utils;

/**
 * Created by User on 2019-11-17.
 */
public class SimulationParameters {

    private Neighbourhoods.Neighbourhood neighbourhood;
    private BoundaryConditions.BoundaryCondition boundaryCondition;
    private boolean isGCMode;
    int GCChangeChance;

    public SimulationParameters() {
    }


    public Neighbourhoods.Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(Neighbourhoods.Neighbourhood neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public BoundaryConditions.BoundaryCondition getBoundaryCondition() {
        return boundaryCondition;
    }

    public void setBoundaryCondition(BoundaryConditions.BoundaryCondition boundaryCondition) {
        this.boundaryCondition = boundaryCondition;
    }

    public boolean isGCMode() {
        return isGCMode;
    }

    public void setGCMode(boolean GCMode) {
        isGCMode = GCMode;
    }

    public int getGCChangeChance() {
        return GCChangeChance;
    }

    public void setGCChangeChance(int GCChangeChance) {
        this.GCChangeChance = GCChangeChance;
    }
}
