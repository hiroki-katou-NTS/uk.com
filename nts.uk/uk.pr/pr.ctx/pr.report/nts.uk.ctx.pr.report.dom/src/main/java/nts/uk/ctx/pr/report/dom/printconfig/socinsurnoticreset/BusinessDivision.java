package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

public enum BusinessDivision {
    /**
     * 会社名・住所を出力
     */
    OUTPUT_COMPANY_NAME(0, "ENUM_outPutCompanyNameAdd_OUTPUTCOMPANYNAME"),
    /**
     * 社会保険事業所名・住所を出力
     */
    OUTPUT_SIC_INSURES(1, "ENUM_outputSocInsurEs_OUTPUTSICINSURES"),
    /*
    * 出力しない
    * */
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
