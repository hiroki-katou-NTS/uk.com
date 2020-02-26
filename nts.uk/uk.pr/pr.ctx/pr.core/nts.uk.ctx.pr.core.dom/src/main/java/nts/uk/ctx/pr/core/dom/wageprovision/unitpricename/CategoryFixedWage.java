package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;


/**
* 固定的賃金区分
*/
public enum CategoryFixedWage
{
    
    COVERED(1, "対象"),
    NOT_COVERED(0, "対象外");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private CategoryFixedWage(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
