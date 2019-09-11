package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;


/**
* 加入形態区分
*/
public enum SubscriptionType
{

    NEW(1, "新規"),
    MOVING(2, "転入"),
    REVIVAL(3, "復活"),
    REJOIN(4, "再加入");

    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private SubscriptionType(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
