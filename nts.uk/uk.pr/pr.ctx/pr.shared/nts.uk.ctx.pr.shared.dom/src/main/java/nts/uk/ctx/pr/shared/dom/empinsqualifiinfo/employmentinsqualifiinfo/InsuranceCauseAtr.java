package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 被保険者となったことの原因区分
 */
public enum InsuranceCauseAtr {
    NEW_EMPLOYMENT_NEW_GRADUATE(0, I18NText.getText("")),
    NEW_EMPLOYMENT_OTHER(1, I18NText.getText("")),
    SWITCHING_FROM_DAY_LABOR(2, I18NText.getText("")),
    OTHER(3, I18NText.getText("")),
    TEMPORARY_RETURN(4, I18NText.getText(""));


    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    InsuranceCauseAtr(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
