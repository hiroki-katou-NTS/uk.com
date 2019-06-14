package nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset;


/**
* 小数点切捨区分
*/
public enum DecimalPointCutoffSegment
{
    
    NOT_USE(0, "しない"),
    USE(1, "する");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private DecimalPointCutoffSegment(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
