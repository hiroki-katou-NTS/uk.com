package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;


/**
* 加入形態区分
*/
public enum SubscriptionType
{
    
    新規(1, "新規"),
    転入(2, "転入"),
    復活(3, "復活"),
    再加入(4, "再加入");
    
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
