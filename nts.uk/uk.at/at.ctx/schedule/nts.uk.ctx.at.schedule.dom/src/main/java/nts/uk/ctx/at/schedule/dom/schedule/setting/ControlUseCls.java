package nts.uk.ctx.at.schedule.dom.schedule.setting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ControlUseCls {
	/* しない */
	NOT_USE(0),
	/* する */
	USE(1);
	
	public final int value;
}
