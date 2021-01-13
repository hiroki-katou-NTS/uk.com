package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums;

/**
 * Enumeration: 帳票共通の単独計算区分
 * @author chinh.hm
 */
public enum IndependentCalculationClassification {
    //1 	単独
    ALONE(1),

    // 2	計算
    CACULATION(2);

    /** The value. */
    public final int value;

    private IndependentCalculationClassification(int value) {
        this.value = value;
    }

}
