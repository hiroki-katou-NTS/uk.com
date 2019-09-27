package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

/*テキスト個人番号区分*/
public enum TextPerNumberClass {
    OUTPUT_NUMBER(0, "Enum_TextPerNumberClass_OUTPUT_NUMBER"),
    OUPUT_BASIC_PEN_NUMBER(1, "Enum_TextPerNumberClass_OUPUT_BASIC_PEN_NUMBER"),
    OUTPUT_BASIC_NO_PERSONAL(2, "Enum_TextPerNumberClass_OUTPUT_BASIC_NO_PERSONAL");

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
