package nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.OutPutProcess;

/**
 * 
 * @author nampt 
 * 社員の日別実績のエラーを作成する
 */
@Stateless
public class CreateEmployeeDailyPerError {

	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepository;

	public OutPutProcess createEmployeeDailyPerError(String companyID, String employeeID, GeneralDate processingDate,
			ErrorAlarmWorkRecordCode errorCode, List<Integer> attendanceItemIDList) {

		OutPutProcess outPutProcess = OutPutProcess.HAS_ERROR;
		// ドメインモデル「社員の日別実績エラー一覧」の事前条件をチェックする
		Boolean existErrorCode = this.employeeDailyPerErrorRepository.checkExistErrorCode(employeeID, processingDate, errorCode.v());
		if (existErrorCode == true) {
			return outPutProcess;
		} else {
			EmployeeDailyPerError employeeDailyPerformanceError = new EmployeeDailyPerError(companyID, employeeID,
					processingDate, errorCode, attendanceItemIDList);
			this.employeeDailyPerErrorRepository.insert(employeeDailyPerformanceError);
			return outPutProcess = OutPutProcess.NO_ERROR;
		}
	}
}
