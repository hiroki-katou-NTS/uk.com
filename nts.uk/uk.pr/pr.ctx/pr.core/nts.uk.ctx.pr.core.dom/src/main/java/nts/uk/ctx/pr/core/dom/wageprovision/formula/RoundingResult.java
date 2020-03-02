package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.i18n.I18NText;

/**
* 結果端数処理
*/
public enum RoundingResult {
    
    ROUND_OFF(0, I18NText.getText("Enum_RoundingResult_ROUND_OFF")),
    ROUND_UP(1, I18NText.getText("Enum_RoundingResult_ROUND_UP")),
    TRUNCATION(2, I18NText.getText("Enum_RoundingResult_TRUNCATION"));
    
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
