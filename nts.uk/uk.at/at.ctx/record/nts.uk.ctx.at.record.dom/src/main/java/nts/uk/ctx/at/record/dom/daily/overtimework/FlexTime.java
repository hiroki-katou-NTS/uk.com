package nts.uk.ctx.at.record.dom.daily.overtimework;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * フレックス時間
 * @author keisuke_hoshina
 *
 */
@Value
public class FlexTime {
	//フレックス時間
	private TimeDivergenceWithCalculationMinusExist flexTime;
	//申請時間
	private AttendanceTime beforeApplicationTime; 
	
	public TimeWithCalculation getNotMinusFlexTime() {
		return TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.flexTime.getTime() == null ? 0 :this.flexTime.getTime().valueAsMinutes()),
				 new AttendanceTime(this.flexTime.getCalcTime()==null ? 0 : this.flexTime.getCalcTime().valueAsMinutes()));
	}
	
	/**
	 * 実績超過乖離時間の計算
	 * @return
	 */
	public int calcOverLimitDivergenceTime() {
		return this.getFlexTime().getDivergenceTime().valueAsMinutes();
	}

	/**
	 * 実績超過乖離時間が発生しているか判定する
	 * @return 乖離時間が発生している
	 */
	public boolean isOverLimitDivergenceTime() {
		return this.calcOverLimitDivergenceTime() > 0 ? true:false;
	}
	
	/**
	 * 事前申請超過時間の計算
	 * @return
	 */
	public int calcPreOverLimitDivergenceTime() {
		return this.getFlexTime().getTime().valueAsMinutes() - this.beforeApplicationTime.valueAsMinutes();
	}

	/**
	 * 事前申請超過時間が発生しているか判定する
	 * @return 乖離時間が発生している
	 */
	public boolean isPreOverLimitDivergenceTime() {
		return this.calcPreOverLimitDivergenceTime() > 0 ? true:false;
	}
	
	/**
	 * 乖離時間のみ再計算
	 * @return
	 */
	public FlexTime calcDiverGenceTime() {
		TimeDivergenceWithCalculationMinusExist calcedDiverGenceTime = this.flexTime==null?this.flexTime:this.flexTime.calcDiverGenceTime();
		return new FlexTime(calcedDiverGenceTime,this.beforeApplicationTime);
	}
	
}
