package nts.uk.ctx.at.record.dom.daily.withinworktime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の法定内時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class WithinStatutoryTimeOfDaily {
	//就業時間
	private AttendanceTime workTime;
	//就業時間(休暇加算時間を含む)
	private AttendanceTime workTimeIncludeVacationTime = new AttendanceTime(0);
	//所定内割増時間
	private AttendanceTime withinPrescribedPremiumTime = new AttendanceTime(0);
	//法定内深夜時間
	private WithinStatutoryMidNightTime withinStatutoryMidNightTime = new WithinStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0)));
	//休暇加算時間
	private AttendanceTime vacationAddTime = new AttendanceTime(0);  
	
	/**
	 * Constructor
	 * @param workTime 就業時間
	 */
	private WithinStatutoryTimeOfDaily(AttendanceTime workTime) {
		this.workTime = workTime;
	}
	
	/**
	 * 日別実績の法定内時間の計算
	 */
	public static WithinStatutoryTimeOfDaily calcStatutoryTime(CalculationRangeOfOneDay oneDay) {
		AttendanceTime workTime = new AttendanceTime(0);
		//AttendanceTime workTime =  oneDay.getWithinWorkingTimeSheet().calcWorkTimeForStatutory(calcActualTime, dedTimeSheet);
		return new WithinStatutoryTimeOfDaily(workTime);
	}
	
	/**
	 * 指定した引数で日別実績の法定内時間を作成する
	 * @author ken_takasu
	 * @param workTime
	 * @param workTimeIncludeVacationTime
	 * @param withinPrescribedPremiumTime
	 * @param withinStatutoryMidNightTime
	 * @param vacationAddTime
	 * @return
	 */
	public static WithinStatutoryTimeOfDaily createWithinStatutoryTimeOfDaily(AttendanceTime workTime,
																	   AttendanceTime workTimeIncludeVacationTime,
																	   AttendanceTime withinPrescribedPremiumTime,
																	   WithinStatutoryMidNightTime withinStatutoryMidNightTime,
																	   AttendanceTime vacationAddTime) {
		WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily = new WithinStatutoryTimeOfDaily(workTime);
		withinStatutoryTimeOfDaily.workTimeIncludeVacationTime = workTimeIncludeVacationTime;
		withinStatutoryTimeOfDaily.withinPrescribedPremiumTime = withinPrescribedPremiumTime;
		withinStatutoryTimeOfDaily.withinStatutoryMidNightTime = withinStatutoryMidNightTime;
		withinStatutoryTimeOfDaily.vacationAddTime = vacationAddTime;
		return withinStatutoryTimeOfDaily;
	}
}