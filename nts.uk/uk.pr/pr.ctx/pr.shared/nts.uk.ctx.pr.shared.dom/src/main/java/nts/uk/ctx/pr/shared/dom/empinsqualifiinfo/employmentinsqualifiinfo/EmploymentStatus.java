package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 雇用形態区分
 */
public enum EmploymentStatus {
    DAILY_WORK(0, I18NText.getText("Enum_EmploymentStatus_DAILY_WORK")),
    DISPATCH(1, I18NText.getText("Enum_EmploymentStatus_DISPATCH")),
    PART_TIME(2, I18NText.getText("Enum_EmploymentStatus_PART_TIME")),
    FIXED_TERM_CONTRACT(3, I18NText.getText("Enum_EmploymentStatus_FIXED_TERM_CONTRACT")),
    SEASONAL(4, I18NText.getText("Enum_EmploymentStatus_SEASONAL")),
    SAILOR(5, I18NText.getText("Enum_EmploymentStatus_SAILOR")),
    OTHER(6, I18NText.getText("Enum_EmploymentStatus_OTHER"));


    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    EmploymentStatus(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
