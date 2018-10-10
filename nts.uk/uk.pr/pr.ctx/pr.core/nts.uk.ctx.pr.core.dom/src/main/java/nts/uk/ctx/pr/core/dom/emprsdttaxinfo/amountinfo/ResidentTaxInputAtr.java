package nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo;

/**
 * 住民税入力区分
 */
public enum ResidentTaxInputAtr {
    /**
     * 全月入力する
     */
    ALL_MONTH(1),
    /**
     * 全月入力しない
     */
    NOT_ALL_MONTH(0);
    /**
     * The value.
     */
    public final int value;

    private ResidentTaxInputAtr(int value) {
        this.value = value;
    }
}
