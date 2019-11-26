package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

//個人番号区分
public enum  PersonalNumClass {
    //個人番号を出力する
    OUTPUT_PER_NUMBER(0, "Enum_PersonalNumClass_OUTPUT_PER_NUMBER"),
    //基礎年金番号を出力する
    OUTPUT_BASIC_PER_NUMBER(1, "Enum_PersonalNumClass_OUTPUT_BASIC_PER_NUMBER"),
    //個人番号が無ければ基礎年金番号を出力する
    OUTPUT_BASIC_PEN_NOPER(2, "Enum_PersonalNumClass_OUTPUT_BASIC_PEN_NOPER"),
    //出力しない
    DO_NOT_OUTPUT(3, "Enum_PersonalNumClass_DO_NOT_OUTPUT");

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
