package nts.uk.ctx.at.record.dom.dailyprocess.calc.goout;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;


/**
 * 外出管理
 * @author keisuke_hoshina
 *
 */
@Getter
public class GoOutManagement {
	private List<OutingTimeOfDailyPerformance> dailyWorkOfGoOutTimeSheet;
	//private GoOutTimeOfDaily dailyOfGoOutTime;
}
