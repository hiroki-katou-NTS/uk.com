package nts.uk.ctx.pr.core.dom.wageprovision.formula;
/**
 * 端数処理位置
 */
public enum RoundingPosition {

    ONE_YEN(0, "1円"),
    TEN_YEN(1, "10円"),
    ONE_HUNDRED_YEN(1, "100円"),
    ONE_THOUSAND_YEN(1, "1000円");


    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private RoundingPosition(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
