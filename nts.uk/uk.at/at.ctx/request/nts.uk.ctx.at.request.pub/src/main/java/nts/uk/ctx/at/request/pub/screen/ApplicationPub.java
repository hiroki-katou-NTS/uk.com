package nts.uk.ctx.at.request.pub.screen;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface ApplicationPub {
	
	/**
	 * requestList #26
	 * getApplicationBySID 
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @return list<ApplicationExport>
	 */
	public List<ApplicationExport> getApplicationBySID(List<String> employeeID, GeneralDate startDate, GeneralDate endDate);
}
