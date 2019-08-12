package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;


/**
* 坑内員区分
*/
public enum PitInsiderDivision
{
    
    NOTTARGETED(0, "ENUM_PitInsiderDivision_NOTTARGETED"),
    TARGET(1, "ENUM_PitInsiderDivision_TARGET");
    
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
