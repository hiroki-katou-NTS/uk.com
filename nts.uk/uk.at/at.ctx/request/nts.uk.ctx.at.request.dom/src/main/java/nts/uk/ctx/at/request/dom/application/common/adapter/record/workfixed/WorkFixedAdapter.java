package nts.uk.ctx.at.request.dom.application.common.adapter.record.workfixed;

import nts.arc.time.GeneralDate;

public interface WorkFixedAdapter {
	/**
	 * requestList147
	 * @param companyID
	 * @param date
	 * @param workPlaceID
	 * @param closureID
	 * @return
	 */
	public boolean getEmploymentFixedStatus(String companyID, GeneralDate date,String workPlaceID, int closureID);
}
