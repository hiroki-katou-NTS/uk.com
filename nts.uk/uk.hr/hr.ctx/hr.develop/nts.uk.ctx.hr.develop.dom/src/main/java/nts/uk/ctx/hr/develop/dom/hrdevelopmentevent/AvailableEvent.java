package nts.uk.ctx.hr.develop.dom.hrdevelopmentevent;

import lombok.AllArgsConstructor;

/**
 * 利用できる
 */
@AllArgsConstructor
public enum AvailableEvent {
	NOT_USE (0),
	
	USE(1);
	
	public final int value;
}
