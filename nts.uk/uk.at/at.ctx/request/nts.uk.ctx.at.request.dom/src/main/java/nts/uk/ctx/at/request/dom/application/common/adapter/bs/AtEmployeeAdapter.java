package nts.uk.ctx.at.request.dom.application.common.adapter.bs;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInforImport;

public interface AtEmployeeAdapter {

	List<String> getListSid(String sId , GeneralDate baseDate);
	/**
	 * Import requestList228
	 * @param employeeIDs
	 * @return
	 */
	List<EmployeeInforImport> getEmployeeInfor(List<String> employeeIDs);
}
