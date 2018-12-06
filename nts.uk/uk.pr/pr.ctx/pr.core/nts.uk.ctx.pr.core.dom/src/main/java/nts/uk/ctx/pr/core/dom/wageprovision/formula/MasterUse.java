package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 使用マスタ
*/
public enum MasterUse
{
    
    EMPLOYMENT(0, "雇用"),
    DEPARTMENT(1, "部門"),
    CLASSIFICATION(2, "分類"),
    JOB_TITLE(3, "職位"),
    SALARY_CLASSIFICATION(4, "給与分類"),
    SALARY_FORM(5, "給与形態");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private MasterUse(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
