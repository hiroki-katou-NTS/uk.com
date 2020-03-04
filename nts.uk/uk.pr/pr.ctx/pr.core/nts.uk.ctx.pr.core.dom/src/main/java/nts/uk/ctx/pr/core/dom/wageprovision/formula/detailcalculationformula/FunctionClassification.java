package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.i18n.I18NText;

/**
* 関数区分
*/
public enum FunctionClassification {
    ALL(0, I18NText.getText("Enum_FunctionCls_ALL")),
    TIME_FUNCTION(1, I18NText.getText("Enum_FunctionCls_TIME_FUNCTION")),
    PAYROLL_SYSTEM(2, I18NText.getText("Enum_FunctionCls_PAYROLL_FUNCTION")),
    LOGIC(3, I18NText.getText("Enum_FunctionCls_LOGIC")),
    STRING_OPERATION(4, I18NText.getText("Enum_FunctionCls_STRING_OEPRATION")),
    DATETIME(5, I18NText.getText("Enum_FunctionCls_DATETIME")),
    MATHEMATICS(6, I18NText.getText("Enum_FunctionCls_MATHEMATICS"));

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private FunctionClassification(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
