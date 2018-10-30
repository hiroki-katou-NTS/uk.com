package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


/**
* 利用マスタ
*/
public enum UsageMaster
{
    
    DEPARMENT(0, "部門"),
    EMPLOYEE(1, "雇用"),
    CLASSIFICATION(2, "分類"),
    POSITION(3, "職位"),
    SALARY(4, "給与分類");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private UsageMaster(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
