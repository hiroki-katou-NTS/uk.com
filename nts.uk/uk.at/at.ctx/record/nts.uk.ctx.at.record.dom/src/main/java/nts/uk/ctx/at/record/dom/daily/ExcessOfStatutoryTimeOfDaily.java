package nts.uk.ctx.at.record.dom.daily;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;

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
	 * 残業時間の計算
	 * @param oneDay 1日の実績
	 */
	public static ExcessOfStatutoryTimeOfDaily CalculationOverTime(CalculationRangeOfOneDay oneDay) {
		if(oneDay.getOutsideWorkTimeSheet().isPresent()) {
			if(oneDay.getOutsideWorkTimeSheet().get().getOverTimeWorkSheet().isPresent()) {
				//oneDay.getOutsideWorkTimeSheet().get().getOverTimeWorkSheet().get().collectOverTimeWorkTime(autoCalcSet);
			}
		}
		else {
			
		}
		return null;
	}
	
	


}
