package nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset;


/**
* 日数端数処理方法
*/
public enum DaysFractionProcessing
{
    
    AFTER(0, "足した後"),
    BEFORE(1, "足す前");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private DaysFractionProcessing(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
