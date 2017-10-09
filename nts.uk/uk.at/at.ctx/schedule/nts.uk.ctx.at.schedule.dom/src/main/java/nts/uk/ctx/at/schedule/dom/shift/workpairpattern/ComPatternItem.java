package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 勤務ペアパターン
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class ComPatternItem extends DomainObject {
	private String companyId;
	private int groupNo;
	private int patternNo;
	private WorkPairPatternName patternName;
}
