package ru.rkhamatyarov.stockportfolioallocation.exception;

/**
 * Custom business exception for application-specific errors.
 */
public class BusinessException extends Exception {

    /**
     * Constructs a BusinessException with the specified error message.
     *
     * @param message the error message
     */
    public BusinessException(final String message) {
        super(message);
    }
}
