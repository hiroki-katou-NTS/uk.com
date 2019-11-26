package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.i18n.I18NText;

/**
* 係数区分（固定項目）
*/
public enum CoefficientClassification {
    
    FIXED_VALUE(0, I18NText.getText("Enum_CoefficientCls_FIXED_VALUE")),
    WORK_DAY(1, I18NText.getText("Enum_CoefficientCls_WORKDAY")),
    WORKDAY_AND_HOLIDAY(2, I18NText.getText("Enum_CoefficientCls_WORKDAY_AND_HOLIDAY"));
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private CoefficientClassification(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
