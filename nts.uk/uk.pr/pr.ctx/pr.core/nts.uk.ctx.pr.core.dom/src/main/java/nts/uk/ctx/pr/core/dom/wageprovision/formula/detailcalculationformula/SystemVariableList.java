package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.i18n.I18NText;

/**
* システム変数一覧
*/
public enum SystemVariableList {
    SYSTEM_YMD_DATE(0, I18NText.getText("Enum_SystemVariableList_SYSTEM_YMD_DATE")),
    SYSTEM_Y_DATE(1, I18NText.getText("Enum_SystemVariableList_SYSTEM_Y_DATE")),
    SYSTEM_YM_DATE(2, I18NText.getText("Enum_SystemVariableList_SYSTEM_YM_DATE")),
    PROCESSING_YEAR_MONTH(3, I18NText.getText("Enum_SystemVariableList_PROCESSING_YEAR_MONTH")),
    PROCESSING_YEAR(4, I18NText.getText("Enum_SystemVariableList_PROCESSING_YEAR")),
    REFERENCE_TIME(5, I18NText.getText("Enum_SystemVariableList_REFERENCE_TIME")),
    STANDARD_DAY(6, I18NText.getText("Enum_SystemVariableList_STANDARD_DAY")),
    WORKDAY(7, I18NText.getText("Enum_SystemVariableList_WORKDAY"));

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private SystemVariableList(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
