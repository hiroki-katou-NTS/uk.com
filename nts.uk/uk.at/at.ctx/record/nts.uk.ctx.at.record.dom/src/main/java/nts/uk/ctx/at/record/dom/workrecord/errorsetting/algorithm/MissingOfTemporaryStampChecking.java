package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.shr.com.time.TimeWithDayAttr;

/*
 * 臨時系打刻漏れをチェックする
 */
@Stateless
public class MissingOfTemporaryStampChecking {

	public EmployeeDailyPerError missingOfTemporaryStampChecking(String companyID, String employeeID,
			GeneralDate processingDate, TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance) {

		EmployeeDailyPerError employeeDailyPerError = null;

		// Optional<TemporaryTimeOfDailyPerformance>
		// temporaryTimeOfDailyPerformance =
		// this.temporaryTimeOfDailyPerformanceRepository.findByKey(employeeID,
		// processingDate);

		if (temporaryTimeOfDailyPerformance != null
				&& !temporaryTimeOfDailyPerformance.getTimeLeavingWorks().isEmpty()) {
			List<TimeLeavingWork> timeLeavingWorks = temporaryTimeOfDailyPerformance.getTimeLeavingWorks();

			List<Integer> attendanceItemIds = new ArrayList<>();

			for (TimeLeavingWork timeLeavingWork : timeLeavingWorks) {
				if (timeLeavingWork.getAttendanceStamp().isPresent() || timeLeavingWork.getLeaveStamp().isPresent()) {

					if (timeLeavingWork.getAttendanceStamp() != null
							&& timeLeavingWork.getAttendanceStamp().isPresent()) {
						Optional<WorkStamp> attendanceWorkStamp = timeLeavingWork.getAttendanceStamp().get().getStamp();
						if (!attendanceWorkStamp.isPresent()) {
							if (timeLeavingWork.getWorkNo().v().intValue() == 1) {
								attendanceItemIds.add(51);
							} else if (timeLeavingWork.getWorkNo().v().intValue() == 2) {
								attendanceItemIds.add(59);
							} else if (timeLeavingWork.getWorkNo().v().intValue() == 3) {
								attendanceItemIds.add(67);
							}
						} else {
							TimeWithDayAttr attendanceTimeWithDay = attendanceWorkStamp.get().getTimeWithDay();
							if (attendanceTimeWithDay == null) {
								if (timeLeavingWork.getWorkNo().v().intValue() == 1) {
									attendanceItemIds.add(51);
								} else if (timeLeavingWork.getWorkNo().v().intValue() == 2) {
									attendanceItemIds.add(59);
								} else if (timeLeavingWork.getWorkNo().v().intValue() == 3) {
									attendanceItemIds.add(67);
								}
							}
						}
					}
					if (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()) {

						Optional<WorkStamp> leaveWorkStamp = timeLeavingWork.getLeaveStamp().get().getStamp();
						if (!leaveWorkStamp.isPresent()) {
							if (timeLeavingWork.getWorkNo().v().intValue() == 1) {
								attendanceItemIds.add(53);
							} else if (timeLeavingWork.getWorkNo().v().intValue() == 2) {
								attendanceItemIds.add(61);
							} else if (timeLeavingWork.getWorkNo().v().intValue() == 3) {
								attendanceItemIds.add(69);
							}
						} else {
							TimeWithDayAttr leaveTimeWithDay = leaveWorkStamp.get().getTimeWithDay();
							if (leaveTimeWithDay == null) {
								if (timeLeavingWork.getWorkNo().v().intValue() == 1) {
									attendanceItemIds.add(53);
								} else if (timeLeavingWork.getWorkNo().v().intValue() == 2) {
									attendanceItemIds.add(61);
								} else if (timeLeavingWork.getWorkNo().v().intValue() == 3) {
									attendanceItemIds.add(69);
								}
							}
						}
					}
				}
			}
			if (!attendanceItemIds.isEmpty()) {
				employeeDailyPerError = new EmployeeDailyPerError(companyID, employeeID, processingDate,
						new ErrorAlarmWorkRecordCode("S001"), attendanceItemIds);
			}
		}
		return employeeDailyPerError;
	}
}
