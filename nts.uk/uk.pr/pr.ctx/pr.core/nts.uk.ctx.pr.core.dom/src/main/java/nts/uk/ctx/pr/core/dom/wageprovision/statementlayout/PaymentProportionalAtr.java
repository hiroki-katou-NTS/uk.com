package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;


/**
* 支給按分区分
*/
public enum PaymentProportionalAtr
{
    
    PROPORTIONAL(0),
    NOT_PROPORTIONAL(1),
    PAYMENT_ONE_A_MONTH(2);
    
    /** The value. */
    public final int value;
    
    private PaymentProportionalAtr(int value)
    {
        this.value = value;
    }
}
