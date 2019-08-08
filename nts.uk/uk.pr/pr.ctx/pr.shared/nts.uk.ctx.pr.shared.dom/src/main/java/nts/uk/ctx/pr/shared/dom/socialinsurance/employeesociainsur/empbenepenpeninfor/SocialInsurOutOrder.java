package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

public enum SocialInsurOutOrder {
    HEAL_INSUR_NUMBER_ORDER(0, "ENUM_healInsurNumberOrder_HEAL_INSUR_NUMBER_ORDER"),
    WELF_AREPEN_NUMBER_ORDER(1, "ENUM_welffarePenNumberOrder_WELF_AREPEN_NUMBER_ORDER"),
    HEAL_INSUR_NUMBER_UNION_ORDER(2, "ENUM_healInsurNumUnionOrder_HEAL_INSUR_NUMBER_UNION_ORDER"),
    ORDER_BY_FUND(3, "ENUM_orderByFund_ORDER_BY_FUND"),
    DIVISION_EMPLOY_ORDER(4, "ENUM_divisionEmOrder_HEAL_INSUR_OFF_ARR_SYMBOL"),
    EMPLOYEE_CODE_ORDER(5, "ENUM_employeeCodeOrder_EMPLOYEE_CODE_ORDER"),
    EMPLOYEE_KANA_ORDER(6, "ENUM_employeeKanaOrder_EMPLOYEE_KANA_ORDER"),
    INSURED_PER_NUMBER_ORDER(7, "ENUM_InsuredPerNumberOrder_INSURED_PER_NUMBER_ORDER");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private SocialInsurOutOrder(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
