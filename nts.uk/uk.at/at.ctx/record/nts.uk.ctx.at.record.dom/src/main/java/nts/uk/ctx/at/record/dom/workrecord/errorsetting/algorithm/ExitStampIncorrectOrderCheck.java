package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicateStateAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 打刻順序不正(入退門)
 * 
 * @author nampt
 *
 */
@Stateless
public class ExitStampIncorrectOrderCheck {

	@Inject
	private RangeOfDayTimeZoneService rangeOfDayTimeZoneService;

	public List<EmployeeDailyPerError> exitStampIncorrectOrderCheck(String companyId, String employeeId,
			GeneralDate processingDate, AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {

		List<EmployeeDailyPerError> employeeDailyPerErrorList = new ArrayList<>();

		if (attendanceLeavingGateOfDaily != null
				&& !attendanceLeavingGateOfDaily.getAttendanceLeavingGates().isEmpty()) {

			List<AttendanceLeavingGate> attendanceLeavingGates = attendanceLeavingGateOfDaily
					.getAttendanceLeavingGates();

			// ペアの逆転がないか確認する(入退門)
			List<Integer> attendanceItemIds = this.checkPairReversed(attendanceLeavingGateOfDaily);
			if (!attendanceItemIds.isEmpty()) {
				EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(companyId, employeeId,
						processingDate, new ErrorAlarmWorkRecordCode("S004"), attendanceItemIds);
				employeeDailyPerErrorList.add(employeeDailyPerError);
			} else {
				if (attendanceLeavingGates.size() == 2) {
					if (attendanceLeavingGates.get(0).getAttendance().isPresent()
							&& attendanceLeavingGates.get(0).getAttendance().get().getTimeWithDay() != null
							&& attendanceLeavingGates.get(1).getAttendance().isPresent()
							&& attendanceLeavingGates.get(1).getAttendance().get().getTimeWithDay() != null) {

						attendanceLeavingGates.sort((e1, e2) -> e1.getAttendance().get().getTimeWithDay().v()
								.compareTo(e2.getAttendance().get().getTimeWithDay().v()));

						if (attendanceLeavingGates.get(0).getWorkNo()
								.greaterThan(attendanceLeavingGates.get(1).getWorkNo())) {
							attendanceItemIds = new ArrayList<>();
							attendanceItemIds.add(75);
							attendanceItemIds.add(77);
							attendanceItemIds.add(79);
							attendanceItemIds.add(81);
							// this.createEmployeeDailyPerError.createEmployeeDailyPerError(companyId,
							// employeeId,
							// processingDate, new
							// ErrorAlarmWorkRecordCode("S004"),
							// attendanceItemIds);
							EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(companyId,
									employeeId, processingDate, new ErrorAlarmWorkRecordCode("S004"),
									attendanceItemIds);
							employeeDailyPerErrorList.add(employeeDailyPerError);
						} else {
							// 重複の判断処理
							TimeWithDayAttr stampStartTimeFirstTime = attendanceLeavingGates.get(0).getAttendance()
									.get().getTimeWithDay();
							TimeWithDayAttr endStartTimeFirstTime = attendanceLeavingGates.get(0).getLeaving().get()
									.getTimeWithDay();
							TimeSpanForCalc timeSpanFirstTime = new TimeSpanForCalc(stampStartTimeFirstTime,
									endStartTimeFirstTime);

							TimeWithDayAttr stampStartTimeSecondTime = attendanceLeavingGates.get(1).getAttendance()
									.get().getTimeWithDay();
							TimeWithDayAttr endStartTimeSecondTime = attendanceLeavingGates.get(1).getLeaving().get()
									.getTimeWithDay();
							TimeSpanForCalc timeSpanSecondTime = new TimeSpanForCalc(stampStartTimeSecondTime,
									endStartTimeSecondTime);

							DuplicateStateAtr duplicateStateAtr = this.rangeOfDayTimeZoneService
									.checkPeriodDuplication(timeSpanFirstTime, timeSpanSecondTime);
							DuplicationStatusOfTimeZone duplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService
									.checkStateAtr(duplicateStateAtr);

							if (duplicationStatusOfTimeZone != DuplicationStatusOfTimeZone.NON_OVERLAPPING) {
								attendanceItemIds = new ArrayList<>();
								attendanceItemIds.add(75);
								attendanceItemIds.add(77);
								attendanceItemIds.add(79);
								attendanceItemIds.add(81);
								EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(companyId,
										employeeId, processingDate, new ErrorAlarmWorkRecordCode("S004"),
										attendanceItemIds);
								employeeDailyPerErrorList.add(employeeDailyPerError);
							} else {
								// 入退門と出退勤の順序不正判断処理
								attendanceItemIds = this.checkOder(attendanceLeavingGateOfDaily,
										timeLeavingOfDailyPerformance);
								if (!attendanceItemIds.isEmpty()) {
									EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(companyId,
											employeeId, processingDate, new ErrorAlarmWorkRecordCode("S004"),
											attendanceItemIds);
									employeeDailyPerErrorList.add(employeeDailyPerError);
								}
							}
						}
					}
				}
			}
		}
		return employeeDailyPerErrorList;
	}

	private List<Integer> checkPairReversed(AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily) {
		List<Integer> attendanceItemIds = new ArrayList<>();
		List<AttendanceLeavingGate> attendanceLeavingGates = attendanceLeavingGateOfDaily.getAttendanceLeavingGates();

		for (AttendanceLeavingGate attendanceLeavingGate : attendanceLeavingGates) {
			if (attendanceLeavingGate.getAttendance().isPresent()
					&& attendanceLeavingGate.getAttendance().get().getTimeWithDay() != null
					&& attendanceLeavingGate.getLeaving().isPresent()
					&& attendanceLeavingGate.getLeaving().get().getTimeWithDay() != null) {
				if (attendanceLeavingGate.getAttendance().get().getTimeWithDay()
						.greaterThan(attendanceLeavingGate.getLeaving().get().getTimeWithDay())) {
					if (attendanceLeavingGate.getWorkNo().v() == 1) {
						attendanceItemIds.add(75);
						attendanceItemIds.add(77);
					} else if (attendanceLeavingGate.getWorkNo().v() == 2) {
						attendanceItemIds.add(79);
						attendanceItemIds.add(81);
					}
				}
			}
		}

		return attendanceItemIds;
	}

	private List<Integer> checkOder(AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		List<Integer> attendanceItemIds = new ArrayList<>();

		if (timeLeavingOfDailyPerformance != null && !timeLeavingOfDailyPerformance.getTimeLeavingWorks().isEmpty()) {
			List<AttendanceLeavingGate> attendanceLeavingGates = attendanceLeavingGateOfDaily
					.getAttendanceLeavingGates();
			// 1回目の入門と出勤．打刻が存在するか確認する(check first stamp attendance has data or
			// not)
			TimeWithDayAttr firstAttendanceTime = attendanceLeavingGates.get(0).getAttendance().get().getTimeWithDay();
			if (firstAttendanceTime != null) {
				if (timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().isPresent()
						&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().get()
								.getStamp().isPresent()
						&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().get()
								.getStamp().get().getTimeWithDay() != null) {
					TimeWithDayAttr timeLeavingAttendanceTime = timeLeavingOfDailyPerformance.getTimeLeavingWorks()
							.get(0).getAttendanceStamp().get().getStamp().get().getTimeWithDay();
					if (firstAttendanceTime.greaterThan(timeLeavingAttendanceTime)) {
						attendanceItemIds.add(75);
						attendanceItemIds.add(31);
					}
				}
			}

			// 1回目の退門と退勤．打刻が存在するか確認する(check first stamp leaving has data or not)
			TimeWithDayAttr firstLeavingTime = attendanceLeavingGates.get(0).getLeaving().get().getTimeWithDay();
			if (firstLeavingTime != null) {
				if (timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().isPresent()
						&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().get().getStamp()
								.isPresent()
						&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().get().getStamp()
								.get().getTimeWithDay() != null) {
					TimeWithDayAttr timeLeavingLeavingTime = timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0)
							.getLeaveStamp().get().getStamp().get().getTimeWithDay();
					if (firstLeavingTime.lessThan(timeLeavingLeavingTime)) {
						attendanceItemIds.add(77);
						attendanceItemIds.add(34);
					}
				}
			}

			// 2回目の入門と出勤．打刻が存在するか確認する(check second stamp attendance has data or
			// not)
			TimeWithDayAttr secondAttendanceTime = attendanceLeavingGates.get(1).getAttendance().get().getTimeWithDay();
			if (secondAttendanceTime != null) {
				if (timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().isPresent()
						&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().get()
								.getStamp().isPresent()
						&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().get()
								.getStamp().get().getTimeWithDay() != null) {
					TimeWithDayAttr timeLeavingAttendanceTime = timeLeavingOfDailyPerformance.getTimeLeavingWorks()
							.get(1).getAttendanceStamp().get().getStamp().get().getTimeWithDay();
					if (secondAttendanceTime.greaterThan(timeLeavingAttendanceTime)) {
						attendanceItemIds.add(79);
						attendanceItemIds.add(41);
					}
				}
			}

			// 2回目の退門と退勤．打刻が存在するか確認する(check second stamp leaving has data or
			// not)
			TimeWithDayAttr secondLeavingTime = attendanceLeavingGates.get(1).getLeaving().get().getTimeWithDay();
			if (secondLeavingTime != null) {
				if (timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().isPresent()
						&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().get().getStamp()
								.isPresent()
						&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().get().getStamp()
								.get().getTimeWithDay() != null) {
					TimeWithDayAttr timeLeavingLeavingTime = timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1)
							.getLeaveStamp().get().getStamp().get().getTimeWithDay();
					if (secondLeavingTime.lessThan(timeLeavingLeavingTime)) {
						attendanceItemIds.add(81);
						attendanceItemIds.add(44);
					}
				}
			}

		}

		return attendanceItemIds;
	}
}
