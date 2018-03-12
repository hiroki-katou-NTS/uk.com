package nts.uk.ctx.pereg.app.find.employee;

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
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
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
	private AffJobTitleHistoryItemRepository jobTitleHisRepo;

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

	@Inject
	private PersonInfoItemAuthRepository perItemAuthRepo;

	public EmployeeInfo getEmployeeInfo(String sid) {
		String companyId = AppContexts.user().companyId();
		GeneralDate date = GeneralDate.today();
		String cid = AppContexts.user().companyId();
		Optional<EmployeeInfo> empInfo = this.employeeMngRepo.findById(sid);
		if (empInfo.isPresent()) {
			EmployeeInfo _emp = empInfo.get();

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

			String roleId = AppContexts.user().roles().forPersonalInfo();

			boolean isBirthdayRef = isSelfRef(roleId, "COM1_00000000000000000000000_CS00002",
					"COM1_000000000000000_CS00002_IS00017", AppContexts.user().employeeId().equals(sid));

			if (!isBirthdayRef) {
				_emp.setBirthday(null);
			}

			boolean isJobEntryRef = isSelfRef(roleId, "COM1_00000000000000000000000_CS00003",
					"COM1_000000000000000_CS00003_IS00020", AppContexts.user().employeeId().equals(sid));

			if (!isJobEntryRef) {
				_emp.setNumberOfWork(-1);
				_emp.setNumberOfTempHist(-1);
			} else {
				AffCompanyHist comHist = achFinder.getAffCompanyHistoryOfEmployee(cid, sid);

				if (comHist != null) {
					AffCompanyHistByEmployee emp = comHist.getAffCompanyHistByEmployee(sid);
					if (emp != null) {
						_emp.setNumberOfWork(
								emp.getLstAffCompanyHistoryItem().stream()
										.filter(f -> f.start().localDate().compareTo(LocalDate.now()) < 0)
										.map(m -> ChronoUnit.DAYS.between(m.start().localDate(),
												m.end().localDate().compareTo(LocalDate.now()) <= 0
														? m.end().localDate()
														: LocalDate.now()))
										.mapToInt(m -> Math.abs(m.intValue())).sum());

						Optional<AffJobTitleHistoryItem> jobTitleHisItem = this.jobTitleHisRepo
								.getByEmpIdAndReferDate(sid, date);

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

			}

			return _emp;
		}

		return new EmployeeInfo();
	}

	private boolean isSelfRef(String roleId, String CtgId, String ItemId, boolean self) {
		Optional<PersonInfoItemAuth> itemAuth = this.perItemAuthRepo.getItemDetai(roleId, CtgId, ItemId);

		if (itemAuth.isPresent()) {
			PersonInfoItemAuth auth = itemAuth.get();
			if (self) {
				return auth.getSelfAuth().equals(PersonInfoAuthType.REFERENCE)
						|| auth.getSelfAuth().equals(PersonInfoAuthType.UPDATE);
			} else {
				return auth.getOtherAuth().equals(PersonInfoAuthType.REFERENCE)
						|| auth.getOtherAuth().equals(PersonInfoAuthType.UPDATE);
			}
		} else {
			return false;
		}
	}
}
