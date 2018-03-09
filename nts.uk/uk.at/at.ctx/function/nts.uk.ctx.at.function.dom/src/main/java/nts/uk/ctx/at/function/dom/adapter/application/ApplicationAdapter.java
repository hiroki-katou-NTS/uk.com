package nts.uk.ctx.at.function.dom.adapter.application;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface ApplicationAdapter {
	/**
	 * 
	 * getApplicationBySID 
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @return list<ApplicationImport>
	 */
	public List<ApplicationImport> getApplicationBySID(List<String> employeeID, GeneralDate startDate, GeneralDate endDate);
}
