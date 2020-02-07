package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

/**
 * 出力形式区分
 */
public enum OutputFormatClass {
    PEN_OFFICE(0, "Enum_OutputFormatClass_PEN_OFFICE"),
    HEAL_INSUR_ASSO(1, "Enum_OutputFormatClass_HEAL_INSUR_ASSO"),
    THE_WELF_PEN(2, "Enum_OutputFormatClass_OUTPUT_THE_WELF_PEN");

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
