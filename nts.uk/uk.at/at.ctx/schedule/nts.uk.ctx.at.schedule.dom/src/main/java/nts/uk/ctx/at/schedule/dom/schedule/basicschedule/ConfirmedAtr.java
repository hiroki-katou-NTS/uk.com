package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import lombok.AllArgsConstructor;

/**
 * 確定区分
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
public enum ConfirmedAtr {
	// 未確定
	UNSETTLED(0),
	// 確定済み
	CONFIRMED(1);

	public final int value;
}
