package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 計算式タイプ
*/
public enum FormulaType
{
    
    CALCULATION_FORMULA_TYPE1(0, "計算式タイプ１"),
    CALCULATION_FORMULA_TYPE2(1, "計算式タイプ２"),
    CALCULATION_FORMULA_TYPE3(2, "計算式タイプ３");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private FormulaType(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
