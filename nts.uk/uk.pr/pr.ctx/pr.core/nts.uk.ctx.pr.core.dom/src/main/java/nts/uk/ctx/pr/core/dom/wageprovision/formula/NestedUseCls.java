package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 入れ子利用区分
*/
public enum NestedUseCls
{
    
    NOTUSABLE(0, "利用不可"),
    USABLE(1, "利用可能");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private NestedUseCls(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
