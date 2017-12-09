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
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItemRepository_v1;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;

@Stateless
public class EmployeeDataMngInfoFinder_ver1 {
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

	public EmployeeInfo getEmployeeInfo(String sid) {
		Optional<EmployeeInfo> nameInfo = this.employeeMngRepo.findById(sid);
		Optional<AffJobTitleHistoryItem> jobTitleHisItem = this.jobTitleHisRepo.getByEmpIdAndReferDate(sid,
				GeneralDate.today());

		Optional<Employment> emp = this.employmentHisItemRepo.getDetailEmploymentHistoryItem(sid, GeneralDate.today());
		if (nameInfo.isPresent()) {
			Optional<AffDepartmentHistory> department = this.departmentRepo.getAffDeptHistByEmpHistStandDate(sid,
					GeneralDate.today());

			if (department.isPresent()) {
				String historyId = department.get().getHistoryItems().get(0).identifier();
				Optional<AffDepartmentHistoryItem> historyItem = this.historyItemRepo.getByHistId(historyId);
				if (historyItem.isPresent()) {
					Optional<EmployeeInfo> departmentInfo = this.employeeMngRepo
							.getDepartment(historyItem.get().getDepartmentId(), GeneralDate.today());
					if (departmentInfo.isPresent()) {
						nameInfo.get().setDepartmentName(departmentInfo.get().getDepartmentName());
					}
				} else {
					nameInfo.get().setDepartmentName(" ");

				}
			}

			if (jobTitleHisItem.isPresent()) {
				Optional<JobTitleInfo> jobInfo = this.jobTitleInfoRepo.find(jobTitleHisItem.get().getJobTitleId(),
						GeneralDate.today());
				if (jobInfo.isPresent()) {

					nameInfo.get().setPosition(jobInfo.get().getJobTitleName().toString());
				}

			} else {
				nameInfo.get().setPosition(" ");
			}

			if (emp.isPresent()) {

				nameInfo.get().setContractCodeType(emp.get().getEmploymentName().toString());
			} else {

				nameInfo.get().setContractCodeType(" ");
			}
			return nameInfo.get();
		}

		return new EmployeeInfo();

	}

}
