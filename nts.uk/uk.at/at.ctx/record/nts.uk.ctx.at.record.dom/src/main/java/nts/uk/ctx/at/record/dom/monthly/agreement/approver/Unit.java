package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.AllArgsConstructor;

/**
 * 単位
 */
@AllArgsConstructor
public enum Unit {
	/* 会社 */
	COMPANY(0),
	
	/* 職場 */
	WORKPLACE(1);
	
	/** The value. */
	public final int value;
}
