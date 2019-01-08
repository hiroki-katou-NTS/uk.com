package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.pererror;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerError;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveError;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveError;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.PauseError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 休暇残数エラーから月別残数エラー一覧を作成する
 * @author shuichi_ishida
 */
public interface CreatePerErrorsFromLeaveErrors {

	/**
	 * 年休エラーから月別残数エラー一覧を作成する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param annualLeaveErrors 年休エラー情報
	 * @return 社員の月別残数エラー一覧
	 */
	List<EmployeeMonthlyPerError> fromAnnualLeave(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, List<AnnualLeaveError> annualLeaveErrors);

	/**
	 * 積立年休エラーから月別残数エラー一覧を作成する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param reserveLeaveErrors 積立年休エラー情報
	 * @return 社員の月別残数エラー一覧
	 */
	List<EmployeeMonthlyPerError> fromReserveLeave(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, List<ReserveLeaveError> reserveLeaveErrors);

	/**
	 * 振休エラーから月別残数エラー一覧を作成する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param pauseErrors 振休エラー情報
	 * @return 社員の月別残数エラー一覧
	 */
	List<EmployeeMonthlyPerError> fromPause(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, List<PauseError> pauseErrors);

	/**
	 * 代休エラーから月別残数エラー一覧を作成する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param dayOffErrors 代休エラー情報
	 * @return 社員の月別残数エラー一覧
	 */
	List<EmployeeMonthlyPerError> fromDayOff(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, List<DayOffError> dayOffErrors);

	/**
	 * 特別休暇エラーから月別残数エラー一覧を作成する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param specialLeaveErrors 特別休暇エラー情報
	 * @return 社員の月別残数エラー一覧
	 */
	List<EmployeeMonthlyPerError> fromSpecialLeave(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, int specialLeaveNo, List<SpecialLeaveError> specialLeaveErrors);
}
