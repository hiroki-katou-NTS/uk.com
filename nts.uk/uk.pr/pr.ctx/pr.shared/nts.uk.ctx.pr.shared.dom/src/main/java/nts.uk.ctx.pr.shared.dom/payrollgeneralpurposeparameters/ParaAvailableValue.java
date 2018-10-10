package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;


/**
* パラメータ有効区分
*/
public enum ParaAvailableValue
{
    
    UNAVAILABLE(0, "無効"),
    AVAILABLE(1, "有効");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ParaAvailableValue(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
