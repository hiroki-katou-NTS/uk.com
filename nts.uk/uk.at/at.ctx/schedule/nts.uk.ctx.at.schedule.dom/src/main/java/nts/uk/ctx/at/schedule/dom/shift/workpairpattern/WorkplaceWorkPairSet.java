package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 勤務ペア設定
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Getter
public class WorkplaceWorkPairSet extends DomainObject {
	private String workplaceId;
	private int groupNo;
	private int patternNo;
	private int pairNo;
	private String workTypeCode;
	private String workTimeCode;
}
