package nts.uk.ctx.bs.employee.app.find.employee.mngdata;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistory;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItem;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeInfo;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentInfo;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryItem_ver1;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryItemRepository_ver1;
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
	private AffJobTitleHistoryItemRepository_ver1 jobTitleHisRepo;

	@Inject
	private JobTitleInfoRepository jobTitleInfoRepo;

	@Inject
	private EmploymentHistoryItemRepository employmentHisItemRepo;

	@Inject
	AffCompanyHistRepository achFinder;

	@Inject
	AffCompanyInfoRepository aciFinder;

	@Inject
	private TempAbsHistRepository tempHistRepo;

	public EmployeeInfo getEmployeeInfo(String sid) {
		String companyId = AppContexts.user().companyId();
		GeneralDate date = GeneralDate.today();
		String cid = AppContexts.user().companyId();
		Optional<EmployeeInfo> empInfo = this.employeeMngRepo.findById(sid);
		if (empInfo.isPresent()) {
			EmployeeInfo _emp = empInfo.get();

			AffCompanyHist comHist = achFinder.getAffCompanyHistoryOfEmployee(cid, sid);
			Optional<TempAbsenceHistory> tempHist = this.tempHistRepo.getByEmployeeId(cid, sid);

			if (tempHist.isPresent()) {
				_emp.setNumberOfTempHist(tempHist
						.get().items().stream().filter(
								f -> f.start().localDate().compareTo(LocalDate.now()) < 0)
						.map(m -> ChronoUnit.DAYS.between(m.start().localDate(),
								m.end().localDate().compareTo(LocalDate.now()) < 0 ? m.end().localDate()
										: LocalDate.now()))
						.mapToInt(m -> Math.abs(m.intValue())).sum());
			}

			if (comHist != null) {
				AffCompanyHistByEmployee emp = comHist.getAffCompanyHistByEmployee(sid);
				if (emp != null) {
					_emp.setNumberOfWork(emp.getLstAffCompanyHistoryItem().stream()
							.filter(f -> f.start().localDate().compareTo(LocalDate.now()) < 0)
							.map(m -> ChronoUnit.DAYS.between(m.start().localDate(),
									m.end().localDate().compareTo(LocalDate.now()) <= 0 ? m.end().localDate()
											: LocalDate.now()))
							.mapToInt(m -> Math.abs(m.intValue())).sum());

					Optional<AffJobTitleHistoryItem_ver1> jobTitleHisItem = this.jobTitleHisRepo.getByEmpIdAndReferDate(sid,
							date);

					if (jobTitleHisItem.isPresent()) {
						Optional<JobTitleInfo> jobInfo = this.jobTitleInfoRepo
								.find(jobTitleHisItem.get().getJobTitleId(), date);

						if (jobInfo.isPresent()) {
							_emp.setPosition(jobInfo.get().getJobTitleName().toString());
						}
					} else {
						_emp.setPosition(" ");
					}

					Optional<EmploymentInfo> employment = this.employmentHisItemRepo
							.getDetailEmploymentHistoryItem(companyId, sid, date);

					if (employment.isPresent()) {
						_emp.setContractCodeType(employment.get().getEmploymentName());
					}
				}
			}

			Optional<AffDepartmentHistory> department = this.departmentRepo.getAffDeptHistByEmpHistStandDate(sid, date);

			if (department.isPresent()) {
				String historyId = department.get().getHistoryItems().get(0).identifier();

				Optional<AffDepartmentHistoryItem> historyItem = this.historyItemRepo.getByHistId(historyId);
				if (historyItem.isPresent()) {
					Optional<EmployeeInfo> departmentInfo = this.employeeMngRepo
							.getDepartment(historyItem.get().getDepartmentId(), date);

					if (departmentInfo.isPresent()) {
						EmployeeInfo _dept = departmentInfo.get();
						_emp.setDepartmentCode(_dept.getDepartmentCode());
						_emp.setDepartmentName(_dept.getDepartmentName());
					} else {
						_emp.setDepartmentCode(" ");
						_emp.setDepartmentName(" ");
					}
				}
			}

			return _emp;
		}

		return new EmployeeInfo();
	}
}
