package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/*
 * 臨時系打刻漏れをチェックする
 */
@Stateless
public class MissingOfTemporaryStampChecking {

	public Optional<EmployeeDailyPerError> missingOfTemporaryStampChecking(String companyID, String employeeID,
			GeneralDate processingDate, TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance) {

		EmployeeDailyPerError employeeDailyPerError = null;

		// Optional<TemporaryTimeOfDailyPerformance>
		// temporaryTimeOfDailyPerformance =
		// this.temporaryTimeOfDailyPerformanceRepository.findByKey(employeeID,
		// processingDate);

		if (temporaryTimeOfDailyPerformance != null
				&& !temporaryTimeOfDailyPerformance.getAttendance().getTimeLeavingWorks().isEmpty()) {
			List<TimeLeavingWork> timeLeavingWorks = temporaryTimeOfDailyPerformance.getAttendance().getTimeLeavingWorks();

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
							TimeWithDayAttr attendanceTimeWithDay =attendanceWorkStamp.get().getTimeDay().getTimeWithDay().isPresent()? attendanceWorkStamp.get().getTimeDay().getTimeWithDay().get():null;
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
							TimeWithDayAttr leaveTimeWithDay =leaveWorkStamp.get().getTimeDay().getTimeWithDay().isPresent()? leaveWorkStamp.get().getTimeDay().getTimeWithDay().get():null;
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
		return Optional.ofNullable(employeeDailyPerError);
	}
}
