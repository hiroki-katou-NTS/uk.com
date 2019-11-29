package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 離職票交付希望区分
 */
public enum RequestForInsurance {

    YES(1, I18NText.getText("QUI004_C222_5")),
    NO(0, I18NText.getText("QUI004_C222_6"));

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    RequestForInsurance(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }

}
