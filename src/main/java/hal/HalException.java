package hal;

/**
 * Represents an exception specific to the Hal application.
 */
public class HalException extends RuntimeException {
    /**
     * Constructs a HalException with the specified error message.
     *
     * @param message The error message.
     */
    public HalException(String message) {
        super(message);
    }
}
