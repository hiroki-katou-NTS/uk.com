package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

public enum BusinessDivision {
    OUTPUT_COMPANY_NAME(0, "ENUM_outPutCompanyNameAdd_OUTPUTCOMPANYNAME"),
    OUTPUT_SIC_INSURES(1, "ENUM_outputSocInsurEs_OUTPUTSICINSURES"),
    DO_NOT_OUTPUT(2, "ENUM_doNotOutput_DONOTOUTPUT");

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
