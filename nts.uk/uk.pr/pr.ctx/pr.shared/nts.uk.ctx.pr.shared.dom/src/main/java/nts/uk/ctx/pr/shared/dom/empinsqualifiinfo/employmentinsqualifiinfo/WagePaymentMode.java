package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 賃金支払態様
 */
public enum WagePaymentMode {
    MONTHLY_SALARY(0, I18NText.getText("Enum_WagePaymentMode_MONTHLY_SALARY")),
    WEEKLY_SALARY(1, I18NText.getText("Enum_WagePaymentMode_WEEEKLY_SALARY")),
    DAILY_WAGE(2, I18NText.getText("Enum_WagePaymentMode_DAILY_WAGE")),
    HOURLY_PAY(3, I18NText.getText("Enum_WagePaymentMode_HOURLY_PAY")),
    OTHER(4, I18NText.getText("Enum_WagePaymentMode_OTHER"));


    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    WagePaymentMode(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
