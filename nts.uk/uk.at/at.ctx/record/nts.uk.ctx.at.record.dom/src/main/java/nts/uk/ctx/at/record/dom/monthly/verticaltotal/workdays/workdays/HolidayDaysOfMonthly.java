package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の休日日数
 * @author shuichu_ishida
 */
@Getter
public class HolidayDaysOfMonthly {

	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 */
	public HolidayDaysOfMonthly(){
		
		this.days = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @return 月別実績の休日日数
	 */
	public static HolidayDaysOfMonthly of(AttendanceDaysMonth days){
		
		val domain = new HolidayDaysOfMonthly();
		domain.days = days;
		return domain;
	}
	
	/**
	 * 集計
	 * @param datePeriod 期間
	 * @param workInfoOfDailys 日別実績の勤務情報リスト
	 * @param workTypeMap 勤務種類マップ
	 * @param vacationAddSet 休暇加算設定
	 */
	public void aggregate(
			DatePeriod datePeriod,
			List<WorkInfoOfDailyPerformance> workInfoOfDailys,
			Map<String, WorkType> workTypeMap,
			VacationAddSet vacationAddSet){
		
		this.days = new AttendanceDaysMonth(0.0);
		for (val workInfoOfDaily : workInfoOfDailys){
			val ymd = workInfoOfDaily.getYmd();
			if (!datePeriod.contains(ymd)) continue;
			val recordWorkInfo = workInfoOfDaily.getRecordWorkInformation();
			val workTypeCd = recordWorkInfo.getWorkTypeCode();
			if (!workTypeMap.containsKey(workTypeCd.v())) continue;
			val workType = workTypeMap.get(workTypeCd.v());
			
			// 勤務種類を判断しカウント数を取得する
			val workTypeDaysCountTable = new WorkTypeDaysCountTable(workType, vacationAddSet);
			
			// 休日日数に加算する
			this.days = this.days.addDays(workTypeDaysCountTable.getHolidayDays().v());
		}
	}
}
