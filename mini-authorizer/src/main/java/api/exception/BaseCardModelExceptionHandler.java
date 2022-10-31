package api.exception;

import api.model.errors.CardCommonErrors;
import api.model.errors.CardErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;

public abstract class BaseCardModelExceptionHandler {

    @ExceptionHandler(CardModelException.class)
    public ResponseEntity<Object> handleModelException(CardModelException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getModel(), HttpStatus.resolve(ex.getModel().getStatus()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        HttpStatus status = ex.getStatus();
        CardErrorModel cardErrorModel = new CardErrorModel(status.value(), status.value() + "000", ex.getReason());
        return new ResponseEntity<>(cardErrorModel, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        CardErrorModel cardErrorModel = CardCommonErrors.INVALID_PARAMETERS;
        cardErrorModel.setDetails("Required request body is missing");
        return new ResponseEntity<>(cardErrorModel, HttpStatus.resolve(cardErrorModel.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        StringBuffer sb = new StringBuffer();
        Iterator<ObjectError> errorsIterator = ex.getBindingResult().getAllErrors().iterator();
        while(errorsIterator.hasNext()) {
            ObjectError error = errorsIterator.next();
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            sb.append(String.format("%s: %s", field, message));
            if (errorsIterator.hasNext()) {
                sb.append(", ");
            }
        }
        CardErrorModel cardErrorModel = CardCommonErrors.INVALID_PARAMETERS;
        cardErrorModel.setDetails(sb.toString());
        return new ResponseEntity<>(cardErrorModel, HttpStatus.resolve(cardErrorModel.getStatus()));
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Object> handleBindException(BindException ex, WebRequest request) {
        StringBuffer sb = new StringBuffer();
        Iterator<ObjectError> errorsIterator = ex.getBindingResult().getAllErrors().iterator();
        while(errorsIterator.hasNext()) {
            ObjectError error = errorsIterator.next();
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            sb.append(String.format("%s: %s", field, message));
            if (errorsIterator.hasNext()) {
                sb.append(", ");
            }
        }
        CardErrorModel cardErrorModel = CardCommonErrors.INVALID_PARAMETERS;
        cardErrorModel.setDetails(sb.toString());
        return new ResponseEntity<>(cardErrorModel, HttpStatus.resolve(cardErrorModel.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
        CardErrorModel cardErrorModel = CardCommonErrors.UNEXPECTED_ERROR;
        return new ResponseEntity<>(cardErrorModel, HttpStatus.resolve(cardErrorModel.getStatus()));
    }

}
