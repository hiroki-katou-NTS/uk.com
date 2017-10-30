package nts.uk.ctx.at.schedule.dom.schedule.setting;

import lombok.AllArgsConstructor;
/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
public enum UseAtr {
	/* しない */
	NOT_USE(0),
	/* する */
	USE(1);
	
	public final int value;
}
