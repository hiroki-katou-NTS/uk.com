package nts.uk.ctx.at.record.dom.monthly.verticaltotal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.WorkDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.WorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の縦計
 * @author shuichu_ishida
 */
@Getter
public class VerticalTotalOfMonthly {
	
	/** 勤務時間 */
	private WorkTimeOfMonthly workTime;
	/** 勤務日数 */
	private WorkDaysOfMonthly workDays;
	
	/**
	 * コンストラクタ
	 */
	public VerticalTotalOfMonthly(){
		
		this.workTime = new WorkTimeOfMonthly();
		this.workDays = new WorkDaysOfMonthly();
	}
	
	/**
	 * ファクトリー
	 * @param workTime 勤務時間
	 * @param workDays 勤務日数
	 * @return 月別実績の縦計
	 */
	public static VerticalTotalOfMonthly of(
			WorkTimeOfMonthly workTime,
			WorkDaysOfMonthly workDays){
		
		val domain = new VerticalTotalOfMonthly();
		domain.workTime = workTime;
		domain.workDays = workDays;
		return domain;
	}
	
	/**
	 * 縦計
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void verticalTotal(
			String companyId,
			String employeeId,
			DatePeriod datePeriod,
			WorkingSystem workingSystem,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 日別実績の勤務情報　取得
		//*****（未）　適切なメソッドがまだない。仮に、1日だけ取る。正式には、期間で取ってくる。
		List<WorkInfoOfDailyPerformance> workInfoOfDailys = new ArrayList<>();
		val workInfoOfDailyOpt = repositories.getWorkInformationOfDaily().find(employeeId, datePeriod.start());
		if (workInfoOfDailyOpt.isPresent()){
			workInfoOfDailys.add(workInfoOfDailyOpt.get());
		}
		Map<GeneralDate, WorkInfoOfDailyPerformance> workInfoOfDailyMap = new HashMap<>();
		for (val workInfoOfDaily : workInfoOfDailys){
			val ymd = workInfoOfDaily.getYmd();
			if (!workInfoOfDailyMap.containsKey(ymd)) workInfoOfDailyMap.put(ymd, workInfoOfDaily);
		}
		
		// 日別実績の勤怠時間　取得
		//*****（未）　適切なメソッドがまだない。仮に、1日だけ取る。正式には、期間で取ってくる。
		List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys = new ArrayList<>();
		val attendanceTimeOfDailyOpt = repositories.getAttendanceTimeOfDaily().find(employeeId, datePeriod.start());
		if (attendanceTimeOfDailyOpt.isPresent()){
			attendanceTimeOfDailys.add(attendanceTimeOfDailyOpt.get());
		}
		
		// 日別実績の出退勤
		//*****（未）　適切なメソッドがまだない。仮に、1日だけ取る。正式には、期間で取ってくる。
		List<TimeLeavingOfDailyPerformance> timeLeaveingOfDailys = new ArrayList<>();
		val timeLeaveOfDailyOpt = repositories.getTimeLeavingOfDaily().findByKey(employeeId, datePeriod.start());
		if (timeLeaveOfDailyOpt.isPresent()){
			timeLeaveingOfDailys.add(timeLeaveOfDailyOpt.get());
		}
		
		// 日別実績の特定日区分
		//*****（未）　適切なメソッドがまだない。仮に、1日だけ取る。正式には、期間で取ってくる。
		List<SpecificDateAttrOfDailyPerfor> specificDateAtrOfDailys = new ArrayList<>();
		val specificDateAtrOfDailyOpt = repositories.getSpecificDateAtrOfDaily().find(employeeId, datePeriod.start());
		if (specificDateAtrOfDailyOpt.isPresent()){
			specificDateAtrOfDailys.add(specificDateAtrOfDailyOpt.get());
		}
		
		// 勤務情報　取得
		val workTypes = repositories.getWorkType().findByCompanyId(companyId);
		Map<String, WorkType> workTypeMap = new HashMap<>();
		for (val workType : workTypes){
			val workTypeCd = workType.getWorkTypeCode().v();
			if (!workTypeMap.containsKey(workTypeCd)) workTypeMap.put(workTypeCd, workType);
		}
		
		// 回数集計　取得
		//*****（未）　集計での利用方法があいまいなため、設計確認要。
		val totalTimesList = repositories.getTotalTimes().getAllTotalTimes(companyId);
		
		// 勤務日数集計
		this.workDays.aggregate(workInfoOfDailys, attendanceTimeOfDailys, timeLeaveingOfDailys,
				specificDateAtrOfDailys, workTypeMap);
		
		// 勤務時間集計
		this.workTime.aggregate(workInfoOfDailyMap, attendanceTimeOfDailys, specificDateAtrOfDailys,
				workTypeMap);
	}
}
