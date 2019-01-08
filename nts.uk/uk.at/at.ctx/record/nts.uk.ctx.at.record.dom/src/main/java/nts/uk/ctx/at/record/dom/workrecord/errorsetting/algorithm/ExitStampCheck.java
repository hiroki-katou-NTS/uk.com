package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

/**
 * 打刻漏れ(入退門)
 * 
 * @author nampt
 *
 */
@Stateless
public class ExitStampCheck {

	@Inject
	private BasicScheduleService basicScheduleService;

	public EmployeeDailyPerError exitStampCheck(String companyId, String employeeId, GeneralDate processingDate,
			AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {
		
		EmployeeDailyPerError employeeDailyPerError = null;
		
		// 1日半日出勤・1日休日系の判定
		WorkStyle workStyle = basicScheduleService
				.checkWorkDay(workInfoOfDailyPerformance.getRecordInfo().getWorkTypeCode().v());

		if (workStyle != WorkStyle.ONE_DAY_REST) {
			if (attendanceLeavingGateOfDaily != null
					&& !attendanceLeavingGateOfDaily.getAttendanceLeavingGates().isEmpty()) {
				List<Integer> attendanceItemIDList = new ArrayList<>();

				List<AttendanceLeavingGate> attendanceLeavingGates = attendanceLeavingGateOfDaily
						.getAttendanceLeavingGates();
				for (AttendanceLeavingGate attendanceLeavingGate : attendanceLeavingGates) {
					
					// 退門のみ存在している(only has attendance time)
					if (attendanceLeavingGate.getAttendance().isPresent()
							&& attendanceLeavingGate.getAttendance().get().getTimeWithDay() != null
							&& (!attendanceLeavingGate.getLeaving().isPresent()
									|| (attendanceLeavingGate.getLeaving().isPresent()
											&& attendanceLeavingGate.getLeaving().get().getTimeWithDay() == null))) {
						if (attendanceLeavingGate.getWorkNo().v() == 1) {
							attendanceItemIDList.add(77);
						} else if (attendanceLeavingGate.getWorkNo().v() == 2) {
							attendanceItemIDList.add(81);
						}
					}
					// 入門のみ存在している(only has leaving time)
					else if ((!attendanceLeavingGate.getAttendance().isPresent()
							|| (attendanceLeavingGate.getAttendance().isPresent()
									&& attendanceLeavingGate.getAttendance().get().getTimeWithDay() == null))
							&& (attendanceLeavingGate.getLeaving().isPresent()
									&& attendanceLeavingGate.getLeaving().get().getTimeWithDay() != null)) {
						if (attendanceLeavingGate.getWorkNo().v() == 1) {
							attendanceItemIDList.add(75);
						} else if (attendanceLeavingGate.getWorkNo().v() == 2) {
							attendanceItemIDList.add(79);
						}
					}
					// 両方存在しない(both has not data)
					else if (!attendanceLeavingGate.getAttendance().isPresent() && !attendanceLeavingGate.getLeaving().isPresent()
							|| ((attendanceLeavingGate.getAttendance().isPresent() && attendanceLeavingGate.getAttendance().get().getTimeWithDay() == null)
									&& (attendanceLeavingGate.getLeaving().isPresent() && attendanceLeavingGate.getLeaving().get().getTimeWithDay() == null))) {
						if (attendanceLeavingGate.getWorkNo().v() == 1) {
							attendanceItemIDList.add(75);
							attendanceItemIDList.add(77);
						} else if (attendanceLeavingGate.getWorkNo().v() == 2) {
							attendanceItemIDList.add(79);
							attendanceItemIDList.add(81);
						}
					}
//					if (!attendanceItemIDList.isEmpty()) {
//						createEmployeeDailyPerError.createEmployeeDailyPerError(companyId, employeeId, processingDate,
//								new ErrorAlarmWorkRecordCode("S003"), attendanceItemIDList);
//					}
				}
				if (!attendanceItemIDList.isEmpty()){
					employeeDailyPerError = new EmployeeDailyPerError(companyId,
							employeeId, processingDate, new ErrorAlarmWorkRecordCode("S003"),
							attendanceItemIDList);
				}
			}
		}
		
		return employeeDailyPerError;
	}

}
