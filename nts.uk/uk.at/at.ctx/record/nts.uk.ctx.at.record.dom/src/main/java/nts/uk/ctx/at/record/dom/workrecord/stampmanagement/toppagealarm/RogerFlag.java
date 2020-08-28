package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import lombok.AllArgsConstructor;

/**
 * 了解フラグ
 * @author yennth
 *
 */
@AllArgsConstructor
public enum RogerFlag {
	/** 未読 */
	UNREAD(0),
	/** 了解 */
	ALREADY_READ(1);
	public final int value;
}
