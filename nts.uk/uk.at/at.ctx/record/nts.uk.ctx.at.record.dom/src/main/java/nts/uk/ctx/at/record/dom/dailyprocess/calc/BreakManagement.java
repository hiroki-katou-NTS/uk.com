package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeSheetOfDaily;


/**
 * 休憩管理
 * @author keisuke_hoshina
 *
 */
@Value
public class BreakManagement {
	private BreakTimeOfDaily breakTimeOfDaily;
	private List<BreakTimeSheetOfDaily> breakTimeSheetOfDaily;
}
