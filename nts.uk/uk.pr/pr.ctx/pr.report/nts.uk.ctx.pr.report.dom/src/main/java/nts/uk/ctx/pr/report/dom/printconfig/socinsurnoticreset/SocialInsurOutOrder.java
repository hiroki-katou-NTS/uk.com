package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

/**
 * 社会保険出力順
 */
public enum SocialInsurOutOrder {

    //部門社員順
    DIVISION_EMP_ORDER(0, "Enum_SocialInsurOutOrder_DIVISION_EMP_ORDER"),
    //社員コード順
    EMPLOYEE_CODE_ORDER(1, "Enum_SocialInsurOutOrder_EMPLOYEE_CODE_ORDER"),
    //社員カナ順
    EMPLOYEE_KANA_ORDER(2, "Enum_SocialInsurOutOrder_EMPLOYEE_KANA_ORDER"),
    //健康保険番号順
    HEAL_INSUR_NUMBER_ORDER(3, "Enum_SocialInsurOutOrder_HEAL_INSUR_NUMBER_ORDER"),
    //厚生年金番号順
    WELF_AREPEN_NUMBER_ORDER(4, "Enum_SocialInsurOutOrder_WELF_AREPEN_NUMBER_ORDER"),
    //健保組合番号順
    HEAL_INSUR_NUMBER_UNION_ORDER(5, "Enum_SocialInsurOutOrder_HEAL_INSUR_NUMBER_UNION_ORDER"),
    //基金加入員番号順
    ORDER_BY_FUND(6, "Enum_SocialInsurOutOrder_ORDER_BY_FUND"),
    //被保険者整理番号順
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
