package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 取得区分
 */

public enum AcquisitionAtr {

    NEW(0, I18NText.getText("QUI001_C222_5")),
    REHIRE(1, I18NText.getText("QUI001_C222_6"));

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    AcquisitionAtr(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
