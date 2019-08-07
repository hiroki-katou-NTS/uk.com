package nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins;


/**
* 適用区分
*/
public enum Distinction {
    
    NOT_USE(0, "Enum_Distinction_NOT_USE"),
    USE(1, "Enum_Distinction_USE");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private Distinction(int value, String nameId){
        this.value = value;
        this.nameId = nameId;
    }
}
