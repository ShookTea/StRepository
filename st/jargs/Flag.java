package st.jargs;

/**
 *
 * @author ShookTea
 */
public class Flag extends Element {
    public Flag(char shortFlag, String longFlag, boolean valueRequired) {
        super(valueRequired);
        this.shortFlag = shortFlag;
        this.longFlag = longFlag;
    }
    
    char getShortFlag() {
        return shortFlag;
    }
    
    boolean isShortFlagSet() {
        return shortFlag != NO_SHORT_FLAG;
    }
    
    String getLongFlag() {
        return longFlag;
    }
    
    boolean isLongFlagSet() {
        return !longFlag.equals(NO_LONG_FLAG);
    }

    private final char shortFlag;
    private final String longFlag;
}
