package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;


/**
* 支給合計対象区分
*/
public enum PaymentTotalObjAtr
{
	// 合計対象内
    INSIDE(0, "Enum_PaymentTotalObjAtr_INSIDE"),
    // 合計対象外
    OUTSIDE(1, "Enum_PaymentTotalObjAtr_OUTSIDE"),
    // 合計対象内（現物）
    INSIDE_ACTUAL(2, "Enum_PaymentTotalObjAtr_INSIDE_ACTUAL"),
    // 合計対象外（現物）
    OUTSIDE_ACTUAL(3, "Enum_PaymentTotalObjAtr_OUTSIDE_ACTUAL");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
	public final String nameId;
    
    private PaymentTotalObjAtr(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
