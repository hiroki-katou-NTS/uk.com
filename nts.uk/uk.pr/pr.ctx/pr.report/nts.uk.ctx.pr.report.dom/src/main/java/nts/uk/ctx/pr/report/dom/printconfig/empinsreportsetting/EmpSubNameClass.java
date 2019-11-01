package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;


/**
* 提出氏名区分
*/
public enum EmpSubNameClass {

    //個人名
    PERSONAL_NAME(0, "Enum_EmpSubNameClass_PERSONAL_NAME"),

    //届出氏名
    REPORTED_NAME(1, "Enum_EmpSubNameClass_REPORTED_NAME");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private EmpSubNameClass(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
