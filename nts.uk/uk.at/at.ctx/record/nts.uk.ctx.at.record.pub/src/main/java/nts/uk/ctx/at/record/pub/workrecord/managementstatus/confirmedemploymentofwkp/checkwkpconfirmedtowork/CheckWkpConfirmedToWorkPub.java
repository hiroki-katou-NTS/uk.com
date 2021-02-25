package nts.uk.ctx.at.record.pub.workrecord.managementstatus.confirmedemploymentofwkp.checkwkpconfirmedtowork;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author tutk
 *
 */
public interface CheckWkpConfirmedToWorkPub {
	
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
