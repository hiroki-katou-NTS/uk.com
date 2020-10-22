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
	
	/**
	 * [RQ674]対象の職場が就業確定されているかチェックする
	 * 
	 * @param companyId
	 * @param baseDate
	 * @param workplaceId
	 * @param closureId
	 * @return true : 確定   or false : 未確定
	 */
	public boolean checkWkpConfirmedToWork(String companyId,GeneralDate baseDate,String workplaceId,int closureId);
}
