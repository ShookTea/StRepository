package st.jargs;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ShookTea
 */
class ElementsPack {
    public ElementsPack() {}
    
    public void insertElements(Element... elems) {
        elementsList.addAll(Arrays.asList(elems));
    }
    
    public Flag[] getAllFlags() {
        return getElementsByType(Flag.class).toArray(new Flag[0]);
    }
    
    public Variable[] getAllVariables() {
        return getElementsByType(Variable.class).toArray(new Variable[0]);
    }
    
    private ArrayList<Element> getElementsByType(Class typeOfElement) {
        ArrayList<Element> ret = new ArrayList<>();
        for (Element each : getAllElements()) {
            if (typeOfElement.isInstance(each)) {
                ret.add(each);
            }
        }
        return ret;
    }
    
    private Element[] getAllElements() {
        return elementsList.toArray(new Element[0]);
    }
    
    private final ArrayList<Element> elementsList = new ArrayList<>();
}
