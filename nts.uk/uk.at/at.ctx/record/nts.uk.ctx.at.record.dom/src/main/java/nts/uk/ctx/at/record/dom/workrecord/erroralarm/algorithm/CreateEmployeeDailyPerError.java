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

}
