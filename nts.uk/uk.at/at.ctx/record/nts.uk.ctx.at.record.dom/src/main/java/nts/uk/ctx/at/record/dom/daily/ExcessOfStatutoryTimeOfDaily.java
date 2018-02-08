package nts.uk.ctx.at.record.dom.daily;

import java.util.Collections;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.FlexTime;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;

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
			ExcessOfStatutoryMidNightTime excessOfStatutoryMidNightTime,
			Optional<OverTimeOfDaily> overTimeWork,
			Optional<HolidayWorkTimeOfDaily> workHolidayTime) {
		super();
		ExcessOfStatutoryMidNightTime = excessOfStatutoryMidNightTime;
		OverTimeWork = overTimeWork;
		WorkHolidayTime = workHolidayTime;
	}
	
	/**
	 * 各時間の計算を指示するクラス
	 * @return
	 */
	public static ExcessOfStatutoryTimeOfDaily calculationExcessTime(CalculationRangeOfOneDay oneDay,AutoCalculationOfOverTimeWork overTimeAutoCalcSet,AutoCalSetting holidayAutoCalcSetting) {
		//所定外深夜
		val excessOfStatutoryMidNightTime = new ExcessOfStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0)),new AttendanceTime(0)); 
		//残業時間
		val overTime = calculationOverTime(oneDay,overTimeAutoCalcSet);
		//休出時間
		val workHolidayTime = calculationHolidayTime(oneDay,holidayAutoCalcSetting);
		//new HolidayWorkTimeOfDaily(Collections.emptyList(), Collections.emptyList(),Finally.empty(), new AttendanceTime(0)); 
		
		return new ExcessOfStatutoryTimeOfDaily(excessOfStatutoryMidNightTime, Optional.of(overTime), Optional.of(workHolidayTime));
	}
	
	/**
	 * 残業時間の計算
	 * @param oneDay 
	 */
	private static OverTimeOfDaily calculationOverTime(CalculationRangeOfOneDay oneDay,AutoCalculationOfOverTimeWork overTimeAutoCalcSet) {
		if(oneDay.getOutsideWorkTimeSheet().isPresent()) {
			if(oneDay.getOutsideWorkTimeSheet().get().getOverTimeWorkSheet().isPresent()) {
				return OverTimeOfDaily.calculationTime(oneDay.getOutsideWorkTimeSheet().get().getOverTimeWorkSheet().get(), overTimeAutoCalcSet);
			}
		}
		return new OverTimeOfDaily(Collections.emptyList(),
								   Collections.emptyList(),
								   Finally.of(new ExcessOverTimeWorkMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0)))),
								   new AttendanceTime(0),
								   new FlexTime(TimeWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(0)), new AttendanceTime(0)),
								   new AttendanceTime(0));
	}
	
	/**
	 * 休出時間の計算
	 * @return
	 */
	private static HolidayWorkTimeOfDaily calculationHolidayTime(CalculationRangeOfOneDay oneDay,AutoCalSetting holidayAutoCalcSetting) {
		if(oneDay.getOutsideWorkTimeSheet().isPresent()) {
			if(oneDay.getOutsideWorkTimeSheet().get().getHolidayWorkTimeSheet().isPresent()) {
				return HolidayWorkTimeOfDaily.calculationTime(oneDay.getOutsideWorkTimeSheet().get().getHolidayWorkTimeSheet().get(), holidayAutoCalcSetting);
			}
		}
		return new HolidayWorkTimeOfDaily(Collections.emptyList(),
				   						  Collections.emptyList(),
				   						  Finally.of(new HolidayMidnightWork(Collections.emptyList())),
				   						  new AttendanceTime(0));
	}
	
	


}
