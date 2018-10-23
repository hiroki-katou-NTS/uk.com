package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;


/**
* パラメータ属性区分
*/
public enum ParaAttributeType {
    
    TEXT(0, "文字"),
    NUMBER(1, "数値"),
    TIME(2, "時間"),
    TARGET_EXEMPT(3, "対象/対象外"),
    SELECTION(4, "選択肢");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ParaAttributeType(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
