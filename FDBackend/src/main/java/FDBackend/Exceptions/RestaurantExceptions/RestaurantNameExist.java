package FDBackend.Exceptions.RestaurantExceptions;

public class RestaurantNameExist extends Exception{
    public RestaurantNameExist(String message){
        super(message);
    }
}
