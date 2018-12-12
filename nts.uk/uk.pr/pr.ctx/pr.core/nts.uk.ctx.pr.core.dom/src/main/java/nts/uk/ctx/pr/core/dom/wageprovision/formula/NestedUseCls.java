package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 入れ子利用区分
*/
public enum NestedUseCls
{
    
    NOT_USE(0, "利用不可"),
    USE(1, "利用可能");
    
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
