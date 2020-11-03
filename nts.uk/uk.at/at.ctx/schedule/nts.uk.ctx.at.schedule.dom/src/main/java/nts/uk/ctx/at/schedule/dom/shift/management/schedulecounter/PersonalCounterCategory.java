package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import nts.arc.enums.EnumAdaptor;

/**
 * 個人計カテゴリ
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.個人計カテゴリ
 * @author dan_pv
 *
 */
public enum PersonalCounterCategory {
	
    /** 月間想定給与額   */
    MONTHLY_EXPECTED_SALARY(0),

    /** 累計想定給与額  */
    CUMULATIVE_ESTIMATED_SALARY(1),

    /** 基準労働時間比較 */
    STANDARD_WORKING_HOURS_COMPARISON(2),

    /** 労働時間 */
    WORKING_HOURS(3),

    /** 夜勤時間 */
    NIGHT_SHIFT_HOURS(4),

    /** 週間休日日数 */
    WEEKS_HOLIDAY_DAYS(5),
 
    /** 出勤・休日日数 */
    ATTENDANCE_HOLIDAY_DAYS(6), 

    /** 回数集計１ */
    TIMES_COUNTING_1(7),
    
    /** 回数集計２ */
    TIMES_COUNTING_2(8),
    
    /** 回数集計３ */
    TIMES_COUNTING_3(9);
	
    public int value;

    private PersonalCounterCategory(int value) {
        this.value = value;
    }
    
    public static PersonalCounterCategory of(int value) {
		return EnumAdaptor.valueOf(value, PersonalCounterCategory.class);
	}

}
