package FDBackend.Exceptions.RestaurantExceptions;

public class RestaurantEmptyDatabase extends Exception{
    public RestaurantEmptyDatabase(String message){
        super(message);
    }
}
