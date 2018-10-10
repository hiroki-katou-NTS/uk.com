package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;


/**
* パラメータ履歴区分
*/
public enum ParaHistoryAtr
{
    
    YMDHIST(0, "年月日履歴"),
    YMHIST(1, "年月履歴"),
    DONOTMANAGE(2, "履歴管理しない");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ParaHistoryAtr(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
