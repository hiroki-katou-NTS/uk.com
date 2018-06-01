package nts.uk.ctx.at.function.dom.adapter.application;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.application.importclass.ApplicationDeadlineImport;

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
	
	/**
	 * @param companyID
	 * @param closureID
	 * @return
	 */
	public ApplicationDeadlineImport getApplicationDeadline(String companyID, Integer closureID);
}
