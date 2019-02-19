package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;


/**
 * 明細書印字区分
 */
public enum StatementPrintAtr
{
    PRINT(1),
    DO_NOT_PRINT(0);

    /** The value. */
    public final int value;

    private StatementPrintAtr(int value)
    {
        this.value = value;
    }
}
