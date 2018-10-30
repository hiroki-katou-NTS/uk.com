package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;


/**
* 控除合計対象区分
*/
public enum DeductionTotalObjAtr
{
    
    OUTSIDE(0, "合計対象外"),
    INSIDE(1, "合計対象内");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private DeductionTotalObjAtr(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
