package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;


/**
 * 事業主負担分計算方法
 */
public enum EmployeeShareAmountMethod {

    /**
     * 全体の保険料から被保険者分を差し引く
     */
    SUBTRACT_INSURANCE_PREMIUM(0, "Enum_EmployeeShareAmountMethod_SUBTRACT_INSURANCE_PREMIUM"),

    /**
     * 事業主負担率を用いて計算する
     */
    EMPLOYEE_CONTRIBUTION_RATIO(1, "Enum_EmployeeShareAmountMethod_EMPLOYEE_CONTRIBUTION_RATIO");

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    private EmployeeShareAmountMethod(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
