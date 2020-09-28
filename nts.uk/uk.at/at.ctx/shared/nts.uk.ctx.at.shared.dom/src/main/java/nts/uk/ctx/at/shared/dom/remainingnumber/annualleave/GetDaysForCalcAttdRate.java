package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.CalYearOffWorkAttendRate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 出勤率計算用日数を取得する
 * @author shuichu_ishida
 */
public class GetDaysForCalcAttdRate {

	private static Object NullObject = new Object();
	
	/**
	 * 日別実績から出勤率計算用日数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 年休出勤率計算結果
	 */
	/** 日別実績から出勤率計算用日数を取得 */
	public static CalYearOffWorkAttendRate algorithm(RequireM1 require, String companyId, String employeeId, DatePeriod period) {
		
		double prescribedDays = 0.0;
		double workingDays = 0.0;
		double deductedDays = 0.0;
		
		Map<String, Object> workTypes = new HashMap<>();
		
		// 「日別実績の勤務情報」を取得
		val workInfos = require.dailyWorkInfos(employeeId, period);
		for (val workInfo : workInfos.entrySet()){
			if (workInfo.getValue().getRecordInfo() == null) continue;
			if (workInfo.getValue().getRecordInfo().getWorkTypeCode() == null) continue;
			val workTypeCode = workInfo.getValue().getRecordInfo().getWorkTypeCode().v();
		
			// 「勤務種類」を取得
			WorkType workType = null;
			if (workTypes.containsKey(workTypeCode)){
				if (workTypes.get(workTypeCode) != NullObject) workType = (WorkType)workTypes.get(workTypeCode);
			}
			else {
				val workTypeOpt = require.workType(companyId, workTypeCode);
				if (workTypeOpt.isPresent()){
					workType = workTypeOpt.get();
					workTypes.putIfAbsent(workTypeCode, workType);
				}
				else {
					workTypes.putIfAbsent(workTypeCode, NullObject);
				}
			}
			if (workType == null) continue;
			
			// 勤務種類を判断してカウント数を取得
			WorkTypeDaysCountTable daysCount = new WorkTypeDaysCountTable(
					workType, new VacationAddSet(), Optional.empty());
			
			// 所定日数に取得したカウント数を加算
			prescribedDays += daysCount.getPredetermineDays().v();
			
			// 出勤率の計算方法を取得
			switch (workType.getCalculateMethod()){
			case DO_NOT_GO_TO_WORK:
				break;
			case MAKE_ATTENDANCE_DAY:
				// 労働日数に1日加算
				workingDays += 1.0;
				break;
			case EXCLUDE_FROM_WORK_DAY:
				// 控除日数に1日加算
				deductedDays += 1.0;
				break;
			case TIME_DIGEST_VACATION:
				//*****（未）　設計保留中。2018.7.26
				break;
			}
		}
		
		return new CalYearOffWorkAttendRate(0.0, prescribedDays, workingDays, deductedDays);
	}

	/**
	 * 日別実績から出勤率計算用日数を取得　（月別集計用）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param companySets 月別集計で必要な会社別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @return 年休出勤率計算結果
	 */
	
	/** 日別実績から出勤率計算用日数を取得　（月別集計用） */
	public static CalYearOffWorkAttendRate algorithm(RequireM2 require, String companyId, String employeeId, DatePeriod period,
			MonAggrCompanySettings companySets, MonthlyCalculatingDailys monthlyCalcDailys) {
		
		double prescribedDays = 0.0;
		double workingDays = 0.0;
		double deductedDays = 0.0;
		
		// 「日別実績の勤務情報」を取得
		val workInfos = monthlyCalcDailys.getWorkInfoOfDailyMap();
		for (val workInfo : workInfos.entrySet()){
			if (!period.contains(workInfo.getKey())) continue;
			if (workInfo.getValue().getRecordInfo() == null) continue;
			if (workInfo.getValue().getRecordInfo().getWorkTypeCode() == null) continue;
			val workTypeCode = workInfo.getValue().getRecordInfo().getWorkTypeCode().v();
		
			// 「勤務種類」を取得
			WorkType workType = companySets.getWorkTypeMap(require, workTypeCode);
			if (workType == null) continue;
			
			// 勤務種類を判断してカウント数を取得
			WorkTypeDaysCountTable daysCount = new WorkTypeDaysCountTable(
					workType, new VacationAddSet(), Optional.empty());
			
			// 所定日数に取得したカウント数を加算
			prescribedDays += daysCount.getPredetermineDays().v();
			
			// 出勤率の計算方法を取得
			switch (workType.getCalculateMethod()){
			case DO_NOT_GO_TO_WORK:
				break;
			case MAKE_ATTENDANCE_DAY:
				// 労働日数に1日加算
				workingDays += 1.0;
				break;
			case EXCLUDE_FROM_WORK_DAY:
				// 控除日数に1日加算
				deductedDays += 1.0;
				break;
			case TIME_DIGEST_VACATION:
				//*****（未）　設計保留中。2018.7.26
				break;
			}
		}
		
		return new CalYearOffWorkAttendRate(0.0, prescribedDays, workingDays, deductedDays);
	}
	
	public static interface RequireM1 {

		Map<GeneralDate, WorkInfoOfDailyAttendance> dailyWorkInfos(String employeeId, DatePeriod datePeriod);
		
		Optional<WorkType> workType(String companyId, String workTypeCd);
	}
	
	public static interface RequireM2 extends MonAggrCompanySettings.RequireM4 {
		
	}
}
