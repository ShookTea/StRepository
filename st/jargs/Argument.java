package st.jargs;

/**
 *
 * @author ShookTea
 */
class Argument {
    public Argument(String argument) {
        this.argument = argument;
    }
    
    String getArgument() {
        return argument;
    }
    
    boolean isShortFlag() {
        return argument.startsWith("-") && !isLongFlag();
    }
    
    boolean isLongFlag() {
        return argument.startsWith("--");
    }
    
    boolean isUsed() {
        return isUsed;
    }
    
    void use() {
        isUsed = true;
    }
    
    private boolean isUsed = false;
    private final String argument;
}
