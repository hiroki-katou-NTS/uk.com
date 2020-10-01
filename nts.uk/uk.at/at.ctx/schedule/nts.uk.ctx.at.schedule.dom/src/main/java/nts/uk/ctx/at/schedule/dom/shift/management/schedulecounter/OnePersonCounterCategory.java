package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import nts.arc.enums.EnumAdaptor;

/**
 * 個人計カテゴリ
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.個人計カテゴリ
 * @author dan_pv
 *
 */
public enum OnePersonCounterCategory {
	
    MONTHLY_EXPECTED_SALARY(0), // 月間想定給与額    

    CUMULATIVE_ESTIMATED_SALARY(1), // 累計想定給与額

    STANDARD_WORKING_HOURS_COMPARISON(2), // 基準労働時間比較

    WORKING_HOURS(3), // 労働時間

    NIGHT_SHIFT_HOURS(4), // 夜勤時間    

    WEEKS_HOLIDAY_DAYS(5), // 週間休日日数
 
    ATTENDANCE_HOLIDAY_DAYS(6), // 出勤・休日日数    

    TIMES_COUNTING_1(7), // 回数集計１    
    
    TIMES_COUNTING_2(8), // 回数集計２    
    
    TIMES_COUNTING_3(9); // 回数集計３    
	
    public int value;

    private OnePersonCounterCategory(int value) {
        this.value = value;
    }
    
    public static OnePersonCounterCategory of(int value) {
		return EnumAdaptor.valueOf(value, OnePersonCounterCategory.class);
	}

}
