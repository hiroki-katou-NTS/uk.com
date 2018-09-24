package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.OutPutProcess;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicateStateAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/*
 * 出退勤打刻順序不正
 */
@Stateless
public class StampIncorrectOrderAlgorithm {

	@Inject
	private RangeOfDayTimeZoneService rangeOfDayTimeZoneService;

	public EmployeeDailyPerError stampIncorrectOrder(String companyID, String employeeID, GeneralDate processingDate,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		
		EmployeeDailyPerError employeeDailyPerError = null;
		
		List<Integer> attendanceItemIds = new ArrayList<>();

		// TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance =
		// timeLeavingOfDailyPerformanceRepository
		// .findByKey(employeeID, processingDate).get();

		if (timeLeavingOfDailyPerformance != null && !timeLeavingOfDailyPerformance.getTimeLeavingWorks().isEmpty()) {
			
			if (timeLeavingOfDailyPerformance.getTimeLeavingWorks().size() >= 2
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().get().getStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().get().getStamp().get().getTimeWithDay() != null
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().get().getStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().get().getStamp().get().getTimeWithDay() != null
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().get().getStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().get().getStamp().get().getTimeWithDay() != null
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().get().getStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().get().getStamp().get().getTimeWithDay() != null) {
				// ペアの逆転がないか確認する
				List<OutPutProcess> pairOutPutList = checkPairReversed(timeLeavingOfDailyPerformance.getTimeLeavingWorks());
				if (pairOutPutList.stream().anyMatch(item -> item == OutPutProcess.HAS_ERROR)) {
					if (pairOutPutList.get(0).value == 1) {
						attendanceItemIds.add(31);
						attendanceItemIds.add(34);
					} else if (pairOutPutList.size() == 2 && pairOutPutList.get(1).value == 1) {
						attendanceItemIds.add(41);
						attendanceItemIds.add(44);
					}
				} else {
					if (timeLeavingOfDailyPerformance.getTimeLeavingWorks().size() >= 2) {
						if (timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getWorkNo()
								.lessThan(timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getWorkNo())) {
							attendanceItemIds.add(31);
							attendanceItemIds.add(34);
							attendanceItemIds.add(41);
							attendanceItemIds.add(44);
						} else {
							// 重複の判断処理
							TimeWithDayAttr stampStartTimeFirstTime = timeLeavingOfDailyPerformance
									.getTimeLeavingWorks().get(0).getAttendanceStamp().get().getStamp().get()
									.getTimeWithDay();
							TimeWithDayAttr endStartTimeFirstTime = timeLeavingOfDailyPerformance.getTimeLeavingWorks()
									.get(0).getLeaveStamp().get().getStamp().get().getTimeWithDay();
							TimeSpanForCalc timeSpanFirstTime = new TimeSpanForCalc(endStartTimeFirstTime,
									stampStartTimeFirstTime);

							TimeWithDayAttr stampStartTimeSecondTime = timeLeavingOfDailyPerformance
									.getTimeLeavingWorks().get(1).getAttendanceStamp().get().getStamp().get()
									.getTimeWithDay();
							TimeWithDayAttr endStartTimeSecondTime = timeLeavingOfDailyPerformance
									.getTimeLeavingWorks().get(1).getLeaveStamp().get().getStamp().get()
									.getTimeWithDay();
							TimeSpanForCalc timeSpanSecondTime = new TimeSpanForCalc(endStartTimeSecondTime,
									stampStartTimeSecondTime);

							DuplicateStateAtr duplicateStateAtr = this.rangeOfDayTimeZoneService
									.checkPeriodDuplication(timeSpanFirstTime, timeSpanSecondTime);
							DuplicationStatusOfTimeZone duplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService
									.checkStateAtr(duplicateStateAtr);

							if (duplicationStatusOfTimeZone != DuplicationStatusOfTimeZone.NON_OVERLAPPING) {
								attendanceItemIds.add(31);
								attendanceItemIds.add(34);
								attendanceItemIds.add(41);
								attendanceItemIds.add(44);
							}
						}
					}
				}
			}				
		}
		if (!attendanceItemIds.isEmpty()) {
			employeeDailyPerError = new EmployeeDailyPerError(companyID,
					employeeID, processingDate, new ErrorAlarmWorkRecordCode("S004"),
					attendanceItemIds);
		}
		
		return employeeDailyPerError;
	}

	private List<OutPutProcess> checkPairReversed(List<TimeLeavingWork> timeLeavingWorks) {
		List<OutPutProcess> outPutProcessList = new ArrayList<>();
		OutPutProcess pairOutPut = OutPutProcess.HAS_ERROR;

		for (TimeLeavingWork timeLeavingWorking : timeLeavingWorks) {
			if (timeLeavingWorking.getLeaveStamp().get().getStamp().get().getTimeWithDay()
					.greaterThanOrEqualTo(timeLeavingWorking.getAttendanceStamp().get().getStamp().get()
							.getTimeWithDay())) {
				pairOutPut = OutPutProcess.NO_ERROR;
			}
			outPutProcessList.add(pairOutPut);
		}

		return outPutProcessList;
	}
}
