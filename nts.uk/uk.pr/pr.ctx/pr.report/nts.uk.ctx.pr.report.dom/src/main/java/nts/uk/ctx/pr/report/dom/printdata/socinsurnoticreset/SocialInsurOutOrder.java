package nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset;

public enum SocialInsurOutOrder {
    HEAL_INSUR_NUMBER_ORDER(0, "Enum_SocialInsurOutOrder_HEAL_INSUR_NUMBER_ORDER"),
    WELF_AREPEN_NUMBER_ORDER(1, "Enum_SocialInsurOutOrder_WELF_AREPEN_NUMBER_ORDER"),
    HEAL_INSUR_NUMBER_UNION_ORDER(2, "Enum_SocialInsurOutOrder_HEAL_INSUR_NUMBER_UNION_ORDER"),
    ORDER_BY_FUND(3, "Enum_SocialInsurOutOrder_ORDER_BY_FUND"),
    DIVISION_EMPLOY_ORDER(4, "Enum_SocialInsurOutOrder_HEAL_INSUR_OFF_ARR_SYMBOL"),
    EMPLOYEE_CODE_ORDER(5, "Enum_SocialInsurOutOrder_EMPLOYEE_CODE_ORDER"),
    EMPLOYEE_KANA_ORDER(6, "Enum_SocialInsurOutOrder_EMPLOYEE_KANA_ORDER"),
    INSURED_PER_NUMBER_ORDER(7, "Enum_SocialInsurOutOrder_INSURED_PER_NUMBER_ORDER");

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
