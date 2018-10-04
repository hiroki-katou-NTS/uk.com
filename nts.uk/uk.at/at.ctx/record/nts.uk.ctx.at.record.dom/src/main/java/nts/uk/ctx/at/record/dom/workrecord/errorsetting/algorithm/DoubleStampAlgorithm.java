package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
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

	public EmployeeDailyPerError doubleStamp(String companyID, String employeeID, GeneralDate processingDate,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {

		EmployeeDailyPerError dailyPerError = null;

		List<Integer> attendanceItemIDs = new ArrayList<>();

		if (timeLeavingOfDailyPerformance != null && !timeLeavingOfDailyPerformance.getTimeLeavingWorks().isEmpty()) {

			List<TimeLeavingWork> timeLeavingWorks = timeLeavingOfDailyPerformance.getTimeLeavingWorks();

			for (TimeLeavingWork timeLeavingWork : timeLeavingWorks) {
				// 出勤の二重打刻チェック処理
				Optional<TimeActualStamp> attendanceTimeActual = timeLeavingWork.getAttendanceStamp();
				attendanceItemIDs.addAll(this.doubleStampCheckProcessing(companyID, employeeID, processingDate,
						attendanceTimeActual, 1, timeLeavingWork.getWorkNo().v()));

				// 退勤の二重打刻チェック処理
				Optional<TimeActualStamp> leavingTimeActual = timeLeavingWork.getLeaveStamp();
				attendanceItemIDs.addAll(this.doubleStampCheckProcessing(companyID, employeeID, processingDate,
						leavingTimeActual, 2, timeLeavingWork.getWorkNo().v()));
			}
		}
		if (!attendanceItemIDs.isEmpty()) {
			dailyPerError = new EmployeeDailyPerError(companyID, employeeID, processingDate,
					new ErrorAlarmWorkRecordCode("S006"), attendanceItemIDs);
		}
		return dailyPerError;
	}

	private List<Integer> doubleStampCheckProcessing(String companyID, String employeeID, GeneralDate processingDate,
			Optional<TimeActualStamp> timeActualStamp, int type, int workNo) {

		List<Integer> attendanceItemIDs = new ArrayList<>();
		if (type == 1) {
			if (timeActualStamp != null && timeActualStamp.isPresent() && workNo == 1) {
				attendanceItemIDs.add(31);
			} else if (timeActualStamp != null && timeActualStamp.isPresent() && workNo == 2) {
				attendanceItemIDs.add(41);
			}
		} else {
			if (timeActualStamp != null && timeActualStamp.isPresent() && workNo == 1) {
				attendanceItemIDs.add(34);
			} else if (timeActualStamp != null && timeActualStamp.isPresent() && workNo == 2) {
				attendanceItemIDs.add(44);
			}
		}

		if (timeActualStamp != null && timeActualStamp.isPresent()
				&& timeActualStamp.get().getNumberOfReflectionStamp() != null
				&& timeActualStamp.get().getNumberOfReflectionStamp() >= 2) {
			return attendanceItemIDs;
		}
		// if (!attendanceItemIDs.isEmpty()) {
		// createEmployeeDailyPerError.createEmployeeDailyPerError(companyID,
		// employeeID, processingDate,
		// new ErrorAlarmWorkRecordCode("S006"), attendanceItemIDs);
		// }
		// }

		return new ArrayList<>();
	}

}
