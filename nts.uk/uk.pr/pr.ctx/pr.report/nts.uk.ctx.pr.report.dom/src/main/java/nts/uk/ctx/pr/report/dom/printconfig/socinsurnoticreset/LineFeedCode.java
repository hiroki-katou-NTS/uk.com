package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;
/**
 * 改行コード区分
 */

public enum LineFeedCode {
    ADD(0, "Enum_LineFeedCode_ADD"),
    DO_NOT_ADD(1, "Enum_LineFeedCode_DO_NOT_ADD"),
    E_GOV(2, "Enum_LineFeedCode_E_GOV");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private LineFeedCode(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
