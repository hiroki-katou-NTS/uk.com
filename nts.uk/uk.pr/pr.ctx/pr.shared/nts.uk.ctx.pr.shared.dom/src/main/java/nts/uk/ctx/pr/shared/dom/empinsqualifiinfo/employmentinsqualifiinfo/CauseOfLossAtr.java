package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

public enum CauseOfLossAtr {

    OTHER_REASON(0, I18NText.getText("離職以外の理由")),
    TURN_OVER_THAN_3(1, I18NText.getText("3以外 の離職 ")),
    TURNOVER_AT_CONVENIENCE(2, I18NText.getText("事業主の都合による離職"));

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
