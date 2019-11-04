package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;


/**
* 雇用保険出力順
*/
public enum EmpInsOutOrder {

    //被保険者番号順
    INSURANCE_NUMBER(0, "Enum_EmpInsOutOrder_INSURANCE_NUMBER"),

    //部門社員順
    DEPARTMENT_EMPLOYEE(1, "Enum_EmpInsOutOrder_DEPARTMENT_EMPLOYEE"),

    //社員コード順
    EMPLOYEE_CODE(2, "Enum_EmpInsOutOrder_EMPLOYEE_CODE"),

    //社員カナ順
    EMPLOYEE(3, "Enum_EmpInsOutOrder_EMPLOYEE");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private EmpInsOutOrder(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
