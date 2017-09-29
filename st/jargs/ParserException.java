package st.jargs;

public class ParserException extends RuntimeException {

    public ParserException() {}
    
    public ParserException(String msg) {
        super(msg);
    }
    
    public ParserException(String msg, Throwable src) {
        super(msg, src);
    }
}
