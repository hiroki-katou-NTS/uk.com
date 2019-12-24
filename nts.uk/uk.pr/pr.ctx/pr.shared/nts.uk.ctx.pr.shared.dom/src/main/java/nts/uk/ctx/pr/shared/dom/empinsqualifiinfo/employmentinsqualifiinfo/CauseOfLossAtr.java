package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 喪失原因
 */
public enum CauseOfLossAtr {

    OTHER_REASON(0, I18NText.getText("Enum_CauseOfLossAtr_OTHER_REASON")),
    TURN_OVER_THAN_3(1, I18NText.getText("Enum_CauseOfLossAtr_TURN_OVER_THAN_3")),
    TURNOVER_AT_CONVENIENCE(2, I18NText.getText("Enum_CauseOfLossAtr_TURNOVER_AT_CONVENIENCE"));

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    CauseOfLossAtr(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }

}
