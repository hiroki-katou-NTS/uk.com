package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;


/**
* 修正禁止区分
*/
public enum ModificationProhibitionCls
{
    
    CORRECTABLE(0, "0：修正可"),
    CAN_NOT_MODIFIED(1, "1：修正不可");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ModificationProhibitionCls(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
