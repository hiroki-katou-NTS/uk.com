package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;


/**
* 明細書レイアウトパターン
*/
public enum StatementLayoutPattern
{
    
    LASER_PRINT_A4_PORTRAIT_ONE_PERSON(0),
    LASER_PRINT_A4_PORTRAIT_TWO_PERSON(1),
    LASER_PRINT_A4_PORTRAIT_THREE_PERSON(2),
    LASER_PRINT_A4_LANDSCAPE_TWO_PERSON(3),
    LASER_CRIMP_PORTRAIT_ONE_PERSON(4),
    LASER_CRIMP_LANDSCAPE_ONE_PERSON(5),
    DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON(6);
    
    /** The value. */
    public final int value;

    private StatementLayoutPattern(int value)
    {
        this.value = value;
    }
}
