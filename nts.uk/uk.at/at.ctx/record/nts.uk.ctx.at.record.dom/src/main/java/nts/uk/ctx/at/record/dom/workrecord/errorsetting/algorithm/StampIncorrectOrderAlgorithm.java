package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.OutPutProcess;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicateStateAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/*
 * 出退勤打刻順序不正
 */
@Stateless
public class StampIncorrectOrderAlgorithm {

	@Inject
	private RangeOfDayTimeZoneService timezoneService;

	public Optional<EmployeeDailyPerError> stampIncorrectOrder(String cid, String sid, GeneralDate ymd,
			TimeLeavingOfDailyPerformance timeLeaving) {

		List<Integer> itemIds = new ArrayList<>();

		if (timeLeaving != null && !timeLeaving.getAttendance().getTimeLeavingWorks().isEmpty()) {
			
			List<TimeLeavingWork> timeLeaves = timeLeaving.getAttendance().getTimeLeavingWorks();
			
			// ペアの逆転がないか確認する
			List<OutPutProcess> pairOutPutList = checkPairReversed(timeLeaves);
			if (pairOutPutList.stream().anyMatch(item -> item == OutPutProcess.HAS_ERROR)) {
				if (pairOutPutList.get(0).value == 1) {
					itemIds.add(31);
					itemIds.add(34);
				} else if (pairOutPutList.size() == 2 && pairOutPutList.get(1).value == 1) {
					itemIds.add(41);
					itemIds.add(44);
				}
			} else {
				if (timeLeaves.size() >= 2) {
					
					val attendance1 = getAttendanceTime(timeLeaves.get(0));
					val attendance2 = getAttendanceTime(timeLeaves.get(1));
					val leave1 = getLeaveTime(timeLeaves.get(0));
					val leave2 = getLeaveTime(timeLeaves.get(1));
					
					if (attendance1.isPresent() && attendance2.isPresent()
							&& leave1.isPresent() && leave2.isPresent()) {
						
						timeLeaves.sort((t1, t2) -> {
							TimeWithDayAttr at1 = getAttendanceTime(t1).get();
							TimeWithDayAttr at2 = getAttendanceTime(t2).get();
							return at1.compareTo(at2);
						});
						
						if (timeLeaves.get(0).getWorkNo().greaterThan(timeLeaves.get(1).getWorkNo())) {
							itemIds.add(31);
							itemIds.add(34);
							itemIds.add(41);
							itemIds.add(44);
						} else {
							// 重複の判断処理
							TimeWithDayAttr startTime1 = attendance1.get();
							TimeWithDayAttr endTime1 = leave1.get();
							TimeSpanForCalc timeSpan1 = new TimeSpanForCalc(startTime1, endTime1);

							TimeWithDayAttr startTime2 = attendance2.get();
							TimeWithDayAttr endTime2 = leave2.get();
							TimeSpanForCalc timeSpan2 = new TimeSpanForCalc(startTime2, endTime2);

							DuplicateStateAtr state = this.timezoneService.checkPeriodDuplication(timeSpan1, timeSpan2);
							DuplicationStatusOfTimeZone status = this.timezoneService.checkStateAtr(state);

							if (status != DuplicationStatusOfTimeZone.NON_OVERLAPPING) {
								itemIds.add(31);
								itemIds.add(34);
								itemIds.add(41);
								itemIds.add(44);
							}
						}
					}
				}
			}
		}
//		}
		if (!itemIds.isEmpty()) {
			return Optional.of(new EmployeeDailyPerError(cid, sid, ymd, new ErrorAlarmWorkRecordCode("S004"), itemIds));
		}

		return Optional.empty();
	}

	private List<OutPutProcess> checkPairReversed(List<TimeLeavingWork> timeLeavingWorks) {
		List<OutPutProcess> outPutProcessList = new ArrayList<>();
		OutPutProcess pairOutPut = OutPutProcess.HAS_ERROR;

		for (TimeLeavingWork timeLeavingWorking : timeLeavingWorks) {

			val attendance1 = getAttendanceTime(timeLeavingWorking);
			val leave1 = getLeaveTime(timeLeavingWorking);
			
			if (attendance1.isPresent() && leave1.isPresent()) {
				
				if (leave1.get().greaterThanOrEqualTo(attendance1.get())) {
					pairOutPut = OutPutProcess.NO_ERROR;
				}
				
				outPutProcessList.add(pairOutPut);
			}
		}

		return outPutProcessList;
	}

	private Optional<TimeWithDayAttr> getLeaveTime(TimeLeavingWork timeLeavingWorking) {
		return timeLeavingWorking.getLeaveStamp().flatMap(c -> c.getStamp())
				.flatMap(c -> c.getTimeDay().getTimeWithDay());
	}

	private Optional<TimeWithDayAttr> getAttendanceTime(TimeLeavingWork timeLeavingWorking) {
		return timeLeavingWorking.getAttendanceStamp().flatMap(c -> c.getStamp())
				.flatMap(c -> c.getTimeDay().getTimeWithDay());
	}
}
