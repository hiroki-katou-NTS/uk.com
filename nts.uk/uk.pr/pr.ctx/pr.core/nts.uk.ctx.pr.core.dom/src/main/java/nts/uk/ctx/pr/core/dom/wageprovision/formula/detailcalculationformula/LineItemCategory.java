package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.i18n.I18NText;

/**
* 明細項目区分
*/
public enum LineItemCategory {
    PAYMENT_ITEM(0, I18NText.getText("Enum_LineItemCategory_PAYMENT_ITEM")),
    DEDUCTION_ITEM(1, I18NText.getText("Enum_LineItemCategory_DEDUCTION_ITEM")),
    ATTENDANCE_ITEM(2, I18NText.getText("Enum_LineItemCategory_ATTENDANCE_ITEM"));

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private LineItemCategory(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
