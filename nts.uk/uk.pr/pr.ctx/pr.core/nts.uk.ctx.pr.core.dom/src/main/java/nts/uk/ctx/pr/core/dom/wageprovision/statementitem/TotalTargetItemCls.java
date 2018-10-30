package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;


/**
* 合計対象項目区分
*/
public enum TotalTargetItemCls
{
    
    TOTAL_OBJECT(0, "0：合計対象"),
    TOTAL_EXEMPT(1, "1：合計対象外");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private TotalTargetItemCls(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
