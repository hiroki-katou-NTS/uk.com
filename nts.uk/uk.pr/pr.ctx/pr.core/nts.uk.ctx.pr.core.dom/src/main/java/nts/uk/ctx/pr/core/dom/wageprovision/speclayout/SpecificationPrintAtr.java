package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;


/**
 * 明細書印字区分
 */
public enum SpecificationPrintAtr
{
    PRINT(0),
    DO_NOT_PRINT(1);

    /** The value. */
    public final int value;

    private SpecificationPrintAtr (int value)
    {
        this.value = value;
    }
}
