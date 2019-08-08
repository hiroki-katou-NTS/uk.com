package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;
/**
 * 改行コード区分
 */

public enum LineFeedCode {
    ADD(0, "ENUM_add_PEN_OFFICE"),
    DO_NOT_ADD(0, "ENUM_doNotAdd_DO_NOT_ADD"),
    E_GOV(0, "ENUM_eGov_E_GOV");

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
