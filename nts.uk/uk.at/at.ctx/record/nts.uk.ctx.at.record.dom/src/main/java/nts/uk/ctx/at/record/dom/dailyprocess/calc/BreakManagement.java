package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.DailyOfBreakTime;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.DailyOfBreakTimeSheet;

/**
 * 休憩管理
 * @author keisuke_hoshina
 *
 */
@Value
public class BreakManagement {
	private DailyOfBreakTime dailyOfBreakTime;
	private List<DailyOfBreakTimeSheet> dailyOfBreakTimeSheet;
}
