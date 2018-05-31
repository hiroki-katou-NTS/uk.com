package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
				Optional<TimeActualStamp> attendanceTimeActual = timeLeavingWork.getAttendanceStamp();
				this.doubleStampCheckProcessing(companyID, employeeID, processingDate, attendanceTimeActual, 1);

				// 退勤の二重打刻チェック処理
				Optional<TimeActualStamp> leavingTimeActual = timeLeavingWork.getLeaveStamp();
				this.doubleStampCheckProcessing(companyID, employeeID, processingDate, leavingTimeActual, 2);
			}
		}
	}

	private void doubleStampCheckProcessing(String companyID, String employeeID, GeneralDate processingDate,
			Optional<TimeActualStamp> timeActualStamp, int type) {

		List<Integer> attendanceItemIDs = new ArrayList<>();
		if (type == 1) {
			if (timeActualStamp != null && timeActualStamp.isPresent() && timeActualStamp.get().getNumberOfReflectionStamp() == 1) {
				attendanceItemIDs.add(31);
			} else if (timeActualStamp != null && timeActualStamp.isPresent() && timeActualStamp.get().getNumberOfReflectionStamp() == 2) {
				attendanceItemIDs.add(41);
			}
		} else {
			if (timeActualStamp != null && timeActualStamp.isPresent() && timeActualStamp.get().getNumberOfReflectionStamp() == 1) {
				attendanceItemIDs.add(34);
			} else if (timeActualStamp != null && timeActualStamp.isPresent() && timeActualStamp.get().getNumberOfReflectionStamp() == 2) {
				attendanceItemIDs.add(44);
			}
		}

		if (timeActualStamp != null && timeActualStamp.isPresent() && timeActualStamp.get().getNumberOfReflectionStamp() >= 2) {
			if (!attendanceItemIDs.isEmpty()) {
				createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, processingDate,
						new ErrorAlarmWorkRecordCode("S006"), attendanceItemIDs);
			}			
		}
	}

}
