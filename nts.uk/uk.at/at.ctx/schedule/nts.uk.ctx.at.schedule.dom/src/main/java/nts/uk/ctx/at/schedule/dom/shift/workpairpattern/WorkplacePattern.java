package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 職場勤務ペアパターングループ
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class WorkplacePattern extends AggregateRoot {
	private String workplaceId;
	private int groupNo;
	private WorkPairPatternGroupName groupName;
	private GroupUsageAtr groupUsageAtr;
	private String note;
}
