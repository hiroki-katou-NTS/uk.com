package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.i18n.I18NText;

/**
* システム変数区分
*/
public enum SystemVariableClassification {
    ALL(0, I18NText.getText("Enum_SystemVariableCls_ALL"));

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private SystemVariableClassification(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
