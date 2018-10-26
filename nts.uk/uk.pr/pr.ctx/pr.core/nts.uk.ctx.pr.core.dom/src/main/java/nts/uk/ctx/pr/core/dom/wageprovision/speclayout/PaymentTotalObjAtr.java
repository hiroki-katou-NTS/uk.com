package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;


/**
* 支給合計対象区分
*/
public enum PaymentTotalObjAtr
{
    INSIDE(0),
    OUTSIDE(1),
    INSIDE_ACTUAL(2),
    OUTSIDE_ACTUAL(3);
    
    /** The value. */
    public final int value;
    
    private PaymentTotalObjAtr(int value)
    {
        this.value = value;
    }
}
