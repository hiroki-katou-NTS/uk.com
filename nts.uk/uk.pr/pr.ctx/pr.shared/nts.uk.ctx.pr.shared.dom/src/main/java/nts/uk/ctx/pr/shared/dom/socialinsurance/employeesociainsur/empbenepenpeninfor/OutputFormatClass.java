package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

/**
 * 出力形式区分
 */
public enum OutputFormatClass {
    PEN_OFFICE(0, "ENUM_penOffice_PEN_OFFICE"),
    HEAL_INSUR_ASSO(0, "ENUM_healInsurAsso_HEAL_INSUR_ASSO"),
    THE_WELF_PEN(0, "ENUM_theWelfPen_OUTPUT_THE_WELF_PEN");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private OutputFormatClass(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
