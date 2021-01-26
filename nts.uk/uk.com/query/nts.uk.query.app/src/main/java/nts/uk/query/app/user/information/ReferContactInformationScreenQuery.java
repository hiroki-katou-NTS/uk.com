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
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoDto;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoPub;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.EmployeeInfoContactAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.PersonContactAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInformationUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInformationUseMethodRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.service.ContactInformation;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.service.UserInformationUseMethodService;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.EmployeeInfoContactImport;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.PersonContactImport;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.JobTitleImport;
import nts.uk.ctx.workflow.dom.adapter.workplace.WkpDepInfo;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * UKDesign.UniversalK.共通.CDL_共通ダイアログ.CDL010_連絡先の参照.メニュー別OCD.連絡先情報を取得する
 * @author DungDV
 *
 */
@Stateless
public class ReferContactInformationScreenQuery {
	@Inject
	private SearchEmployeeService employeeService;

	@Inject
	private EmployeeGeneralInfoPub employeeGeneralInfoPub;

	@Inject
	private EmploymentRepository employmentRepository;

	@Inject
	private ClassificationRepository classificationRepository;
	
	@Inject
	private PersonContactAdapter personContactAdapter;
	
	@Inject
	private EmployeeInfoContactAdapter employeeInfoContactAdapter;
	
	@Inject
	private UserInformationUseMethodRepository userInformationUseMethodRepository;
	
	@Inject
	private WorkplaceApproverAdapter workplaceApproverAdapter;
	
	@Inject
	private SyJobTitleAdapter syJobTitleAdapter;

	/**
	 * 
	 * @param employeeId
	 * @param referenceDate
	 * @return ReferContactInformationDto
	 */
	public ReferContactInformationDto getContactInfomation(String employeeId) {
		String cid = AppContexts.user().companyId();
		String sid = employeeId.isEmpty() ? AppContexts.user().employeeId() : employeeId;
		GeneralDate baseDate = GeneralDate.today();
		String personalId = AppContexts.user().personId();
		String employmentCode = "";
		String classificationCode = "";
		String jobTitleCode = "";
		String wkpId = "";
		// step 1: <call> 社員IDから個人社員基本情報を取得
		EmpBasicInfo empBasicInfo = new EmpBasicInfo();

		List<EmpBasicInfo> listEmpInfo = employeeService.getEmpBasicInfo(Arrays.asList(sid));
		if (!listEmpInfo.isEmpty()) {
			empBasicInfo = listEmpInfo.get(0);
			personalId = empBasicInfo.getPId();
		}

		// step 2 : 連絡先情報を取得(Require, 会社ID, 社員ID, 個人ID)
		RequireImpl require = new RequireImpl(personContactAdapter, employeeInfoContactAdapter, userInformationUseMethodRepository);
		ContactInformation contactInfo = UserInformationUseMethodService.get(require, AppContexts.user().companyId(), sid, personalId);
		ContactInformationDTO contactInformation = ContactInformationDTO.builder()
				.companyMobileEmailAddress(contactInfo.getCompanyMobileEmailAddress() != null ? contactInfo.getCompanyMobileEmailAddress().orElse("") : "")
				.personalMobileEmailAddress(contactInfo.getPersonalMobileEmailAddress() != null ? contactInfo.getPersonalMobileEmailAddress().orElse("") : "")
				.personalEmailAddress(contactInfo.getPersonalEmailAddress() != null ? contactInfo.getPersonalEmailAddress().orElse("") : "")
				.companyEmailAddress(contactInfo.getCompanyEmailAddress() != null ? contactInfo.getCompanyEmailAddress().orElse("") : "")
				.otherContactsInfomation(contactInfo.getOtherContactsInfomation())
				.seatDialIn(contactInfo.getSeatDialIn() != null ? contactInfo.getSeatDialIn().v() : "")
				.seatExtensionNumber(contactInfo.getSeatExtensionNumber() != null ? contactInfo.getSeatExtensionNumber().v() : "")
				.emergencyNumber1(contactInfo.getEmergencyNumber1())
				.emergencyNumber2(contactInfo.getEmergencyNumber2())
				.personalMobilePhoneNumber(contactInfo.getPersonalMobilePhoneNumber())
				.companyMobilePhoneNumber(contactInfo.getCompanyMobilePhoneNumber())
				.build();

		// step 3 : <call> 社員ID（List）と期間から個人情報を取得する
		EmployeeGeneralInfoDto employeeGeneralInfoImport = employeeGeneralInfoPub.getPerEmpInfo(Arrays.asList(sid),
				DatePeriod.oneDay(baseDate), true, true, true, true, false);

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
		JobTitleImport jobTitleInfo = syJobTitleAdapter.findJobTitleByPositionId(cid, jobTitleCode, baseDate);

		// step 7 : get*(ログイン会社ID、職場ID) - get domain 職場情報
		if (!employeeGeneralInfoImport.getWorkplaceDto().isEmpty()
				&& !employeeGeneralInfoImport.getWorkplaceDto().get(0).getWorkplaceItems().isEmpty()) {
			wkpId = employeeGeneralInfoImport.getWorkplaceDto().get(0).getWorkplaceItems().get(0)
					.getWorkplaceId();
		}
		Optional<WkpDepInfo> workplaceInfo = this.workplaceApproverAdapter.findByWkpIdNEW(cid, wkpId, baseDate);
		return ReferContactInformationDto.builder()
				.businessName(empBasicInfo.getBusinessName().isEmpty() ? TextResource.localize("CDL010_19") : empBasicInfo.getBusinessName())
				.employmentName(employment.map(mapper -> mapper.getEmploymentName().v()).orElse(TextResource.localize("CDL010_19")))
				.workplaceName(workplaceInfo.map(mapper -> mapper.getName()).orElse(TextResource.localize("CDL010_19")))
				.jobTitleName(jobTitleInfo != null ? jobTitleInfo.getPositionName() : TextResource.localize("CDL010_19"))
				.classificationName(classification.map(mapper -> mapper.getClassificationName().v()).orElse(TextResource.localize("CDL010_19")))
				.contactInformation(contactInformation)
				.build();
	}
	
	@AllArgsConstructor
    private static class RequireImpl implements UserInformationUseMethodService.Require {
		
		@Inject
		private PersonContactAdapter personContactAdapter;
		
		@Inject
		private EmployeeInfoContactAdapter employeeInfoContactAdapter;
		
		@Inject
		private UserInformationUseMethodRepository userInformationUseMethodRepository;
		
		@Override
		public Optional<UserInformationUseMethod> getUserInfoByCid(String cid) {
			return this.userInformationUseMethodRepository.findByCId(cid);
		}

		@Override
		public Optional<PersonContactImport> getByPersonalId(String personalId) {
			return this.personContactAdapter.getPersonalContact(personalId);
		}

		@Override
		public Optional<EmployeeInfoContactImport> getByContactInformation(String employeeId) {
			return this.employeeInfoContactAdapter.get(employeeId);
		}
		
	}
}
