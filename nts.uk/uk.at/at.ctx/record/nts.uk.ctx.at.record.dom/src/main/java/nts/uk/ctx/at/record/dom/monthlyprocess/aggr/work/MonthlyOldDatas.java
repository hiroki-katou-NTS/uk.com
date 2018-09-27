package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 集計前の月別実績データ
 * @author shuichu_ishida
 */
@Getter
public class MonthlyOldDatas {

	/** 月別実績の勤怠時間 */
	private Optional<AttendanceTimeOfMonthly> attendanceTime;
	/** 月別実績の任意項目 */
	private List<AnyItemOfMonthly> anyItemList;
	
	public MonthlyOldDatas(){
		this.attendanceTime = Optional.empty();
		this.anyItemList = new ArrayList<>();
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

		MonthlyOldDatas result = new MonthlyOldDatas();
		
		// 月別実績の勤怠時間
		result.attendanceTime = repositories.getAttendanceTimeOfMonthly().find(
				employeeId, yearMonth, closureId, closureDate);
		
		// 月別実績の任意項目
		result.anyItemList = repositories.getAnyItemOfMonthly().findByMonthlyAndClosure(
				employeeId, yearMonth, closureId, closureDate);
		
		return result;
	}

	/**
	 * データ取得
	 * @param attendanceTime 月別実績の勤怠時間
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 集計前の月別実績データ
	 */
	public static MonthlyOldDatas loadData(
			Optional<AttendanceTimeOfMonthly> attendanceTime,
			RepositoriesRequiredByMonthlyAggr repositories){

		MonthlyOldDatas result = new MonthlyOldDatas();
		
		// 月別実績の勤怠時間
		result.attendanceTime = attendanceTime;
		
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
			RepositoriesRequiredByMonthlyAggr repositories){

		// 月別実績(WORK)指定がない時、データ読み込み
		if (!monthlyWorkOpt.isPresent()){
			return MonthlyOldDatas.loadData(employeeId, yearMonth, closureId, closureDate, repositories);
		}
		
		MonthlyOldDatas result = new MonthlyOldDatas();
		val monthlyWork = monthlyWorkOpt.get();
		
		// 月別実績の勤怠時間
		result.attendanceTime = monthlyWork.getAttendanceTime();
		
		// 月別実績の任意項目
		result.anyItemList = monthlyWork.getAnyItemList();
		
		return result;
	}
}
