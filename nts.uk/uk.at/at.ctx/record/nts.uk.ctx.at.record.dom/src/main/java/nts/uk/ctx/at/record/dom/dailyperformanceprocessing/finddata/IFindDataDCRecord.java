package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.finddata;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.application.ApplicationRecordImport;
import nts.uk.ctx.at.record.dom.adapter.company.StatusOfEmployeeExport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootOfEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.EmployeeDateErrorOuput;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.ClosurePeriod;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.arc.time.calendar.period.DatePeriod;

public interface IFindDataDCRecord {

	Optional<IdentityProcessUseSet> findIdentityByKey(String companyId);
	
	Optional<ApprovalProcessingUseSetting> findApprovalByCompanyId(String companyId);
	
	List<StatusOfEmployeeExport> getListAffComHistByListSidAndPeriod(Optional<String> keyRandom, List<String> sid, DatePeriod datePeriod);
	
	List<ClosurePeriod> fromPeriod(String employeeId, GeneralDate criteriaDate, DatePeriod period);
	
	List<Identification> findByEmployeeID(String employeeID,GeneralDate startDate,GeneralDate endDate);
	
	List<ApproveRootStatusForEmpImport> getApprovalByListEmplAndListApprovalRecordDateNew(
			List<GeneralDate> approvalRecordDates, List<String> employeeID, Integer rootType);
	
	ApprovalRootOfEmployeeImport getDailyApprovalStatus(String approverId, List<String> targetEmployeeIds, DatePeriod period);
	
	List<ApplicationRecordImport> getApplicationBySID(List<String> employeeID, GeneralDate startDate, GeneralDate endDate);
	
	List<ConfirmationMonth> findBySomeProperty(List<String> employeeIds, int processYM, int closureDate, boolean isLastDayOfMonth, int closureId);
	
	List<EmployeeDateErrorOuput> checkEmployeeErrorOnProcessingDate(String employeeId, GeneralDate startDate,
			GeneralDate endDate);
	
	void clearAllStateless();
}
