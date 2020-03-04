package nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset;


/**
* 出勤日数選択
*/
public enum SelectWorkDays
{
    
    FROM_STATEMENT_ITEM(0, "明細書項目から選択"),
    FROM_EMPLOYMENT(1, "就業からの連携");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private SelectWorkDays(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
