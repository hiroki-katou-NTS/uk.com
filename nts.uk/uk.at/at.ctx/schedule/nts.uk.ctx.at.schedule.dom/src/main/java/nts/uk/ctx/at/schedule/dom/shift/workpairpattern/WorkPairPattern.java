package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 会社勤務ペアパターングループ
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class WorkPairPattern extends AggregateRoot {
	private String companyId;
	private int groupNo;
	private String groupName;
	private int groupUsageCls;
	private String remarks;
}
