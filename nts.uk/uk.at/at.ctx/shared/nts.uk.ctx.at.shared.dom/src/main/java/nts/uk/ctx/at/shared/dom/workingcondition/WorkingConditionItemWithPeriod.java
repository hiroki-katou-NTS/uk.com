package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;


/**
 * 期間付き労働条件項目
 * @author HieuLt
 *	
 */
@Getter
@RequiredArgsConstructor
public class WorkingConditionItemWithPeriod {
	
	/** 期間 **/
	private final DatePeriod datePeriod;
	
	/** 労働条件項目 **/
	private final WorkingConditionItem workingConditionItem;
	
}
