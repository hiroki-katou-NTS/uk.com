package nts.uk.screen.com.app.find.ccg005;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.employmentrole.RoleType;
import nts.uk.ctx.bs.employee.pub.jobtitle.JobTitleExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiry;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiryRepository;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.B：権限の設定.メニュー別OCD.権限の設定を取得
 * 
 */
@Stateless
public class PermissionSettingsScreenQuery {
	
	@Inject
	SyJobTitlePub syJobTitlePub;
	
	@Inject
	SpecifyAuthInquiryRepository specifyAuthInquiryRepository;
	
	@Inject
	RoleRepository roleRepo;
	
	public PermissionSettingDto getPermissionSetting() {
		String cId = AppContexts.user().companyId();
		//	Get List<在席照会で参照できる権限の指定>
		List<SpecifyAuthInquiry> listSpecifyAuthInquiry = this.specifyAuthInquiryRepository.getByCid(cId);
		//	Get List<ロール>
		List<Role> listRole = this.roleRepo.findByType(cId, RoleType.EMPLOYMENT.value);
		// Get List<職位情報>
		List<JobTitleExport> listJobTitle = this.syJobTitlePub.findAll(cId, GeneralDate.today());
		List<RoleDto> lstRoleDto = listRole.stream().map(x -> RoleDto.builder()
				.roleId(x.getRoleId())
				.roleCode(x.getRoleCode().v())
				.roleType(x.getRoleType().value)
				.employeeReferenceRange(x.getEmployeeReferenceRange().value)
				.name(x.getName().v())
				.contractCode(x.getContractCode().v())
				.assignAtr(x.getAssignAtr().value)
				.companyId(x.getCompanyId())
				.build())
				.collect(Collectors.toList());
		
		return PermissionSettingDto.builder()
				.specifyAuthInquiry(listSpecifyAuthInquiry)
				.role(lstRoleDto)
				.jobTitle(listJobTitle)
				.build();
	}
	
}
