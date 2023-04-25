package br.com.dea.management.exceptions;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String message) {
        super(message);
    }

    public  InvalidParameterException(Class<?> clazz, long id,long id2) {
        super(String.format("Entity %s Scrum Master  %d must be different from Product Owner", clazz.getSimpleName(), id));
    }
}
