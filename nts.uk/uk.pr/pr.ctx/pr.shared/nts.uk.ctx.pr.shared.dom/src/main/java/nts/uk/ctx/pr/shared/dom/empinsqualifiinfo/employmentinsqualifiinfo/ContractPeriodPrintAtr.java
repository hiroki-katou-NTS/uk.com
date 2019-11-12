package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.i18n.I18NText;

/**
 * 契約期間の印刷区分
 */
public enum ContractPeriodPrintAtr {
    PRINT(1, I18NText.getText("QUI001_C222_28")),
    DO_NOT_PRINT(0, I18NText.getText("QUI001_C222_29"));

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    ContractPeriodPrintAtr(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
