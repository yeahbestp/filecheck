package com.best.filechecker.util.exceptions;

public class StorageException extends RuntimeException {

    /**
     * Constructs StorageException with detailed message and cause
     * Exception is thrown when application is not able to store the file
     *
     * @param message presenting
     */

    public StorageException(String message) {
        super(message);
    }

    /**
     * Constructs StorageException with detailed message and cause
     * Exception is thrown when application is not able to store the file
     *
     * @param message the detail message (which is saved for later retrieval
     *                 by the {@link #getMessage()} method).
     * @param cause    the cause (which is saved for later retrieval by the
     *                 {@link #getCause()} method).  (A {@code null} value is
     *                 permitted, and indicates that the cause is nonexistent or
     *                 unknown.
     */

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

}