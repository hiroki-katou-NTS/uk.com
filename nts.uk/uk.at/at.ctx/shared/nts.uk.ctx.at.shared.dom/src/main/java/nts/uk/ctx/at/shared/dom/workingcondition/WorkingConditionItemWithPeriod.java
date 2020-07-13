package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * 期間付き労働条件項目
 * @author HieuLt
 *	
 */
@Getter
@RequiredArgsConstructor
public class WorkingConditionItemWithPeriod {
	/** 労働条件項目 **/
	private final WorkingConditionItem workingConditionItem;
	/** 社員ID **/
	private final String empID;
}
