package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 勤怠項目の入力制御
 * @author tutk
 *
 */
@Getter
public class InputControlMonthly extends DomainObject {
	
	/**本人が変更できる*/
	private boolean youCanChangeIt;
	/**他人が変更できる*/
	private boolean canBeChangedByOthers;
	public InputControlMonthly(boolean youCanChangeIt, boolean canBeChangedByOthers) {
		super();
		this.youCanChangeIt = youCanChangeIt;
		this.canBeChangedByOthers = canBeChangedByOthers;
	}
	
}
