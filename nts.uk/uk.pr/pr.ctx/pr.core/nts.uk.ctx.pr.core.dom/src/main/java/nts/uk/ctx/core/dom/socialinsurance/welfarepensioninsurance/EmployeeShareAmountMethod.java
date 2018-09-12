package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;


/**
 * 事業主負担分計算方法
 */
public enum EmployeeShareAmountMethod {

    SUBTRACT_INSURANCE_PREMIUM(0, "Enum_EmployeeShareAmountMethod_SUBTRACT_INSURANCE_PREMIUM"),
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
