package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;


/**
* 事業所区分
*/
public enum OfficeCls {

    //所属する労保事業所名・住所を出力
    OUPUT_LABOR_OFFICE(0, "Enum_OfficeCls_OUPUT_LABOR_OFFICE"),

    //会社名・住所を出力
    OUTPUT_COMPANY(1, "Enum_OfficeCls_OUTPUT_COMPANY"),

    //出力しない
    DO_NOT_OUTPUT(2, "Enum_OfficeCls_DO_NOT_OUTPUT");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private OfficeCls(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
