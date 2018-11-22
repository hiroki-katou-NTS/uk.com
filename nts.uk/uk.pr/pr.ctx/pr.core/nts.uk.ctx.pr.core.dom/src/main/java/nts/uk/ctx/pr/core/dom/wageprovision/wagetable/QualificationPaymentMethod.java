package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;


/**
* 資格支払方法
*/
public enum QualificationPaymentMethod
{
    PAY_ONLY_ONE_HIGHEST_BENEFIT(0, "一番高い手当を1つだけ支給する"),
    ADD_MULTIPLE_APPLICABLE_AMOUNT(1, "複数該当した金額を加算する");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private QualificationPaymentMethod(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
