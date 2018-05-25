package nts.uk.ctx.at.record.pub.workrecord.workfixed;

import nts.arc.time.GeneralDate;

public interface WorkFixedPub {
	/**
	 * RequestList147
	 * 対象の職場が就業確定されているかチェックする
	 * @param companyID
	 * @param date
	 * @param workPlaceID
	 * @param closureID
	 * @return true: confirm, false: pending
	 */
	public boolean getEmploymentFixedStatus(String companyID, GeneralDate date,String workPlaceID, int closureID);
}
