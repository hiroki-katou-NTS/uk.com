package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* マスタ分岐利用
*/
public enum MasterBranchUse
{
    
    NOT_USE(0, "利用しない"),
    USE(1, "利用する");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private MasterBranchUse(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
