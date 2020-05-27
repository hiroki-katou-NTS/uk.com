package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 集計前の月別実績データ
 * @author shuichi_ishida
 */
@Getter
public class MonthlyOldDatas {

	/** 月別実績の勤怠時間 */
	private Optional<AttendanceTimeOfMonthly> attendanceTime;
	/** 月別実績の任意項目 */
	private List<AnyItemOfMonthly> anyItemList;
	/** 年休月別残数データ */
	private Optional<AnnLeaRemNumEachMonth> annualLeaveRemain;
	/** 積立年休残数月別データ */
	private Optional<RsvLeaRemNumEachMonth> reserveLeaveRemain;
	/** 振休月別残数データ */
	private Optional<AbsenceLeaveRemainData> absenceLeaveRemain;
	/** 代休月別残数データ */
	private Optional<MonthlyDayoffRemainData> monthlyDayoffRemain;
	/** 特別休暇月別残数データ */
	private List<SpecialHolidayRemainData> specialLeaveRemainList;
	
	public MonthlyOldDatas(){
		this.attendanceTime = Optional.empty();
		this.anyItemList = new ArrayList<>();
		this.annualLeaveRemain = Optional.empty();
		this.reserveLeaveRemain = Optional.empty();
		this.absenceLeaveRemain = Optional.empty();
		this.monthlyDayoffRemain = Optional.empty();
		this.specialLeaveRemainList = new ArrayList<>();
	}

	/**
	 * データ取得
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 集計前の月別実績データ
	 */
	public static MonthlyOldDatas loadData(
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			RepositoriesRequiredByMonthlyAggr repositories){
		val require = createRequireImpl(repositories);
		return loadDataRequire(require, employeeId, yearMonth, closureId, closureDate, repositories);
	}
	
	public static MonthlyOldDatas loadDataRequire(
			Require require,
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			RepositoriesRequiredByMonthlyAggr repositories){

		MonthlyOldDatas result = new MonthlyOldDatas();
		
		// 月別実績の勤怠時間
		result.attendanceTime = require.find(
				employeeId, yearMonth, closureId, closureDate);
		
		// 月別実績の任意項目
		result.anyItemList = require.findByMonthlyAndClosure(
				employeeId, yearMonth, closureId, closureDate);
		
		// 年休月別残数データ
		result.annualLeaveRemain = require.findAnnLeaRemNumEachMonth(
				employeeId, yearMonth, closureId, closureDate);
		
		// 積立年休月別残数データ
		result.reserveLeaveRemain = require.findRsvLeaRemNumEachMonth(
				employeeId, yearMonth, closureId, closureDate);
		
		// 振休月別残数データ
		result.absenceLeaveRemain = require.findAbsenceLeaveRemainData(
				employeeId, yearMonth, closureId, closureDate);
		
		// 代休月別残数データ
		result.monthlyDayoffRemain = require.findMonthDayoffRemainData(
				employeeId, yearMonth, closureId, closureDate);
		
		// 特別休暇月別残数データ
		result.specialLeaveRemainList = require.findSpecialHolidayRemainData(
				employeeId, yearMonth, closureId, closureDate);
		
		return result;
	}

	/**
	 * データ取得
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param monthlyWorkOpt 月別実績(WORK)
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 集計前の月別実績データ
	 */
	public static MonthlyOldDatas loadData(
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			Optional<IntegrationOfMonthly> monthlyWorkOpt,
			RepositoriesRequiredByMonthlyAggr repositories) {
		val require = createRequireImpl(repositories);
		return loadDataRequire(require, employeeId, yearMonth, closureId, closureDate, monthlyWorkOpt, repositories);
	}
	
	public static MonthlyOldDatas loadDataRequire(
			Require require,
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			Optional<IntegrationOfMonthly> monthlyWorkOpt,
			RepositoriesRequiredByMonthlyAggr repositories){

		// 月別実績(WORK)指定がない時、データ読み込み
		if (!monthlyWorkOpt.isPresent()){
			return MonthlyOldDatas.loadDataRequire(require, employeeId, yearMonth, closureId, closureDate, repositories);
		}
		
		MonthlyOldDatas result = new MonthlyOldDatas();
		val monthlyWork = monthlyWorkOpt.get();
		
		// 月別実績の勤怠時間
		result.attendanceTime = monthlyWork.getAttendanceTime();
		
		// 月別実績の任意項目
		result.anyItemList = monthlyWork.getAnyItemList();
		
		// 年休月別残数データ
		result.annualLeaveRemain = monthlyWork.getAnnualLeaveRemain();
		
		// 積立年休月別残数データ
		result.reserveLeaveRemain = monthlyWork.getReserveLeaveRemain();
		
		// 振休月別残数データ
		result.absenceLeaveRemain = monthlyWork.getAbsenceLeaveRemain();
		
		// 代休月別残数データ
		result.monthlyDayoffRemain = monthlyWork.getMonthlyDayoffRemain();
		
		// 特別休暇月別残数データ
		result.specialLeaveRemainList = monthlyWork.getSpecialLeaveRemainList();
		
		return result;
	}
	
	public static interface Require{
		//repositories.getAttendanceTimeOfMonthly().find(employeeId, yearMonth, closureId, closureDate);
		Optional<AttendanceTimeOfMonthly> find(String employeeId, YearMonth yearMonth,ClosureId closureId, ClosureDate closureDate);
		//repositories.getAnyItemOfMonthly().findByMonthlyAndClosure(employeeId, yearMonth, closureId, closureDate);
		List<AnyItemOfMonthly> findByMonthlyAndClosure(String employeeId, YearMonth yearMonth,ClosureId closureId, ClosureDate closureDate);
//		repositories.getAnnLeaRemNumEachMonth().find(employeeId, yearMonth, closureId, closureDate);
		Optional<AnnLeaRemNumEachMonth> findAnnLeaRemNumEachMonth(String employeeId, YearMonth yearMonth,ClosureId closureId, ClosureDate closureDate);
//		repositories. ().find(employeeId, yearMonth, closureId, closureDate);
		Optional<RsvLeaRemNumEachMonth> findRsvLeaRemNumEachMonth(String employeeId, YearMonth yearMonth,ClosureId closureId, ClosureDate closureDate);
//		repositories.getAbsenceLeaveRemainData().find(employeeId, yearMonth, closureId, closureDate);
		Optional<AbsenceLeaveRemainData> findAbsenceLeaveRemainData(String employeeId, YearMonth yearMonth,ClosureId closureId, ClosureDate closureDate);
//		repositories.getMonthlyDayoffRemainData().find(employeeId, yearMonth, closureId, closureDate);
		Optional<MonthlyDayoffRemainData> findMonthDayoffRemainData(String employeeId, YearMonth yearMonth,ClosureId closureId, ClosureDate closureDate);
//		repositories.getSpecialHolidayRemainData().find(employeeId, yearMonth, closureId, closureDate);
		List<SpecialHolidayRemainData> findSpecialHolidayRemainData(String employeeId, YearMonth yearMonth,ClosureId closureId, ClosureDate closureDate);
	}

	public static Require createRequireImpl(RepositoriesRequiredByMonthlyAggr repositories) {
		return new Require() {
			@Override
			public Optional<AttendanceTimeOfMonthly> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
					ClosureDate closureDate) {
				return repositories.getAttendanceTimeOfMonthly().find(employeeId, yearMonth, closureId, closureDate);
			}
			@Override
			public List<AnyItemOfMonthly> findByMonthlyAndClosure(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getAnyItemOfMonthly().findByMonthlyAndClosure(employeeId, yearMonth, closureId, closureDate);
			}
			@Override
			public Optional<AnnLeaRemNumEachMonth> findAnnLeaRemNumEachMonth(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getAnnLeaRemNumEachMonth().find(employeeId, yearMonth, closureId, closureDate);
			}
			@Override
			public Optional<RsvLeaRemNumEachMonth> findRsvLeaRemNumEachMonth(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getRsvLeaRemNumEachMonth().find(employeeId, yearMonth, closureId, closureDate);
			}
			@Override
			public Optional<AbsenceLeaveRemainData> findAbsenceLeaveRemainData(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getAbsenceLeaveRemainData().find(employeeId, yearMonth, closureId, closureDate);
			}
			@Override
			public Optional<MonthlyDayoffRemainData> findMonthDayoffRemainData(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getMonthlyDayoffRemainData().find(employeeId, yearMonth, closureId, closureDate);
			}
			@Override
			public List<SpecialHolidayRemainData> findSpecialHolidayRemainData(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getSpecialHolidayRemainData().find(employeeId, yearMonth, closureId, closureDate);
			}
		}; 
	}
}