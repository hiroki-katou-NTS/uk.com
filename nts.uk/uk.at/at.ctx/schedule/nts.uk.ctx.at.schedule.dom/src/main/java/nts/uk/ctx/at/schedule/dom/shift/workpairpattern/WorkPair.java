package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 勤務ペア
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class WorkPair extends AggregateRoot {
	private int pairNo;
	private String workTypeCode;
	private String workTimeCode;
}
