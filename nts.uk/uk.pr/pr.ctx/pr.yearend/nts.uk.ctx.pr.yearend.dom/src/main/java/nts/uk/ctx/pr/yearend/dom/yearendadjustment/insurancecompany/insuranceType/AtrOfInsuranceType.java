package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType;


/**
* 保険種類区分
*/
public enum AtrOfInsuranceType
{
    
    GENERAL_LIFE_INSURANCE(0, "一般生命保険"),
    NURSING_MEDICAL_INSURANCE(1, "介護医療保険"),
    INDIVIDUAL_ANNUITY_INSURANCE(2, "個人年金保険");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private AtrOfInsuranceType(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
