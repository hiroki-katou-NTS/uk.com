package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

public enum TextPerNumberClass {
    OUTPUT_NUMBER(0, "ENUM_outputNumber_OUTPUT_NUMBER"),
    OUPUT_BASIC_PEN_NUMBER(1, "ENUM_outputBasicPenNumber_OUPUT_BASIC_PEN_NUMBER"),
    OUTPUT_BASIC_NO_PERSONAL(0, "ENUM_outputBasicNoPersonal_OUTPUT_BASIC_NO_PERSONAL"),;

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private TextPerNumberClass(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
