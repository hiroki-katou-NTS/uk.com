package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GrantCarryForward {
	/* する */
	ToDo(0),
	/* しない */
	NotDo(1);

	public final int value;
}
