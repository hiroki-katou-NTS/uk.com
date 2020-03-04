package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

/**
 * 資格支払方法
 */
public enum QualificationPaymentMethod {
	
	// 複数該当した金額を加算する
    ADD_MULTIPLE_APPLICABLE_AMOUNT(0, "Enum_Qualify_Pay_Method_Add_Multiple"),
    // 一番高い手当を1つだけ支給する
    PAY_ONLY_ONE_HIGHEST_BENEFIT(1, "Enum_Qualify_Pay_Method_Only_One_Highest");


    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    private QualificationPaymentMethod(int value, String nameId) {
        this.value  = value;
        this.nameId = nameId;
    }
}
