package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;

import nts.arc.i18n.I18NText;

/*
**
* 改行コード区分
*/
public enum LineFeedCode {

    /**
     * 付加する
     */
    ADD(0, I18NText.getText("#QUI001_A222_41")),

    /**
     * 付加しない
     */
    NO_ADD(1, I18NText.getText("#QUI001_A222_42")),

    /**
     * e-Gov
     */
    E_GOV(2, I18NText.getText("#QUI001_A222_43"));

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    LineFeedCode(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
