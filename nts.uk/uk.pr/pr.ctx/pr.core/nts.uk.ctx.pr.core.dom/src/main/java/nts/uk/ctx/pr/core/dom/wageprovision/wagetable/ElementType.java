package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;
/**
 * 要素種類
 */
public enum ElementType {
    EMPLOYMENT("M001", "雇用"),
    DEPARTMENT("M002", "部門"),
    CLASSIFICATION("M003", "分類"),
    JOB_TITLE("M004", "職位"),
    SALARY_CLASSIFICATION("M005", "給与分類"),
    QUALIFICATION("M006", "資格"),
    FINE_WORK("M007", "精皆勤レベル"),
    AGE("N001", "年齢"),
    SERVICE_YEAR("N002", "勤続年数"),
    FAMILY_MEMBER("N003", "家族人数");

    /** The value. */
    public final String value;

    /** The name id. */
    public final String nameId;
    private ElementType(String value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
