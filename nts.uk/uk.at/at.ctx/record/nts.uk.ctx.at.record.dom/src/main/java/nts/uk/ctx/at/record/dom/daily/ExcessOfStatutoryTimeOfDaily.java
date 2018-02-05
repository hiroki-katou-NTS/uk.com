package nts.uk.ctx.at.record.dom.daily;

import java.util.Collections;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の所定外時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class ExcessOfStatutoryTimeOfDaily {
	@Setter
	private ExcessOfStatutoryMidNightTime ExcessOfStatutoryMidNightTime;
	private Optional<OverTimeOfDaily> OverTimeWork;
	private Optional<HolidayWorkTimeOfDaily> WorkHolidayTime;
	
	
	/**
	 * Constructor
	 * @param excessOfStatutoryMidNightTime
	 * @param overTimeWork
	 * @param workHolidayTime
	 */
	public ExcessOfStatutoryTimeOfDaily(
			nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryMidNightTime excessOfStatutoryMidNightTime,
			Optional<OverTimeOfDaily> overTimeWork, Optional<HolidayWorkTimeOfDaily> workHolidayTime) {
		super();
		ExcessOfStatutoryMidNightTime = excessOfStatutoryMidNightTime;
		OverTimeWork = overTimeWork;
		WorkHolidayTime = workHolidayTime;
	}
	
	/**
	 * 各時間の計算を指示するクラス
	 * @return
	 */
	public static void calculationExcessTime() {
		//所定外深夜
		val excessOfStatutoryMidNightTime = new ExcessOfStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0)),new AttendanceTime(0)); 
		//残業時間
		//val overTimeWork = ;
		//休出時間
		val workHolidayTime = new HolidayWorkTimeOfDaily(Collections.emptyList(), Collections.emptyList(),Finally.empty(), new AttendanceTime(0)); 
		
		//return new ExcessOfStatutoryTimeOfDaily(excessOfStatutoryMidNightTime, overTimeWork, workHolidayTime);
	}
	
	/**
	 * 残業時間の計算
	 * @param oneDay 1日の実績
	 */
	private static OverTimeWorkOfDaily calculationOverTime(CalculationRangeOfOneDay oneDay) {
		if(oneDay.getOutsideWorkTimeSheet().isPresent()) {
			if(oneDay.getOutsideWorkTimeSheet().get().getOverTimeWorkSheet().isPresent()) {
				//oneDay.getOutsideWorkTimeSheet().get().getOverTimeWorkSheet().get().collectOverTimeWorkTime(autoCalcSet);
			}
		}
		//return ;
		return null;
	}
	
//	/**
//	 * 休出時間の計算
//	 * @return
//	 */
//	private static HolidayWorkTimeOfDaily calcuolationHolidayTime() {
//		
//	}
	
	


}
