package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;

/**
 * 日別計算用の個人情報
 * @author keisuke_hoshina
 *
 */
@Value
public class DailyCalculationPersonalInformation {
	private WorkingSystem workingSystem;
	private DailyTime StatutoryWorkingTime;
}
