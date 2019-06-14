package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

/**
 * 範囲利用区分
 */
public enum UseRangeAtr {

    /**
     * 設定する
     */
    USE(1),
    /**
     * 設定しない
     */
    NOT_USE(0);

    public final int value;

    private UseRangeAtr(int value) {
        this.value = value;
    }
}
