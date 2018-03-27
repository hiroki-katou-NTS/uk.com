package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
@Getter
public class InputControlOfAttendanceItem extends DomainObject {

	/**本人が変更できる*/
	private boolean youCanChangeIt;
	/**他人が変更できる*/
	private boolean canBeChangedByOthers;
	
	public InputControlOfAttendanceItem(boolean youCanChangeIt, boolean canBeChangedByOthers) {
		super();
		this.youCanChangeIt = youCanChangeIt;
		this.canBeChangedByOthers = canBeChangedByOthers;
	}
	
	
}
