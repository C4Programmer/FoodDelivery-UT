package FDBackend.Exceptions;

import FDBackend.Configurations.HttpResponse;
import FDBackend.Exceptions.MenuExceptions.MenuRestaurantNotExist;
import FDBackend.Exceptions.RestaurantExceptions.*;
import FDBackend.Exceptions.UserExceptions.UserEmailExist;
import FDBackend.Exceptions.UserExceptions.UserNullObject;
import FDBackend.Exceptions.UserExceptions.UserUsernameExist;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandling {

    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request.";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request.";
    public static final String INCORRECT_CREDENTIALS = "Username / Password incorrect. Please try again.";
    public static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission.";

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
    }
    @ExceptionHandler(UserEmailExist.class)
    public ResponseEntity<HttpResponse> userEmailExist(UserEmailExist exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(UserNullObject.class)
    public ResponseEntity<HttpResponse> userNullObject(UserNullObject exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(UserUsernameExist.class)
    public ResponseEntity<HttpResponse> userUsernameExist(UserUsernameExist exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(){
        return createHttpResponse(HttpStatus.FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception){
        return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage().toUpperCase());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED,supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(){
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,INTERNAL_SERVER_ERROR_MSG);
    }
    @ExceptionHandler(RestaurantDeliveryZoneNullObject.class)
    public ResponseEntity<HttpResponse> restaurantNullObjectSentException(RestaurantDeliveryZoneNullObject exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(RestaurantAdministratorNullObject.class)
    public ResponseEntity<HttpResponse> restaurantAdministratorNullObject(RestaurantAdministratorNullObject exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(RestaurantNameNullObject.class)
    public ResponseEntity<HttpResponse> restaurantNameNullObject(RestaurantNameNullObject exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(RestaurantLocationNullObject.class)
    public ResponseEntity<HttpResponse> restaurantLocationNullObject(RestaurantLocationNullObject exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(RestaurantNameExist.class)
    public ResponseEntity<HttpResponse> restaurantNameExist(RestaurantNameExist exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(RestaurantEmptyDatabase.class)
    public ResponseEntity<HttpResponse> restaurantEmptyDatabase(RestaurantEmptyDatabase exception){
        return createHttpResponse(HttpStatus.NOT_FOUND,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(RestaurantAdminAlreadyAssigned.class)
    public ResponseEntity<HttpResponse> restaurantAdminAlreadyAssigned(RestaurantAdminAlreadyAssigned exception){
        return createHttpResponse(HttpStatus.FORBIDDEN, exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(RestaurantMenuNullObject.class)
    public ResponseEntity<HttpResponse> restaurantMenuNullObject(RestaurantMenuNullObject exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(MenuRestaurantNotExist.class)
    public ResponseEntity<HttpResponse> menuRestaurantNotExist(MenuRestaurantNotExist exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage().toUpperCase());
    }

    //private methods
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        HttpResponse httpResponse =  new HttpResponse(httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase().toUpperCase()
                ,message.toUpperCase());
        return new ResponseEntity<>(httpResponse,httpStatus);
    }
}
