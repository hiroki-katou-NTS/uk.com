package nts.uk.ctx.at.schedule.dom.schedule.setting.function.control;

import lombok.AllArgsConstructor;

/**
 * するしない区分
 * 
 * @author TanLV
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
