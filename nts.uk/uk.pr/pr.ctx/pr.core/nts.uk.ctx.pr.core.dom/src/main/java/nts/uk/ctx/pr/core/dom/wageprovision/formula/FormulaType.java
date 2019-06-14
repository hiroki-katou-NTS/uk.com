package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.i18n.I18NText;

/**
* 計算式タイプ
*/
public enum FormulaType {
    
    CALCULATION_FORMULA_TYPE1(0, I18NText.getText("Enum_FormulaType_CALCULATION_FORMULA_TYPE_1")),
    CALCULATION_FORMULA_TYPE2(1, I18NText.getText("Enum_FormulaType_CALCULATION_FORMULA_TYPE_2")),
    CALCULATION_FORMULA_TYPE3(2, I18NText.getText("Enum_FormulaType_CALCULATION_FORMULA_TYPE_3"));
    
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
