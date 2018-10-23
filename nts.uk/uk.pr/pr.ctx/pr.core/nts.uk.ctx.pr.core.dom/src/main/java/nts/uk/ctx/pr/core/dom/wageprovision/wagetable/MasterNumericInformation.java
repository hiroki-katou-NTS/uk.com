package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;


/**
* 要素設定
*/
public enum MasterNumericInformation {

    MASTER_ITEM(0, "マスタ項目"),
    NUMERIC_ITEM(1, "数値項目");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private MasterNumericInformation(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
