package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

public enum SubNameClass {
    PERSONAL_NAME(0, "ENUM_personalName_PERSONAL_NAME"),
    REPORTED_NAME(1, "ENUM_reportedName_REPORTED_NAME");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private SubNameClass(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
