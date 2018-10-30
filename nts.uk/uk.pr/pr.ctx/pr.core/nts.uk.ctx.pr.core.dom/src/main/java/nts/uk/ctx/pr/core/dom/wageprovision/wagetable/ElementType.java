package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;


/**
 * 明細書印字区分
 */
public enum ElementType
{
    EMPLOYMENT(0),
    DEPARTMENT(1),
    CLASSIFICATION(2),
    JOB_TITLE(3),
    SALARY_CLASSIFICATION(4),
    QUALIFICATION(5),
    FINE_WORK(6),
    AGE(7),
    SERVICE_YEAR(8),
    FAMILY_MEMBER(9);


    /** The value. */
    public final int value;

    private ElementType(int value)
    {
        this.value = value;
    }
}
