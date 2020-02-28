package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
 * 式中端数処理
 */
public enum RoundingMethod {

    ROUND_OFF(0, "QMM017_168"),
    ROUND_UP(1, "QMM017_169"),
    TRUNCATION(2, "QMM017_170"),
    DO_NOTHING(3, "QMM017_171");

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    private RoundingMethod(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
