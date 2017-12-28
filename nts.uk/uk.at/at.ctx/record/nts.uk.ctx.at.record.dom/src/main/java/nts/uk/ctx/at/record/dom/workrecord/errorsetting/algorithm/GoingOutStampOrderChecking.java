package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.CheckState;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicateStateAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class GoingOutStampOrderChecking {

	@Inject
	private OutingTimeOfDailyPerformanceRepository outingTimeOfDailyPerformanceRepository;

	@Inject
	private RangeOfDayTimeZoneService rangeOfDayTimeZoneService;
	
	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;
	
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;
	
	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepository;

	public void goingOutStampOrderChecking(String companyId, String employeeId, GeneralDate processingDate) {
		Optional<OutingTimeOfDailyPerformance> outingTimeOfDailyPerformance = this.outingTimeOfDailyPerformanceRepository
				.findByEmployeeIdAndDate(employeeId, processingDate);
		if (outingTimeOfDailyPerformance.isPresent()) {

			List<OutingTimeSheet> outingTimeSheets = outingTimeOfDailyPerformance.get().getOutingTimeSheets();
			outingTimeSheets.sort((e1, e2) -> e1.getGoOut().getStamp().get().getTimeWithDay().v()
					.compareTo(e2.getGoOut().getStamp().get().getTimeWithDay().v()));
			
			int outingFrameNo = 1;
			for (OutingTimeSheet item : outingTimeSheets){
				TimeActualStamp goOut = item.getGoOut();
				AttendanceTime outingTimeCalculation = item.getOutingTimeCalculation();
				AttendanceTime outingTime = item.getOutingTime();
				GoingOutReason reasonForGoOut = item.getReasonForGoOut();
				TimeActualStamp comeBack = item.getComeBack();
				item = new OutingTimeSheet(new OutingFrameNo(outingFrameNo), goOut, outingTimeCalculation, outingTime, reasonForGoOut, comeBack);
				outingFrameNo ++;
			}
			
			List<OutingTimeSheet> outingTimeSheetsTemp = new ArrayList<>(); 
			List<Integer> frameNo = outingTimeSheets.stream().map(item -> item.getOutingFrameNo().v()).collect(Collectors.toList());

			for (OutingTimeSheet outingTimeSheet : outingTimeSheets) {
				if (outingTimeSheet.getGoOut().getStamp().get().getTimeWithDay()
						.lessThanOrEqualTo(outingTimeSheet.getComeBack().getStamp().get().getTimeWithDay())) {
					
					List<Integer> attendanceItemIDList = new ArrayList<>();
					
					if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((1)))) {
						attendanceItemIDList.add(88);
						attendanceItemIDList.add(91);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((2)))) {
						attendanceItemIDList.add(95);
						attendanceItemIDList.add(98);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((3)))) {
						attendanceItemIDList.add(102);
						attendanceItemIDList.add(105);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((4)))) {
						attendanceItemIDList.add(109);
						attendanceItemIDList.add(112);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((5)))) {
						attendanceItemIDList.add(116);
						attendanceItemIDList.add(119);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((6)))) {
						attendanceItemIDList.add(123);
						attendanceItemIDList.add(126);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((7)))) {
						attendanceItemIDList.add(130);
						attendanceItemIDList.add(133);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((8)))) {
						attendanceItemIDList.add(137);
						attendanceItemIDList.add(140);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((9)))) {
						attendanceItemIDList.add(144);
						attendanceItemIDList.add(147);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((10)))) {
						attendanceItemIDList.add(151);
						attendanceItemIDList.add(154);
					}
					
					// list contain different 
					outingTimeSheetsTemp = outingTimeSheets.stream().filter(item -> !frameNo.contains(item.getOutingFrameNo().v())).collect(Collectors.toList());
					
					TimeWithDayAttr stampStartTimeFirstTime = outingTimeSheet.getGoOut().getStamp().get().getTimeWithDay();
					TimeWithDayAttr endStartTimeFirstTime = outingTimeSheet.getComeBack().getStamp().get().getTimeWithDay();
					TimeSpanForCalc timeSpanFirstTime = new TimeSpanForCalc(stampStartTimeFirstTime, endStartTimeFirstTime);
					
					List<DuplicationStatusOfTimeZone> newList = new ArrayList<>();
					
					// 他の時間帯との時間帯重複を確認する
					// check with another outingFrameNo
					for(OutingTimeSheet timeSheet : outingTimeSheetsTemp){						
						TimeWithDayAttr stampStartTimeSecondTime = timeSheet.getGoOut().getStamp().get().getTimeWithDay();
						TimeWithDayAttr endStartTimesecondTime = timeSheet.getComeBack().getStamp().get().getTimeWithDay();
						TimeSpanForCalc timeSpanSecondTime = new TimeSpanForCalc(stampStartTimeSecondTime, endStartTimesecondTime);
						
						DuplicateStateAtr duplicateStateAtr = this.rangeOfDayTimeZoneService
								.checkPeriodDuplication(timeSpanFirstTime, timeSpanSecondTime);
						DuplicationStatusOfTimeZone duplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService
								.checkStateAtr(duplicateStateAtr);
						newList.add(duplicationStatusOfTimeZone);
					}	
					
					if (newList.stream().anyMatch(item -> item != DuplicationStatusOfTimeZone.NON_OVERLAPPING)) {
						createEmployeeDailyPerError.createEmployeeDailyPerError(companyId, employeeId, processingDate, new ErrorAlarmWorkRecordCode("S004"), attendanceItemIDList);
					} else {
						// 出退勤時間帯に包含されているか確認する
						CheckState checkState = checkConjugation(companyId, employeeId, processingDate, outingTimeSheet);
						if(checkState == CheckState.NON_INCLUSION){
							createEmployeeDailyPerError.createEmployeeDailyPerError(companyId, employeeId, processingDate, new ErrorAlarmWorkRecordCode("S004"), attendanceItemIDList);
						}
					}
					
				}
			}
		}
	}
	
	private CheckState checkConjugation(String comanyID, String employeeID, GeneralDate processingDate, OutingTimeSheet outingTimeSheet){
		CheckState state = CheckState.INCLUSION;
		
		Optional<TimeLeavingOfDailyPerformance> timeLeavingOfDailyPerformance = this.timeLeavingOfDailyPerformanceRepository
				.findByKey(employeeID, processingDate);
		
		TimeWithDayAttr stampStartTimeFirstTime = outingTimeSheet.getGoOut().getStamp().get().getTimeWithDay();
		TimeWithDayAttr endStartTimeFirstTime = outingTimeSheet.getComeBack().getStamp().get().getTimeWithDay();
		TimeSpanForCalc timeSpanFirstTime = new TimeSpanForCalc(stampStartTimeFirstTime, endStartTimeFirstTime); 
		
		if(timeLeavingOfDailyPerformance.isPresent()){
			List<TimeLeavingWork> timeLeavingWorks = timeLeavingOfDailyPerformance.get().getTimeLeavingWorks();
			for (TimeLeavingWork timeLeavingWork : timeLeavingWorks){
//				if(timeLeavingWork.getAttendanceStamp().getStamp() != null){
					TimeWithDayAttr stampStartTimeSecondTime = timeLeavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay();
					TimeWithDayAttr endStartTimesecondTime = timeLeavingWork.getLeaveStamp().getStamp().get().getTimeWithDay();
					TimeSpanForCalc timeSpanSecondTime = new TimeSpanForCalc(stampStartTimeSecondTime, endStartTimesecondTime);
					DuplicateStateAtr duplicateStateAtr = this.rangeOfDayTimeZoneService
							.checkPeriodDuplication(timeSpanFirstTime, timeSpanSecondTime);
					DuplicationStatusOfTimeZone duplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService
							.checkStateAtr(duplicateStateAtr);
					if (duplicationStatusOfTimeZone == DuplicationStatusOfTimeZone.SAME_WORK_TIME || 
							duplicationStatusOfTimeZone == DuplicationStatusOfTimeZone.INCLUSION_OUTSIDE ) {
						state = CheckState.INCLUSION;
					} else {
						Optional<TemporaryTimeOfDailyPerformance> temporaryTimeOfDailyPerformance = this.temporaryTimeOfDailyPerformanceRepository
								.findByKey(employeeID, processingDate);

						if (temporaryTimeOfDailyPerformance.isPresent()) {
							List<TimeLeavingWork> leavingWorks = temporaryTimeOfDailyPerformance.get().getTimeLeavingWorks();
							for(TimeLeavingWork leavingWork : leavingWorks){
								TimeWithDayAttr newStampStartTimeSecondTime = leavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay();
								TimeWithDayAttr newEndStartTimesecondTime = leavingWork.getLeaveStamp().getStamp().get().getTimeWithDay();
								TimeSpanForCalc newTimeSpanSecondTime = new TimeSpanForCalc(newStampStartTimeSecondTime, newEndStartTimesecondTime);
								DuplicateStateAtr newDuplicateStateAtr = this.rangeOfDayTimeZoneService
										.checkPeriodDuplication(timeSpanFirstTime, newTimeSpanSecondTime);
								DuplicationStatusOfTimeZone newDuplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService
										.checkStateAtr(newDuplicateStateAtr);
								if(newDuplicationStatusOfTimeZone == DuplicationStatusOfTimeZone.SAME_WORK_TIME || 
										newDuplicationStatusOfTimeZone == DuplicationStatusOfTimeZone.INCLUSION_OUTSIDE){
									state = CheckState.INCLUSION;
								} else {
									state = CheckState.NON_INCLUSION;
								}
							}
							
						}
					}
//				}
			}
		}

		
		return state;
	}
	
}
