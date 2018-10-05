package nts.uk.ctx.at.record.dom.daily.holidayworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

/**
 * 休出深夜時間
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class HolidayWorkMidNightTime {
	//時間
	private TimeDivergenceWithCalculation time;
	//法定区分
	private StaturoryAtrOfHolidayWork statutoryAtr;
	
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
	 * 乖離時間を再計算
	 * @return
	 */
	public HolidayWorkMidNightTime calcDiverGenceTime() {
		return new HolidayWorkMidNightTime(this.time!=null?this.time.calcDiverGenceTime():TimeDivergenceWithCalculation.emptyTime(),this.statutoryAtr);
	}
	
	public void reCreate(TimeDivergenceWithCalculation timeDivergenceWithCalculation) {
		this.time = timeDivergenceWithCalculation;
	}
	
}
