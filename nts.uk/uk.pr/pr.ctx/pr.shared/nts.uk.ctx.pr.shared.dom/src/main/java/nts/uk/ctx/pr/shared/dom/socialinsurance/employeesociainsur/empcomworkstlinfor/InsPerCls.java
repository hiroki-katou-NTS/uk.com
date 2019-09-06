package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor;


/**
* 被保険者区分
*/
public enum InsPerCls {
    //一般被保険者
    GEN_INS_PER(0, "ENUM_InsPerCls_GEN_INS_PER"),
    //パート扱い
    PART_HANDLING(1, "ENUM_InsPerCls_PART_HANDLING"),
    //短時間労働者
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


    public static InsPerCls valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (InsPerCls val : InsPerCls.values()) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
