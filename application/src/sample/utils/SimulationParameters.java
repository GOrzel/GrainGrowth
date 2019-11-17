package sample.utils;

/**
 * Created by User on 2019-11-17.
 */
public class SimulationParameters {

    private Neighbourhoods.Neighbourhood neighbourhood;
    private BoundaryConditions.BoundaryCondition boundaryCondition;

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
}
