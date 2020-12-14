package nts.uk.screen.at.app.approvalstatus;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public interface AppSttScreenRepository {
	public List<EmpPeriod> getCountEmp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnApprApp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnApprDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnConfirmDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnApprMonth(GeneralDate endDate, YearMonth yearMonth, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Integer> getCountUnConfirmMonth(GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, Pair<String, GeneralDate>> getCountWorkConfirm(ClosureId closureId, YearMonth yearMonth, String companyID, List<String> wkpIDLst);
	
	public List<EmpPeriod> getMailCountUnApprApp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, String> getMailCountUnConfirmDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, String> getMailCountUnApprDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, String> getMailCountUnConfirmMonth(GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public Map<String, String> getMailCountUnApprMonth(GeneralDate endDate, YearMonth yearMonth, List<String> wkpIDLst, List<String> employmentCDLst);
	
	public List<String> getMailCountWorkConfirm(GeneralDate startDate, GeneralDate endDate, ClosureId closureId, YearMonth yearMonth, 
			String companyID, List<String> wkpIDLst, List<String> employmentCDLst);
}
