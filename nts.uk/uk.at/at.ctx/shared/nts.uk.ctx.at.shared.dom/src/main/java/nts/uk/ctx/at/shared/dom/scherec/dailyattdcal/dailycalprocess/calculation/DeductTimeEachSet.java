package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 設定別控除時間
 * @author shuichi_ishida
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeductTimeEachSet {
	
	/** 実働用 */
	private AttendanceTime forActualWork = AttendanceTime.ZERO;
	/** 割増用 */
	private AttendanceTime forPremium = AttendanceTime.ZERO;
	
	/**
	 * 加算する
	 * @param other 加算する設定別控除時間
	 */
	public void add(DeductTimeEachSet other){
		this.forActualWork = this.forActualWork.addMinutes(other.forActualWork.valueAsMinutes());
		this.forPremium = this.forPremium.addMinutes(other.forPremium.valueAsMinutes());
	}
	
	/**
	 * 加算する
	 * @param other 加算する勤怠時間
	 */
	public void add(AttendanceTime other){
		this.forActualWork = this.forActualWork.addMinutes(other.valueAsMinutes());
		this.forPremium = this.forPremium.addMinutes(other.valueAsMinutes());
	}
}
