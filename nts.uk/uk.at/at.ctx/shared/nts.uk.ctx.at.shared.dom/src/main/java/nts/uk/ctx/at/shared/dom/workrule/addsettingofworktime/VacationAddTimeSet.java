package nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;

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
	
	/**
	 * Constructor 
	 */
	public VacationAddTimeSet(BreakDownTimeDay additionTime, AddVacationSet addVacationSet) {
		super();
		AdditionTime = additionTime;
		this.addVacationSet = addVacationSet;
	}
	
	
}
