package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.StateAttr;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicateStateAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/*
 * 臨時系打刻順序不正をチェックする
 */
@Stateless
public class TemporaryStampOrderChecking {

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	@Inject
	private RangeOfDayTimeZoneService rangeOfDayTimeZoneService;

	public EmployeeDailyPerError temporaryStampOrderChecking(String employeeID, String companyID,
			GeneralDate processingDate, TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance) {

		EmployeeDailyPerError employeeDailyPerError = null;

		List<Integer> attendanceItemIDList = new ArrayList<>();

		if (temporaryTimeOfDailyPerformance != null
				&& !temporaryTimeOfDailyPerformance.getTimeLeavingWorks().isEmpty()) {

			List<TimeLeavingWork> newTimeLeavingWorks = temporaryTimeOfDailyPerformance.getTimeLeavingWorks();

			List<TimeLeavingWork> timeLeavingWorks = newTimeLeavingWorks.stream()
					.filter(item -> (item.getAttendanceStamp() != null && item.getAttendanceStamp().isPresent()
							&& item.getAttendanceStamp().get().getStamp().isPresent()
							&& item.getAttendanceStamp().get().getStamp().get().getTimeWithDay() != null)
							&& (item.getLeaveStamp() != null && item.getLeaveStamp().isPresent()
									&& item.getLeaveStamp().get().getStamp().isPresent()
									&& item.getLeaveStamp().get().getStamp().get().getTimeWithDay() != null))
					.collect(Collectors.toList());

			timeLeavingWorks.sort((e1, e2) -> (e1.getAttendanceStamp().get().getStamp().get().getTimeWithDay().v())
					.compareTo(e2.getAttendanceStamp().get().getStamp().get().getTimeWithDay().v()));

			int workNo = 1;
			for (TimeLeavingWork item : timeLeavingWorks) {
				TimeActualStamp attendanceStamp = null;
				TimeActualStamp leaveStamp = null;
				if (item.getAttendanceStamp().isPresent()) {
					attendanceStamp = item.getAttendanceStamp().get();
				}
				if (item.getLeaveStamp().isPresent()) {
					leaveStamp = item.getLeaveStamp().get();
				}
				item = new TimeLeavingWork(new WorkNo((workNo)), attendanceStamp, leaveStamp);
				workNo++;
			}

			for (TimeLeavingWork timeLeavingWork : timeLeavingWorks) {

				StateAttr duplicationStateAttr = StateAttr.NO_DUPLICATION;

				TimeWithDayAttr attendanceTimeWithDay = timeLeavingWork.getAttendanceStamp().get().getStamp().get()
						.getTimeWithDay();
				TimeWithDayAttr leaveTimeWithDay = timeLeavingWork.getLeaveStamp().get().getStamp().get()
						.getTimeWithDay();

				if (attendanceTimeWithDay.lessThanOrEqualTo(leaveTimeWithDay)) {
					// 他の出退勤との時間帯重複を確認する
					duplicationStateAttr = confirmDuplication(employeeID, processingDate, timeLeavingWork,
							temporaryTimeOfDailyPerformance);
					if (duplicationStateAttr == StateAttr.DUPLICATION) {
						if (timeLeavingWork.getWorkNo().equals(new WorkNo((1)))) {
							attendanceItemIDList.add(51);
							attendanceItemIDList.add(53);
						} else if (timeLeavingWork.getWorkNo().equals(new WorkNo((2)))) {
							attendanceItemIDList.add(59);
							attendanceItemIDList.add(61);
						} else if (timeLeavingWork.getWorkNo().equals(new WorkNo((3)))) {
							attendanceItemIDList.add(67);
							attendanceItemIDList.add(69);
						}
					}
				} else {
					if (timeLeavingWork.getWorkNo().equals(new WorkNo((1)))) {
						attendanceItemIDList.add(51);
						attendanceItemIDList.add(53);
					} else if (timeLeavingWork.getWorkNo().equals(new WorkNo((2)))) {
						attendanceItemIDList.add(59);
						attendanceItemIDList.add(61);
					} else if (timeLeavingWork.getWorkNo().equals(new WorkNo((3)))) {
						attendanceItemIDList.add(67);
						attendanceItemIDList.add(69);
					}
				}
			}
			if (!attendanceItemIDList.isEmpty()) {
				employeeDailyPerError = new EmployeeDailyPerError(companyID, employeeID, processingDate,
						new ErrorAlarmWorkRecordCode("S004"), attendanceItemIDList);
			}
		}

		return employeeDailyPerError;
	}

	private StateAttr confirmDuplication(String employeeID, GeneralDate processingDate, TimeLeavingWork timeLeavingWork,
			TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance) {

		StateAttr stateAttr = StateAttr.NO_DUPLICATION;

		List<DuplicationStatusOfTimeZone> statusOfTimeZones = new ArrayList<>();

		// ドメインモデル「日別実績の出退勤」を取得する
		Optional<TimeLeavingOfDailyPerformance> timeLeavingOfDailyPerformance = this.timeLeavingOfDailyPerformanceRepository
				.findByKey(employeeID, processingDate);

		if (timeLeavingOfDailyPerformance.isPresent()) {
			// 【パラメータ】出退勤が出退勤と重複しているか確認する
			TimeWithDayAttr stampStartTimeFirstTime = timeLeavingWork.getAttendanceStamp().get().getStamp().get()
					.getTimeWithDay();
			TimeWithDayAttr endStartTimeFirstTime = timeLeavingWork.getLeaveStamp().get().getStamp().get()
					.getTimeWithDay();
			TimeSpanForCalc timeSpanFirstTime = new TimeSpanForCalc(stampStartTimeFirstTime, endStartTimeFirstTime);

			List<TimeLeavingWork> timeLeavingWorks = timeLeavingOfDailyPerformance.get().getTimeLeavingWorks();
			for (TimeLeavingWork leavingWork : timeLeavingWorks) {

				if (leavingWork.getAttendanceStamp() != null && leavingWork.getAttendanceStamp().isPresent()
						&& leavingWork.getAttendanceStamp().get().getStamp() != null
						&& leavingWork.getAttendanceStamp().get().getStamp().isPresent()
						&& leavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay() != null
						&& leavingWork.getLeaveStamp() != null && leavingWork.getLeaveStamp().isPresent()
						&& leavingWork.getLeaveStamp().get().getStamp() != null
						&& leavingWork.getLeaveStamp().get().getStamp().isPresent()
						&& leavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay() != null) {
					TimeWithDayAttr stampStartTimeSecondTime = leavingWork.getAttendanceStamp().get().getStamp().get()
							.getTimeWithDay();
					TimeWithDayAttr endStartTimeSecondTime = leavingWork.getLeaveStamp().get().getStamp().get()
							.getTimeWithDay();
					TimeSpanForCalc timeSpanSecondTime = new TimeSpanForCalc(stampStartTimeSecondTime,
							endStartTimeSecondTime);

					DuplicateStateAtr duplicateStateAtr = this.rangeOfDayTimeZoneService
							.checkPeriodDuplication(timeSpanFirstTime, timeSpanSecondTime);
					DuplicationStatusOfTimeZone duplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService
							.checkStateAtr(duplicateStateAtr);
					statusOfTimeZones.add(duplicationStatusOfTimeZone);
				}
			}

			if (statusOfTimeZones.stream().anyMatch(item -> item != DuplicationStatusOfTimeZone.NON_OVERLAPPING)) {
				stateAttr = StateAttr.DUPLICATION;
			} else {
				List<DuplicationStatusOfTimeZone> newStatusOfTimeZones = new ArrayList<>();
				List<TimeLeavingWork> leavingWorks = temporaryTimeOfDailyPerformance.getTimeLeavingWorks();
				for (TimeLeavingWork leavingWork : leavingWorks) {
					if (!leavingWork.getWorkNo().equals(timeLeavingWork.getWorkNo())) {
						if (leavingWork.getAttendanceStamp() != null && leavingWork.getAttendanceStamp().isPresent()
								&& leavingWork.getAttendanceStamp().get().getStamp() != null
								&& leavingWork.getAttendanceStamp().get().getStamp().isPresent()
								&& leavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay() != null
								&& leavingWork.getLeaveStamp() != null && leavingWork.getLeaveStamp().isPresent()
								&& leavingWork.getLeaveStamp().get().getStamp() != null
								&& leavingWork.getLeaveStamp().get().getStamp().isPresent()
								&& leavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay() != null) {
							TimeWithDayAttr stampStartSecondTime = leavingWork.getAttendanceStamp().get().getStamp()
									.get().getTimeWithDay();
							TimeWithDayAttr endStartSecondTime = leavingWork.getLeaveStamp().get().getStamp().get()
									.getTimeWithDay();
							TimeSpanForCalc spanTimeSecondTime = new TimeSpanForCalc(stampStartSecondTime,
									endStartSecondTime);

							DuplicateStateAtr newDuplicateStateAtr = this.rangeOfDayTimeZoneService
									.checkPeriodDuplication(timeSpanFirstTime, spanTimeSecondTime);
							DuplicationStatusOfTimeZone newDuplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService
									.checkStateAtr(newDuplicateStateAtr);
							newStatusOfTimeZones.add(newDuplicationStatusOfTimeZone);
						}
					}
				}
				if (newStatusOfTimeZones.stream()
						.anyMatch(item -> item != DuplicationStatusOfTimeZone.NON_OVERLAPPING)) {
					stateAttr = StateAttr.DUPLICATION;
				}
			}
		}

		return stateAttr;
	}

}
