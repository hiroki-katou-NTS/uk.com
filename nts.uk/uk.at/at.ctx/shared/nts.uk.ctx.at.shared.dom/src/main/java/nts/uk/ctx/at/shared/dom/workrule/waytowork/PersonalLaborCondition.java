package nts.uk.ctx.at.shared.dom.workrule.waytowork;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;

/**
 * 個人労働条件
 * @author keisuke_hoshina
 *
 */
@Value
public class PersonalLaborCondition {
	private PredetermineTime holidayAddTimeSet;
}
