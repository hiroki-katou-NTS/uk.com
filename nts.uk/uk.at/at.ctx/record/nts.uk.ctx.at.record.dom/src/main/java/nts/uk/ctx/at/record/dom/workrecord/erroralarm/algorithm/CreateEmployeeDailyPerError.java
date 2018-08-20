package nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author nampt Minh Hùng 社員の日別実績のエラーを作成する
 */
@Stateless
public class CreateEmployeeDailyPerError {

	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepository;

	public void createEmployeeDailyPerError(String companyID, String employeeID, GeneralDate processingDate,
			ErrorAlarmWorkRecordCode errorCode, List<Integer> attendanceItemIDList) {
		// ドメインモデル「社員の日別実績エラー一覧」の事前条件をチェックする
		Boolean existErrorCode = this.employeeDailyPerErrorRepository.checkExistErrorCode(employeeID, processingDate,
				errorCode.v());
		if (existErrorCode == false) {
			EmployeeDailyPerError employeeDailyPerformanceError = new EmployeeDailyPerError(companyID, employeeID,
					processingDate, errorCode, attendanceItemIDList, 0);
			this.employeeDailyPerErrorRepository.insert(employeeDailyPerformanceError);

		}
	}
	
	public void createEmployeeError(EmployeeDailyPerError dailyPerError){
		Boolean existErrorCode = this.employeeDailyPerErrorRepository.checkExistErrorCode(dailyPerError.getEmployeeID(), dailyPerError.getDate(),
				dailyPerError.getErrorAlarmWorkRecordCode().v());
		if (existErrorCode == false) {
			this.employeeDailyPerErrorRepository.insert(dailyPerError);
		}
	}

	public void createEmployeeDailyPerError(EmployeeDailyPerError error) {

		EmployeeDailyPerError obj;
		if (error.getErrorAlarmMessage().isPresent()) {

			obj = new EmployeeDailyPerError(error.getCompanyID(), error.getEmployeeID(), error.getDate(),
					error.getErrorAlarmWorkRecordCode(), error.getAttendanceItemList(), error.getErrorCancelAble(),
					error.getErrorAlarmMessage().get().v());
		} else {
			obj = new EmployeeDailyPerError(error.getCompanyID(), error.getEmployeeID(), error.getDate(),
					error.getErrorAlarmWorkRecordCode(), error.getAttendanceItemList(), error.getErrorCancelAble());
		}
		this.employeeDailyPerErrorRepository.insert(obj);
	}

	/**
	 * 対象期間に日別実績のエラーが発生しているかチェックする
	 * 
	 * @param companyID
	 * @param employeeID:
	 *            社員ID
	 * @param durationDate:
	 *            対象日一覧：List＜年月日＞
	 * @return 対象日一覧の確認が済んでいる：boolean
	 */
	public boolean employeeDailyRecordErrorCheck(String companyID, String employeeID, DatePeriod durationDate,boolean checkExistRecordErrorListDate) {
//		return employeeDailyPerErrorRepository.checkExistRecordErrorListDate(companyID, employeeID,
//				getDaysBetween(durationDate.start(), durationDate.end()));
		return checkExistRecordErrorListDate;
	}

	private List<GeneralDate> getDaysBetween(GeneralDate startDate, GeneralDate endDate) {
		List<GeneralDate> daysBetween = new ArrayList<>();

		while (startDate.beforeOrEquals(endDate)) {
			daysBetween.add(startDate);
			GeneralDate temp = startDate.addDays(1);
			startDate = temp;
		}

		return daysBetween;
	}
}
