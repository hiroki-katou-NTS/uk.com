package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.i18n.I18NText;

/**
* 式中端数処理
*/
public enum RoundingMethod {
    
    ROUND_OFF(0, I18NText.getText("Enum_RoundingMethod_ROUND_OFF")),
    ROUND_UP(1, I18NText.getText("Enum_RoundingMethod_ROUND_UP")),
    TRUNCATION(2, I18NText.getText("Enum_RoundingMethod_TRUNCATION")),
    DO_NOTHING(3, I18NText.getText("Enum_RoundingMethod_DO_NOTHING"));
    
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
