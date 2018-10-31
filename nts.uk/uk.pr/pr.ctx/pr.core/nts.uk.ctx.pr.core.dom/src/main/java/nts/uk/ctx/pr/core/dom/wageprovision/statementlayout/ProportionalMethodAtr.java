package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;


/**
* 按分方法区分
*/
public enum ProportionalMethodAtr
{
    
    BY_PROPORTION(0, "割合で計算"),
    DAYS_DEDUCTION(1, "日数控除");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ProportionalMethodAtr(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
