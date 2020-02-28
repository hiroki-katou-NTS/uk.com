package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;


/**
* 加算適用区分
*/
public enum AdditiAppCategory
{
    
    適用しない(0, "適用しない"),
    適用する(1, "適用する");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private AdditiAppCategory(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
