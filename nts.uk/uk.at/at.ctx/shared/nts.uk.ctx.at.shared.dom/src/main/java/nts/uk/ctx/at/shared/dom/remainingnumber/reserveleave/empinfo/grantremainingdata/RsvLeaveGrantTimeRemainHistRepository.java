package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT
 *
 */
public interface RsvLeaveGrantTimeRemainHistRepository {

	public void addOrUpdate(ReserveLeaveGrantTimeRemainHistoryData domain);
	
	public void deleteAfterDate(String employeeId, GeneralDate date);

}
