package nts.uk.ctx.at.shared.dom.common.time;

import lombok.Value;

/**
 * 1日の時間内訳
 * @author keisuke_hoshina
 *
 */
@Value
public class BreakdownTimeDay {

	private AttendanceTime morning;
	private AttendanceTime afternoon;
	private AttendanceTime oneDay;
	
	/**
	 * 所定労働時間の取得
	 * @return　所定労働時間
	 */
	public int getPredetermineWorkTime(){
		return this.morning.valueAsMinutes() + this.afternoon.valueAsMinutes();
	}
}
