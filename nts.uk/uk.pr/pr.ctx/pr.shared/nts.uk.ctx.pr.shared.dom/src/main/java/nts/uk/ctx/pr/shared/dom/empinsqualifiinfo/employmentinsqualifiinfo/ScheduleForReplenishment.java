package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 補充予定区分
 */
public enum ScheduleForReplenishment {

    NO(0, I18NText.getText("なし")),
    YES(1, I18NText.getText("あり"));

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    ScheduleForReplenishment(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }

}
