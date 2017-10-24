package nts.uk.ctx.at.record.breakorgoout.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 外出理由
 *
 */
@AllArgsConstructor
public enum GoingOutReason {
	
	/* 私用 */
	PRIVATE(0),
	/* 公用 */
	PUBLIC(1),
	/* 有償 */
	COMPENSATION(2),
	/* 組合 */
	UNION(3);
	
	public final int value;

}
