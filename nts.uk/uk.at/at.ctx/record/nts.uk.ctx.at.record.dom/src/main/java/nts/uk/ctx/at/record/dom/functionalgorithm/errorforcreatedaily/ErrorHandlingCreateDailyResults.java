package nts.uk.ctx.at.record.dom.functionalgorithm.errorforcreatedaily;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;

/**
 * 日別実績の作成エラー処理
 * 
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ErrorHandlingCreateDailyResults {

	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;

	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;

	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepository;

	public void executeCreateError(String companyId, String employeeId, GeneralDate disposalDay,
			String empCalAndSumExeLogId, ExecutionContent executionContent, ErrMessageResource resourceID,
			ErrMessageContent messageError) {
		// エラー処理
		if(empCalAndSumExeLogId != null) {
			ErrMessageInfo errMessageInfo = new ErrMessageInfo(employeeId, empCalAndSumExeLogId, resourceID,
					executionContent, disposalDay, messageError);
			this.errMessageInfoRepository.add(errMessageInfo);
		}

		// 社員の日別実績のエラーを作成する	
		// ドメインモデル「社員の日別実績エラー一覧」の事前条件をチェックする
		Boolean existErrorCode = this.employeeDailyPerErrorRepository.checkExistErrorCode(employeeId, disposalDay,
				"S025");	
		if (!existErrorCode) {
			EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(companyId, employeeId, disposalDay,
					new ErrorAlarmWorkRecordCode("S025"), new ArrayList<>());
			this.createEmployeeDailyPerError.createEmployeeError(employeeDailyPerError);
		}
	}

}
