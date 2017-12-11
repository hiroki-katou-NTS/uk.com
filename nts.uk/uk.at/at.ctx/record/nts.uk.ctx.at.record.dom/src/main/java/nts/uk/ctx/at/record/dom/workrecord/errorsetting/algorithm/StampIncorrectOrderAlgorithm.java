package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.OutPutProcess;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;

/*
 * 打刻順序不正
 */
@Stateless
public class StampIncorrectOrderAlgorithm {

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;

	public OutPutProcess stampIncorrectOrder(String companyID, String employeeID, GeneralDate processingDate,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		OutPutProcess outPutProcess = OutPutProcess.NO_ERROR;
		List<Integer> attendanceItemIds = new ArrayList<>();

		if (timeLeavingOfDailyPerformance == null) {
			timeLeavingOfDailyPerformance = timeLeavingOfDailyPerformanceRepository
					.findByKey(employeeID, processingDate).get();
		}
		// ペアの逆転がないか確認する
		// TODO - hoi? tuan'
		OutPutProcess pairOutPut = OutPutProcess.NO_ERROR;

		if (pairOutPut == OutPutProcess.NO_ERROR) {
			if (timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getWorkNo()
					.greaterThan(timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getWorkNo())) {
				this.createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, processingDate,
						new ErrorAlarmWorkRecordCode(SystemFixedErrorAlarm.INCORRECT_STAMP.name()), attendanceItemIds);
				outPutProcess = OutPutProcess.HAS_ERROR;
			} else {
				// 重複の判断処理
				// TODO
				if (true) {
					this.createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, processingDate,
							new ErrorAlarmWorkRecordCode(SystemFixedErrorAlarm.INCORRECT_STAMP.name()), attendanceItemIds);
					outPutProcess = OutPutProcess.HAS_ERROR;
				} 
			}
			return outPutProcess;
		} else if (pairOutPut == OutPutProcess.HAS_ERROR) {
			this.createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, processingDate,
					new ErrorAlarmWorkRecordCode(SystemFixedErrorAlarm.INCORRECT_STAMP.name()), attendanceItemIds);
			outPutProcess = OutPutProcess.HAS_ERROR;
		}

		return outPutProcess;
	}
}
