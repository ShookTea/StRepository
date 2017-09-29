package st.jargs;

/**
 *
 * @author ShookTea
 */
public class FlagBuilder {
    protected FlagBuilder(Parser p) {
        this.longFlag = Element.NO_LONG_FLAG;
        this.shortFlag = Element.NO_SHORT_FLAG;
        this.variableRequired = false;
        this.parser = p;
    }
    
    public FlagBuilder setLongFlag(String longFlag) {
        this.longFlag = longFlag;
        return this;
    }
    
    public FlagBuilder setShortFlag(char shortFlag) {
        this.shortFlag = shortFlag;
        return this;
    }
    
    public FlagBuilder setVariableRequired(boolean variableRequired) {
        this.variableRequired = variableRequired;
        return this;
    }
    
    public FlagBuilder setVariableRequired() {
        return this.setVariableRequired(true);
    }
    
    public Flag build() {
        Flag f = new Flag(shortFlag, longFlag, variableRequired);
        if (parser != null) {
            parser.insertElements(f);
        }
        return f;
    }
    
    public static FlagBuilder createFlag() {
        return new FlagBuilder(null);
    }

    private String longFlag;
    private char shortFlag;
    private boolean variableRequired;
    private Parser parser;
}
