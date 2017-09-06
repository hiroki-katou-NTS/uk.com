package nts.uk.ctx.at.shared.dom.common.time;

import lombok.Value;

/**
 * 1日の時間内訳
 * @author keisuke_hoshina
 *
 */
@Value
public class BreakdownTimeDay {

	private int morning;
	private int afternoon;
	
	public int getPredetermineWorkTime(){
		return this.morning + this.afternoon;
	}
}
