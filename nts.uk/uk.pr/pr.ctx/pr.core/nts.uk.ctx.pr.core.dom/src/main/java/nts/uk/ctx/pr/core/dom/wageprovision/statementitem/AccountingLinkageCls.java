package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;


/**
* 会計連動区分
*/
public enum AccountingLinkageCls
{
    
    LINKAGE(0, "連動可"),
    UNABLE_TO_LINK(1, "連動不可");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private AccountingLinkageCls(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
