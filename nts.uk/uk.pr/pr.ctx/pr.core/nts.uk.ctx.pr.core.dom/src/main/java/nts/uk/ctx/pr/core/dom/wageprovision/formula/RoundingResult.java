package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 結果端数処理
*/
public enum RoundingResult
{
    
    ROUND_OFF(0, "四捨五入"),
    ROUND_UP(1, "切り上げ"),
    TRUNCATION(2, "切り捨て");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private RoundingResult(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
