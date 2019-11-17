package sample.exceptions;

/**
 * Created by User on 2019-11-17.
 */
public class NoBoundaryConditionSet extends Exception {

    public NoBoundaryConditionSet(){
        super();
    }

    public NoBoundaryConditionSet(String message){
        super(message);
    }
}
