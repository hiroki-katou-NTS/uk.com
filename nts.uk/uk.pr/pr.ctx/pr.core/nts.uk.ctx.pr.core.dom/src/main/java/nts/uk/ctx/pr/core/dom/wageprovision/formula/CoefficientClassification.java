package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 係数区分（固定項目）
*/
public enum CoefficientClassification
{
    
    FIXED_VALUE(0, "固定値"),
    WORK_DAY(1, "出勤日数"),
    WORKDAY_AND_HOLIDAY(2, "出勤日数＋年休使用数");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private CoefficientClassification(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
