package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.i18n.I18NText;

/**
* 関数一覧
*/
public enum FunctionList {
    CONDITIONAL_EXPRESSION(0, I18NText.getText("Enum_FunctionList_CONDITIONAL_EXPRESSION")),
    AND(1, I18NText.getText("Enum_FunctionList_AND")),
    OR(2, I18NText.getText("Enum_FunctionList_OR")),
    ROUND_OFF(3, I18NText.getText("Enum_FunctionList_ROUND_OFF")),
    TRUNCATION(4, I18NText.getText("Enum_FunctionList_TRUNCATION")),
    ROUND_UP(5, I18NText.getText("Enum_FunctionList_ROUND_UP")),
    MAX_VALUE(6, I18NText.getText("Enum_FunctionList_MAX_VALUE")),
    MIN_VALUE(7, I18NText.getText("Enum_FunctionList_MIN_VALUE")),
    NUMBER_OF_FAMILY_MEMBER(8, I18NText.getText("Enum_FunctionList_NUMBER_OF_FALIMY_MEMBER")),
    ADDITIONAL_YEARMONTH(9, I18NText.getText("Enum_FunctionList_ADDITIONAL_YEARMONTH")),
    YEAR_EXTRACTION(10, I18NText.getText("Enum_FunctionList_YEAR_EXTRACTION")),
    MONTH_EXTRACTION(11, I18NText.getText("Enum_FunctionList_MONTH_EXTRACTION"));

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private FunctionList(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
