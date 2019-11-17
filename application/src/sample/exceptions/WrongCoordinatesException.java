package sample.exceptions;

/**
 * Created by User on 2019-11-17.
 */
public class WrongCoordinatesException extends Exception{

    public WrongCoordinatesException(){
        super();
    }

    public WrongCoordinatesException(String message){
        super(message);
    }

}
