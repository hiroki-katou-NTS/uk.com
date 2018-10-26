package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;


/**
* 支給計算方法区分
*/
public enum PaymentCaclMethodAtr
{
    
    MANUAL_INPUT(0),
    PERSON_INFO_REF(1),
    CACL_FOMULA(2),
    WAGE_TABLE(3),
    COMMON_AMOUNT(4),
    BREAKDOWN_ITEM(5);
    
    /** The value. */
    public final int value;
    
    private PaymentCaclMethodAtr(int value)
    {
        this.value = value;
    }
}
