package nts.uk.ctx.at.record.pubimp.dailyprocess.attendancetime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ProvisionalCalculationService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.requestlist.PrevisionalForImp;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubImport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubLateLeaveExport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class DailyAttendanceTimePubImpl implements DailyAttendanceTimePub{

	@Inject
	private ProvisionalCalculationService provisionalCalculationService;
	/**
	 * RequestList No.23
	 */
	@Override
	public DailyAttendanceTimePubExport calcDailyAttendance(DailyAttendanceTimePubImport imp) {
		val result = calcDailyAttendanceTime(imp);
		
		if(!result.isEmpty()) {
			return isPresentValueForAttendanceTime(result.get(0));
		}
		else {
			return notPresentValueForAttendanceTime();
		}
	}
	
	/**
	 * RequestList No.13
	 */
	@Override
	public DailyAttendanceTimePubLateLeaveExport calcDailyLateLeave(DailyAttendanceTimePubImport imp) {
		val result = calcDailyAttendanceTime(imp);
		
		if(!result.isEmpty()) {
			return isPresentValueForLateLeave(result.get(0));
		}
		else {
			return new DailyAttendanceTimePubLateLeaveExport(new AttendanceTime(0), new AttendanceTime(0));
		}
	}


	/**
	 * 実績計算
	 * @param imp　import
	 * @return 計算結果
	 */
	private List<IntegrationOfDaily> calcDailyAttendanceTime(DailyAttendanceTimePubImport imp) {

		if(imp.getEmployeeid() == null || imp.getYmd() == null || imp.getWorkEndTime() == null || imp.getWorkStartTime() == null || imp.getWorkTypeCode() == null)
			return Collections.emptyList();
		
		//時間帯の作成
		TimeZone timeZone = new TimeZone(new TimeWithDayAttr(imp.getWorkStartTime().valueAsMinutes()),
										 new TimeWithDayAttr(imp.getWorkEndTime().valueAsMinutes()));
		Map<Integer, TimeZone> timeZoneMap = new HashMap<Integer, TimeZone>();
		timeZoneMap.put(1, timeZone);
		
		//休憩時間帯の作成
		List<BreakTimeSheet> breakTimeSheets = new ArrayList<>();
		//-----------
		if(imp.getBreakStartTime() != null && imp.getBreakEndTime() != null) {
			breakTimeSheets.add(new BreakTimeSheet(new BreakFrameNo(1),
							new TimeWithDayAttr(imp.getBreakStartTime().valueAsMinutes()),
							new TimeWithDayAttr(imp.getBreakEndTime().valueAsMinutes()),
							imp.getBreakEndTime().minusMinutes(imp.getBreakStartTime().valueAsMinutes())));
		}
		
		
		return provisionalCalculationService.calculation(Arrays.asList(new PrevisionalForImp(imp.getEmployeeid(), 
																											imp.getYmd(),
				  																							timeZoneMap, 
				  																							imp.getWorkTypeCode(), 
				  																							imp.getWorkTimeCode(), 
				  																							breakTimeSheets, 
				  																							Collections.emptyList(), 
				  																							Collections.emptyList())));
	}
	
	/**
	 * 日別計算で値が作成できた時のクラス作成 	
	 * @param integrationOfDaily 1日の実績(WORK)
	 * @return RequestList No23 Output class
	 */
	private DailyAttendanceTimePubExport isPresentValueForAttendanceTime(IntegrationOfDaily integrationOfDaily) {
		val overTimeFrames = new HashMap<OverTimeFrameNo,TimeWithCalculation>();
		val holidayWorkFrames = new HashMap<HolidayWorkFrameNo,TimeWithCalculation>();
		val bonusPays = new HashMap<Integer,AttendanceTime>();
		val specBonusPays = new HashMap<Integer,AttendanceTime>();
		TimeWithCalculation flexTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
		TimeWithCalculation excessTimeMidNightTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
		
		
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily() != null){
				if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()!= null) {
					if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily() != null) {
						if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime() != null)
							 excessTimeMidNightTime = TimeWithCalculation.convertFromTimeDivergence(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime());
						if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
							flexTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getNotMinusFlexTime();
						}
					}
				}
			}
				
			for(int loopNumber = 1 ; loopNumber <=10 ; loopNumber++ ) {
				final int loop = loopNumber;
				//残業
				if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
					val getOver = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime()
									.stream().filter(tc -> tc.getOverWorkFrameNo().v().intValue() == loop).findFirst();
					if(getOver.isPresent()) {
						overTimeFrames.put(new OverTimeFrameNo(loopNumber), TimeWithCalculation.convertFromTimeDivergence(getOver.get().getOverTimeWork()));
					}
					else {
						overTimeFrames.put(new OverTimeFrameNo(loopNumber),TimeWithCalculation.sameTime(new AttendanceTime(0)));
					}
				}
				//休出
				if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()) {
					val getHol = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime()
									.stream().filter(tc -> tc.getHolidayFrameNo().v().intValue() == loop).findFirst();
					if(getHol.isPresent()) {
						holidayWorkFrames.put(new HolidayWorkFrameNo(loopNumber), TimeWithCalculation.convertFromTimeDivergence(getHol.get().getHolidayWorkTime().get()));
					}
					else {
						holidayWorkFrames.put(new HolidayWorkFrameNo(loopNumber),TimeWithCalculation.sameTime(new AttendanceTime(0)));
					}
				}
				//加給
				if(loopNumber < integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor().getRaisingSalaryTimes().size()) {
					
					bonusPays.put(loopNumber, integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor().getRaisingSalaryTimes().get(loopNumber-1).getBonusPayTime());
				}
				else {
					bonusPays.put(loopNumber, new AttendanceTime(0));
				}
				//特定日加給
				if(loopNumber < integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor().getRaisingSalaryTimes().size()) {
					
					specBonusPays.put(loopNumber, integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor().getRaisingSalaryTimes().get(loopNumber-1).getBonusPayTime());
				}
				else {
					specBonusPays.put(loopNumber, new AttendanceTime(0));
				}

			
			}
		}
		return new DailyAttendanceTimePubExport(overTimeFrames,
				holidayWorkFrames,
				bonusPays,
				specBonusPays,
				flexTime,
				excessTimeMidNightTime
			);
	}

	/**
	 * 日別計算で値が作成できなかった時のクラス作成
	 * @return　RequestList No23 Output class(all zero)
	 */
	private DailyAttendanceTimePubExport notPresentValueForAttendanceTime() {
		val overTimeFrames = new HashMap<OverTimeFrameNo,TimeWithCalculation>();
		val holidayWorkFrames = new HashMap<HolidayWorkFrameNo,TimeWithCalculation>();
		val bonusPays = new HashMap<Integer,AttendanceTime>();
		val specBonusPays = new HashMap<Integer,AttendanceTime>();
		for(int loopNumber = 1 ; loopNumber <=10 ; loopNumber++ ) {
			//残業
			overTimeFrames.put(new OverTimeFrameNo(loopNumber), TimeWithCalculation.sameTime(new AttendanceTime(0)));
			//休出
			holidayWorkFrames.put(new HolidayWorkFrameNo(loopNumber), TimeWithCalculation.sameTime(new AttendanceTime(0)));
			//加給
			bonusPays.put(loopNumber,new AttendanceTime(0));
			//特定日加給
			specBonusPays.put(loopNumber,new AttendanceTime(0));
		}
		return new DailyAttendanceTimePubExport(overTimeFrames,
												holidayWorkFrames,
												bonusPays,
												specBonusPays,
												TimeWithCalculation.sameTime(new AttendanceTime(0)),
												TimeWithCalculation.sameTime(new AttendanceTime(0))
											);
	}
	

	/**
	 * 遅刻時間、早退時間計算
	 * @param integrationOfDaily　日別実績(WORK)
	 * @return output class ( No.13)
	 */
	private DailyAttendanceTimePubLateLeaveExport isPresentValueForLateLeave(IntegrationOfDaily integrationOfDaily) {
		int lateTime = 0;
		int leaveEarlyTime = 0;
		if(integrationOfDaily != null
		&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()
		&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily() != null
		&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {
			//遅刻時間
			lateTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getLateTimeOfDaily()
						.stream().map(tc -> tc.getLateTime().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(ts -> ts));
			//早退時間
			leaveEarlyTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getLeaveEarlyTimeOfDaily()
								.stream().map(tc -> tc.getLeaveEarlyTime().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(ts -> ts));
		}
		return new DailyAttendanceTimePubLateLeaveExport(new AttendanceTime(lateTime), new AttendanceTime(leaveEarlyTime));
	}
}
