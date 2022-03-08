package nts.uk.ctx.at.schedule.dom.schedule.share;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;

public class AffiliationInforOfDailyAttdHelperInSchedule {
	/**
	 * 日別勤怠の所属情報を作る
	 * @param wplID 職場ID
	 * @param workplaceGroupId 職場グループID
	 * @return
	 */
	public static AffiliationInforOfDailyAttd createAffiliationInforOfDailyAttd( String wplID, Optional<String> workplaceGroupId ) {
		
		val domain = new AffiliationInforOfDailyAttd();
		
		domain.setWplID(wplID);
		domain.setWorkplaceGroupId(workplaceGroupId);
		
		return domain;
	}
}
