package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;

/**
* 基金喪失理由区分
*/
public enum ReasonForLoss
{
    
    PENSION(1, "年金"),
    SPECIAL(2, "特別"),
    MOVING_OUT(3, "転出"),
    RS_PENDING(4, "申脱保留"),
    APPLICATION(5, "申脱(選)"),
    OFFER(6, "申脱"),
    LEAVE(7, "休職"),
    NO_BENEFITS(8, "無給付"),
    PENSION_ELECTION(9, "年金(選)"),
    SPECIAL_SELECT(10, "特例(選)");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ReasonForLoss(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
