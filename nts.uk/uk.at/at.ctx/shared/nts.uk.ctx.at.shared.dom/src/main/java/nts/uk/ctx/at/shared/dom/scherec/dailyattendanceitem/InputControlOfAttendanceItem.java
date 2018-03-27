package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
@Getter
public class InputControlOfAttendanceItem extends DomainObject {

	/**他人が変更できる*/
	private boolean canBeChangedByOthers;
	
	/**本人が変更できる*/
	private boolean youCanChangeIt;

	public InputControlOfAttendanceItem(boolean canBeChangedByOthers, boolean youCanChangeIt) {
		super();
		this.canBeChangedByOthers = canBeChangedByOthers;
		this.youCanChangeIt = youCanChangeIt;
	}
}
