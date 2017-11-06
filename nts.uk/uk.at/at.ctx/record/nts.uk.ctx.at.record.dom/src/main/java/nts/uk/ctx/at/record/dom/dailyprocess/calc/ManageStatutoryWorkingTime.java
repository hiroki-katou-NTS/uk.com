package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
/**
 * 法定労働時間管理
 * @author keisuke_hoshina
 *
 */

@Value
public class ManageStatutoryWorkingTime {
	private DailyTime predetermineWorkingTime;
	private DailyTime statutoryWorkingTime;
}
