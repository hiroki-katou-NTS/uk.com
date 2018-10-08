package nts.uk.ctx.at.record.dom.daily.midnight;

import lombok.Getter;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;

/**
 * 所定内深夜時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class WithinStatutoryMidNightTime {
	//時間
	private TimeDivergenceWithCalculation time; 
	
	public WithinStatutoryMidNightTime(TimeDivergenceWithCalculation time) {
		super();
		this.time = time;
	}

	
	 /**
	  * 所定内深夜時間の計算指示を出す
	  * @return　所定内深夜時間
	  */
	 public static WithinStatutoryMidNightTime calcPredetermineMidNightTime(CalculationRangeOfOneDay oneDay) {
		 if(oneDay.getWithinWorkingTimeSheet().isPresent()) {
			val calcTime = oneDay.getWithinWorkingTimeSheet().get().calcMidNightTime();
			return new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(calcTime));
		 }
		 else {
			 return new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)));
		 }
	 }
	 
	/**
	 * 実績超過乖離時間の計算
	 * @return
	 */
	public int calcOverLimitDivergenceTime() {
		return this.getTime().getDivergenceTime().valueAsMinutes();
	}
		/**
	 * 実績超過乖離時間が発生しているか判定する
	 * @return 乖離時間が発生している
	 */
	public boolean isOverLimitDivergenceTime() {
		return this.calcOverLimitDivergenceTime() > 0 ? true:false;
	}
	
	/**
	 * 乖離時間のみ再計算
	 * @return
	 */
	public WithinStatutoryMidNightTime calcDiverGenceTime() {
		return new WithinStatutoryMidNightTime(this.time!=null?this.time.calcDiverGenceTime():TimeDivergenceWithCalculation.emptyTime());
	}
	
	/**
	 * 深夜時間の上限時間調整処理
	 * @param upperTime 上限時間
	 */
	public void controlUpperTime(AttendanceTime upperTime) {
		this.time = TimeDivergenceWithCalculation.createTimeWithCalculation(this.time.getTime().greaterThan(upperTime)?upperTime:this.time.getTime(), 
																			this.time.getCalcTime().greaterThan(upperTime)?upperTime:this.time.getCalcTime()); 
	}
		
}
