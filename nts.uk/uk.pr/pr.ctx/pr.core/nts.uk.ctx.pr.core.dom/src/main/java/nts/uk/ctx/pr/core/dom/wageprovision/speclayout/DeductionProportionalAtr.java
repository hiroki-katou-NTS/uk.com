package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;


/**
* 控除按分区分
*/
public enum DeductionProportionalAtr
{
    
    PROPORTIONAL(0, "按分する"),
    NOT_PROPORTIONAL(1, "按分しない"),
    DEDUCTION_ONCE_A_MONTH(2, "月１回控除");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private DeductionProportionalAtr(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
