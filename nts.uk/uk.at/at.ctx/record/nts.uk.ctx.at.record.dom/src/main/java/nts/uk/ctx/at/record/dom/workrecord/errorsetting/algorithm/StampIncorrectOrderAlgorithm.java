package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.OutPutProcess;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicateStateAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/*
 * 打刻順序不正
 */
@Stateless
public class StampIncorrectOrderAlgorithm {

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;
	
	@Inject
	private RangeOfDayTimeZoneService rangeOfDayTimeZoneService;

	public OutPutProcess stampIncorrectOrder(String companyID, String employeeID, GeneralDate processingDate) {
		OutPutProcess outPutProcess = OutPutProcess.NO_ERROR;
		List<Integer> attendanceItemIds = new ArrayList<>();

		TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance = timeLeavingOfDailyPerformanceRepository
					.findByKey(employeeID, processingDate).get();
		// ペアの逆転がないか確認する			
		List<OutPutProcess> pairOutPutList = checkPairReversed(timeLeavingOfDailyPerformance.getTimeLeavingWorks());

		if (pairOutPutList.stream().anyMatch(item -> item == OutPutProcess.HAS_ERROR)) {
			if (timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getWorkNo()
					.greaterThan(timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getWorkNo())) {
				this.createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, processingDate,
						new ErrorAlarmWorkRecordCode("S004"), attendanceItemIds);
				outPutProcess = OutPutProcess.HAS_ERROR;
			} else {
				// 重複の判断処理
				TimeWithDayAttr stampStartTimeFirstTime = timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().getStamp().getTimeWithDay();
				TimeWithDayAttr endStartTimeFirstTime = timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().getStamp().getTimeWithDay();
				TimeSpanForCalc timeSpanFirstTime = new TimeSpanForCalc(stampStartTimeFirstTime, endStartTimeFirstTime);
				
				TimeWithDayAttr stampStartTimeSecondTime = timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().getStamp().getTimeWithDay();
				TimeWithDayAttr endStartTimeSecondTime = timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().getStamp().getTimeWithDay();
				TimeSpanForCalc timeSpanSecondTime = new TimeSpanForCalc(stampStartTimeSecondTime, endStartTimeSecondTime);
				
				DuplicateStateAtr duplicateStateAtr = this.rangeOfDayTimeZoneService.checkPeriodDuplication(timeSpanFirstTime, timeSpanSecondTime);
				DuplicationStatusOfTimeZone duplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService.checkStateAtr(duplicateStateAtr);
				
				if (duplicationStatusOfTimeZone != DuplicationStatusOfTimeZone.NON_OVERLAPPING) {
					this.createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, processingDate,
							new ErrorAlarmWorkRecordCode("S004"), attendanceItemIds);
					outPutProcess = OutPutProcess.HAS_ERROR;
				} 
			}
			return outPutProcess;
		} else {
			this.createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, processingDate,
					new ErrorAlarmWorkRecordCode(SystemFixedErrorAlarm.INCORRECT_STAMP.name()), attendanceItemIds);
			outPutProcess = OutPutProcess.HAS_ERROR;
		}

		return outPutProcess;
	}
	
	private List<OutPutProcess> checkPairReversed(List<TimeLeavingWork> timeLeavingWorks){
		List<OutPutProcess> outPutProcessList = new ArrayList<>();
		OutPutProcess pairOutPut = OutPutProcess.HAS_ERROR;
		
		for(TimeLeavingWork timeLeavingWorking : timeLeavingWorks){
			if (timeLeavingWorking.getLeaveStamp().getStamp().getAfterRoundingTime().greaterThanOrEqualTo(timeLeavingWorking.getLeaveStamp().getStamp().getAfterRoundingTime())) {
				pairOutPut = OutPutProcess.NO_ERROR;
			}
			outPutProcessList.add(pairOutPut);
		}
		
		return outPutProcessList;
	}
}
