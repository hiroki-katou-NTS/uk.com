package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;


/**
* 厚生年金保険資格喪失原因
*/
enum ReasonsForLossPensionIns {
    
    BLANK(0, "Enum_ReasonsForLossPensionIns_BLANK"),
    RETIREMENT(4, "Enum_ReasonsForLossPensionIns_RETIREMENT"),
    DEATH(5, "Enum_ReasonsForLossPensionIns_DEATH"),
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
