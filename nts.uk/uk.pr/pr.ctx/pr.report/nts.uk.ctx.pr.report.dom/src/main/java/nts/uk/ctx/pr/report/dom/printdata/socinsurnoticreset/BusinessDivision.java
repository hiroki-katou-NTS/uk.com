package nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset;

public enum BusinessDivision {
    OUTPUT_COMPANY_NAME(0, "Enum_BusinessDivision_OUTPUT_COMPANY_NAME"),
    OUTPUT_SIC_INSURES(1, "Enum_BusinessDivision_OUTPUT_SIC_INSURES"),
    DO_NOT_OUTPUT(2, "Enum_BusinessDivision_DO_NOT_OUTPUT");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private BusinessDivision(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
