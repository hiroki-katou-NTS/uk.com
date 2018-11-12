package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 計算式の設定方法
*/
public enum FormulaSettingMethod
{
    
    SIMPLESETTING(0, "かんたん設定"),
    DETAILSETTING(1, "詳細設定");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private FormulaSettingMethod(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
