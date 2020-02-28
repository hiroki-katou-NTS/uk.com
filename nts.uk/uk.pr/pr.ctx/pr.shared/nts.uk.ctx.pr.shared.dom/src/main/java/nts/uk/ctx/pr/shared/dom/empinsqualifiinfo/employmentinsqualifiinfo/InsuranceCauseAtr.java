package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 被保険者となったことの原因区分
 */
public enum InsuranceCauseAtr {
    NEW_EMPLOYMENT_NEW_GRADUATE(0, I18NText.getText("Enum_InsuranceCause_NEW_EMPLOYMENT_NEW_GRADUATE")),
    NEW_EMPLOYMENT_OTHER(1, I18NText.getText("Enum_InsuranceCause_NEW_EMPLOYMENT_OTHER")),
    SWITCHING_FROM_DAY_LABOR(2, I18NText.getText("Enum_InsuranceCause_SWITCHING_FROM_DAY_LABOR")),
    OTHER(3, I18NText.getText("Enum_InsuranceCause_OTHER")),
    TEMPORARY_RETURN(4, I18NText.getText("Enum_InsuranceCause_TEMPORARY_RETURN_OLDER_65"));


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
