package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;


/**
* 給与契約形態ごとの対象区分
*/
public enum SalaryContractTypeClass
{
    
    COVERED(1, "対象"),
    NOT_COVERED(0, "対象外");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private SalaryContractTypeClass(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
