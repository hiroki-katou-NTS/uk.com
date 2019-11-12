package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 就職経路
 */
public enum JobPath {
    STABLE_INTRODUCTION(0, I18NText.getText("")),
    SELF_EMPLOYMENT(1, I18NText.getText("")),
    PRIVATE_INTRODUCTION(2, I18NText.getText("")),
    DO_NOT_KNOW(3, I18NText.getText(""));


    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    JobPath(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
