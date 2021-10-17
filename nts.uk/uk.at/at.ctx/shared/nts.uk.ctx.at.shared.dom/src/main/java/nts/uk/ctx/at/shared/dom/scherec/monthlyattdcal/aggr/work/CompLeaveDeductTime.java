package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 代休控除時間
 * @author shuichi_ishida
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompLeaveDeductTime {
	/** 実働用 */
	private AttendanceTimeMonth forActualWork = new AttendanceTimeMonth(0);
	/** 割増用 */
	private AttendanceTimeMonth forPremium = new AttendanceTimeMonth(0);
	
	/**
	 * 加算する
	 * @param other 代休控除時間
	 */
	public void add(CompLeaveDeductTime other){
		this.forActualWork = this.forActualWork.addMinutes(other.forActualWork.valueAsMinutes());
		this.forPremium = this.forPremium.addMinutes(other.forPremium.valueAsMinutes());
	}

	/**
	 * 加算する
	 * @param other 勤怠月間時間
	 */
	public void add(AttendanceTimeMonth other){
		this.forActualWork = this.forActualWork.addMinutes(other.valueAsMinutes());
		this.forPremium = this.forPremium.addMinutes(other.valueAsMinutes());
	}
}
