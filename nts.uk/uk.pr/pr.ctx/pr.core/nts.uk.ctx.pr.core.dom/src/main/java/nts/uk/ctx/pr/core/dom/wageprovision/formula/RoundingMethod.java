package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 式中端数処理
*/
public enum RoundingMethod
{
    
    ROUND_OFF(0, "四捨五入"),
    ROUND_UP(1, "切り上げ"),
    TRUNCATION(2, "切り捨て"),
    DO_NOTHING(3, "何もしない");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private RoundingMethod(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
