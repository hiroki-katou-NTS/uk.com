package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 基底項目区分
*/
public enum BaseItemClassification
{
    
    FIXED_VALUE(0, "固定値"),
    STANDARD_DAY(1, "基準日数"),
    WORKDAY(2, "要勤務日数"),
    ATTENDANCE_DAY(3, "出勤日数"),
    ATTENDANCE_DAY_AND_HOLIDAY(4, "出勤日数＋年休使用数"),
    REFERENCE_DATE_TIME(5, "基準日数×基準時間"),
    SERVICE_DAY_MUL_REFER_TIME(6, "要勤務日数×基準時間"),
    WORKDAY_MUL_REFER_TIME(7, "出勤日数×基準時間"),
    WORKDAY_AND_HOLIDAY_MUL_REFER_TIME(8, "（出勤日数＋年休使用数）×基準時間"),
    ATTENDANCE_TIME(9, "出勤時間");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private BaseItemClassification(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
