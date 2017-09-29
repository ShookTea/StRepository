package st.jargs;

/**
 *
 * @author ShookTea
 */
public abstract class Element {
    public Element(boolean isValueRequired) {
        this.isValueRequired = isValueRequired;
    }
    
    public boolean isUsed() {
        return isUsed;
    }
    
    protected void use() {
        isUsed = true;
    }
    
    protected void use(String value) {
        if (!isValueRequired()) {
            throw new Error("It shouldn't ever happen");
        }
        isUsed = true;
        this.value = value;
    }
    
    public String getValue() throws ParserException {
        if (!isValueRequired()) {
            throw new ParserException("Request value from non-value element");
        }
        if (!isUsed()) {
            throw new ParserException("Request value from not used element");
        }
        return value;
    }
    
    protected boolean isValueRequired() {
        return isValueRequired;
    }
    
    private String value;
    private boolean isUsed = false;
    private final boolean isValueRequired;
    
    public static final char NO_SHORT_FLAG = '-';
    public static final String NO_LONG_FLAG = "-";
}
