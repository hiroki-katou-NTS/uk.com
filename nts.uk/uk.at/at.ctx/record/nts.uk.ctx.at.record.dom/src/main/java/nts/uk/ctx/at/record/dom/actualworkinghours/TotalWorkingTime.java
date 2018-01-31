package nts.uk.ctx.at.record.dom.actualworkinghours;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.raisesalarytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 総労働時間
 *
 */
@Getter
public class TotalWorkingTime {
	
	//総労働時間
	private AttendanceTime totalTime;
	
	//総計算時間
	private AttendanceTime totalCalcTime;
	
	//実働時間
	private AttendanceTime actualTime;
	
	//日別実績の残業時間
	//private OverTimeOfDaily overTimeWorkOfDaily;
	
	//日別実績の休出時間
	//private HolidayWorkTimeOfDaily holidayWorkTimeOfDaily;
	
	//日別実績の法定内時間
	private WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily;
	// TODO has some class which haven't written
	
	//日別実績の所定外時間
	private ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily;
	
	//日別実績の遅刻時間
	private List<LateTimeOfDaily> lateTimeOfDaily = Collections.emptyList();
	
	//日別実績の早退時間
	private List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily = Collections.emptyList(); 
	
	//日別実績の休憩時間
	private BreakTimeOfDaily breakTimeOfDaily;
	
	//日別実績の外出時間	
	private List<OutingTimeOfDailyPerformance> outingTimeOfDailyPerformance;
		
	//加給時間
	private RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor;
	
	//勤務回数
	private WorkTimes workTimes;
	
	/*日別実績の臨時時間*/
	private TemporaryTimeOfDaily temporaryTime;
	
	/**
	 * 
	 * @param withinStatutory
	 * @param breakTime
	 * @return
	 */
	private TotalWorkingTime(WithinStatutoryTimeOfDaily withinStatutory,BreakTimeOfDaily breakTime) {
		this.withinStatutoryTimeOfDaily = withinStatutory;
		this.breakTimeOfDaily = breakTime;
	}
	public TotalWorkingTime(AttendanceTime totalTime, AttendanceTime totalCalcTime, AttendanceTime actualTime,
			WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily,
			ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily, List<LateTimeOfDaily> lateTimeOfDaily,
			List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily, BreakTimeOfDaily breakTimeOfDaily,
			List<OutingTimeOfDailyPerformance> outingTimeOfDailyPerformance,
			RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor, WorkTimes workTimes,
			TemporaryTimeOfDaily temporaryTime) {
		super();
		this.totalTime = totalTime;
		this.totalCalcTime = totalCalcTime;
		this.actualTime = actualTime;
		this.withinStatutoryTimeOfDaily = withinStatutoryTimeOfDaily;
		this.excessOfStatutoryTimeOfDaily = excessOfStatutoryTimeOfDaily;
		this.lateTimeOfDaily = lateTimeOfDaily;
		this.leaveEarlyTimeOfDaily = leaveEarlyTimeOfDaily;
		this.breakTimeOfDaily = breakTimeOfDaily;
		this.outingTimeOfDailyPerformance = outingTimeOfDailyPerformance;
		this.raiseSalaryTimeOfDailyPerfor = raiseSalaryTimeOfDailyPerfor;
		this.workTimes = workTimes;
		this.temporaryTime = temporaryTime;
	}
	
	/**
	 * 日別実績の総労働時間の計算
	 * @return 
	 */
	public static TotalWorkingTime calcAllDailyRecord(CalculationRangeOfOneDay oneDay) {
		
		//総労働時間
		val totalWorkTime = new AttendanceTime(0);
		//総計算時間
		val totalCalcTime = new AttendanceTime(0);
		//実働時間
		val actualTime = new AttendanceTime(0);
		/*日別実績の法定内時間*/
		val withinStatutoryTimeOfDaily = WithinStatutoryTimeOfDaily.calcStatutoryTime(oneDay);
		//日別実績の所定外時間
		val excesstime = new ExcessOfStatutoryTimeOfDaily(new ExcessOfStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0)),new AttendanceTime(0)) ,Optional.empty(),Optional.empty());
		//日別実績の遅刻時間
		val lateTime = Collections.emptyList();
		//日別実績の早退時間
		val earlyTime = Collections.emptyList();
		//日別実績の休憩時間
		val breakTime = new BreakTimeOfDaily(DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
																   TimeWithCalculation.sameTime(new AttendanceTime(0)),
																   TimeWithCalculation.sameTime(new AttendanceTime(0))),
																	DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
																	TimeWithCalculation.sameTime(new AttendanceTime(0)),
																	TimeWithCalculation.sameTime(new AttendanceTime(0))),
																	new BreakTimeGoOutTimes(0),
																	new AttendanceTime(0),
																	Collections.emptyList());
		//日別実績の外出時間	
		val outingTime = Collections.emptyList();
		//加給時間
		val raiseTime = new RaiseSalaryTimeOfDailyPerfor(Collections.emptyList(),Collections.emptyList());
		//勤務回数
		val workTimes = new WorkTimes(1);
		/*日別実績の臨時時間*/
		val tempTime = new TemporaryTimeOfDaily(Collections.emptyList());
		
		

		/*日別実績の休憩時間*/
		//this.breakTimeOfDaily = BreakTimeOfDaily.calcTotalBreakTime(oneDay);
		//return new TotalWorkingTime(WithinStatutoryTimeOfDaily.calcStatutoryTime(oneDay)
					 				//,BreakTimeOfDaily.calcTotalBreakTime(oneDay));
		/*日別実績の遅刻時間*/
		/*日別実績の早退時間(途中)*/
		/*日別実績の外出時間*/
		
		/*日別実績の加給時間*/
		/*日別実績の法定外時間*/
		
		//-------完全未着手--------//
		/*日別実績の休暇*/
		/*日別実績の臨時時間*/
		
		/*日別実績の短時間勤務時間*/
		
		/*日別実績の実働時間*/
		/*日別実績の勤務回数*/
		/*日別実績の総労働時間*/
		//-------完全未着手--------//
		return new TotalWorkingTime(totalWorkTime,
									totalCalcTime,
									actualTime,
									withinStatutoryTimeOfDaily,
									excesstime,
									Collections.emptyList(),
									Collections.emptyList(),
									breakTime,
									Collections.emptyList(),
									raiseTime,
									workTimes,
									tempTime);
	}






}
