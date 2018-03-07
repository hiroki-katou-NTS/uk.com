package nts.uk.ctx.at.record.dom.daily.midnight;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;

/**
 * 所定内深夜時間
 * @author keisuke_hoshina
 *
 */
@Value
public class WithinStatutoryMidNightTime {
	private TimeWithCalculation time; 
	
	 /**
	  * 所定内深夜時間の計算指示を出す
	  * @return　所定内深夜時間
	  */
	 public static WithinStatutoryMidNightTime calcPredetermineMidNightTime(CalculationRangeOfOneDay oneDay,AutoCalAtrOvertime autoCalcSet) {
		 if(oneDay.getWithinWorkingTimeSheet().isPresent()) {
			val calcTime = oneDay.getWithinWorkingTimeSheet().get().calcMidNightTime(autoCalcSet);
			return new WithinStatutoryMidNightTime(TimeWithCalculation.sameTime(calcTime));
		 }
		 else {
			 return new WithinStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		 }
	 }
}
