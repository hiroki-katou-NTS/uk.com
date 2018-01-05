package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;

/**
 * 
 * @author nampt 2重打刻
 * 
 */
@Stateless
public class DoubleStampAlgorithm {

	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;

	public void doubleStamp(String companyID, String employeeID, GeneralDate processingDate,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {

		if (timeLeavingOfDailyPerformance != null && !timeLeavingOfDailyPerformance.getTimeLeavingWorks().isEmpty()) {

			List<TimeLeavingWork> timeLeavingWorks = timeLeavingOfDailyPerformance.getTimeLeavingWorks();

			for (TimeLeavingWork timeLeavingWork : timeLeavingWorks) {
				// 出勤の二重打刻チェック処理
				TimeActualStamp attendanceTimeActual = timeLeavingWork.getAttendanceStamp();
				this.doubleStampCheckProcessing(companyID, employeeID, processingDate, attendanceTimeActual);

				// 退勤の二重打刻チェック処理
				TimeActualStamp leavingTimeActual = timeLeavingWork.getLeaveStamp();
				this.doubleStampCheckProcessing(companyID, employeeID, processingDate, leavingTimeActual);
			}
		}
	}

	private void doubleStampCheckProcessing(String companyID, String employeeID, GeneralDate processingDate,
			TimeActualStamp timeActualStamp) {

		List<Integer> attendanceItemIDs = new ArrayList<>();

		if (timeActualStamp.getNumberOfReflectionStamp() >= 2) {
			createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, processingDate,
					new ErrorAlarmWorkRecordCode("S006"), attendanceItemIDs);
		}
	}

}
