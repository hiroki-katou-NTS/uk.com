package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

/*提出氏名区分*/
public enum SubNameClass {
    PERSONAL_NAME(0, "Enum_SubNameClass_PERSONAL_NAME"),
    REPORTED_NAME(1, "Enum_SubNameClass_REPORTED_NAME");

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
