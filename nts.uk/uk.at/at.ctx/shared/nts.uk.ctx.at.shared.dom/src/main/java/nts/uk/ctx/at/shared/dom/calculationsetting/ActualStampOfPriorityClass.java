package nts.uk.ctx.at.shared.dom.calculationsetting;

import lombok.AllArgsConstructor;

/**
 * 
 * 実打刻と申請の優先区分
 */
@AllArgsConstructor
public enum ActualStampOfPriorityClass {
	
	/* 実打刻優先 */
	ACTUAL_STAMP_PRIORITY(0),
	/* 直行直帰・出張申請の打刻優先  */
	GOSTRAIGHT_OR_BUSINESS_STAMP_PRIORITY(1);
	
	public final int value;

}
