package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.interval.IntervalTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.AbsenceOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.OverSalaryOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.TimeDigestOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.TransferHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.YearlyReservedOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.WithinOutingTotalTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class AttendanceTimeOfDailyAttendanceHelper {

	public static AttendanceTimeOfDailyAttendance attTimeDailyDummy = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
			, ActualWorkingTimeOfDaily.of(TotalWorkingTime.createAllZEROInstance(), 0, 0, 0, 0)
			, new StayingTimeOfDaily()
			, new AttendanceTimeOfExistMinus(1200)
			, new AttendanceTimeOfExistMinus(3600));
	
	/**
	 * totalTime = empty
	 */
	public static AttendanceTimeOfDailyAttendance createLateTime_totalTime_empty(){		
		return new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(null, 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
	}
	
	/**
	 * late time list = empty
	 * @return
	 */
	public static TotalWorkingTime createLateTime(List<LateTimeOfDaily> lateTimeOfDaily) {
		List<HolidayWorkFrameTime> lstHolidayWorkFrameTime = new ArrayList<>();
		for(int i = 1; i<=10; i++) {
			TimeDivergenceWithCalculation holidayWorkTime = TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0));			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(i),
					Finally.of(holidayWorkTime), Finally.of(holidayWorkTime), Finally.of(new AttendanceTime(0)));
			lstHolidayWorkFrameTime.add(frameTime);
		}
		return new TotalWorkingTime(new AttendanceTime(0),
									new AttendanceTime(0),
									new AttendanceTime(0),
									WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))), 
																								new AttendanceTime(0)),
									new ExcessOfStatutoryTimeOfDaily(new ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)), new AttendanceTime(0)),
																	 Optional.of(new OverTimeOfDaily(Collections.emptyList(), 
																			 						 Collections.emptyList(), 
																			 						 Finally.of(new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0)))),
																			 						 new AttendanceTime(0),
																			 						 new FlexTime(
																			 								 TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0)), 
																			 						 new AttendanceTime(0))),
																	 Optional.of(new HolidayWorkTimeOfDaily(Collections.emptyList(), 
																			 								lstHolidayWorkFrameTime, 
																			 								Finally.of(new HolidayMidnightWork(Collections.emptyList())), 
																			 								new AttendanceTime(0)))),
									lateTimeOfDaily,//lateTime empty
									Arrays.asList(new LeaveEarlyTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)), 
											  								TimeWithCalculation.sameTime(new AttendanceTime(0)), 
											  								new WorkNo(0), 
											  								TimevacationUseTimeOfDaily.defaultValue(), 
											  								IntervalExemptionTime.defaultValue())),
									new BreakTimeOfDaily(DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
											DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
																  new BreakTimeGoOutTimes(0),
																  new AttendanceTime(0),
																  Collections.emptyList()),
									Collections.emptyList(),
									new RaiseSalaryTimeOfDailyPerfor(new ArrayList<>(),new ArrayList<>()),
									new WorkTimes(0),
									new TemporaryTimeOfDaily(Collections.emptyList()),
									new ShortWorkTimeOfDaily(new WorkTimes(0),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 ChildCareAttribute.CARE),
									new HolidayOfDaily(new AbsenceOfDaily(new AttendanceTime(0)),
													   new TimeDigestOfDaily(new AttendanceTime(0),new AttendanceTime(0)),
													   new YearlyReservedOfDaily(new AttendanceTime(0)),
													   new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new OverSalaryOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new SpecialHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new AnnualOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new TransferHolidayOfDaily(new AttendanceTime(0))),
									IntervalTimeOfDaily.empty());
	}
	
	/**
	 * late time not empty
	 * @return
	 */
	public static TotalWorkingTime createLateTime() {
		List<HolidayWorkFrameTime> lstHolidayWorkFrameTime = new ArrayList<>();
		for(int i = 1; i<=10; i++) {
			TimeDivergenceWithCalculation holidayWorkTime = TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0));			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(i),
					Finally.of(holidayWorkTime), Finally.of(holidayWorkTime), Finally.of(new AttendanceTime(0)));
			lstHolidayWorkFrameTime.add(frameTime);
		}
		
		List<LateTimeOfDaily> lateTimes = Arrays.asList(new LateTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(120)), 
				  TimeWithCalculation.sameTime(new AttendanceTime(120)), 
				  new WorkNo(0), 
				  new TimevacationUseTimeOfDaily(
						  new AttendanceTime(480)
						  , new AttendanceTime(480)
						  , new AttendanceTime(480)
						  , new AttendanceTime(480)
						  , Optional.of(new SpecialHdFrameNo(1))
						  , new AttendanceTime(480)
						  , new AttendanceTime(480)), 
				  IntervalExemptionTime.defaultValue()));
		
		return new TotalWorkingTime(new AttendanceTime(0),
									new AttendanceTime(0),
									new AttendanceTime(0),
									WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))), 
																								new AttendanceTime(0)),
									new ExcessOfStatutoryTimeOfDaily(new ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)), new AttendanceTime(0)),
																	 Optional.of(new OverTimeOfDaily(Collections.emptyList(), 
																			 						 Collections.emptyList(), 
																			 						 Finally.of(new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0)))),
																			 						 new AttendanceTime(0),
																			 						 new FlexTime(
																			 								 TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0)), 
																			 						 new AttendanceTime(0))),
																	 Optional.of(new HolidayWorkTimeOfDaily(Collections.emptyList(), 
																			 								lstHolidayWorkFrameTime, 
																			 								Finally.of(new HolidayMidnightWork(Collections.emptyList())), 
																			 								new AttendanceTime(0)))),
									lateTimes,
									Arrays.asList(new LeaveEarlyTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)), 
											  								TimeWithCalculation.sameTime(new AttendanceTime(0)), 
											  								new WorkNo(0), 
											  								TimevacationUseTimeOfDaily.defaultValue(), 
											  								IntervalExemptionTime.defaultValue())),
									new BreakTimeOfDaily(DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
											DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
																  new BreakTimeGoOutTimes(0),
																  new AttendanceTime(0),
																  Collections.emptyList()),
									Collections.emptyList(),
									new RaiseSalaryTimeOfDailyPerfor(new ArrayList<>(),new ArrayList<>()),
									new WorkTimes(0),
									new TemporaryTimeOfDaily(Collections.emptyList()),
									new ShortWorkTimeOfDaily(new WorkTimes(0),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 ChildCareAttribute.CARE),
									new HolidayOfDaily(new AbsenceOfDaily(new AttendanceTime(0)),
													   new TimeDigestOfDaily(new AttendanceTime(0),new AttendanceTime(0)),
													   new YearlyReservedOfDaily(new AttendanceTime(0)),
													   new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new OverSalaryOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new SpecialHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new AnnualOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new TransferHolidayOfDaily(new AttendanceTime(0))),
									IntervalTimeOfDaily.empty());
	}
	
	/**
	 * 勤務時間 = empty
	 */
	public static AttendanceTimeOfDailyAttendance create_actualWorkingTimeOfDaily_empty(ActualWorkingTimeOfDaily actualWorkingTimeOfDaily){		
		return new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, actualWorkingTimeOfDaily
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
	}
	
	/**
	 * 総労働時間 = empty
	 */
	public static AttendanceTimeOfDailyAttendance create_totalTime_empty(){		
		return new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(null, 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
	}
	
	/**
	 * 早退時間リスト = empty
	 * @return
	 */
	public static TotalWorkingTime createEarlyTime(List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily) {
		List<HolidayWorkFrameTime> lstHolidayWorkFrameTime = new ArrayList<>();
		for(int i = 1; i<=10; i++) {
			TimeDivergenceWithCalculation holidayWorkTime = TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0));			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(i),
					Finally.of(holidayWorkTime), Finally.of(holidayWorkTime), Finally.of(new AttendanceTime(0)));
			lstHolidayWorkFrameTime.add(frameTime);
		}
		return new TotalWorkingTime(new AttendanceTime(0),
									new AttendanceTime(0),
									new AttendanceTime(0),
									WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))), 
																								new AttendanceTime(0)),
									new ExcessOfStatutoryTimeOfDaily(new ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)), new AttendanceTime(0)),
																	 Optional.of(new OverTimeOfDaily(Collections.emptyList(), 
																			 						 Collections.emptyList(), 
																			 						 Finally.of(new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0)))),
																			 						 new AttendanceTime(0),
																			 						 new FlexTime(
																			 								 TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0)), 
																			 						 new AttendanceTime(0))),
																	 Optional.of(new HolidayWorkTimeOfDaily(Collections.emptyList(), 
																			 								lstHolidayWorkFrameTime, 
																			 								Finally.of(new HolidayMidnightWork(Collections.emptyList())), 
																			 								new AttendanceTime(0)))),
									Collections.emptyList(),//lateTime
									leaveEarlyTimeOfDaily,//earlyTime
									new BreakTimeOfDaily(DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
											DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
																  new BreakTimeGoOutTimes(0),
																  new AttendanceTime(0),
																  Collections.emptyList()),
									Collections.emptyList(),
									new RaiseSalaryTimeOfDailyPerfor(new ArrayList<>(),new ArrayList<>()),
									new WorkTimes(0),
									new TemporaryTimeOfDaily(Collections.emptyList()),
									new ShortWorkTimeOfDaily(new WorkTimes(0),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 ChildCareAttribute.CARE),
									new HolidayOfDaily(new AbsenceOfDaily(new AttendanceTime(0)),
													   new TimeDigestOfDaily(new AttendanceTime(0),new AttendanceTime(0)),
													   new YearlyReservedOfDaily(new AttendanceTime(0)),
													   new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new OverSalaryOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new SpecialHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new AnnualOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new TransferHolidayOfDaily(new AttendanceTime(0))),
									IntervalTimeOfDaily.empty());
	}
	
	/**
	 * 早退時間 not empty
	 * @return
	 */
	public static TotalWorkingTime createEarlyTime() {
		List<HolidayWorkFrameTime> lstHolidayWorkFrameTime = new ArrayList<>();
		for(int i = 1; i<=10; i++) {
			TimeDivergenceWithCalculation holidayWorkTime = TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0));			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(i),
					Finally.of(holidayWorkTime), Finally.of(holidayWorkTime), Finally.of(new AttendanceTime(0)));
			lstHolidayWorkFrameTime.add(frameTime);
		}
		
		List<LeaveEarlyTimeOfDaily> leaveEarlyTimes = Arrays.asList(new LeaveEarlyTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(120)), 
					TimeWithCalculation.sameTime(new AttendanceTime(120)), 
					new WorkNo(0), 
					new TimevacationUseTimeOfDaily(
							new AttendanceTime(480)
							, new AttendanceTime(480)
							, new AttendanceTime(480)
							, new AttendanceTime(480)
							, Optional.of(new SpecialHdFrameNo(1))
							, new AttendanceTime(480)
							, new AttendanceTime(480)), 
					IntervalExemptionTime.defaultValue()));
		
		return new TotalWorkingTime(new AttendanceTime(0),
									new AttendanceTime(0),
									new AttendanceTime(0),
									WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))), 
																								new AttendanceTime(0)),
									new ExcessOfStatutoryTimeOfDaily(new ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)), new AttendanceTime(0)),
																	 Optional.of(new OverTimeOfDaily(Collections.emptyList(), 
																			 						 Collections.emptyList(), 
																			 						 Finally.of(new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0)))),
																			 						 new AttendanceTime(0),
																			 						 new FlexTime(
																			 								 TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0)), 
																			 						 new AttendanceTime(0))),
																	 Optional.of(new HolidayWorkTimeOfDaily(Collections.emptyList(), 
																			 								lstHolidayWorkFrameTime, 
																			 								Finally.of(new HolidayMidnightWork(Collections.emptyList())), 
																			 								new AttendanceTime(0)))),
									Collections.emptyList(),
									leaveEarlyTimes,
									new BreakTimeOfDaily(DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
											DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
																  new BreakTimeGoOutTimes(0),
																  new AttendanceTime(0),
																  Collections.emptyList()),
									Collections.emptyList(),
									new RaiseSalaryTimeOfDailyPerfor(new ArrayList<>(),new ArrayList<>()),
									new WorkTimes(0),
									new TemporaryTimeOfDaily(Collections.emptyList()),
									new ShortWorkTimeOfDaily(new WorkTimes(0),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 ChildCareAttribute.CARE),
									new HolidayOfDaily(new AbsenceOfDaily(new AttendanceTime(0)),
													   new TimeDigestOfDaily(new AttendanceTime(0),new AttendanceTime(0)),
													   new YearlyReservedOfDaily(new AttendanceTime(0)),
													   new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new OverSalaryOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new SpecialHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new AnnualOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new TransferHolidayOfDaily(new AttendanceTime(0))),
									IntervalTimeOfDaily.empty());
	}
	
	/**
	 * 外出時間リスト = empty
	 * @return
	 */
	public static TotalWorkingTime createOutingTime(List<OutingTimeOfDaily> outingTimes) {
		List<HolidayWorkFrameTime> lstHolidayWorkFrameTime = new ArrayList<>();
		for(int i = 1; i<=10; i++) {
			TimeDivergenceWithCalculation holidayWorkTime = TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0));			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(i),
					Finally.of(holidayWorkTime), Finally.of(holidayWorkTime), Finally.of(new AttendanceTime(0)));
			lstHolidayWorkFrameTime.add(frameTime);
		}
		return new TotalWorkingTime(new AttendanceTime(0),
									new AttendanceTime(0),
									new AttendanceTime(0),
									WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))), 
																								new AttendanceTime(0)),
									new ExcessOfStatutoryTimeOfDaily(new ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)), new AttendanceTime(0)),
																	 Optional.of(new OverTimeOfDaily(Collections.emptyList(), 
																			 						 Collections.emptyList(), 
																			 						 Finally.of(new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0)))),
																			 						 new AttendanceTime(0),
																			 						 new FlexTime(
																			 								 TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0)), 
																			 						 new AttendanceTime(0))),
																	 Optional.of(new HolidayWorkTimeOfDaily(Collections.emptyList(), 
																			 								lstHolidayWorkFrameTime, 
																			 								Finally.of(new HolidayMidnightWork(Collections.emptyList())), 
																			 								new AttendanceTime(0)))),
									Collections.emptyList(),//lateTime empty
									Collections.emptyList(),//earlyTime empty
									new BreakTimeOfDaily(DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
											DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
																  new BreakTimeGoOutTimes(0),
																  new AttendanceTime(0),
																  Collections.emptyList()),
									outingTimes,//outingTime
									new RaiseSalaryTimeOfDailyPerfor(new ArrayList<>(),new ArrayList<>()),
									new WorkTimes(0),
									new TemporaryTimeOfDaily(Collections.emptyList()),
									new ShortWorkTimeOfDaily(new WorkTimes(0),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 ChildCareAttribute.CARE),
									new HolidayOfDaily(new AbsenceOfDaily(new AttendanceTime(0)),
													   new TimeDigestOfDaily(new AttendanceTime(0),new AttendanceTime(0)),
													   new YearlyReservedOfDaily(new AttendanceTime(0)),
													   new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new OverSalaryOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new SpecialHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new AnnualOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new TransferHolidayOfDaily(new AttendanceTime(0))),
									IntervalTimeOfDaily.empty());
	}
	
	/**
	 * 外出時間リスト = empty
	 * @return
	 */
	public static TotalWorkingTime createOutingTime_not_empty() {
		List<HolidayWorkFrameTime> lstHolidayWorkFrameTime = new ArrayList<>();
		for(int i = 1; i<=10; i++) {
			TimeDivergenceWithCalculation holidayWorkTime = TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0));			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(i),
					Finally.of(holidayWorkTime), Finally.of(holidayWorkTime), Finally.of(new AttendanceTime(0)));
			lstHolidayWorkFrameTime.add(frameTime);
		}
		
		val reasonTimeChange = new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, EngravingMethod.WEB_STAMP_INPUT);
		val workTimeInformation = new WorkTimeInformation(reasonTimeChange, new TimeWithDayAttr(510));
		
		val actualStamp = new WorkStamp(workTimeInformation, Optional.of(new WorkLocationCD("code")));
		val stamp = new WorkStamp(workTimeInformation, Optional.of(new WorkLocationCD("code")));
		val goOut = new TimeActualStamp(actualStamp, stamp,10);
		val comeBack = new TimeActualStamp(actualStamp, stamp,10);
		
		List<OutingTimeOfDaily> outTimes = Arrays.asList(new OutingTimeOfDaily(
				  new BreakTimeGoOutTimes(120)
				, GoingOutReason.UNION
				, new TimevacationUseTimeOfDaily(
						new AttendanceTime(480)
						, new AttendanceTime(480)
						, new AttendanceTime(480)
						, new AttendanceTime(480)
						, Optional.of(new SpecialHdFrameNo(1))
						, new AttendanceTime(480)
						, new AttendanceTime(480))
				, OutingTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(120))
						, WithinOutingTotalTime.sameTime(TimeWithCalculation.sameTime(new AttendanceTime(120)))
						, TimeWithCalculation.sameTime(new AttendanceTime(120)))
				, OutingTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(120))
						, WithinOutingTotalTime.sameTime(TimeWithCalculation.sameTime(new AttendanceTime(120)))
						, TimeWithCalculation.sameTime(new AttendanceTime(120)))
				, Arrays.asList(new OutingTimeSheet(
						  new OutingFrameNo(1)
						, Optional.of(goOut)
						, new AttendanceTime(120)
						, new AttendanceTime(360)
						, GoingOutReason.UNION
						, Optional.of(comeBack)
						))
				
				
				));
		return new TotalWorkingTime(new AttendanceTime(0),
									new AttendanceTime(0),
									new AttendanceTime(0),
									WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new AttendanceTime(0), 
																								new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))), 
																								new AttendanceTime(0)),
									new ExcessOfStatutoryTimeOfDaily(new ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)), new AttendanceTime(0)),
																	 Optional.of(new OverTimeOfDaily(Collections.emptyList(), 
																			 						 Collections.emptyList(), 
																			 						 Finally.of(new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0), new AttendanceTime(0)))),
																			 						 new AttendanceTime(0),
																			 						 new FlexTime(
																			 								 TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0)), 
																			 						 new AttendanceTime(0))),
																	 Optional.of(new HolidayWorkTimeOfDaily(Collections.emptyList(), 
																			 								lstHolidayWorkFrameTime, 
																			 								Finally.of(new HolidayMidnightWork(Collections.emptyList())), 
																			 								new AttendanceTime(0)))),
									Collections.emptyList(),//lateTime empty
									Collections.emptyList(),//earlyTime empty
									new BreakTimeOfDaily(DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
											DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
																  new BreakTimeGoOutTimes(0),
																  new AttendanceTime(0),
																  Collections.emptyList()),
									outTimes,//outingTime
									new RaiseSalaryTimeOfDailyPerfor(new ArrayList<>(),new ArrayList<>()),
									new WorkTimes(0),
									new TemporaryTimeOfDaily(Collections.emptyList()),
									new ShortWorkTimeOfDaily(new WorkTimes(0),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 ChildCareAttribute.CARE),
									new HolidayOfDaily(new AbsenceOfDaily(new AttendanceTime(0)),
													   new TimeDigestOfDaily(new AttendanceTime(0),new AttendanceTime(0)),
													   new YearlyReservedOfDaily(new AttendanceTime(0)),
													   new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new OverSalaryOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new SpecialHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new AnnualOfDaily(new AttendanceTime(0), new AttendanceTime(0)),
													   new TransferHolidayOfDaily(new AttendanceTime(0))),
									IntervalTimeOfDaily.empty());
	}
	
}
