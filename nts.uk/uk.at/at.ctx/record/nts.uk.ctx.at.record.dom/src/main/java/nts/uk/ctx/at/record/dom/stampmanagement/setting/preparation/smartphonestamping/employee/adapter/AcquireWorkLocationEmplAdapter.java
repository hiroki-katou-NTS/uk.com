package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.adapter;

import nts.arc.time.GeneralDate;

/**
 * 社員が所属している職場を取得するAdapter
 */
public interface AcquireWorkLocationEmplAdapter  {
	
	public String getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date);
}
