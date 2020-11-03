package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import java.util.List;
import java.util.Map;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;

public interface ApprovalSttScreenRepository {
	
	public String deleteTemporaryTable();
	
	public String setSqlSessionParam(DatePeriod period);
	
	public String setWorkPlaceTempTable(List<String> wkpIDLst);
	
	public String setEmployeeTemp(DatePeriod period, List<String> wkpCDLst);
	
	public Map<String, Integer> getCountEmp();
	
	public void setUnApprApp(DatePeriod period);
	
	public Map<String, Integer> getCountUnApprApp();
	
	public List<EmpPeriod> getEmpFromWkp(String wkpID);
}
