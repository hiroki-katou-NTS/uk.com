package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;


/**
* 明細書出力区分
*/
public enum SpecificationOutputCls
{
    
    OUTPUT(0, "0：出力する"),
    DO_NOT_OUTPUT(1, "1：出力しない"),
    ALWAYS_OUTPUT(2, "2：常に出力する");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private SpecificationOutputCls(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
