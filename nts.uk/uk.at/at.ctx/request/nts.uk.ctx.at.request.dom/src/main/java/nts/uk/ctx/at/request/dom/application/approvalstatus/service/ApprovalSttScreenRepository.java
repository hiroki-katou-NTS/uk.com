package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.WkpEmpMail;

public interface ApprovalSttScreenRepository {
	
	public List<EmpPeriod> getCountEmp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnApprApp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountDayResult(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountMonthResult(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountWorkConfirm(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<EmpPeriod> getMailCountUnApprApp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<WkpEmpMail> getMailCountUnConfirmDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<WkpEmpMail> getMailCountUnApprDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<WkpEmpMail> getMailCountUnConfirmMonth(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<WkpEmpMail> getMailCountUnApprMonth(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<WkpEmpMail> getMailCountWorkConfirm(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
}
