package nts.uk.query.app.user.information;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
import nts.uk.ctx.bs.employee.dom.employee.service.EmpBasicInfo;
import nts.uk.ctx.bs.employee.dom.employee.service.SearchEmployeeService;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoDto;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoPub;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.PersonContactAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInformationUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.service.ContactInformationDTO;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.service.UserInformationUseMethodService;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.EmployeeInfoContactImport;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.PersonContactImport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ReferContactInformationScreenQuery {
	@Inject
	private SearchEmployeeService employeeService;

	@Inject
	private UserInformationUseMethodService userInfomationUseMethodService;

	@Inject
	private EmployeeGeneralInfoPub employeeGeneralInfoPub;

	@Inject
	private EmploymentRepository employmentRepository;

	@Inject
	private ClassificationRepository classificationRepository;

	@Inject
	private JobTitleInfoRepository jobTitleInfoRepository;

	@Inject
	private WorkplaceInformationRepository wplInfoRepository;
	
	@Inject
	private PersonContactAdapter personContactAdapter;

	public ReferContactInformationDto getContactInfomation(String employeeId, GeneralDate referenceDate) {
		String cid = AppContexts.user().companyId();
		String sid = AppContexts.user().employeeId();
		String personalId = AppContexts.user().personId();
		String employmentCode = "";
		String classificationCode = "";
		String jobTitleCode = "";
		String workplaceCode = "";
		// step 1: <call> 社員IDから個人社員基本情報を取得
		EmpBasicInfo empBasicInfo = new EmpBasicInfo();

		List<EmpBasicInfo> listEmpInfo = employeeService
				.getEmpBasicInfo(Arrays.asList(employeeId.isEmpty() ? sid : employeeId));
		if (!listEmpInfo.isEmpty()) {
			empBasicInfo = listEmpInfo.get(0);
		}

		// step 2 : 連絡先情報を取得(Require, 会社ID, 社員ID, 個人ID)
		UserInformationUseMethodServiceImpl require = new UserInformationUseMethodServiceImpl(personContactAdapter);
		ContactInformationDTO contactInfomation = this.userInfomationUseMethodService.get(require, sid, personalId);

		// step 3 : <call> 社員ID（List）と期間から個人情報を取得する

		EmployeeGeneralInfoDto employeeGeneralInfoImport = employeeGeneralInfoPub.getPerEmpInfo(Arrays.asList(sid),
				DatePeriod.oneDay(referenceDate), true, true, true, true, false);

		// step 4 : get*(ログイン会社ID、雇用コード) - get domain 雇用
		if (!employeeGeneralInfoImport.getEmploymentDto().isEmpty()
				&& !employeeGeneralInfoImport.getEmploymentDto().get(0).getEmploymentItems().isEmpty()) {
			employmentCode = employeeGeneralInfoImport.getEmploymentDto().get(0).getEmploymentItems().get(0)
					.getEmploymentCode();
		}
		Optional<Employment> employment = this.employmentRepository.findEmployment(cid, employmentCode);

		// step 5 : get*(ログイン会社ID、分類コード) - get domain 分類
		if (!employeeGeneralInfoImport.getClassificationDto().isEmpty()
				&& !employeeGeneralInfoImport.getClassificationDto().get(0).getClassificationItems().isEmpty()) {
			classificationCode = employeeGeneralInfoImport.getClassificationDto().get(0).getClassificationItems().get(0)
					.getClassificationCode();
		}
		Optional<Classification> classification = classificationRepository.findClassification(cid, classificationCode);

		// step 6 : get*(ログイン会社ID、職位ID) - get domain 職位情報
		if (!employeeGeneralInfoImport.getJobTitleDto().isEmpty()
				&& !employeeGeneralInfoImport.getJobTitleDto().get(0).getJobTitleItems().isEmpty()) {
			jobTitleCode = employeeGeneralInfoImport.getJobTitleDto().get(0).getJobTitleItems().get(0).getJobTitleId();
		}
		Optional<JobTitleInfo> jobTitleInfo = this.jobTitleInfoRepository.findByJobCode(cid, jobTitleCode);

		// step 7 : get*(ログイン会社ID、職場ID) - get domain 職場情報
		if (!employeeGeneralInfoImport.getWorkplaceDto().isEmpty()
				&& !employeeGeneralInfoImport.getWorkplaceDto().get(0).getWorkplaceItems().isEmpty()) {
			workplaceCode = employeeGeneralInfoImport.getWorkplaceDto().get(0).getWorkplaceItems().get(0)
					.getWorkplaceId();
		}
		List<WorkplaceInformation> workplaceInfo = this.wplInfoRepository.getAllWorkplaceByCompany(cid, workplaceCode);
		return ReferContactInformationDto
				.builder()
				.businessName(empBasicInfo.getBusinessName())
				.employmentName(employment.get().getEmploymentName().v())
				.workplaceName(workplaceInfo.get(0).getWorkplaceName().v())
				.jobTitleName(jobTitleInfo.get().getJobTitleName().v())
				.classificationName(classification.get().getClassificationName().v())
				.contactInformationDTO(contactInfomation)
				.build();
	}
	

	@AllArgsConstructor
    private static class UserInformationUseMethodServiceImpl implements UserInformationUseMethodService.Require {
		
		@Inject
		PersonContactAdapter personContactAdapter;
		
		
		@Override
		public Optional<UserInformationUseMethod> getUserInfoByCid(String cid) {
			return null;
		}

		@Override
		public Optional<PersonContactImport> getByPersonalId(String personalId) {
			return Optional.of(this.personContactAdapter.getListContact(Arrays.asList(personalId)).get(0));
		}

		@Override
		public Optional<EmployeeInfoContactImport> getByContactInformation(String employeeId) {
			return null;
		}
		
	}
}
