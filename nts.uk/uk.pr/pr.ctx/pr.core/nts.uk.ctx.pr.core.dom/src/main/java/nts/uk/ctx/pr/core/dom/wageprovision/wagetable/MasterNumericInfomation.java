package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;


/**
 * 明細書印字区分
 */
public enum MasterNumericInfomation
{
    MASTER_ITEM(0),
    NUMERIC_ITEM(1);

    /** The value. */
    public final int value;

    private MasterNumericInfomation(int value)
    {
        this.value = value;
    }
}
