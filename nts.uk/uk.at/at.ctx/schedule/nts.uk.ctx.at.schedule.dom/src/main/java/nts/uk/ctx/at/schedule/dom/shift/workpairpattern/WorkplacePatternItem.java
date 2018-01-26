package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 勤務ペアパターン
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Getter
public class WorkplacePatternItem extends DomainObject {
	private String workplaceId;
	private int groupNo;
	private int patternNo;
	private WorkPairPatternName patternName;
	private List<WorkplaceWorkPairSet> listWorkplaceWorkPairSet;

	public static WorkplacePatternItem convertFromJavaType(String workplaceId, int groupNo, int patternNo,
			String patternName, List<WorkplaceWorkPairSet> listWorkplaceWorkPairSet) {
		return new WorkplacePatternItem(workplaceId, groupNo, patternNo, new WorkPairPatternName(patternName),
				listWorkplaceWorkPairSet);
	}
}
