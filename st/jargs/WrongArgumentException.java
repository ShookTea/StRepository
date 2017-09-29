package st.jargs;

/**
 *
 * @author ShookTea
 */
public class WrongArgumentException extends Exception {
    
    public WrongArgumentException() {
        super();
    }
    
    public WrongArgumentException(String message) {
        super(message);
    }
    
    public WrongArgumentException(String message, Throwable src) {
        super(message, src);
    }
}
