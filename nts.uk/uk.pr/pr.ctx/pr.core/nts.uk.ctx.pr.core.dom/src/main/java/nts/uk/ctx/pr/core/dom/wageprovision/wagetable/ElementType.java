package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;
/**
 * 要素種類
 */
public enum ElementType {
    EMPLOYMENT(0, "M001 雇用"),
    DEPARTMENT(1, "M002 部門"),
    CLASSIFICATION(2, "M003 分類"),
    JOB_TITLE(3, "M004 職位"),
    SALARY_CLASSIFICATION(4, "M005 給与分類"),
    QUALIFICATION(5, "M006 資格"),
    FINE_WORK(6, "M007 精皆勤レベル"),
    AGE(7, "N001 年齢"),
    SERVICE_YEAR(8, "N002 勤続年数"),
    FAMILY_MEMBER(9, "N003 家族人数");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private ElementType(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
