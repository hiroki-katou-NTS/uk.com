package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 調整区分
*/
public enum AdjustmentClassification
{
    
    NOT_ADJUST(0, "調整しない"),
    PLUS_ADJUST(1, "プラス調整"),
    MINUS_ADJUST(2, "マイナス調整"),
    PLUS_MINUS_ADJUST(3, "プラスマイナス反転");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private AdjustmentClassification(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
