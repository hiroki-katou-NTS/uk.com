package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;


/**
* 固定的賃金区分
*/
public enum FixedWageClassification
{
    /*
    * 対象
    * */
    OBJECT(1),
    /*
    * 対象外
    * */
    NOT_COVERED(0);
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    private FixedWageClassification(int value)
    {
        this.value = value;
    }
}
