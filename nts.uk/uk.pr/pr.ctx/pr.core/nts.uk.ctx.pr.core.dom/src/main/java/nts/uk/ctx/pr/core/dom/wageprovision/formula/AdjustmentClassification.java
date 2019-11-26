package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.i18n.I18NText;

/**
* 調整区分
*/
public enum AdjustmentClassification {
    NOT_ADJUST(0, I18NText.getText("Enum_AdjustmentCls_NOT_ADJUST")),
    PLUS_ADJUST(1, I18NText.getText("Enum_AdjustmentCls_PLUS_ADJUST")),
    MINUS_ADJUST(2, I18NText.getText("Enum_AdjustmentCls_MINUS_ADJUST")),
    PLUS_MINUS_ADJUST(3, I18NText.getText("Enum_AdjustmentCls_PLUS_MINUS_ADJUST"));
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private AdjustmentClassification(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
