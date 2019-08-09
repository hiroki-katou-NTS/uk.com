package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor;


/**
* 被保険者区分
*/
public enum InsPerCls {

    GEN_INS_PER(0, "ENUM_InsPerCls_GEN_INS_PER"),
    PART_HANDLING(1, "ENUM_InsPerCls_PART_HANDLING"),
    SHORT_TIME_WORKERS(2, "ENUM_InsPerCls_SHORT_TIME_WORKERS");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private InsPerCls(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
