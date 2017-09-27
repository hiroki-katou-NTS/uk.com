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
@Getter
@AllArgsConstructor
public class WorkPairSetting extends DomainObject {
	private int patternNo;
	private int pairNo;
}
