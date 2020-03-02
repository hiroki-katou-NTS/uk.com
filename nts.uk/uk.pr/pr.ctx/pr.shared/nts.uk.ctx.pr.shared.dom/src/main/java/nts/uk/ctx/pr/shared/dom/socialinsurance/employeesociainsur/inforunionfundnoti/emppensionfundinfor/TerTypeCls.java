package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;

/**
* 終了形態区分
*/
public enum TerTypeCls
{
    
    PENSION(1, "年金"),
    LUMP_SUM(2, "一時金"),
    SELECTION(3, "選一"),
    LEAVE(4, "休職"),
    MOVING_OUT(5, "転出"),
    NO_BENEFIT(6, "無給付"),
    OFFER(7, "申脱"),
    RS_PENDING(8, "申脱保留");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private TerTypeCls(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
