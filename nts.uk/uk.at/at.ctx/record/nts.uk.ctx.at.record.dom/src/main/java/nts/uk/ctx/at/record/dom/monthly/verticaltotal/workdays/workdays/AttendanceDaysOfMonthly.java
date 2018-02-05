package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.vtotalwork.AttendanceStatusMap;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の出勤日数
 * @author shuichu_ishida
 */
@Getter
public class AttendanceDaysOfMonthly {

	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 */
	public AttendanceDaysOfMonthly(){
		
		this.days = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @return 月別実績の出勤日数
	 */
	public static AttendanceDaysOfMonthly of(AttendanceDaysMonth days){
		
		val domain = new AttendanceDaysOfMonthly();
		domain.days = days;
		return domain;
	}
	
	/**
	 * 集計
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param workInfoOfDailys 日別実績の勤務情報リスト
	 * @param workTypeMap 勤務種類マップ
	 * @param attendanceStatusMap 出勤状態マップ
	 * @param vacationAddSet 休暇加算設定
	 */
	public void aggregate(
			DatePeriod datePeriod,
			WorkingSystem workingSystem,
			List<WorkInfoOfDailyPerformance> workInfoOfDailys,
			Map<String, WorkType> workTypeMap,
			AttendanceStatusMap attendanceStatusMap,
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
			
			// 労働制を取得
			if (workingSystem == WorkingSystem.EXCLUDED_WORKING_CALCULATE){

				// 計算対象外の時、無条件で、出勤日数に加算する
				this.days = this.days.addDays(workTypeDaysCountTable.getAttendanceDays().v());
			}
			else {
				
				// その他労働制の時、出勤状態を判断して、出勤日数に加算する
				if (attendanceStatusMap.isAttendanceDay(ymd)){
					this.days = this.days.addDays(workTypeDaysCountTable.getAttendanceDays().v());
				}
			}
		}
	}
}
