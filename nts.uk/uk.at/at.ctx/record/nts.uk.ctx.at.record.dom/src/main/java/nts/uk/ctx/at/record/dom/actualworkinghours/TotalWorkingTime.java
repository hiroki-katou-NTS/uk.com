package nts.uk.ctx.at.record.dom.actualworkinghours;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
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
@AllArgsConstructor
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
	
	/**
	 * 日別実績の総労働時間の計算
	 * @return 
	 */
	public static TotalWorkingTime calcAllDailyRecord(CalculationRangeOfOneDay oneDay) {
		/*日別実績の法定内時間*/
		//this.withinStatutoryTimeOfDaily = WithinStatutoryTimeOfDaily.calcStatutoryTime(oneDay);
		/*日別実績の休憩時間*/
		//this.breakTimeOfDaily = BreakTimeOfDaily.calcTotalBreakTime(oneDay);
		return new TotalWorkingTime(WithinStatutoryTimeOfDaily.calcStatutoryTime(oneDay)
					 				,BreakTimeOfDaily.calcTotalBreakTime(oneDay));
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
	}




}
