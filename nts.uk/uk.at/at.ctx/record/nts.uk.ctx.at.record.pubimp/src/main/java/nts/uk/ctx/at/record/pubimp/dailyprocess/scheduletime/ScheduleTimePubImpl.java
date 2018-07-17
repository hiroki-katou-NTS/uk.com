package nts.uk.ctx.at.record.pubimp.dailyprocess.scheduletime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ProvisionalCalculationService;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkingTimeSheet;
import nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute;
import nts.uk.ctx.at.record.dom.shorttimework.primitivevalue.ShortWorkTimFrameNo;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.BreakTimeStampIncorrectOrderChecking;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePubImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class ScheduleTimePubImpl implements ScheduleTimePub{

	@Inject
	private ProvisionalCalculationService provisionalCalculationService;
	
	@Override
	public ScheduleTimePubExport calculationScheduleTime(ScheduleTimePubImport impTime) {
		if(impTime == null) {
			return new ScheduleTimePubExport("",
					 GeneralDate.today(),
					 new AttendanceTime(0),
					 new AttendanceTime(0),
					 new AttendanceTime(0),
					 new AttendanceTime(0),
					 new AttendanceTime(0),
					 new AttendanceTime(0),
					 Collections.emptyList()
					 );
		}
		//時間帯リスト
		Map<Integer, TimeZone> timeSheets = getTimeZone(impTime.getStartClock(),impTime.getEndClock());
		List<BreakTimeSheet> breakTimeSheets = getBreakTimeSheets(impTime.getBreakStartTime(),impTime.getBreakEndTime());
		List<OutingTimeSheet> outingTimeSheets = new ArrayList<>();
		List<ShortWorkingTimeSheet> shortWorkingTimeSheets = getShortTimeSheets(impTime.getChildCareStartTime(),impTime.getChildCareEndTime());
		//実績計算
		Optional<IntegrationOfDaily> integrationOfDaily = provisionalCalculationService.calculation(impTime.getEmployeeId(), 
												  													impTime.getTargetDate(), 
												  													timeSheets, 
												  													impTime.getWorkTypeCode(), 
												  													impTime.getWorkTimeCode(), 
												  													breakTimeSheets, 
												  													outingTimeSheets, 
												  													shortWorkingTimeSheets);	
		if(integrationOfDaily.isPresent()) {
			return getreturnValye(integrationOfDaily.get());
		}
		else {
			return new ScheduleTimePubExport(impTime.getEmployeeId(),
					 GeneralDate.today(),
					 new AttendanceTime(0),
					 new AttendanceTime(0),
					 new AttendanceTime(0),
					 new AttendanceTime(0),
					 new AttendanceTime(0),
					 new AttendanceTime(0),
					 Collections.emptyList()
					 );				
		}
	}

	
	/**
	 * 実績からOutputクラスへ値を移行する
	 * @param integrationOfDaily 実績
	 * @return Outputクラス
	 */
	private ScheduleTimePubExport getreturnValye(IntegrationOfDaily integrationOfDaily) {
		String empId = integrationOfDaily.getAffiliationInfor().getEmployeeId();
		GeneralDate ymd = integrationOfDaily.getAffiliationInfor().getYmd();
		
		//総労働時間
		AttendanceTime totalWorkTime = new AttendanceTime(0);
		//計画所定時間
		AttendanceTime preTime = new AttendanceTime(0);
		//実働時間
		AttendanceTime actualWorkTime = new AttendanceTime(0);
		//平日時間
		AttendanceTime weekDayTime = new AttendanceTime(0);
		//休憩時間
		AttendanceTime breakTime = new AttendanceTime(0);
		//育児介護時間
		AttendanceTime childCareTime = new AttendanceTime(0);
		//人件費時間
		List<AttendanceTime> personalExpenceTime = new ArrayList<>();
		
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()
		 &&integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily() != null) {
			//割増
			personalExpenceTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance()
													.getPremiumTimes().stream().map(tc -> tc.getPremitumTime()).collect(Collectors.toList());
			//計画所定
			preTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getWorkScheduleTimeOfDaily().getSchedulePrescribedLaborTime();
			
			if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {
				//総労働時間
				totalWorkTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime();
				//実働時間
				actualWorkTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getActualTime();
				
				//休憩時間
				if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getBreakTimeOfDaily() != null) {
				breakTime = new AttendanceTime(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getBreakTimeOfDaily()
											   .getBreakTimeSheet().stream().map(tc -> tc.getBreakTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
				}
				//育児介護時間
				if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily() != null) {
					childCareTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily().getTotalTime().getTotalTime().getTime();
				}
				
				//平日時間
				weekDayTime =  new AttendanceTime(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getWithinStatutoryTimeOfDaily().getWorkTime().valueAsMinutes()
						       					  - integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().calcTotalHolTime().valueAsMinutes()
						       					  - integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getVacationAddTime().valueAsMinutes());
			}
		}
		 
		return new ScheduleTimePubExport(empId, ymd, totalWorkTime, preTime, actualWorkTime, weekDayTime, breakTime, childCareTime, personalExpenceTime);
	}

	/**
	 * 短時間勤務時間端を作成する
	 * @param childCareStartTime 短時間勤務開始時刻
	 * @param childCareEndTime　短時間勤務狩猟時刻
	 * @return　短時間勤務時間帯
	 */
	private List<ShortWorkingTimeSheet> getShortTimeSheets(List<Integer> childCareStartTime,List<Integer> childCareEndTime) {
		List<ShortWorkingTimeSheet> returnList = new ArrayList<>();
		for(int shortTimeStamp = 1 ; shortTimeStamp< childCareStartTime.size();shortTimeStamp++) {
			if(shortTimeStamp <= childCareStartTime.size()
			 &&shortTimeStamp <= childCareEndTime.size()) {
				returnList.add(new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(shortTimeStamp), 
													 ChildCareAttribute.CHILD_CARE,
													 new TimeWithDayAttr(childCareStartTime.get(shortTimeStamp - 1).intValue()),
													 new TimeWithDayAttr(childCareEndTime.get(shortTimeStamp - 1).intValue()),
													 new AttendanceTime(0),
													 new AttendanceTime(0)));
			}
		}
		return returnList;
	}

	/**
	 * 休憩時間帯作成
	 * @param breakStartTime 休憩開始時刻
	 * @param breakEndTime　休憩終了時刻
	 * @return　休憩時間帯
	 */
	private List<BreakTimeSheet> getBreakTimeSheets(List<Integer> breakStartTime, List<Integer> breakEndTime) {
		List<BreakTimeSheet> returnList = new ArrayList<>();
		for(int breatStampNo = 1;breatStampNo <= breakStartTime.size() ; breatStampNo++) {
			if( breatStampNo <= breakStartTime.size() 
			  &&breatStampNo <= breakEndTime.size()) {
				returnList.add(new BreakTimeSheet(new BreakFrameNo(breatStampNo),
											  new TimeWithDayAttr(breakStartTime.get(breatStampNo - 1).intValue()),
											  new TimeWithDayAttr(breakEndTime.get(breatStampNo - 1).intValue()),
											  new AttendanceTime(0)));
			}
		}
		return returnList;
	}

	/**
	 * 勤務時間帯作成
	 * @param startClock 出勤時刻
	 * @param endClock 退勤時刻
	 * @return　勤務時間帯
	 */
	private Map<Integer, TimeZone> getTimeZone(List<Integer> startClock, List<Integer> endClock) {
		Map<Integer, TimeZone> timeList = new HashMap<>();
		
		for(int workNo = 1 ; workNo < startClock.size() ; workNo++) {
			if(startClock.size() >= workNo
			&& endClock.size() >= workNo) {
				timeList.put(1,new TimeZone(new TimeWithDayAttr(startClock.get(workNo).intValue()),new TimeWithDayAttr(endClock.get(workNo).intValue())));
			}
		}
		return timeList;
	}

}