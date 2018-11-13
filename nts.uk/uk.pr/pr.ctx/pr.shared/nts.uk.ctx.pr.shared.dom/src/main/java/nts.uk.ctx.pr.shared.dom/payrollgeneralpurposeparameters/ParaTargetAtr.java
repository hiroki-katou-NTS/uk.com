package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;


/**
* パラメータ対象区分
*/
public enum ParaTargetAtr {
    
    TARGET(0, "対象"),
    NOT_COVERED(1, "対象外");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ParaTargetAtr(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
