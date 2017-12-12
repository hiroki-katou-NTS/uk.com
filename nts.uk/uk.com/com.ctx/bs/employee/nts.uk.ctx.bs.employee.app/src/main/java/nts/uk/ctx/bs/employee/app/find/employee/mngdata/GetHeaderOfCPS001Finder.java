package nts.uk.ctx.bs.employee.app.find.employee.mngdata;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistory;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItem;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeInfo;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentInfo;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryItemRepository_v1;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetHeaderOfCPS001Finder {
	@Inject
	private EmployeeDataMngInfoRepository employeeMngRepo;

	@Inject
	private AffDepartmentHistoryRepository departmentRepo;

	@Inject
	private AffDepartmentHistoryItemRepository historyItemRepo;

	@Inject
	private AffJobTitleHistoryItemRepository_v1 jobTitleHisRepo;

	@Inject
	private JobTitleInfoRepository jobTitleInfoRepo;

	@Inject
	private EmploymentHistoryItemRepository employmentHisItemRepo;

	@Inject
	private EmployeeRepository employeeRepo;

	@Inject
	private TempAbsHistRepository tempHistRepo;
	

	public EmployeeInfo getEmployeeInfo(String sid) {
		String companyId = AppContexts.user().companyId();
		GeneralDate date = GeneralDate.today();
		
		Optional<EmployeeInfo> empInfo = this.employeeMngRepo.findById(sid);
		Optional<Employee> empFull = this.employeeRepo.findByEmployeeID(companyId, sid, date);
		Optional<TempAbsenceHistory> tempHist = this.tempHistRepo.getByEmployeeId(sid);
		if (empFull.isPresent() && tempHist.isPresent()) {
			empFull.get().setTemporaryAbsenceHistory(tempHist.get());
			empInfo.get().setNumberOfWork(empFull.get().getDaysOfEntire() - empFull.get().getDaysOfTemporaryAbsence());

		}
		Optional<AffJobTitleHistoryItem> jobTitleHisItem = this.jobTitleHisRepo.getByEmpIdAndReferDate(sid, date);

		Optional<EmploymentInfo> emp = this.employmentHisItemRepo.getDetailEmploymentHistoryItem(companyId, sid, date);
		if (empInfo.isPresent()) {
			Optional<AffDepartmentHistory> department = this.departmentRepo.getAffDeptHistByEmpHistStandDate(sid, date);

			if (department.isPresent()) {
				String historyId = department.get().getHistoryItems().get(0).identifier();
				Optional<AffDepartmentHistoryItem> historyItem = this.historyItemRepo.getByHistId(historyId);
				if (historyItem.isPresent()) {
					Optional<EmployeeInfo> departmentInfo = this.employeeMngRepo
							.getDepartment(historyItem.get().getDepartmentId(), date);
					if (departmentInfo.isPresent()) {
						empInfo.get().setDepartmentName(departmentInfo.get().getDepartmentName());
					}
				} else {
					empInfo.get().setDepartmentName(" ");

				}
			}

			if (jobTitleHisItem.isPresent()) {
				Optional<JobTitleInfo> jobInfo = this.jobTitleInfoRepo.find(jobTitleHisItem.get().getJobTitleId(), date);
				if (jobInfo.isPresent()) {

					empInfo.get().setPosition(jobInfo.get().getJobTitleName().toString());
				}

			} else {
				empInfo.get().setPosition(" ");
			}

			if (emp.isPresent()) {

				empInfo.get().setContractCodeType(emp.get().getEmploymentName());
			} else {

				empInfo.get().setContractCodeType(" ");
			}
			return empInfo.get();
		}	

		return new EmployeeInfo();

	}

}
