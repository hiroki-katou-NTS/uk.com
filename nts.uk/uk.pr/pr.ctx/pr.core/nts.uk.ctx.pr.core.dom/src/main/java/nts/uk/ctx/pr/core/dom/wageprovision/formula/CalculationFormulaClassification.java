package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 固定/計算式/既定区分
*/
public enum CalculationFormulaClassification
{
    
    FIXED_VALUE(0, "固定値"),
    FORMULA(1, "計算式"),
    DEFINITION_FORMULA(2, "既定計算式");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private CalculationFormulaClassification(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
