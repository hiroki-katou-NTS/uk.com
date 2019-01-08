package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;


/**
* 利用マスタ
*/
public enum UsageMaster {
    // 雇用
    EMPLOYEE(0, "Enum_UsageMaster_EMPLOYEE"),
    // 部門
    DEPARMENT(1, "Enum_UsageMaster_DEPARMENT"),
    // 分類
    CLASSIFICATION(2, "Enum_UsageMaster_CLASSIFICATION"),
    // 職位
    POSITION(3, "Enum_UsageMaster_POSITION"),
    // 給与分類
    SALARY(4, "Enum_UsageMaster_SALARY");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private UsageMaster(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
