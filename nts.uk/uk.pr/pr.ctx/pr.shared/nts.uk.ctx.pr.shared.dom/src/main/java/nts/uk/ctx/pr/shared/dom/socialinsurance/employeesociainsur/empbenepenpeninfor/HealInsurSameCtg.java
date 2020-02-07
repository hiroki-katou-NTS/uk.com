package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;


/**
* 健保同一区分
*/
public enum HealInsurSameCtg
{
    
    SAMESETTING(0, "ENUM_HealInsurSameCtg_SAMESETTING"),
    SETUPDIVIDUAL(1, "ENUM_HealInsurSameCtg_SETUPDIVIDUAL");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private HealInsurSameCtg(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
