package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


/**
* 利用マスタ
*/
public enum UsageMaster {

    EMPLOYEE(0, "雇用"),
    DEPARMENT(1, "部門"),
    CLASSIFICATION(2, "分類"),
    POSITION(3, "職位"),
    SALARY(4, "給与分類");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private UsageMaster(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
