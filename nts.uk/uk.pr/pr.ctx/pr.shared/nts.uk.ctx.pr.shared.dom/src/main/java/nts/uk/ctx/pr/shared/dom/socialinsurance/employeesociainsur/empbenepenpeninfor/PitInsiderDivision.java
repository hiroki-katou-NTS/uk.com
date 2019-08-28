package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;


/**
* 坑内員区分
*/
public enum PitInsiderDivision
{
    
    NOTTARGETED(0, "Enum_PitInsiderDivision_NOTTARGETED"),
    TARGET(1, "Enum_PitInsiderDivision_TARGET");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private PitInsiderDivision(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
