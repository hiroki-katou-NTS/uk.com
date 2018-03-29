package nts.uk.ctx.at.record.pubimp.dailyprocess.attendancetime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ProvisionalCalculationService;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubImport;
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
	 * RequestList No23
	 */
	@Override
	public DailyAttendanceTimePubExport calcDailyAttendance(DailyAttendanceTimePubImport imp) {

		if(imp.getEmployeeid() == null || imp.getYmd() == null || imp.getWorkEndTime() == null || imp.getWorkStartTime() == null)
			return notPresentValue();
		
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
		val calculateResult = provisionalCalculationService.calculation(imp.getEmployeeid(),
																		imp.getYmd(),
																		timeZoneMap,
																		imp.getWorkTypeCode(),
																		imp.getWorkTimeCode(),
																		breakTimeSheets,
																		Collections.emptyList(),
																		Collections.emptyList());
		if(calculateResult.isPresent()) {
			return isPresentValue(calculateResult.get());
		}
		else {
			return notPresentValue();
		}

	}
	
	/**
	 * 日別計算で値が作成できた時のクラス作成 	
	 * @param integrationOfDaily 1日の実績(WORK)
	 * @return RequestList No23 Output class
	 */
	private DailyAttendanceTimePubExport isPresentValue(IntegrationOfDaily integrationOfDaily) {
		val overTimeFrames = new HashMap<OverTimeFrameNo,TimeWithCalculation>();
		val holidayWorkFrames = new HashMap<HolidayWorkFrameNo,TimeWithCalculation>();
		val bonusPays = new HashMap<Integer,AttendanceTime>();
		val specBonusPays = new HashMap<Integer,AttendanceTime>();
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			for(int loopNumber = 1 ; loopNumber <=10 ; loopNumber++ ) {
				//残業
				if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
					if(loopNumber < integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime().size()) {
						overTimeFrames.put(new OverTimeFrameNo(loopNumber), TimeWithCalculation.convertFromTimeDivergence(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime().get(loopNumber - 1).getOverTimeWork()));
					}
					else {
						overTimeFrames.put(new OverTimeFrameNo(loopNumber),TimeWithCalculation.sameTime(new AttendanceTime(0)));
					}
				}
				//休出
				if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()) {
					if(loopNumber < integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime().size()) {
						holidayWorkFrames.put(new HolidayWorkFrameNo(loopNumber), TimeWithCalculation.convertFromTimeDivergence(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime().get(loopNumber - 1).getHolidayWorkTime().get()));
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
				integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getNotMinusFlexTime(),
				TimeWithCalculation.convertFromTimeDivergence(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime())
			);
	}

	/**
	 * 日別計算で値が作成できなかった時のクラス作成
	 * @return　RequestList No23 Output class(all zero)
	 */
	private DailyAttendanceTimePubExport notPresentValue() {
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

}
