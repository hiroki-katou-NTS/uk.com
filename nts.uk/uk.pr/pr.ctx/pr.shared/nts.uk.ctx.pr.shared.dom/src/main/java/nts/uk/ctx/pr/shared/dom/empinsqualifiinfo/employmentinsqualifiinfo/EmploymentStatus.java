package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 雇用形態区分
 */
public enum EmploymentStatus {
    DAILY_WORK(0, I18NText.getText("")),
    DISPATCH(1, I18NText.getText("")),
    PART_TIME(2, I18NText.getText("")),
    FIXED_TERM_CONTRACT(3, I18NText.getText("")),
    SEASONAL(4, I18NText.getText("")),
    SAILOR(5, I18NText.getText("")),
    OTHER(6, I18NText.getText(""));


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
