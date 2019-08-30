package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;


/**
* 厚生年金保険資格喪失原因
*/
public enum ReasonsForLossPensionIns {
    //空白
    BLANK(0, "Enum_ReasonsForLossPensionIns_BLANK"),
    //退職等
    RETIREMENT(4, "Enum_ReasonsForLossPensionIns_RETIREMENT"),
    //死亡
    DEATH(5, "Enum_ReasonsForLossPensionIns_DEATH"),
    //70歳到達（厚生年金保険のみ喪失）
    ONLY_PENSION_INSURANCE(6, "Enum_ReasonsForLossPensionIns_ONLY_PENSION_INSURANCE");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ReasonsForLossPensionIns(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
