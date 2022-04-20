package FDBackend.Exceptions.UserExceptions;

public class UserEmailExist extends Exception{
    public UserEmailExist(String message){
        super(message);
    }
}
