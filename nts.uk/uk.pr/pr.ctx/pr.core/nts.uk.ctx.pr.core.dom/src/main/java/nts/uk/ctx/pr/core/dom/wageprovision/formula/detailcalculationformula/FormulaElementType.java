package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.i18n.I18NText;

/**
* 関数区分
*/
public enum FormulaElementType {
    PAYMENT_ITEM(0, I18NText.getText("Enum_FormulaElementType_PAYMENT_ITEM")),
    DEDUCTION_ITEM(1, I18NText.getText("Enum_FormulaElementType_DEDUCTION_ITEM")),
    ATTENDANCE_ITEM(2, I18NText.getText("Enum_FormulaElementType_ATTENDANCE_ITEM")),
    COMPANY_UNIT_PRICE_ITEM(3, I18NText.getText("Enum_FormulaElementType_COMPANY_UNIT_PRICE_ITEM")),
    INDIVIDUAL_UNIT_PRICE_ITEM(4, I18NText.getText("Enum_FormulaElementType_INDIVIDUAL_UNIT_PRICE_ITEM")),
    FUNCTION_ITEM(5, I18NText.getText("Enum_FormulaElementType_FUNCTION_ITEM")),
    VARIABLE_ITEM(6, I18NText.getText("Enum_FormulaElementType_VARIABLE_ITEM")),
    PERSON_ITEM(7, I18NText.getText("Enum_FormulaElementType_PERSON_ITEM")),
    FORMULA_ITEM(8, I18NText.getText("Enum_FormulaElementType_FORMULA_ITEM")),
    WAGE_TABLE_ITEM(9, I18NText.getText("Enum_FormulaElementType_WAGE_TABLE_ITEM"));

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private FormulaElementType(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
