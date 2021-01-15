package nts.uk.ctx.at.request.dom.application.common.adapter.bs;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SyEmployeeImport;

public interface SyEmployeeAdapter {
	
	SyEmployeeImport getPersonInfor(String employeeId);
	
	List<SyEmployeeImport> getPersonInfor(List<String> employeeIds);
	
	public Map<String, String> getListEmpInfo(String companyID , GeneralDate referenceDate, List<String> workplaceID);
}
