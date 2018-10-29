package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;


/**
 * 厚生年金基金加入区分
 */
public enum FundClassification {

    JOIN(1, "Enum_FundClassification_JOIN"),
    NOT_JOIN(0, "Enum_FundClassification_NOT_JOIN");

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    private FundClassification(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
