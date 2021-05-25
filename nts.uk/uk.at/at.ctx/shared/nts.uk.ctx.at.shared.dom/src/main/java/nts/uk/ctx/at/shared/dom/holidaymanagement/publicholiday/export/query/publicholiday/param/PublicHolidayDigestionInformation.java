package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;

/**
 * 
 * @author hayata_maekawa
 *
 *		公休消化情報
 *
 */
@Getter
@Setter
public class PublicHolidayDigestionInformation {
	
	/*
	 * 公休日数
	 */
	public LeaveGrantDayNumber publicHolidayday;
	
	/*
	 * 繰越数
	 */
	public LeaveRemainingDayNumber carryForwardNumber;
	
	/*
	 * 取得数
	 */
	public LeaveUsedDayNumber numberOfAcquisitions;
	
	
	
	public PublicHolidayDigestionInformation(
			LeaveGrantDayNumber publicHolidayday, LeaveRemainingDayNumber carryForwardNumber, LeaveUsedDayNumber numberOfAcquisitions){
		this.publicHolidayday = publicHolidayday;
		this.carryForwardNumber = carryForwardNumber;
		this.numberOfAcquisitions = numberOfAcquisitions;
	}
	
	
}
