package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.pererror;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.PauseError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.AnnualLeaveError;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.EmployeeMonthlyPerError;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.ErrorType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.ReserveLeaveError;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 休暇残数エラーから月別残数エラー一覧を作成する
 * @author shuichi_ishida
 */
public class CreatePerErrorsFromLeaveErrors {

	/**
	 * 年休エラーから月別残数エラー一覧を作成する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param annualLeaveErrors 年休エラー情報
	 * @return 社員の月別残数エラー一覧
	 */
	public static List<EmployeeMonthlyPerError> fromAnnualLeave(String employeeId, YearMonth yearMonth, ClosureId closureId,
				ClosureDate closureDate, List<AnnualLeaveError> annualLeaveErrors) {

		List<EmployeeMonthlyPerError> results = new ArrayList<>();
		if (annualLeaveErrors == null) return results;
		
		// 年休エラー処理
		for (val annualLeaveError : annualLeaveErrors){
			results.add(new EmployeeMonthlyPerError(
					ErrorType.YEARLY_HOLIDAY,
					yearMonth,
					employeeId,
					closureId,
					closureDate,
					null,
					annualLeaveError,
					null));
		}
		
		return results;
	}
	/**
	 * 積立年休エラーから月別残数エラー一覧を作成する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param reserveLeaveErrors 積立年休エラー情報
	 * @return 社員の月別残数エラー一覧
	 */
	public static List<EmployeeMonthlyPerError> fromReserveLeave(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, List<ReserveLeaveError> reserveLeaveErrors) {

		List<EmployeeMonthlyPerError> results = new ArrayList<>();
		if (reserveLeaveErrors == null) return results;

		// 積立年休エラー処理
		for (val reserveLeaveError : reserveLeaveErrors){
			results.add(new EmployeeMonthlyPerError(
					ErrorType.NUMBER_OF_MISSED_PIT,
					yearMonth,
					employeeId,
					closureId,
					closureDate,
					null,
					null,
					reserveLeaveError));
		}
		
		return results;
	}
	/**
	 * 振休エラーから月別残数エラー一覧を作成する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param pauseErrors 振休エラー情報
	 * @return 社員の月別残数エラー一覧
	 */
	public static List<EmployeeMonthlyPerError> fromPause(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, List<PauseError> pauseErrors) {

		List<EmployeeMonthlyPerError> results = new ArrayList<>();
		if (pauseErrors == null) return results;
		
		// 振休エラー処理
		if (pauseErrors.size() > 0){
			results.add(new EmployeeMonthlyPerError(
					ErrorType.REMAIN_LEFT,
					yearMonth,
					employeeId,
					closureId,
					closureDate,
					null,
					null,
					null));
		}
		
		return results;
	}
	/**
	 * 代休エラーから月別残数エラー一覧を作成する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param dayOffErrors 代休エラー情報
	 * @return 社員の月別残数エラー一覧
	 */
	public static List<EmployeeMonthlyPerError> fromDayOff(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, List<DayOffError> dayOffErrors) {

		List<EmployeeMonthlyPerError> results = new ArrayList<>();
		if (dayOffErrors == null) return results;
		
		// 代休エラー処理
		if (dayOffErrors.size() > 0){
			results.add(new EmployeeMonthlyPerError(
					ErrorType.REMAINING_ALTERNATION_NUMBER,
					yearMonth,
					employeeId,
					closureId,
					closureDate,
					null,
					null,
					null));
		}
		
		return results;
	}
	/**
	 * 特別休暇エラーから月別残数エラー一覧を作成する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param specialLeaveErrors 特別休暇エラー情報
	 * @return 社員の月別残数エラー一覧
	 */
	
	/** 特別休暇エラーから月別残数エラー一覧を作成する */
	public static List<EmployeeMonthlyPerError> fromSpecialLeave(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, int specialLeaveNo, List<SpecialLeaveError> specialLeaveErrors) {

		List<EmployeeMonthlyPerError> results = new ArrayList<>();
		if (specialLeaveErrors == null) return results;
		
		// 特別休暇エラー処理
		if (specialLeaveErrors.size() > 0){
			results.add(new EmployeeMonthlyPerError(
					specialLeaveNo,
					ErrorType.SPECIAL_REMAIN_HOLIDAY_NUMBER,
					yearMonth,
					employeeId,
					closureId,
					closureDate,
					null,
					null,
					null));
		}
		
		return results;
	}

}
