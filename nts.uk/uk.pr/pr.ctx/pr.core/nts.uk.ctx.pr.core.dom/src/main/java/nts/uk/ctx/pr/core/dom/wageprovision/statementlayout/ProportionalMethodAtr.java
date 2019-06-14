package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;


/**
* 按分方法区分
*/
public enum ProportionalMethodAtr
{
    // 割合で計算
    BY_PROPORTION(0, "Enum_ProportionalMethodAtr_BY_PROPORTION"),
    // 日数控除
    DAYS_DEDUCTION(1, "Enum_ProportionalMethodAtr_DAYS_DEDUCTION");
    
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
