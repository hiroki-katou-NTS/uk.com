package nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset;

public enum  PersonalNumClass {
    OUTPUT_PER_NUMBER(0, "ENUM_outputPerNumber_OUTPUT_PER_NUMBER"),
    OUTPUT_BASIC_PER_NUMBER(1, "ENUM_outputBasicPenNum_OUTPUT_BASIC_PER_NUMBER"),
    OUTPUT_BASIC_PEN_NOPER(2, "ENUM_outputBasicPenNoPer_OUTPUT_BASIC_PEN_NOPER"),
    DO_NOT_OUTPUT(3, "ENUM_doNotOutPut_DO_NOT_OUTPUT");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private PersonalNumClass(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
