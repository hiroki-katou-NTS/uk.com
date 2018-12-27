package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.pererror;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerError;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.ErrorType;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveError;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveError;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.PauseError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 実装：休暇残数エラーから月別残数エラー一覧を作成する
 * @author shuichi_ishida
 */
@Stateless
public class CreatePerErrorsFromLeaveErrorsImpl implements CreatePerErrorsFromLeaveErrors {

	/** 年休エラーから月別残数エラー一覧を作成する */
	@Override
	public List<EmployeeMonthlyPerError> fromAnnualLeave(String employeeId, YearMonth yearMonth, ClosureId closureId,
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
	
	/** 積立年休エラーから月別残数エラー一覧を作成する */
	@Override
	public List<EmployeeMonthlyPerError> fromReserveLeave(String employeeId, YearMonth yearMonth, ClosureId closureId,
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
	
	/** 振休エラーから月別残数エラー一覧を作成する */
	@Override
	public List<EmployeeMonthlyPerError> fromPause(String employeeId, YearMonth yearMonth, ClosureId closureId,
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
	
	/** 代休エラーから月別残数エラー一覧を作成する */
	@Override
	public List<EmployeeMonthlyPerError> fromDayOff(String employeeId, YearMonth yearMonth, ClosureId closureId,
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
	
	/** 特別休暇エラーから月別残数エラー一覧を作成する */
	@Override
	public List<EmployeeMonthlyPerError> fromSpecialLeave(String employeeId, YearMonth yearMonth, ClosureId closureId,
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
