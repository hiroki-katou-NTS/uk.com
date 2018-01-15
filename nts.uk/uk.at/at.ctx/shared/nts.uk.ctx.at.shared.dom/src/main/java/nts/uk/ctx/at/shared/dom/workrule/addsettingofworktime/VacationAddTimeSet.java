package nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;

/**
 * 休暇加算時間設定
 * @author keisuke_hoshina
 *
 */
@Getter
public class VacationAddTimeSet {
	//加給時間
	private BreakDownTimeDay AdditionTime;
	//加算休暇設定
	private AddVacationSet addVacationSet;
	
}
