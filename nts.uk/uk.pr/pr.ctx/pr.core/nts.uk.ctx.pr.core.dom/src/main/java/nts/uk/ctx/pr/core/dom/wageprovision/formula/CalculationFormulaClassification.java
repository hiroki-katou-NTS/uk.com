package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.i18n.I18NText;

/**
* 固定/計算式/既定区分
*/
public enum CalculationFormulaClassification {
    
    FIXED_VALUE(0, I18NText.getText("Enum_CalculationFormulaCls_FIXED_VALUE")),
    FORMULA(1, I18NText.getText("Enum_CalculationFormulaCls_FORMULA")),
    DEFINITION_FORMULA(2, I18NText.getText("Enum_CalculationFormulaCls_DEFINITION_FORMULA"));
    
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
