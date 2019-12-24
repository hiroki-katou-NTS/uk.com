package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;


/**
* 控除合計対象区分
*/
public enum DeductionTotalObjAtr
{
    
    OUTSIDE(0, "Enum_DeductionTotalObjAtr_OUTSIDE"),
    INSIDE(1, "Enum_DeductionTotalObjAtr_INSIDE");
    
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
