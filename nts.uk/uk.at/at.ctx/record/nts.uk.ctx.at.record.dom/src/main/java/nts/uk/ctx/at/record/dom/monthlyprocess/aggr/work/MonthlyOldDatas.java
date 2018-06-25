package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 集計前の月別実績データ
 * @author shuichu_ishida
 */
@Getter
public class MonthlyOldDatas {

	/** 月別実績の勤怠時間 */
	private Optional<AttendanceTimeOfMonthly> attendanceTime;
	
	public MonthlyOldDatas(){
		
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
		
		return result;
	}

	/**
	 * データ取得
	 * @param attendanceTime 月別実績の勤怠時間
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
}
