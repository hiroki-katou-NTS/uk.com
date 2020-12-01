package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

@Getter
public class TimeCareNursingSet {
	
	/** 時間休暇消化単位 **/
	private TimeDigestiveUnit timeDigestiveUnit;
	
	/** 管理区分 **/
	private ManageDistinct manageDistinct;

	public TimeCareNursingSet(TimeDigestiveUnit timeDigestiveUnit, ManageDistinct manageDistinct) {
		super();
		this.timeDigestiveUnit = timeDigestiveUnit;
		this.manageDistinct = manageDistinct;
	}
	

}
