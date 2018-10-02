package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 法定労働控除時間
 * @author keisuke_hoshina
 *
 */
@Value
public class StatutoryDeductionForFlex {
	//実働用
	private AttendanceTime forActualWork;
	//割増用
	private AttendanceTime forPremium;
}
