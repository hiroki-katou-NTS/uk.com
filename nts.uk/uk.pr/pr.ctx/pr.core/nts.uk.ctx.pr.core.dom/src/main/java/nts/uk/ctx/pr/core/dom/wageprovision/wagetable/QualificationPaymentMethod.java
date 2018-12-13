package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

/**
 * 資格支払方法
 */
public enum QualificationPaymentMethod {
    ADD_MULTIPLE_APPLICABLE_AMOUNT(0, "複数該当した金額を加算する"),
    PAY_ONLY_ONE_HIGHEST_BENEFIT(1, "一番高い手当を1つだけ支給する");


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
