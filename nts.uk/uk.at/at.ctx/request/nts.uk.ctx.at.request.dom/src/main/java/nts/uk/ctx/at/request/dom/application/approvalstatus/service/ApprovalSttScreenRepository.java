package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.WkpEmpMail;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public interface ApprovalSttScreenRepository {
	
	public List<EmpPeriod> getCountEmp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnApprApp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnApprDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnConfirmDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnApprMonth(GeneralDate endDate, YearMonth yearMonth, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnConfirmMonth(GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Pair<String, GeneralDate>> getCountWorkConfirm(ClosureId closureId, YearMonth yearMonth, String companyID, List<String> wkpIDLst);
	
	public List<EmpPeriod> getMailCountUnApprApp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<WkpEmpMail> getMailCountUnConfirmDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<WkpEmpMail> getMailCountUnApprDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<WkpEmpMail> getMailCountUnConfirmMonth(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<WkpEmpMail> getMailCountUnApprMonth(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<WkpEmpMail> getMailCountWorkConfirm(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
}
